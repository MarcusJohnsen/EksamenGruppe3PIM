<%-- 
    Document   : ViewAllProducts
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
        <form action="FrontController" method="POST"> 
            <input type="hidden" name="command" value="editProduct" /> 
            <table align="center" border = "1" width = "15%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#87E187">
                        <td>ProductID</td>
                        <td>ProductName</td>
                        <td>ProductDescription</td>
                        <td>picturePath</td>
                    </tr>
                </thead>
                
                <tbody>
                    <%
                        ArrayList<Product> productList = (ArrayList<Product>) Product.getProductList();
                        for (Product product : productList) {
                            int ProductID = product.getProductID();
                            String ProductName = product.getName();
                            String ProductDescription = product.getDescription();
                            String picturePath = product.getPicturePath();
                    %>  
                    <tr>
                        <td align="center"><input type="radio" name=productchoice value="<%=ProductID%>"></td>
                        <td align="center"> <%=ProductName%> </td>
                        <td align="center"> <%=ProductDescription%> </td>
                        <td align="center"> <%=picturePath%> </td>
                    </tr>
                </tbody>
                        <%}%>
            </table>
        </form>
            
            <p align="center"><input type="submit" value="Edit product"/></p>
            <p align="center"><input type="submit" value="Delete product"/></p>
    </body>
</html>