<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js">
	
</script>
</head>
<body>
	
	<nav>
		<a href="Home.jsp">Home</a>
		<a href="ViewBooking.jsp">View Booking</a>
		<a href="DeleteBooking.jsp">Delete Booking</a>	
		<a href="CreateBooking.jsp">Create Booking</a>
	</nav>
	<h1>Update Booking</h1>
	<h3>Please enter a valid order number to update the booking</h3>
	<form id="Get-Details" method="GET" action="UpdateServlet">
	Order Number: <input type="text" name="orderNumber"  required="required"/><br/>
	<input type="submit" value="GET DETAILS" /><br/>
	</form>
	<form id="user-form">				
		Order Number: <input type="text" name="orderNumber" value="${requested.orderNumber}" readonly="readonly"/><br/>
		
		Name: <input type="text" name="name" id="name" value="${requested.billTo.name}" required="required"/><br/>
		Date: <input type="date" name="date"  value="${requested.orderDate}" required="required"/><br/>
		Country: <input type="text" name="country"  value="${requested.billTo.county}" required="required"/><br/>
		Street: <input type="text" name="street"  value="${requested.billTo.street}" required="required"/><br/>
		City: <input type="text" name="city"  value="${requested.billTo.city}" required="required"><br/>
		   <c:forEach items="${requested.cars.car}" var="item">
		   <h4>Current Car: ${item.carName}</h4>
		   <h4>Current Quantity: ${item.quantity}</h4>
		   <h4>Current Price: ${item.price}</h4>
		   <!-- set default selected from database
		   		Adapted from: https://stackoverflow.com/questions/12008698/set-selected-option-on-existing-select-tag-with-jstl -->
		 <h2>Change car</h2>
   Car Model: <select id="selectCar"  onchange="myFunction(event)">
   					<option disabled selected>Choose a car</option>
    				<option value=150>Ford</option>
   				 	<option value=200>Renault</option>
    				<option value=500>BMW</option>
    				<option value=600>Mercedes</option>
				</select><br/>
   		<input id="carModel" type ="hidden" name="carModel" value="${item.carName}" required="required"/>
 Price: <input id="priceInput"  type="number" name="price" value="${item.price}" readonly="readonly"><br/>
		Quantity: <input type="number" name="quantity" value ="${item.quantity}" required="required"/><br/>
		
</c:forEach>
		
		<input type="submit" value="UPDATE DETAILS" /><br/>
	</form>

	<script>
	//Adapted From: https://stackoverflow.com/questions/5913/getting-the-text-from-a-drop-down-box
	function myFunction(e) {
	    document.getElementById("priceInput").value = e.target.value;
	    document.getElementById("carModel").value =  $("#selectCar option:selected").text();
	   
	}
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