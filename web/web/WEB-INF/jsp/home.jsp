<%--
  Created by IntelliJ IDEA.
  User: Jaime
  Date: 11/11/2014
  Time: 06:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% session.setAttribute("pageTitle",""); %>
<jsp:include page="layout/top.jsp" />
<jsp:include page="layout/menu.jsp" />

<div id="news">
  <ul>
    <li class="articleIcon">
      <div class="atitle">Another North Korean overture as questions remain on Kim's whereabouts, some people say he's dead, some others say he has been raped to death by a squirel with rabies</div>
      <div class="adate">2014-11-13 04:17</div>
      <div class="atopic">World</div>
      <div class="aimage">
        <img src="http://i2.cdn.turner.com/cnn/dam/assets/140917002315-pkg-goodman-spain-catalonia-independence-00001513-story-top.jpg" />
      </div>
    </li>
  </ul>
</div>

<!--<h2>${msg}</h2>
  <ul>
    <li>${isLogged}</li>
    <li>${sessionId}</li>
    <li>${res}</li>
  </ul>-->

<jsp:include page="layout/bot.jsp" />