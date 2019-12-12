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
        <c:set var="product" value='${requestScope["product"]}'/>
        <c:set var="CategoryList" value='${requestScope["categoryList"]}'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
            <h1 align="center">Edit Categories for product</h1>
            <h3 align="center"><c:out value="${product.getObjectTitle()}"/>, product ID: <c:out value="${product.getObjectID()}"/></h3>
            <br>
            <form action="FrontController" method="POST">
                <input type="hidden" name="PIMObjectType" value="<c:out value="${pimObjectType}"/>"/>
                <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                    <thead>
                        <tr bgcolor = "#FF4B4B">
                            <th align="center">Category ID</th>
                            <th align="center">Category Name</th>
                            <th align="center">Category Description</th>
                            <th></th>
                        </tr>
                    </thead>

                    <c:forEach items='${CategoryList}' var="category">
                        <tr>
                            <td align="center" width="5%"> <c:out value="${category.getObjectID()}"/> </td>
                            <td align="center" width="20%"> <c:out value="${category.getObjectTitle()}"/> </td>
                            <td align="center" width="30%"> <c:out value="${category.getObjectDescription()}"/> </td>
                            <c:choose>
                                <c:when test="${product.getProductCategories().contains(category)}">
                                    <td align="center" width="1%"><input type="checkbox" name=categoryChoices value="<c:out value="${category.getObjectID()}"/>" checked></td>
                                    </c:when>
                                    <c:otherwise>
                                    <td align="center" width="1%"><input type="checkbox" name=categoryChoices value="<c:out value="${category.getObjectID()}"/>"></td>
                                    </c:otherwise>
                                </c:choose>
                        </tr>
                    </c:forEach>
                </table>
                <br>

                <c:set var="error" value='${requestScope["error"]}'/>
                <c:if test="${not empty error}">
                    <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
                </c:if>

                <input type="hidden" name="pimObjectID" value="<c:out value="${product.getObjectID()}"/>" />
                <input type="hidden" name="command" value="editCategoriesToProduct" />
                <p align="center"><input type="submit" value="Submit Changes" /></p>
            </form>
        </div>
    </body>
</html>
