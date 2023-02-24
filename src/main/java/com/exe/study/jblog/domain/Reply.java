package com.exe.study.jblog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;			// 댓글 일련번호

    @Column(nullable = false, length = 200)
    private String content;	// 댓글 내용


    @CreationTimestamp
    private Timestamp createDate;	// 댓글 등록일


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postid")
    private Post post;	// 관련된 포스트


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
    private User user;	// 관련된 유저

}