<%-- 
    Document   : bulkSelect
    Created on : 28-11-2019, 12:40:28
    Author     : Andreas
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Product"%>
<%@page import="businessLogic.Category"%>
<%@page import="businessLogic.Attribute"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:set var="pimObjectType" value='${requestScope["PIMObjectType"]}'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Select</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
        <style>
            .buttonTable{
                width:68px;
                height: 17px; 
                border: 1px solid #000000; 
                background-color: #94e2ff; 
                cursor: pointer
            }
            .buttonTable:hover{
                background-color: #ffffff;
                box-shadow: 1px 1px 10px 0px #000000;
            }
        </style>
    </head>
    <%
        Category category = (Category) request.getAttribute("pimObject");
        String categoryName = category.getObjectTitle();
        int categoryID = category.getObjectID();
    %>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>

        <div class="main">
            <h1 align="center">Bulk edit for "<%=categoryName%> ID:<%=categoryID%>"</h1>

            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <h2  align="center" style="color: red"><%=error%></h2>
            <%}%>


            <form action="FrontController">
                <table align="center" border = "1" width = "60%" style="float: top" bgcolor="fffef2">
                    <thead>
                        <tr bgcolor = "#FF4B4B">
                            <th align="center">ID</th>
                            <th align="center">Name</th>
                            <th align="center">Description</th>
                            <th align="center">
                                <input type="button" value="Check" onclick="check()" class="buttonTable"/>
                                <input type="button" value="Uncheck" onclick="unCheck()" class="buttonTable"/>
                            </th>

                        </tr>
                    </thead>

                    <%
                        TreeSet<Product> productList = category.getCategoryProducts();
                        for (Product product : productList) {
                            int ProductID = product.getObjectID();
                            String ProductName = product.getObjectTitle();
                            String ProductDescription = product.getObjectDescription();
                    %>  
                    <tr>
                        <td align="center" width="3%"> <%=ProductID%> </td>
                        <td align="center" width="20%"> <%=ProductName%> </td>
                        <td align="center" width="30%"> <%=ProductDescription%> </td>
                        <td align="center" width="1%"><input type="checkbox" name=productChoice value="<%=ProductID%>" checked></td>
                    </tr>
                    <%}%>
                </table>
                <br>
                <input type="hidden" name="command" value="bulkSelect" />
                <input type="hidden" name="categoryID" value="<%=categoryID%>" />
                <p align="center"><input type="submit" value="Select" /></p>
            </form>
        </div>    

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
