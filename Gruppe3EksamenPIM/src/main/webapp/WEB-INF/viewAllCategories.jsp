<%-- 
    Document   : veiwAllCategories
    Created on : 13-11-2019, 13:05:45
    Author     : Andreas
--%>

<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category View</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
        <h1 align="center">Category list</h1>
        <form action="FrontController">
            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <th align="center">Category ID</th>
                        <th align="center">Category Name</th>
                        <th align="center">Category Description</th>
                        <th></th>
                    </tr>
                </thead>

                    <%
                        TreeSet<Category> categoryList = (TreeSet<Category>) request.getAttribute("categoryList");
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
                <%}%>
            </table>
            <input type="hidden" name="command" value="selectCategory" />
            <p align="center"><input type="submit" name="submitButton" value="Edit Category"/></p>
            <p align="center"><input type="submit" name="submitButton" value="Delete Category"/></p>
            <p align="center"><input type="submit" name="submitButton" value="Bulk Edit"/></p>
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
        </div>
    </body>
</html>
