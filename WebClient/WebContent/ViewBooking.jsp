<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<nav>
<a href="Home.jsp">Home</a>
</nav>
<h1>View Booking</h1>
<h2>Please enter an order number and submit to display the details</h2>
<form method="GET" action="CarServlet">
 <input type="text" name="param1" required="required"/>
  <input type="submit" value="Submit"/>
  
</form>
<c:forEach items="${requested.cars.car}" var="item">
	<h3>Number = ${requested.orderNumber}</h3>
    <h3>Date = ${requested.orderDate}</h3>
   <h3>Name = ${requested.billTo.name}</h3>
   <h3>Street = ${requested.billTo.street}</h3>
      <h3>City = ${requested.billTo.city}</h3>
   <h3>County = ${requested.billTo.county}</h3>
   
   <h3> Car = ${item.carName}</h3>
   <h3> Quantity = ${item.quantity}</h3>
   <h3> Price = ${item.price}</h3>
</c:forEach>
   
</body>
</body>
</html>