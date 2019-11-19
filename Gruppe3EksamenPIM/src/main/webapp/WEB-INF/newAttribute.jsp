<%-- 
    Document   : newAttribute
    Created on : 18-11-2019, 13:14:28
    Author     : Andreas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Attribute</title>
    </head>
    <body>
        <h1 align="center">New Attribute</h1>
        <br>
        <form action="FrontController">
            <p align="center">
                Attribute name:
                <br>
                <input type="hidden" name="command" value="addAttribute" />
                <input type="text" name="attributeName" value="" /> </p>
            <p align="center"><input type="submit" value="Save" /></p>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="index" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>
    </body>
</html>
