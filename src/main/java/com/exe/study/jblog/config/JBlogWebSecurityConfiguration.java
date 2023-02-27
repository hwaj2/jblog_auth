package com.exe.study.jblog.config;

import com.exe.study.jblog.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class JBlogWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 사용자가 입력한 username으로 User객체를 검색하고 password를 비교
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); //로그인시에 암호화된 비밀번호로 인증처리하도록 추가
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //인증없이 접근을 허용하는 경로
        http.authorizeHttpRequests()
                .antMatchers("/webjars/**", "/js/**", "image/**", "/","/auth/**").permitAll();
        //나머지 경로는 인증이 필요
        http.authorizeHttpRequests().anyRequest().authenticated();
        // CSRF 토큰을 받지 않음
        http.csrf().disable();
        // 사용자 정의 로그인 화면 제공
        http.formLogin().loginPage("/auth/login");
        // 로그인 요청 URI를 변경
        http.formLogin().loginProcessingUrl("/auth/securitylogin");
        // 로그아웃
        http.logout().logoutUrl("/auth/logout").logoutSuccessUrl("/");
    }


}
