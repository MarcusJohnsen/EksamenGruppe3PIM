<%-- 
    Document   : newCategory
    Created on : 13-11-2019, 11:47:28
    Author     : Andreas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Category</title>
    </head>
    <body>
        <h1 align="center">Create New Category</h1>
        <br>
        <form action="FrontController">
            <p align="center">
                Category Name:
                <br>
                <input type="text" name="Category Name" value="" />
            </p>

            <p align="center">
                Category Description:
                <br>
                <textarea name="Category Description" rows="8" cols="40"></textarea>
            </p>

            <input type="hidden" name="command" value="addCategory" />
            <p align="center">
                <input type="submit" value="Save" />
            </p>
        </form>
    </body>
</html>
