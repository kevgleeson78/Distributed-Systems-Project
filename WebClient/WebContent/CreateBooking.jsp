<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js">
	
</script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<nav>
<a href="Home.jsp">Home</a>
<a href="ViewBooking.jsp">View Booking</a>
<a href="UpdateBooking.jsp">Update Booking</a>	
<a href="DeleteBooking.jsp">Delete Booking</a>
</nav>
 <!-- The below form can be accessed by the servlet via the name of each input within the request using the request.getPerameter("name") -->
<h1>Create a new Booking</h1>
<form action="CarServlet" method="POST">
<!-- Math.Random to generate a random number for the order number -->
   Order Number: <input type="text" name="orderNumber" value="<%= (int) (Math.random() * 100000) %>" readonly="readonly"/><br/>
   Name:  <input type="text" name="name" required="required"/><br/>
   Country: <input type ="text" name="country"  required="required"/><br/>
   Street: <input type ="text" name="street" required="required"/><br/>
   City: <input type ="text" name="city" required="required"/><br/>

   <!-- Adapted from: https://stackoverflow.com/questions/44679332/javascript-change-input-value-when-select-option-is-selected -->
   <!-- Assign both text display and numerical value to retrieval -->
	Car Model: <select id="selectCar"  onchange="myFunction(event)">
					<option disabled selected>Choose a car</option>
    				<option value=150>Ford</option>
   				 	<option value=200>Renault</option>
    				<option value=500>BMW</option>
    				<option value=600>Mercedes</option>
				</select><br/>
		
		<input id="carModel" type ="hidden" name="carModel" required="required"/>
 Price: <input id="priceInput" type="number" name="price" readonly="readonly"><br/>
 Quantity: <input type ="number" name="quantity" required="required"/><br/> 
 Date: <input type="date" name="date" required="required"/>
    
 		<input type="submit" />
</form>
</body>
<script>
 
//Adapted From: https://stackoverflow.com/questions/5913/getting-the-text-from-a-drop-down-box
// When the option is selected the price will update along with the car name
function myFunction(e) {
    document.getElementById("priceInput").value = e.target.value;
    document.getElementById("carModel").value =  $("#selectCar option:selected").text();
   
}
</script>
</html>