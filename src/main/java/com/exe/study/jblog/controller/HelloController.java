package com.exe.study.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 컨트롤러를 통한 간접 호출
@Controller
public class HelloController {

    @GetMapping("/html")
    public String html(){
        System.out.println("HTML파일이 요청되었습니다.");
        return "redirect:hello.html";
    }

    @GetMapping("/image")
    public String image(){
        System.out.println("이미지파일이 요청되었습니다.");
        return "redirect:image/jenny.jpeg";
    }

    @GetMapping("/jsp")
    public String jsp(Model model){
        model.addAttribute("message","안녕^_^");
        System.out.println("JSP파일이 요청되었습니다.");
        return "hello";
    }

}
