<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js">
	
</script>
</head>
<body>
	<h2>Update Booking</h2>
	<nav>
		<a href="Home.jsp">Home</a>
	</nav>
	<form id="user-form">				
		Order Number: <input type="text" name="orderNumber" /><br/>
		Name: <input type="text" name="name" id="name" /><br/>
		Date: <input type="date" name="date" /><br/>
		Country: <input type="text" name="country" /><br/>
		Street: <input type="text" name="street" /><br/>
		City: <input type="text" name="city" /><br/>
		Car Model: <input type="text" name="model" /><br/>
		Quantity: <input type="number" name="quantity" /><br/>
		Price: <input type="number" name="price" /><br/>
		<input type="submit" value="Submit" /><br/>
	</form>

	<script>
		var form = $('#user-form');
		var name = jQuery('input[name="name"]').val();
		form.submit(function() {
			$.ajax({
				type : "PUT",
				dataType: "application/xml",
				url : "CarServlet",
				data : form.serialize(),
				success : function(data) {
					console.log(data);
				}
			});
		});
	</script>
</body>
</html>