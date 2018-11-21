<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Hello</title>
</head>
<body>
<h2>Hi There!</h2>
<br>
<h3>Date=<%= new Date() %>
</h3><a href="CarServlet?param1=Kevin">Home</a>
<form method="GET" action="CarServlet">
 <input type="text" name="param1"/>
  <input type="submit" value="Submit"/>
  
</form>
</body>
</html>
