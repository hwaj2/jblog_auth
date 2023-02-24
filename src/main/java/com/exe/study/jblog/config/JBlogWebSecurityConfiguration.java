package com.exe.study.jblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class JBlogWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

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
    }



}
