<%--
  Created by IntelliJ IDEA.
  User: Jaime
  Date: 11/11/2014
  Time: 07:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Kitsune ${pageTitle}</title>
  <link rel="icon" type="image/png" href="src/icon/favicon.png">
  <link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,700' rel='stylesheet' type='text/css' />
  <link href="src/css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="banner">
  <div id="blogo"></div>
  <span id="btext">キツネ</span>
</div>
<c:if test="${notifications != null}">
<div id="notifications">
  <ul>
    <c:forEach items="${notifications}" var="item">
      <li>${item}</li>
    </c:forEach>
  </ul>
</div>
</c:if>