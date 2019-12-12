Back<%-- 
    Document   : viewAllProductDetails
    Created on : 27-Nov-2019, 10:35:38
    Author     : Marcus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Bundle"%>
<%@page import="businessLogic.Distributor"%>
<%@page import="businessLogic.Category"%>
<%@page import="businessLogic.Attribute"%>
<%@page import="businessLogic.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <c:set var="product" value='${requestScope["pimObject"]}'/>
        <title>View all products</title>
        <link href="css/StyleTable.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
        <div class="main">
            <%
                Product product = (Product) request.getAttribute("pimObject");
                int productID = product.getObjectID();
                String productName = product.getObjectTitle();
                String productDescription = product.getObjectDescription();
                String picturePath = product.getPicturePath();
            %>
            <h1 align="center">Product information for product number:  <c:out value="${product.getObjectID()}"/></h1>
            <p align="center">
                Product Name: 
                <br>
                <c:out value="${product.getObjectTitle()}"/>
            </p>
            <p align="center">
                Product Description:
                <br>
                <c:out value="${product.getObjectDescription()}"/>
            </p>
            <p align="center">
                Product picture: 
                <br>
                <img src="<c:out value="${product.getPicturePath()}"/>" style="max-width: 350px; width: 100%; max-height: 500px; height: auto;"/>

            </p>

            <table align="center" border="1" width = "50%" style="float: top">
                <thead>
                    <tr>
                        <th align="left">Category Name</th>
                        <th align="center">Category Description</th>
                    </tr>
                </thead>

                <p align="center"> This product is part of the following categories: </p>
                <c:forEach items="${product.getProductCategories()}" var="category">
                <tr>
                    <td align="left" width="20%"> <c:out value="${category.getObjectTitle()}"/> </td>
                    <td align="center" width="30%"> <c:out value="${category.getObjectDescription()}"/> </td>
                </tr>
                </c:forEach>
            </table>

            <table align="center" border="1" width = "50%" style="float: top">
                <thead>
                    <tr>
                        <th align="left">Attribute Name</th>
                        <th align="center">Attribute Value</th>
                    </tr>
                </thead>
                <p align="center"> This product has the following attributes: </p>
                <c:forEach items="${product.getProductAttributes()}" var="attribute">
                <tr>
                    <td align="left" width="20%"> <c:out value="${attribute.getObjectTitle()}"/> </td>
                    <td align="center" width="30%"> <c:out value="${attribute.getAttributeValueForID(product.getObjectID())}"/> </td>
                </tr>
                </c:forEach>
            </table>

            <table align="center" border="1" width = "50%" style="float: top">
                <thead>
                    <tr>
                        <th align="left">Distributor Name</th>
                        <th align="center">Distributor Description</th>
                    </tr>
                </thead>
                <p align="center"> This product has the following distributors: </p>
                <c:forEach items="${product.getProductDistributors()}" var="distributor">
                <tr>
                    <td align="left" width="20%"> <c:out value="${distributor.getObjectTitle()}"/> </td>
                    <td align="center" width="30%"> <c:out value="${distributor.getObjectDescription()}"/> </td>
                </tr>
                </c:forEach>
            </table>

            <c:set var="error" value='${requestScope["error"]}'/>
                <c:if test="${not empty error}">
                    <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
                </c:if>
            <br>
        </div>
    </body>
</html>