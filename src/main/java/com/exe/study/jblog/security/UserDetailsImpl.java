package com.exe.study.jblog.security;

import com.exe.study.jblog.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter @Setter
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID= 1L;
    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
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
