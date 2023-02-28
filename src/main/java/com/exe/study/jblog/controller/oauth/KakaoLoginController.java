package com.exe.study.jblog.controller.oauth;

import com.exe.study.jblog.domain.User;
import com.exe.study.jblog.service.UserService;
import com.exe.study.jblog.service.kakao.KakaoLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KakaoLoginController {

    @Value("${kakao.default.password}")
    private String kakaoPassword;

    @Autowired
    private KakaoLoginService kakaoLoginService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/oauth/kakao")
    public String kakaoCallback(String code){
        // 1. 인가코드를 통해 엑세스 토큰 발급받기
        String accessToken = kakaoLoginService.getAccessToken(code);

        // 2. 엑세스 토큰을 이용하여 사용자 정보 얻기
        User kakaoUser = kakaoLoginService.getUserInfo(accessToken);

        // 3. 기존회원이 아니면 신규회원으로 등록
        User findUser = userService.getUserName(kakaoUser.getUsername());

        if(findUser.getUsername() == null){
            userService.insertUser(kakaoUser);
        }

        // 4. 카카오로 받은 사용자 정보를 기반으로 인증을 처리
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), kakaoPassword);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/";


    }




}
