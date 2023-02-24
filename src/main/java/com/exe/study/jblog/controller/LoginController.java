package com.exe.study.jblog.controller;

import com.exe.study.jblog.domain.User;
import com.exe.study.jblog.dto.ResponseDTO;
import com.exe.study.jblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // 로그인페이지
    @GetMapping("/auth/login")
    public String login(){
        return "system/login";
    }

    // 로그인
    @PostMapping("/auth/login")
    public @ResponseBody ResponseDTO<?> login(@RequestBody User user, HttpSession session){
        User findUser = userService.getUserName(user.getUsername());
        if(findUser.getUsername() == null) {
            System.out.println("해당 아이디가 존재하지 않습니다.");
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "해당 아이디가 존재하지 않습니다.");
        }
        else {
            if(user.getPassword().equals(findUser.getPassword())) {
                // 로그인 성공시 세션에 저장
                System.out.println("로그인성공!");
                session.setAttribute("principal", findUser);
                return new ResponseDTO<>(HttpStatus.OK.value(), findUser.getUsername() + "님이 로그인에 성공하였습니다.");
            } else {
                System.out.println("비밀번호오류!");
                return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "비밀번호가 맞지 않습니다.");
            }
        }
    }


    //로그아웃
    @GetMapping("auth/logout")
    public String logout(HttpSession session){
        session.invalidate();
        System.out.println("로그아웃 되었습니다.");
        return "redirect:/";
    }



 }
