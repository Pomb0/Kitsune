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

<div class="nav">
  <ul>
    <c:forEach items="${topics}" var="item">
    <li><a href="?topic=${item.id}">${item.name}</a></li>
    </c:forEach>
  </ul>
</div>

<div id="news">
  <ul>
    <c:forEach items="${articles}" var="item">
    <a href="article?id=${item.id}">
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
    </a>
    </c:forEach>
  </ul>
</div>
<div class="nav">
  <ul>
<c:if test="${prevPage != 0}"><li><a href="?topic=${topic}&page=${prevPage}">Previous</a></li></c:if>
<c:if test="${nextPage != 0}"><li><a href="?topic=${topic}&page=${nextPage}">Next</a></li></c:if>
  </ul>
</div>


<jsp:include page="layout/bot.jsp" />