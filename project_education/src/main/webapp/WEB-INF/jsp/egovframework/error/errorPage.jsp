<%-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ --%>
<%-- System Error 를 표시하는 화면이다.                               			--%>
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
        <img src="${pageContext.request.contextPath}/images/ico_error.png" alt="시스템오류">
        <p>오류가 발생 하였습니다.<br/>담당자에게 문의 바랍니다.</p>
        <div style="display:none;">
            ${exception}<br/>
        </div>
    </div>
    <a class="back-btn-a" href="#void" onclick="history.back()">돌아가기</a>
    </section>
    

</body>
</html>
