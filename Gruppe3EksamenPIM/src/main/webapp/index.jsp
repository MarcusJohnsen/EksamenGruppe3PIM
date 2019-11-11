<%-- 
    Document   : index
    Created on : 11-11-2019, 08:41:54
    Author     : Andreas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PIM System</title>
    </head>
    <body>
        <h1 align="center">Products</h1>
        <form action="FrontController">
            <input type="hidden" name="command" value="newProduct" />
            <p align="center"><input type="submit" value="New Product"/></p>
        </form>
    </body>
</html>
