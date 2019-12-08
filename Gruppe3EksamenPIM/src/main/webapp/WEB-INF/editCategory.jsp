<%-- 
    Document   : editCategory
    Created on : 19-11-2019, 10:20:47
    Author     : Andreas
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="businessLogic.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:set var="pimObjectType" value='${requestScope["PIMObjectType"]}'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Category</title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <%
            Category category = (Category) request.getAttribute("pimObject");
            String CategoryName = category.getObjectTitle();
            String CategoryDescription = category.getObjectDescription();
            int CategoryID = category.getObjectID();
        %>
        <div class="main">
            <h1 align="center">Edit Category Info</h1>
            <form action="FrontController" method="POST">
                <input type="hidden" name="PIMObjectType" value="<c:out value="${pimObjectType}"/>"/>
                <input type="hidden" name="command" value="selectAttributesForCategory" />
                <input type="hidden" name="categoryID" value="<%=CategoryID%>" />
                <p align="center"><input type="submit" value="Add Attributes to Category"/></p>
            </form>
            <form action="FrontController">
                <input type="hidden" name="PIMObjectType" value="<c:out value="${pimObjectType}"/>"/>
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

                <c:set var="error" value='${requestScope["error"]}'/>
                <c:if test="${not empty error}">
                    <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
                </c:if>

                <p align="center">
                    Save the changes:
                    <br>
                    <input type="hidden" name="command" value="editCategory" />
                    <input type="submit" value="Update"/></p>
            </form>  
        </div>    
    </body>
</html>