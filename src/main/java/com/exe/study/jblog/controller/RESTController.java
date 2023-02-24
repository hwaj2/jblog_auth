package com.exe.study.jblog.controller;


import com.exe.study.jblog.domain.User;
import org.springframework.web.bind.annotation.*;

@RestController   // 반환객체를 자동으로 JSON타입으로 변환
public class RESTController {
    // POST맨 사용시, http 요청 데이터의 타입에 따라서 MIME타입을 변경하여 테스트를 진행


    // GET : SELECT(조회)
    @GetMapping("/jblog")
    public User httpGet(){
        User findUser = User.builder()
                .id(1)
                .username("dolldori")
                .password("1234")
                .email("dolldori@kakao.com")
                .build();
        return findUser; // User객체가 자동으로 JSON형태로 변환
    }


    // POST : INSERT(등록)
    // JSON 형태로 값 전달 : 사용자가 전달한 정보를 JSON형태로 변환하기위해서는 @RequestBody를 사용
    @PostMapping("/jblog")
    public String httpPost(@RequestBody User user){
        return "POST 요청처리 입력값은  " + user.getUsername() + "," + user.getEmail() + "입니다.";
    }


    // PUT : UPDATE(수정)
    @PutMapping("/jblog")
    public String httpPut(@RequestBody User user){
        return "PUT 요청처리 수정값은 " + user.toString();
    }

    // DELETE : DELETE(삭제)
    @DeleteMapping("/jblog")
    public String httpDelete(@RequestParam int id){
        return "DELETE 요청처리 삭제값은 " + id;
    }







}
