package com.exe.study.jblog.exception;

// 사용자 정의 예외 클래스 생성하기
// 모든예외의 부모클래스인 Exception을 사용해도되지만, RuntimeException을 상속받아 * Unchecked 예외 클래스로 작성
public class JBlogException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public JBlogException(String message) {
        super(message);
    }

}
