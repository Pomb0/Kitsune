<%--
  Created by IntelliJ IDEA.
  User: Jaime
  Date: 11/11/2014
  Time: 06:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% session.setAttribute("pageTitle","Kitsune"); %>
<jsp:include page="layout/top.jsp" />
  <h2>${msg}</h2>
  <ul>
    <li>${isLogged}</li>
    <li>${sessionId}</li>
    <li>${res}</li>
  </ul>

<jsp:include page="layout/bot.jsp" />