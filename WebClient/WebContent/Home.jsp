<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Hello</title>
</head>
<body>
<nav>
<a href="CarServlet?param1=1">View Booking</a>
<a href="ViewBooking.jsp">View Bookings</a>
<a href="DeleteBooking.jsp">Delete Booking</a>
<a href="UpdateBooking.jsp">Update Booking</a>
<a href="CreateBooking.jsp">Create Booking</a>
</nav>
<h2>Hi There!</h2>
<br>
<h3>Date=<%= new Date() %>
</h3>
<form method="GET" action="CarServlet">
 <input type="text" name="param1"/>
  <input type="submit" value="Submit"/>
  
</form>
</body>
</html>
