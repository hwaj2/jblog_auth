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

    // 스프링 다국어 처리를 위해 사용되는 인터페이스 객체로, 각 언어별로 메세지를 관리할 수있음
    // 메세지파일은 .properties파일 형식으로 작성, 이를 호출하여 사용
    @Bean("messageSource")
    public MessageSource messageSource(){ //messageSource로 시작하는 메세지 파일을 메모리에 로딩하는 객체
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("message/messageSource");
        return messageSource;
    }

    // 사용자의 지역설정을 파악하여 언어를 선택하는 인터페이스객체로, 요청 헤더에서 언어정보를 파악하여 해당언어로 설정
    @Bean
    public LocaleResolver localeResolver(){ //브라우저에서 전송한 로케일 정보를 추출하여 세션에 등록, 세션종료될때까지 로케일을 유지
        return new SessionLocaleResolver();
    }


    // 객체매핑을 쉽게해주는 객체로, dto > entity, entity > dto로 자동매핑해줌
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
