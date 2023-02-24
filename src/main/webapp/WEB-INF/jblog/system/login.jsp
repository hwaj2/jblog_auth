<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container mt-3">
    <form>
        <div class="mb-3">
            <label for="username">
                <spring:message code="user.login.form.username"/>
            </label>
            <input type="text" class="form-control" id="username"
                   placeholder="Enter username">
        </div>
        <div class="mb-3">
            <label for="password">
                <spring:message code="user.login.form.password"/>
            </label>
            <input type="password" class="form-control" id="password"
                   placeholder="Enter password">
        </div>

        <button id="btn-login" class="btn btn-secondary">
            <spring:message code="user.login.form.login_btn"/>
        </button>
    </form>

</div>
<script src="/js/login.js"></script>
<%@ include file="../layout/footer.jsp" %>