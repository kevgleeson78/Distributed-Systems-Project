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
</nav>

<form id="user-form">
    <table>
        <tr>
            <td> User id:</td>
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