<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script
            src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js">
    </script>
</head>
<body>
<nav>
<a href="Home.jsp">Home</a>
<a href="ViewBooking.jsp">View Booking</a>
<a href="UpdateBooking.jsp">Update Booking</a>	
<a href="CreateBooking.jsp">Create Booking</a>
</nav>
<h1>Delete Booking</h1>
<h3>Please Enter a valid booking number to delete</h3>
<form id="user-form">
    <table>
        <tr>
            <td> User id:</td>
            <td><input type="text" name="name" id="name" required="required"/></td>
        </tr>
       
    </table>
    <input type="submit" value="Submit"/>
</form>



<script>
// In order ot use put or delete methods jquery has to be used as html forms only accept get and post methods
// The belwo code was adapted from: https://stackoverflow.com/questions/2153917/how-to-send-a-put-delete-request-in-jquery
var form = $('#user-form');

form.submit(function()
{
    $.ajax({
        url: 'CarServlet',
        data: jQuery('input[name="name"]').val(),
        type: 'DELETE',
        success: function(data){ 
            console.log(data);
        }
            });   
       });
</script>
</body>
</html>