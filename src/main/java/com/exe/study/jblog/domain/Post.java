package com.exe.study.jblog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    // summernote를 적용하면 다량의 <html> 태그들이 포함된다.
    @Lob
    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    private Timestamp createDate;

    private int cnt;

    // 도메인간의 연관관계 설정(데이터 베이스의 FK라고 이해)
    // post관점에서 회원과의 관계는 N : 1
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
    private User user;

    // post관점에서 댓글과의 관계는 1 : N
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE) //Post삭제시 Reply도 같이 삭제되어짐
    @OrderBy("id desc")
    private List<Reply> replyList;


}
