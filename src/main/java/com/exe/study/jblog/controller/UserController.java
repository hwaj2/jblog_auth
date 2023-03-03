package com.exe.study.jblog.controller;

import com.exe.study.jblog.constant.OAuthType;
import com.exe.study.jblog.domain.User;
import com.exe.study.jblog.dto.ResponseDTO;
import com.exe.study.jblog.dto.UserDTO;
import com.exe.study.jblog.security.UserDetailsImpl;
import com.exe.study.jblog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${kakao.default.password}")
    private String kakaoPassword;
    @Value("${google.default.password}")
    private String GooglePassword;

    // 로그인
    @GetMapping("/auth/login")
    public String login(){
        return "system/login";
    }

    // 회원가입 페이지
    @GetMapping("/auth/insertUser")
    public String insertUser(){
        return "user/insertUser";
    }


    // 회원가입
    @PostMapping("/auth/insertUser")
    public @ResponseBody ResponseDTO<?> insertUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult){
    /*
        @Valid 어노테이션에 의해 유효성검사 작동, 그 결과가 자동으로 BindingResult객체에 저장
        반드시!! BindingResult변수는 반드시 @Valid 다음에 위치해야한다
    */
        User user = modelMapper.map(userDTO, User.class); // * dto > entity로 변경
        User findUser =  userService.getUserName(user.getUsername());

        if(findUser.getUsername() == null){
            userService.insertUser(user);
            return new ResponseDTO<>(HttpStatus.OK.value(), user.getUsername() + "님의 회원가입이 정상적으로 완료되었습니다.");
        }else{
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), user.getUsername() + "님은 이미 회원입니다.");
        }
    }


    // 회원 조회(페이지없음)
    @GetMapping("/user/{id}")
    public @ResponseBody User getUser(@PathVariable int id){
        User findUser =  userService.getUser(id);
        return findUser;
    }

    // 회원 정보수정 페이지
    @GetMapping("/user/updateUser")
    public String updateUser(){
        return "user/updateUser";
    }

    // 회원 정보수정
    @PutMapping("/user")
    public @ResponseBody ResponseDTO<?> updateUser(@RequestBody User user, @AuthenticationPrincipal UserDetailsImpl principal){

        // 수정전에 카카오,구글 회원인지 확인후 비밀번호 고정하기
        if(principal.getUser().getOauth().equals(OAuthType.KAKAO)){
            user.setPassword(kakaoPassword);
        }else if(principal.getUser().getOauth().equals(OAuthType.GOOGLE)){
            user.setPassword(GooglePassword);
        }

        principal.setUser(userService.updateUser(user)); //회원정보 수정 + *세션갱신
        return new ResponseDTO<>(HttpStatus.OK.value(), user.getUsername() + "수정이 완료되었습니다.");
    }




}


