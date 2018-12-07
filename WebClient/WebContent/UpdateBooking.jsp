<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js">
	
</script>
</head>
<body>
	<h1>Update Booking</h1>
	<h3>Please enter a valid order number to update the booking</h3>
	<nav>
		<a href="Home.jsp">Home</a>
	</nav>
	<form id="Get-Details" method="GET" action="UpdateServlet">
	Order Number: <input type="text" name="orderNumber"  required="required"/><br/>
	<input type="submit" value="GET DETAILS" /><br/>
	</form>
	<form id="user-form">				
		Order Number: <input type="text" name="orderNumber" value="${requested.orderNumber}" readonly="readonly"/><br/>
		Name: <input type="text" name="name" id="name" value="${requested.billTo.name}" required="required"/><br/>
		Date: <input type="date" name="date"  value="${requested.car.orderDate}" required="required"/><br/>
		Country: <input type="text" name="country"  value="${requested.billTo.country}" required="required"/><br/>
		Street: <input type="text" name="street"  value="${requested.billTo.street}" required="required"/><br/>
		City: <input type="text" name="city"  value="${requested.billTo.city}"/ required="required"><br/>
		Car Model: <input type="text" name="model"  value="${requested.car.carModel}" required="required"/><br/>
		Quantity: <input type="number" name="quantity"  value="${requested.car.quantity}" required="required"/><br/>
		Price: <input type="number" name="price"  value="${requested.car.price}" required="required"/><br/>
		<input type="submit" value="UPDATE DETAILS" /><br/>
	</form>

	<script>
		var form = $('#user-form');
		var name = jQuery('input[name="name"]').val();
		form.submit(function() {
			$.ajax({
				type : "PUT",
				dataType: "application/xml",
				url : "UpdateServlet",
				data : form.serialize(),
				success : function(data) {
					console.log(data);
				}
			});
		});
	</script>
</body>
</html>