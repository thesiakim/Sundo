<%-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ --%>
<%-- 401 ERROR 를 표시하는 화면이다.                               			--%>
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
        <img src="${pageContext.request.contextPath}/images/ico_error.png" alt="권한이 없을 때">
        <p>해당 페이지에 대한 권한이 없습니다.</p>
    </div>
    <a class="back-btn-a" href="#void" onclick="history.back()">돌아가기</a>
    </section>
    

</body>
</html>


