<%--
  Created by IntelliJ IDEA.
  User: Jaime
  Date: 13/11/2014
  Time: 04:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% session.setAttribute("pageTitle","- Register"); %>
<jsp:include page="layout/top.jsp" />
<div id="basicForm">
  <form name="register" method="post" action="register">
    <p><input name="name" type="text" placeholder="Name" autocomplete="off" /></p>
    <p><input name="username" type="text" placeholder="Username" autocomplete="off"/></p>
    <p><input name="password" type="password" placeholder="Password" autocomplete="off"/></p>
    <p><input name="passveri" type="password" placeholder="Password" autocomplete="off"/></p>
    <p><input name="mail" type="text" placeholder="Mail" autocomplete="off"/></p>
    <p><input name="submit" class="button" type="submit" value="Register" /> </p>
  </form>
</div>
<jsp:include page="layout/bot.jsp" />