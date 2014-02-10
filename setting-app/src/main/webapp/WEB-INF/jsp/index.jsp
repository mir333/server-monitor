<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--@elvariable id="data" type="java.util.List<im.ligas.settings.ServerData>"--%>
<%--@elvariable id="errors" type="java.util.List<im.ligas.settings.ErrorData>"--%>

<html>
<head><title>Rig status</title></head>
<body>
<h1>Rig data</h1>
<c:if test="${error1}">
    <h1>No new updates</h1>
</c:if>
<div style="color: red">
    <c:forEach items="${errors}" var="error">
        <fmt:formatDate pattern="dd.MM. HH:mm:ss" value="${error.date}"/>--<c:out value="${error.message}"/>
        <br/>
    </c:forEach>
    <br/>
</div>
<c:forEach items="${data}" var="serverData">
    <div>
        <div style="float: left;width: 50%">
            <fmt:formatDate pattern="dd.MM. HH:mm:ss" value="${serverData.now}"/>
        </div>
        <div style="float: right;width: 50%">
            <b>remote address</b>: <c:out value="${serverData.remoteAddr}"/>
            <b>local address</b>: <c:out value="${serverData.localAddr}"/>
            <b>up time</b>: <c:out value="${serverData.systemup}"/><br/>
        </div>
        <div>
            <c:forEach items="${serverData.gpu}" var="gpudata">
                <c:out value="${gpudata.id}"/>::<c:out value="${gpudata.temp}"/>::
                <c:out value="${gpudata.hashrate}"/>::<c:out value="${gpudata.pool}"/>
                <br/>
            </c:forEach>
        </div>
    </div>
    <hr/>
</c:forEach>
</body>
</html>
