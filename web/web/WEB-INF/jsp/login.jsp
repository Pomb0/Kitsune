<%--
  Created by IntelliJ IDEA.
  User: Jaime
  Date: 13/11/2014
  Time: 04:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% session.setAttribute("pageTitle"," - Login"); %>
<jsp:include page="layout/top.jsp" />
<div id="basicForm">
  <form name="login" method="post" action="login">
    <p><input name="username" type="text" placeholder="Username" autocomplete="off" required="required" /></p>
    <p><input name="password" type="password" placeholder="Password" autocomplete="off" required="required" /></p>
    <p>
      <input name="submit" class="button" type="submit" value="Sign In" />
      <a class="linkBtn" href="register">Register</a>
    </p>
  </form>
</div>
<h1>${SESSid}</h1>
<jsp:include page="layout/bot.jsp" />