package com.exe.study.jblog.security;

import com.exe.study.jblog.domain.User;
import com.exe.study.jblog.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // password에 대한 검증은 시큐리티가 자동으로 처리한다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username).orElseThrow(()->{
            return new UsernameNotFoundException(username +
                    " 사용자가 없습니다.");
        });

        return new UserDetailsImpl(principal);
    }
}
