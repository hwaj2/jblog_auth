<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">

    <form>
        <input type="hidden" id="id" value="${principal.user.id }">
        <div class="form-group">
            <label for="username">Username:</label> <input type="text" class="form-control" value="${principal.user.username }" id="username">
        </div>
        <c:if test="${principal.user.oauth == 'BLOG'}">
        <div class="form-group">
            <label for="password">Password:</label> <input type="password" class="form-control" placeholder="Enter password" id="password">
        </div>
        </c:if>
        <div class="form-group">
            <label for="email">Email:</label> <input type="email" class="form-control" value="${principal.user.email }" id="email">
        </div>
    </form>

    <button id="btn-update" class="btn btn-primary">회원수정</button>

</div>

<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>
