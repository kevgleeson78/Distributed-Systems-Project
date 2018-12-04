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
	<h1>Number = ${requested.orderNumber}</h1>
     
    <h1>Name= ${requested.billTo.name}</h1>
    <h1>Date = ${requested.car.orderDate}</h1>
    <h1>Country = ${requested.billTo.country}</h1>
    <h1>Street = ${requested.billTo.street}</h1>
    <h1>City = ${requested.billTo.city}</h1>
 
    <h1>Model = ${requested.car.carModel}</h1>
    <h1>Quantity = ${requested.car.quantity}</h1>
    <h1>Price = ${requested.car.price}</h1>
    <h1>Order Date = ${requested.car.orderDate}</h1>

   

</body>
</html>