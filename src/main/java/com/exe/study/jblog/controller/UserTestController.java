package com.exe.study.jblog.controller;

import com.exe.study.jblog.domain.RoleType;
import com.exe.study.jblog.domain.User;
import com.exe.study.jblog.exception.JBlogException;
import com.exe.study.jblog.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// POSTMAN을 이용해서 회원 테스트 진행(웹페이지없음)
@RequestMapping("/postman")
@Controller
public class UserTestController {

    @Autowired
    private UserRepository userRepository;

    //회원등록
    @PostMapping("/user")
    public @ResponseBody String insertUser(@RequestBody User user) {
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return user.getUsername() + " 회원님의 가입이 성공적으로 완료 되었습니다.";
    }

    //회원단건조회
    @GetMapping("/user/get/{id}")
    public @ResponseBody User getUser(@PathVariable int id) {
        User findUser = userRepository.findById(id).orElseThrow(() -> {
            return new JBlogException(id + " 번 회원이 존재하지 않습니다.");
        });
        return findUser;
    }

    //회원목록조회
    @GetMapping("/user/list")
    public @ResponseBody List<User> getUserList(){
        return userRepository.findAll();
    }

    //회원수정
    @Transactional //원래는 서비스에서 사용해야함
    @PutMapping("/user")
    public @ResponseBody String updateUser(@RequestBody User user){
        User findUser = userRepository.findById(user.getId()).orElseThrow(()->{
            return new JBlogException(user.getId() + " 번 회원이 존재하지 않습니다.");
        });
        findUser.setUsername(user.getUsername());
        findUser.setPassword(user.getPassword());
        findUser.setEmail(user.getEmail());
        // userRepository.save(findUser); @Transactional이용시에 jpa에서 변경감지 기능을 통해 자동으로 update진행
        return "회원 수정 성공";
    }

    //회원삭제
    @DeleteMapping("/user/{id}")
    public @ResponseBody String deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
        return "회원삭제 성공!!";
    }

    //목록 페이징처리(@PageDefault사용)
    @GetMapping("/user/page")
    public @ResponseBody Page<User> getUserListPaging2(
            @PageableDefault(page = 0, size = 2, direction = Sort.Direction.DESC, sort = {"id","username"}) Pageable pageable){ // 페이지번호, 출력할 데이터의 개수, 정렬에 관한 정보
        // 첫번째 페이지(0)에 해당하는 2개의 데이터 조회
        // id 내림차순 정렬
        // 만약, page와 size정보를 동적으로 변경하고 싶다면, /user/page?page=0&size=5
        return userRepository.findAll(pageable);
        }

    }


