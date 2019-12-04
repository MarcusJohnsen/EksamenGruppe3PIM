<%-- 
    Document   : editCategory
    Created on : 19-11-2019, 10:20:47
    Author     : Andreas
--%>

<%@page import="businessLogic.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Category</title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <%
            Category category = (Category) request.getAttribute("category");
            String CategoryName = category.getName();
            String CategoryDescription = category.getDescription();
            int CategoryID = category.getCategoryID();
        %>
        <div class="main">
        <h1 align="center">Edit Category Info</h1>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="selectAttributesForCategory" />
            <input type="hidden" name="categoryID" value="<%=CategoryID%>" />
            <p align="center"><input type="submit" value="Add Attributes to Category"/></p>
        </form>
        <form action="FrontController">
            <input type="hidden" name="categoryID" value="<%=CategoryID%>"/>
            <p align="center">
                Category Name:
                <br>
                <input type="text" name="Category Name" value="<%=CategoryName%>" required="required"/>
            </p>
            
            <p align="center">
                Category Description:
                <br>
                <textarea name="Category Description" rows="8" cols="40" required="required"><%=CategoryDescription%></textarea>
            </p>
            
            <p align="center">
                Save the changes:
                <br>
                <input type="hidden" name="command" value="editCategory" />
                <input type="submit" value="Update"/></p>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllCategories" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>    
            </div>    
    </body>
</html>