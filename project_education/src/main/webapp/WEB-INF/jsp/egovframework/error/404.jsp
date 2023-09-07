<%-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ --%>
<%-- 404 ERROR 를 표시하는 화면이다.                               			--%>
<%--                                                                       	--%>
<%-- @author                                                               	--%>
<%-- @version 1.0 2017/03/07                                               	--%>
<%-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ --%>
<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html lang="ko">
<head>
</head>
<body>

    <!--  
    -->
    <header>
        <a href="${pageContext.request.contextPath}/main.do"></a>
    </header>
    <section>
    <div class="error-msg">
        <img src="${pageContext.request.contextPath}/images/ico_error.png" alt="존재하지 않는 경로">
        <p>해당 페이지를 찾을 수 없습니다.</p>
    </div>
    <a class="back-btn-a" href="#void" onclick="history.back()">돌아가기</a>
    </section>
    

</body>
</html>


