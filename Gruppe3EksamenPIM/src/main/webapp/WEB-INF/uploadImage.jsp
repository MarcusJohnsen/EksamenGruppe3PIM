<%-- 
    Document   : uploadImage
    Created on : 11-11-2019, 18:55:31
    Author     : Michael N. Korsgaard
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="UploadServlet" method="POST" enctype="multipart/form-data">
            <h1 align="center">Upload picture</h1>
            <p align="center">
                Select Picture:
                <input type = "file" name = "file" size = "50" />
                Save:
                <input type="submit" value="Save" />
            </p>
        </form>
    </body>
</html>