<%-- 
    Document   : EditProduct
    Created on : 12-11-2019, 10:34:52
    Author     : Andreas
--%>

<%@page import="java.util.HashMap"%>
<%@page import="businessLogic.Attribute"%>
<%@page import="java.util.ArrayList"%>
<%@page import="businessLogic.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Product</title>
    </head>
    <body>
        <%
            Product product = (Product) request.getAttribute("product");
            String ProductName = product.getName();
            String ProductDescription = product.getDescription();
            ArrayList<String> ProductDist = product.getDistributors();
            String picturePath = product.getPicturePath();
            int productID = product.getProductID();
            Attribute attribute = (Attribute) request.getAttribute("attribute");
            ArrayList<Attribute> attributes = 
            
        %>
        <h1 align="center">Edit Product Information for product number <%=productID%></h1>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="selectCategoriesForProduct" />
            <input type="hidden" name="productID" value="<%=productID%>" />
            <p align="center"><input type="submit" value="Add Categories to Product" /></p>
        </form>
        <form action="FrontController">
            <input type="hidden" name="productID" value="<%=productID%>" />
            <p align="center">
                Product Name:
                <br>
                <input type="text" name="Product Name" value="<%=ProductName%>"/>
            </p>

            <p align="center">
                Product Description:
                <br>
                <textarea name="Product Description" rows="8" cols="40"><%=ProductDescription%></textarea>
            </p>

            <p align="center">
                Product Distributors: <img onclick="newFieldD()" src="decorations/addPage.png" width="20" height="25" alt="addIcon"/> 
                <br>
                <%for (String productdist : ProductDist) {%>
                <input type="text" name="Product Distributors" value="<%=productdist%>"/>
                <br>
                <br>
                <%}%>
                <input type="text" name="Product Distributors" value=""/>

            </p>
            <div id="myDIVD" align="center"> 
            </div>
            <p align="center">
                Product Attributes <img onclick="newFieldA()" src="decorations/addPage.png" width="20" height="25" alt="addIcon"/> 
                <br>
                <%for (Attribute attributeList : attributes) {%>
                <input type="text" name="Product Attributes" value="<%=attributeList%>"/>
                <br>
                <br>
                <%}%>
                <input type="text" name="Product Attributes" value=""/>

            </p>
            
            <div id="myDIVA" align="center"> 
            </div>

            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <h2 align="center" style="color: red"><%=error%></h2>
            <%}%>

            <!--<p align="center">
                Select Picture:
                <input type = "file" name = "file" size = "50" value="<%=picturePath%>"/>
            </p>
            <br>-->
            <p align="center">
                Save the changes:
                <br>
                <input type="hidden" name="command" value="editProduct" />
                <input type="submit" value="Update"/></p>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllProducts" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>
        <script>
            function newFieldD() {
                var x = document.createElement("INPUT");
                var br = document.createElement('br');
                var br2 = document.createElement('br');
                x.setAttribute("type", "text");
                x.setAttribute("name", "Product Distributors");
                document.getElementById("myDIVD").appendChild(x);
                document.getElementById("myDIVD").appendChild(br);
                document.getElementById("myDIVD").appendChild(br2);
            }
            function newFieldA() {
                var x = document.createElement("INPUT");
                var br = document.createElement('br');
                var br2 = document.createElement('br');
                x.setAttribute("type", "text");
                x.setAttribute("name", "Product Attributes");
                document.getElementById("myDIVA").appendChild(x);
                document.getElementById("myDIVA").appendChild(br);
                document.getElementById("myDIVA").appendChild(br2);
            }
            
        </script>
    </body>
</html>
