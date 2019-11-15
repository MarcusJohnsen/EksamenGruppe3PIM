<%-- 
    Document   : viewAllProducts
    Created on : 11-Nov-2019, 14:06:02
    Author     : Marcus
--%>

<%@page import="businessLogic.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products view</title>
    </head>
    <body>

        <h2 align="center">Products list</h2>

        <br><br>

        <form action="FrontController" method="POST"> 
            <input type="hidden" name="command" value="selectProduct" />
            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <td align="center">ID</td>
                        <td align="center">Name</td>
                        <td align="center">Description</td>
                    </tr>
                </thead>

                <tbody>
                    <%
                        ArrayList<Product> productList = (ArrayList<Product>) request.getAttribute("productList");
                        for (Product product : productList) {
                            int ProductID = product.getProductID();
                            String ProductName = product.getName();
                            String ProductDescription = product.getDescription();
                    %>  
                    <tr>
                        <td align="center" width="3%"> <%=ProductID%> </td>
                        <td align="center" width="20%"> <%=ProductName%> </td>
                        <td align="center" width="30%"> <%=ProductDescription%> </td>
                        <td align="center" width="1%"><input type="radio" name=productChoice value="<%=ProductID%>"></td>
                    </tr>
                </tbody>
                <%}%>
            </table>

            <br><br>

            <p align="center"><input type="submit" name="submitButton" value="Edit Product"/></p>
            <p align="center"><input type="submit" name="submitButton" value="Delete Product"/></p>
        </form>
        <%
                String error = (String) request.getAttribute("error");
                if (error != null) {%>
        <h2 style="color: red" align="center"><%=error%></h2>
        <%}%>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="index" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>

    </body>
</html>