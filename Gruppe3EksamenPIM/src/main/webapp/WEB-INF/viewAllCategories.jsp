<%-- 
    Document   : veiwAllCategories
    Created on : 13-11-2019, 13:05:45
    Author     : Andreas
--%>

<%@page import="businessLogic.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category View</title>
    </head>
    <body>
        <h1 align="center">Category list</h1>
        <form action="FrontController">
            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <td align="center">Category ID</td>
                        <td align="center">Category Name</td>
                        <td align="center">Category Description</td>
                    </tr>
                </thead>

                <tbody>
                    <%
                        ArrayList<Category> categoryList = (ArrayList<Category>) request.getAttribute("categoryList");
                        for (Category category : categoryList) {
                            int CategoryID = category.getCategoryID();
                            String CategoryName = category.getName();
                            String CategoryDescription = category.getDescription();
                    %>  
                    <tr>
                        <td align="center" width="5%"> <%=CategoryID%> </td>
                        <td align="center" width="20%"> <%=CategoryName%> </td>
                        <td align="center" width="30%"> <%=CategoryDescription%> </td>
                        <td align="center" width="1%"><input type="radio" name=categoryChoice value="<%=CategoryID%>"></td>
                    </tr>
                </tbody>
                <%}%>
            </table>
            <input type="hidden" name="command" value="selectCategory" />
            <!--<p align="center"><input type="submit" name="submitButton" value="Edit Category"/></p>-->
            <p align="center"><input type="submit" name="submitButton" value="Delete Category"/></p>
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
