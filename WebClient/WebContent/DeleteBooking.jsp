<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>User List</title>
</head>
<body>
   
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
   

   <form method="POST">
   	
   	<input type="submit" value="buttonname" /></input>
   </form>
</body>
</html>