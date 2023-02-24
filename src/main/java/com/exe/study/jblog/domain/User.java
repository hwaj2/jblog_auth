package com.exe.study.jblog.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@ToString
@Builder // 빌더패턴 적용
@NoArgsConstructor  // 기본생성자 생성
@AllArgsConstructor // 전체생성자 생성
@Entity //특정 클래스를 엔티티로 설정할때 사용
@Table(name="USERS") // 생략시에는 동일한이름의 테이블 자동매핑(클래스명과 테이블 명이 달라 설정되어짐)
public class User {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //IDENTITY : 자동으로 증가된 값을 할당
    private int id; //회원 일련번호

    @Column(nullable = false, length = 50, unique = true)
    private String username; // 아이디

    @Column(length = 100)
    private String password; // 비밀번호

    @Column(nullable = false, length = 100)
    private String email;    // 이메일

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreationTimestamp //현재 시간이 기본값으로 등록되도록 설정
    private Timestamp createDate;
}
