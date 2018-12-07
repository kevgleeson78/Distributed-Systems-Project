<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<form action="CarServlet" method="POST">
  Order Number:  <input type="text" name="orderNumber" value="<%= (int) (Math.random() * 100000) %>" readonly="readonly"/><br/>
   Name:  <input type="text" name="name" required="required"/><br/>
   Country: <input type ="text" name="country"  required="required"/><br/>
   Street: <input type ="text" name="street" required="required"/><br/>
   City: <input type ="text" name="city" required="required"/><br/>
   Car Model: <input type ="text" name="carModel" required="required"/><br/>
   Quantity: <input type ="number" name="quantity" required="required"/><br/>
   Price: <input type="number" name="price" required="required"/><br/>
   Date: <input type="date" name="date" required="required"/>
    
    <input type="submit" />
</form>
</body>
</html>