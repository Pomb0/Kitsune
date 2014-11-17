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
<h1>Profile:</h1>
<div class="center">
  <span class="inline left">
    <ul>
      <li><b>Username:</b> ${puser.username}</li>
      <li><b>Mail:</b> ${puser.mail}</li>
      <li><b>Name:</b> ${puser.name}</li>
      <li><b>IsAdmin:</b> ${puser.admin}</li>
    </ul>
  </span>
</div>

<c:if test="${user.admin}">
  <div id="basicForm">
    <form name="modify" method="post" action="profile">
      <p><input name="name" type="text" placeholder="Name" autocomplete="off" required="required" value="${puser.name}" /></p>
      <p><input name="username" type="text" placeholder="Username" autocomplete="off" required="required" value="${puser.username}" /></p>
      <p><input name="password" type="password" placeholder="Password" autocomplete="off" /></p>
      <p><input name="passveri" type="password" placeholder="Password" autocomplete="off" /></p>
      <p><input name="mail" type="email" placeholder="Mail" autocomplete="off" required="required" value="${puser.mail}" /></p>
      <p>

        <input type="radio" name="isAdmin" value="false" <c:if test="${!puser.admin}">checked</c:if> >User
        <input type="radio" name="isAdmin" value="true" <c:if test="${puser.admin}">checked</c:if> >Admin

      </p>
      <input type="hidden" name="id" value="${puser.id}" />
      <p>
        <input name="submit" class="button" type="submit" value="Save" />
      </p>

    </form>
  </div>
</c:if>


<jsp:include page="layout/bot.jsp" />