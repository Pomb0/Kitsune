<%--
  Created by IntelliJ IDEA.
  User: Jaime
  Date: 17/11/2014
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% session.setAttribute("pageTitle",""); %>
<jsp:include page="layout/top.jsp" />
<jsp:include page="layout/menu.jsp" />

<!--

private int id;
private String url;
private String title;
private Date date;
private String body;
private String topic;
private String thumb;
private List<String> highlights;
private List<Author> authors;
private List<String> images;
private List<String> videos;
-->

<h1></h1>
<div id="news" class="center">
  <span class="inline left">
    <h1>${article.title}</h1>
    <ul>
      <li><b>Url:</b> <a href="${article.url}">${article.url}</a></li>
      <li><b>Topic:</b> ${article.topic}</li>
      <li><b>Author:</b><br />
        <ul>
          <c:forEach items="${article.authors}" var="item">
            <li><a href="author?id=${item.id}">${item.name}</a></li>
          </c:forEach>
        </ul>
      </li>
      <li><b>Date:</b> ${article.date}</li>
      <li><b>Highlights:</b><br />
        <ul>
          <c:forEach items="${article.highlights}" var="item">
          <li>${item}</li>
          </c:forEach>
        </ul>
      </li>
      <li><b>Body:</b>
        <ul>
          <li>${article.body}</li>
        </ul>
      </li>

      <li><b>Videos:</b><br />
        <ul>
          <c:forEach items="${article.videos}" var="item">
            <video width="320" height="240" controls>
              <source src="${item}" type="video/mp4">
              Your browser does not support the video tag.
            </video>
          </c:forEach>
        </ul>
      </li>

      <li><b>Images:</b><br />
        <ul>
          <c:forEach items="${article.images}" var="item">
            <li><img src="${item}" alt=""></li>
          </c:forEach>
        </ul>
      </li>

    </ul>
  </span>
</div>


<jsp:include page="layout/bot.jsp" />
