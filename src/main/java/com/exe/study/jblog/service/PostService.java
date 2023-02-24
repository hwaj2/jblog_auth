package com.exe.study.jblog.service;

import com.exe.study.jblog.domain.Post;
import com.exe.study.jblog.persistence.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // 포스트 등록
    @Transactional
    public void insertPost(Post post){
        post.setCnt(0);
        postRepository.save(post);
    }

    // 포스트 수정(추후 꼭 수정 필요, 영속성 감지기능으로 인해서 데이터가 바뀌어야하는데 바뀌지 않는경우,,? 뭐지 )
    // *** 내가 헷갈린 이유는 기존에는 엔티티 클래스에서 update메소드로 값을 넣어줬었는데 순간 착각 디버깅으로 값확인 및 이해 완료!!!!
    @Transactional
    public void updatePost(Post post){
        //더티체킹에 의해 UPDATE구문 자동으로 처리(JPA 자동감지 기능)
        //트랜잭성이 종료되는 시점에 값이 변경된 엔티티를 찾아서 해당 엔티티에 대해서 update처리
        Post findPost = postRepository.findById(post.getId()).get();
        findPost.setTitle(post.getTitle());
        findPost.setContent(post.getContent());
    }

    // 포스트 삭제
    @Transactional
    public void deletePost(int id){
        postRepository.deleteById(id);
    }

    // 포스트 목록조회
    public Page<Post> getPostList(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    // 포스트 상세조회
    public Post getPost(int id){
        return postRepository.findById(id).get();
    }

}
