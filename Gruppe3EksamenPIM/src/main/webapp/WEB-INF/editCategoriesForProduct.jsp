<%-- 
    Document   : SelectCategoriesForProduct
    Created on : 19-11-2019, 10:22:09
    Author     : Michael N. Korsgaard
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Category"%>
<%@page import="businessLogic.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:set var="pimObjectType" value='${requestScope["PIMObjectType"]}'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <%
            Product product = (Product) request.getAttribute("product");
            TreeSet<Category> categoryList = (TreeSet<Category>) request.getAttribute("categoryList");
            int productID = product.getObjectID();

        %>
        <div class="main">
            <h1 align="center">Edit Categories for product</h1>
            <h3 align="center"><%=product.getObjectTitle()%>, product ID: <%=productID%></h3>
            <br>
            <form action="FrontController">
                <input type="hidden" name="PIMObjectType" value="<c:out value="${pimObjectType}"/>"/>
                <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                    <thead>
                        <tr bgcolor = "#FF4B4B">
                            <th align="center">Category ID</th>
                            <th align="center">Category Name</th>
                            <th align="center">Category Description</th>
                        </tr>
                    </thead>

                    <%
                        for (Category category : categoryList) {
                            int CategoryID = category.getObjectID();
                            String CategoryName = category.getObjectTitle();
                            String CategoryDescription = category.getObjectDescription();
                            boolean alreadyOnProduct = product.getProductCategories().contains(category);
                    %>  
                    <tr>
                        <td align="center" width="5%"> <%=CategoryID%> </td>
                        <td align="center" width="20%"> <%=CategoryName%> </td>
                        <td align="center" width="30%"> <%=CategoryDescription%> </td>
                        <%if (alreadyOnProduct) {%>
                        <td align="center" width="1%"><input type="checkbox" name=categoryChoices value="<%=CategoryID%>" checked></td>
                            <%} else {%>
                        <td align="center" width="1%"><input type="checkbox" name=categoryChoices value="<%=CategoryID%>"></td>
                            <%}%>
                    </tr>
                    <%}%>
                </table>
                <br>

                <c:set var="error" value='${requestScope["error"]}'/>
                <c:if test="${not empty error}">
                    <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
                </c:if>

                <input type="hidden" name="pimObjectID" value="<%=productID%>" />
                <input type="hidden" name="command" value="editCategoriesToProduct" />
                <p align="center"><input type="submit" value="Submit Changes" /></p>
            </form>
        </div>
    </body>
</html>
