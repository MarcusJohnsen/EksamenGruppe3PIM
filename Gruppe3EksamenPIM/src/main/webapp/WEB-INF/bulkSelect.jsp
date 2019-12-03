<%-- 
    Document   : bulkSelect
    Created on : 28-11-2019, 12:40:28
    Author     : andre
--%>

<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Product"%>
<%@page import="businessLogic.Category"%>
<%@page import="businessLogic.Attribute"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Select</title>
        <style>
            .buttonTable{
                width:68px;
                height: 17px; 
                border: 1px solid #990033; 
                background-color: #FF4B4B; 
                cursor: pointer
            }
            .buttonTable:hover{
                background-color: #ff9999;
                box-shadow: 1px 1px 10px 0px #000000;
            }
        </style>
    </head>
    <%
        Category category = (Category) request.getAttribute("category");
        String categoryName = category.getName();
        int categoryID = category.getCategoryID();
    %>
    <body>
        <h1 align="center">Bulk edit for "<%=categoryName%> ID:<%=categoryID%>"</h1>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
        <h2  align="center" style="color: red"><%=error%></h2>
        <%}%>
        
        
        <form action="FrontController">
            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <td align="center">ID</td>
                        <td align="center">Name</td>
                        <td align="center">Description</td>
                        <td align="center">
                            <input type="button" value="Check" onclick="check()" class="buttonTable"/>
                            <input type="button" value="Uncheck" onclick="unCheck()" class="buttonTable"/>
                        </td>
                        
                    </tr>
                </thead>

                <tbody>
                    <%
                        TreeSet<Product> productList = (TreeSet<Product>) request.getAttribute("productList");
                        for (Product product : productList) {
                            int ProductID = product.getProductID();
                            String ProductName = product.getName();
                            String ProductDescription = product.getDescription();
                    %>  
                    <tr>
                        <td align="center" width="3%"> <%=ProductID%> </td>
                        <td align="center" width="20%"> <%=ProductName%> </td>
                        <td align="center" width="30%"> <%=ProductDescription%> </td>
                        <td align="center" width="1%"><input type="checkbox" name=productChoice value="<%=ProductID%>" checked></td>
                    </tr>
                </tbody>
                <%}%>
            </table>
            <br>
            <input type="hidden" name="command" value="bulkSelect" />
            <input type="hidden" name="categoryID" value="<%=categoryID%>" />
            <p align="center"><input type="submit" value="Select" /></p>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="index" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>
        <script>
            function check() {
                var boxes = document.getElementsByName("productChoice");
                for (var i = 0; i < boxes.length; i++) {
                    if (boxes[i].type === "checkbox") {
                        boxes[i].checked = true;
                    }
                }
            }

            function unCheck() {
                var boxes = document.getElementsByName("productChoice");
                for (var i = 0; i < boxes.length; i++) {
                    if (boxes[i].type === "checkbox") {
                        boxes[i].checked = false;
                    }
                }
            }
        </script>

    </body>
</html>
