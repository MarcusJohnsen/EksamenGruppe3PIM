<%-- 
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
            <h1 align="center">Product information for product number:  <%=productID%></h1>
            <p align="center">
                Product Name: 
                <br>
                <%=productName%>
            </p>
            <p align="center">
                Product Description:
                <br>
                <%=productDescription%>
            </p>
            <p align="center">
                Product picture: 
                <br>
                <img src="<%=picturePath%>" style="max-width: 350px; width: 100%; max-height: 500px; height: auto;"/>

            </p>

            <table align="center" border="1" width = "50%" style="float: top">
                <thead>
                    <tr>
                        <th align="left">Category Name</th>
                        <th align="center">Category Description</th>
                    </tr>
                </thead>

                <p align="center"> This product is part of the following categories: </p>
                <%
                    TreeSet<Category> categoryList = product.getProductCategories();
                    for (Category category : categoryList) {
                        String categoryName = category.getObjectTitle();
                        String categoryDescription = category.getObjectDescription();
                %>
                <tr>
                    <td align="left" width="20%"> <%=categoryName%> </td>
                    <td align="center" width="30%"> <%=categoryDescription%> </td>
                </tr>
                <%}%>
            </table>

            <table align="center" border="1" width = "50%" style="float: top">
                <thead>
                    <tr>
                        <th align="left">Attribute Name</th>
                        <th align="center">Attribute Value</th>
                    </tr>
                </thead>
                <p align="center"> This product has the following attributes: </p>
                <%
                    TreeSet<Attribute> attributeList = product.getProductAttributes();
                    for (Attribute attribute : attributeList) {
                        String attributeName = attribute.getObjectTitle();
                        String attributeValue = attribute.getAttributeValueForID(productID);
                %>
                <tr>
                    <td align="left" width="20%"> <%=attributeName%> </td>
                    <td align="center" width="30%"> <%=attributeValue%> </td>
                </tr>
                <%}%>
            </table>

            <table align="center" border="1" width = "50%" style="float: top">
                <thead>
                    <tr>
                        <th align="left">Distributor Name</th>
                        <th align="center">Distributor Description</th>
                    </tr>
                </thead>
                <p align="center"> This product has the following distributors: </p>
                <%
                    TreeSet<Distributor> distributorList = product.getProductDistributors();
                    for (Distributor distributor : distributorList) {
                        String distributorName = distributor.getObjectTitle();
                        String distributorDescription = distributor.getObjectDescription();
                %>
                <tr>
                    <td align="left" width="20%"> <%=distributorName%> </td>
                    <td align="center" width="30%"> <%=distributorDescription%> </td>
                </tr>
                <%}%>
            </table>

            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <h2 align="center" style="color: red"><%=error%></h2>
            <%}%>
            <br>
        </div>
    </body>
</html>