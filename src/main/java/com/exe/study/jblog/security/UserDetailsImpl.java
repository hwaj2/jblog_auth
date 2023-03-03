package com.exe.study.jblog.security;

import com.exe.study.jblog.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter @Setter
public class UserDetailsImpl implements UserDetails, OAuth2User {

    private static final long serialVersionUID= 1L;
    private User user;
    private Map<String, Object> attributes; //구글에서 조회한 사용자 정보를 담을 컬렉션


    // 기본생성자
    public UserDetailsImpl(User user) {
        this.user = user;
    }

    // OAuth 로그인시 사용할 생성자
    public UserDetailsImpl(User user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }


    // OAuth2 - 구글에서 조회한 사용자 정보가 저장된 컬렉션 반환 = 인증서버가 인증클라이언트에게 전달할 리소스 오너의 정보가 저장
    @Override
    public Map<String, Object> getAttributes(){
        return attributes;
    }
    // OAuth2 -이름은 사용하지 않는 정보이므로 null반환
    @Override
    public String getName() {
        return null;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { //계정이 만료되지 않았는지 반환
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //계정이 잠겨있는지 반환
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //비밀번호가 만료되지 않았는지 반환
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정이 활성화되었는지 반환
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //계정이 가지고 있는 권한 목록 저장하여 반환
        // 권한 목록
        Collection<GrantedAuthority> roleList = new ArrayList<>();

        // 권한 목록 설정
        roleList.add(()-> {
            return "ROLE_" + user.getRole();
        });

        return roleList;
    }
}
