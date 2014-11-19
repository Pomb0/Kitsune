<%--
  Created by IntelliJ IDEA.
  User: Jaime
  Date: 18/11/2014
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% session.setAttribute("pageTitle",""); %>
<jsp:include page="layout/top.jsp" />
<jsp:include page="layout/menu.jsp" />


<div id="news">
  <ul>
    <c:forEach items="${authorList}" var="item">
      <a href="author?id=${item.id}">
        <li class="articleIcon userIcon">
          <div class="atitle">${item.name}</div>
        </li>
      </a>
    </c:forEach>
  </ul>
</div>

<jsp:include page="layout/bot.jsp" />