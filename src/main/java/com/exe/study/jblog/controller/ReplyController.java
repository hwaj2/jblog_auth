package com.exe.study.jblog.controller;

import com.exe.study.jblog.domain.Reply;
import com.exe.study.jblog.domain.User;
import com.exe.study.jblog.dto.ResponseDTO;
import com.exe.study.jblog.persistence.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    // 댓글 등록
    @PostMapping("/reply/{postId}")
    public @ResponseBody ResponseDTO<?> insertReply( //댓글엔티티를 보면서 이해하면서 적용
            @PathVariable int postId, @RequestBody Reply reply, HttpSession httpSession){
        User principal = (User) httpSession.getAttribute("principal"); //세션을 이용해 사용자의 정보 가져오기
        replyService.insertReply(postId, reply, principal);
        return new ResponseDTO<>(HttpStatus.OK.value(), postId + "번 포스트에 대한 댓글이 등록 완료되었습니다.");
    }

    // 댓글 삭제
    @DeleteMapping("/reply/{replyId}")
    public @ResponseBody ResponseDTO<?> deleteReply(@PathVariable int replyId){
        replyService.deleteReply(replyId);
        return new ResponseDTO<>(HttpStatus.OK.value(), replyId + "번 댓글이 삭제되었습니다.");
    }
}
