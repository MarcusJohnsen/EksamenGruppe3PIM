<%-- 
    Document   : newProduct
    Created on : 11-11-2019, 08:55:22
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
        <h1>Create New Product</h1>
        <form action="FrontController" method="POST">
            <p align="center">
                Product Name:
                <br>
                <input type="text" name="Product Name" value="" />
            </p>

            <p align="center">
                Product Description:
                <br>
                <textarea name="Product Description" rows="5" cols="40">
                </textarea>
            </p>

            <p align="center">
                Product Distributors:
                <br>
                <input type="text" name="Product Distributors" value="" />
                <button onclick="newField()">Add New Field</button>
            </p>

            <p align="center">
                Upload Picture:
                

            </p>

            <br>
            <p align="center">
                <input type="submit" value="Upload" />
            </p>
        </form>


        <script>
            function newField() {
                var x = document.createElement("INPUT");
                x.setAttribute("type", "text");
                x.setAttribute("name", "Product Distributors");
                document.body.appendChild(x);
            }
        </script>
    </body>
</html>
