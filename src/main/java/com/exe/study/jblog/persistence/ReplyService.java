package com.exe.study.jblog.persistence;


import com.exe.study.jblog.domain.Post;
import com.exe.study.jblog.domain.Reply;
import com.exe.study.jblog.domain.User;
import com.exe.study.jblog.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@Transactional(readOnly = true)
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PostRepository postRepository;

    // 댓글등록
    @Transactional
    public void insertReply(int postId, Reply reply, User user){
        Post post = postRepository.findById(postId).get();
        reply.setUser(user);
        reply.setPost(post);
        replyRepository.save(reply);
    }

    // 댓글 삭제
    @Transactional
    public void deleteReply(int replyId){
        replyRepository.deleteById(replyId);
    }

}
