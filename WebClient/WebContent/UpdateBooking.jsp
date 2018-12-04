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
    <table>
        <tr>
            <td> User id:</td>
            <td><input type="text" name="orderNumber" id="orderNumber"/></td>
            <td><input type="text" name="name" id="name"/></td>
        </tr>
       
    </table>
    
    <input type="submit" value="Submit"/>
</form>



<script>

var form = $('#user-form');

form.submit(function()
{
    $.ajax({
        url: 'CarServlet',
        data: jQuery('input[name="orderNumber"]').val(),
        type: 'PUT',
        success: function(data){ 
            console.log(data);
        }
            });   
       });
</script>
</body>
</html>