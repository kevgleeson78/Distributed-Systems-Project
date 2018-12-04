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
  Order Number:  <input type="text" name="orderNumber" /><br/>
   Name:  <input type="text" name="name" /><br/>
   Country: <input type ="text" name="country"/><br/>
   Street: <input type ="text" name="street"/><br/>
   City: <input type ="text" name="city"/><br/>
   Car Model: <input type ="text" name="carModel"/><br/>
   Quantity: <input type ="number" name="quantity"/><br/>
   Price: <input type="number" name="price"/><br/>
   Date: <input type="date" name="date"/>
    
    <input type="submit" />
</form>
</body>
</html>