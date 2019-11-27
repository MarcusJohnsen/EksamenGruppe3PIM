<%-- 
    Document   : newBundle
    Created on : 25-11-2019, 12:15:56
    Author     : Andreas
--%>

<%@page import="businessLogic.Product"%>
<%@page import="businessLogic.Bundle"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Bundle</title>
    </head>
    <body>
        <%
        
        %>
        <h1 align="center">Create Bundle</h1>
        <form action="FrontController">
            <p align="center">
                Bundle Name:
                <br>
                <input type="text" name="Bundle Name" value="" required="required" />
            </p>

            <p align="center">
                Bundle Description:
                <br>
                <textarea name="Bundle Description" rows="8" cols="40" required="required" ></textarea>
            </p>

            <input type="hidden" name="command" value="addBundle" />
            <%
            String error = (String) request.getAttribute("error");
            if(error != null){
            %>
            <h2  align="center" style="color: red"><%=error%></h2>
            <%}%>
            
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
                        <td align="center" width="1%"><input type="checkbox" name=productChoice value="<%=ProductID%>"></td>
                    </tr>
                </tbody>
                <%}%>
            </table>
            
            <p align="center">
                <input type="submit" value="Save" />
            </p>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="index" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>
    </body>
</html>
