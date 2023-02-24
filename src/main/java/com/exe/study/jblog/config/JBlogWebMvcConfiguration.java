package com.exe.study.jblog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

//환경설정 적용하기
@Configuration
public class JBlogWebMvcConfiguration implements WebMvcConfigurer {

    // 다국어처리(2가지의 객체 필요)
    @Bean("messageSource")
    public MessageSource messageSource(){ //messageSource로 시작하는 메세지 파일을 메모리에 로딩하는 객체
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("message/messageSource");
        return messageSource;
    }
    @Bean
    public LocaleResolver localeResolver(){ //브라우저에서 전송한 로케일 정보를 추출하여 세션에 등록, 세션종료될때까지 로케일을 유지
        return new SessionLocaleResolver();
    }


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
