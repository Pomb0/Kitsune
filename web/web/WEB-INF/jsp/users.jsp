<%--
  Created by IntelliJ IDEA.
  User: Jaime
  Date: 17/11/2014
  Time: 19:02
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% session.setAttribute("pageTitle",""); %>
<jsp:include page="layout/top.jsp" />
<jsp:include page="layout/menu.jsp" />


<div id="news">
  <ul>
    <c:forEach items="${userList}" var="item">
      <li class="articleIcon">
        <a href="profile?id=${item.id}"><div class="atitle">${item.username}</div></a>
      </li>
    </c:forEach>
  </ul>
</div>

<jsp:include page="layout/bot.jsp" />