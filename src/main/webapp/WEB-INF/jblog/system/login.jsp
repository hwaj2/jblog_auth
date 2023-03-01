<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container mt-3">
    <form action="/auth/securitylogin" method="post">
        <div class="mb-3">
            <label for="username">
                <spring:message code="user.login.form.username"/>
            </label>
            <input type="text" class="form-control" name="username" placeholder="Enter username">
        </div>
        <div class="mb-3">
            <label for="password">
                <spring:message code="user.login.form.password"/>
            </label>
            <input type="password" class="form-control" name="password" placeholder="Enter password">
        </div>

        <button id="btn-login" class="btn btn-secondary">
            <spring:message code="user.login.form.login_btn"/>
        </button>

        <a href="https://kauth.kakao.com/oauth/authorize?client_id=1c2685d26cbbd907d5fb9764e52454af&redirect_uri=http://localhost:8080/oauth/kakao&response_type=code">
            <img height="38px" src="/image/kakao_login.png"/></a>

        <a href="../oauth2/authorization/google">
            <img height="38px" src="/image/google_login.png"/></a>
    </form>

</div>
<%@ include file="../layout/footer.jsp" %>