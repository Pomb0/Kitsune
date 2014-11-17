<%--
  Created by IntelliJ IDEA.
  User: Jaime
  Date: 11/11/2014
  Time: 06:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% session.setAttribute("pageTitle",""); %>
<jsp:include page="layout/top.jsp" />
<jsp:include page="layout/menu.jsp" />

<div id="news">
  <ul>
    <c:forEach items="${articles}" var="item">
    <li class="articleIcon">
      <div class="atitle">${item.title}</div>
      <div class="adate">${item.date}</div>
      <div class="atopic">${item.topic}</div>
      <c:if test="${item.thumb != null}">
      <div class="aimage">
        <img src="${item.thumb}" />
      </div>
      </c:if>
    </li>
    </c:forEach>
  </ul>
</div>


<jsp:include page="layout/bot.jsp" />