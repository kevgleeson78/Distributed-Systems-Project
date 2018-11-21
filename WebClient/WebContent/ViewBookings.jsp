<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<nav>
<a href="Home.jsp">Home</a>
</nav>
		<c:forEach items="${requested.orderNumber}" var="cars">
			${cars}
		</c:forEach>
     <h1>Order Number = ${requested.orderNumber}</h1>
     
    <h1>Name= ${requested.billTo.name}</h1>
    <h1>Date = ${requested.orderDate}</h1>
    <h1>Country = ${requested.billTo.country}</h1>
    <h1>Street = ${requested.billTo.street}</h1>
    <h1>City = ${requested.billTo.city}</h1>
    <c:forEach items="${requested.car.item}" var="item">
    <h1>Model = ${item.carModel}</h1>
    <h1>Quantity = ${item.quantity}</h1>
    <h1>Price = ${item.price}</h1>
    <h1>Order Date = ${item.orderDate}</h1>
</c:forEach>
   

</body>
</html>