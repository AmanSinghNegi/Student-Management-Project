
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Student Form</title>
    </head>
    <body>
        <form action="StudentControllerServlet" method="get">
            <input type="hidden" name="command" value="ADD" />
            
            <table>
                <tbody>
                    <tr>
                        <td> <label>First Name:</label></td>
                        <td><input type="text" name="fname"></td>
                    </tr>
                    
                    <tr>
                        <td> <label>Last Name:</label></td>
                        <td><input type="text" name="lname"></td>
                    </tr>
                    
                    <tr>
                        <td> <label>Email:</label></td>
                        <td><input type="text" name="uemail"></td>
                    </tr>
                    
                    <tr>
                        <td> <label></label></td>
                        <td><input type="submit" value="Save" class="save"></td>
                    </tr>
                </tbody>
            </table>
            
            <p> 
                <a href="StudentControllerServlet">Back</a>
            </p>
        </form>
    </body>
</html>
