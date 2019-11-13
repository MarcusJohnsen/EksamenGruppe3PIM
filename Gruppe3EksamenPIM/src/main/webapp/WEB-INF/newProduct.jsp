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
        <h1 align="center">Create New Product</h1>
        <!-- enctype="multipart/form-data" -->
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="addProduct" />
            <p align="center">
                Product Name:
                <br>
                <input type="text" name="Product Name" value="" />
            </p>

            <p align="center">
                Product Description:
                <br>
                <textarea name="Product Description" rows="8" cols="40"></textarea>
            </p>

            <p align="center">
                Product Distributors: <img onclick="newField()" src="decorations/addPage.png" width="20" height="25" alt="addIcon"/>
                <br>
                <input type="text" name="Product Distributors" value=""/>
            </p>

            <div id="myDIV" align="center"> 
            </div>

            <br>
            <p align="center">
                Select Picture:
                <input type = "file" name = "file" size = "50" />
            </p>

            <br>
            <p align="center">
                Save product:
                <input type="submit" value="Save" />
            </p>
        </form>


        <script>
            function newField() {
                var x = document.createElement("INPUT");
                var br = document.createElement('br');
                var br2 = document.createElement('br');
                x.setAttribute("type", "text");
                x.setAttribute("name", "Product Distributors");
                document.getElementById("myDIV").appendChild(x);
                document.getElementById("myDIV").appendChild(br);
                document.getElementById("myDIV").appendChild(br2);
            }
        </script>
    </body>
</html>
