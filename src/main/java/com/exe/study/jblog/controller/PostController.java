package com.exe.study.jblog.controller;

import com.exe.study.jblog.domain.Post;
import com.exe.study.jblog.domain.User;
import com.exe.study.jblog.dto.PostDTO;
import com.exe.study.jblog.dto.ResponseDTO;
import com.exe.study.jblog.service.PostService;
import com.exe.study.jblog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    // 포스트 목록 조회(index페이지)
    @GetMapping({"","/"})
    public String getPostList(Model model, @PageableDefault(size = 3,sort = "id",
            direction = Sort.Direction.DESC)Pageable pageable){
        model.addAttribute("postList", postService.getPostList(pageable));
        return "index";
    }

    // 포스트 등록페이지
    @GetMapping("/post/insertPost")
    public String insertPost(){
        return "post/insertPost";
    }


    // 포스트 등록
    @PostMapping("/post1") // 등록될때 연관된 회원 엔티티가 할당되야하므로 > 세션으로부터 사용자정보확인
    public @ResponseBody ResponseDTO<?> insertPost1(@RequestBody Post post, HttpSession httpSession){
        User principal = (User) httpSession.getAttribute("principal");
        post.setUser(principal);
        post.setCnt(0);
        postService.insertPost(post);
        return new ResponseDTO<>(HttpStatus.OK.value(), "성공적으로 포스트가 등록되었습니다.");
    }

    // 포스트 등록
    @PostMapping("/post")
    public @ResponseBody ResponseDTO<?> insertPost(@Valid @RequestBody PostDTO postDTO,
                                                   BindingResult bindingResult, HttpSession httpSession){
        Post post = modelMapper.map(postDTO, Post.class); //dto > entity로 변환

        User principal = (User) httpSession.getAttribute("principal");
        post.setUser(principal);
        post.setCnt(0);

        postService.insertPost(post);
        return new ResponseDTO<>(HttpStatus.OK.value(), "성공적으로 포스트가 등록되었습니다.");
    }




    // 포스트 조회
    @GetMapping("/post/{id}")
    public String getPost(@PathVariable int id, Model model){
        model.addAttribute("post", postService.getPost(id));
        return "post/getPost";
    }

    // 포스트 수정페이지
    @GetMapping("/post/updatePost/{id}")
    public String updatePost(@PathVariable int id, Model model){
        model.addAttribute("post", postService.getPost(id));
        return "post/updatePost";
    }

    // 포스트 수정
    @PutMapping("post")
    public @ResponseBody ResponseDTO<?> updatePost(@RequestBody Post post){
        postService.updatePost(post);
        System.out.println("포스트 수정완료!");
        return new ResponseDTO<>(HttpStatus.OK.value(), post.getId() + "번의 포스트가 수정완료 되었습니다.");
    }

    // 포스트 삭제
    @DeleteMapping("post/{id}")
    public @ResponseBody ResponseDTO<?> deletePost(@PathVariable int id){
        postService.deletePost(id);
        return new ResponseDTO<>(HttpStatus.OK.value(), id +"번의 포스트가 삭제완료 되었습니다.");
    }

    // 회원가입 페이지
    @GetMapping("test/auth/insertUser")
    public String insertUser(){
        return "user/insertUser";
    }


    // 회원가입
    @PostMapping("test/auth/insertUser")
    public @ResponseBody ResponseDTO<?> insertUser(@RequestBody User user){ // ? 인이유는 어떤타입의 데이터가 반환될지 특정할수 없기 때문(문자,객체,컬렉션)
        User findUser = userService.getUserName(user.getUsername());
        if(findUser.getUsername() == null){
            userService.insertUser(user);
            return new ResponseDTO<>(HttpStatus.OK.value(), user.getUsername() + "님의 회원가입이 성공적으로 완료되었습니다.");
        }else{
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), user.getUsername() + "님은 이미 존재하는 회원입니다.");
        }
    }


}
