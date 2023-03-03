package com.exe.study.jblog.security;

import com.exe.study.jblog.constant.OAuthType;
import com.exe.study.jblog.constant.RoleType;
import com.exe.study.jblog.domain.User;
import com.exe.study.jblog.persistence.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/*

OAuth2 client 라이브러리
    OAuth 프로토콜을 사용하는(구글,카카오,네이버,페이스북,깃허브등등) 방식을 편리하게 사용할수 있도록 제공해주는 라이브러리로(단,카카오,네이버는 별도 provider생성해야함)
    OAuth2 client 라이브러리가 제공되는 서비스 플랫폼에 한해서 약속된규칙에 의거하여 자동으로 컨드롤러 생성해 정보를 받아와줌
    고정된uri ) 프론트에서 로그인요청시 "/oauth2/authorization/{이름}", 등록 redirect-uri는 "/login/oauth2/code/{이름}"  꼭기억!!
    OAuth2 client를 지원하는 구글/페이스북 : Map<String, Object>형태로 반환
    OAuth2 client를 지원하지않는 네이버/카카오 : json형태로 반환

*/


@Service
public class OAuth2UserDetailsServiceImpl extends DefaultOAuth2UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Value("${google.default.password}")
    private String googlePassword;

    // 1. 사용자가 구글 로그인에 성공하면 구글은 인증클라이언트 쪽으로 CODE를 전달한다.
    // 2. OAuth2Client는 전달된 CODE를 기반으로 AccessToken이 저장된 OAuth2UserRequest를 리턴받는다.
    // 3. OAuth2Client는 OAuth2UserRequest를 인자로 넘기면서 loadUser() 메소드를 호출해준다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        System.out.println(" 구글 api 연동 성공!!!!!!!");

        // AccessToken이 저장된 userRequest를 이용하여 구글로부터 회원 정보를 받아옴
        OAuth2User oauth2User = super.loadUser(userRequest);

        // 구글이 전달해준 정보를 바탕으로 저장할 회원정보를 생성
        String providerId = oauth2User.getAttribute("sub");
        String email = oauth2User.getAttribute("email");
        String username = email + "_" + providerId;
        String password = passwordEncoder.encode(googlePassword);

        // 회원가입이 되어있는 사용자인지 확인
        User findUser = userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });

        if(findUser.getUsername() == null) {
            // 신규 가입 처리
            findUser = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(RoleType.USER)
                    .oauth(OAuthType.GOOGLE)
                    .build();
            userRepository.save(findUser);
        }

        // OAuth2Client가 자동으로 리턴된 객체를 기반으로 세션을 갱신
        // UserDetailsImp에 설정된 정보로 Authentication객체를 SecurityContext에 자동으로 등록
        return new UserDetailsImpl(findUser, oauth2User.getAttributes());
    }

}
