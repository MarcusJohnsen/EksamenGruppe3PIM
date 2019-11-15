<%-- 
    Document   : deleteCategory
    Created on : 14-11-2019, 09:59:24
    Author     : andre
--%>

<%@page import="businessLogic.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Category</title>
    </head>
    <body>
        <%
            Category category = (Category) request.getAttribute("category");
            String categoryName = category.getName();
            int categoryID = category.getCategoryID();
        %>
    <center>
        <h1>Confirm Deletion</h1>
        <br>
        <h2>Please confirm deletion of this category:</h2><br>
        <h3>Category ID: <i><%=categoryID%></i></h3><br>
        <h3>Category Name: <i><%=categoryName%></i></h3>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="deleteCategory" />
            <input type="hidden" name="categoryID" value="<%=categoryID%>" />
            <input type="submit" value="DELETE" style="background-color: red"/>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllCategories" />
            <input type="submit" value="Go Back" />
        </form>
    </center>

</body>
</html>
