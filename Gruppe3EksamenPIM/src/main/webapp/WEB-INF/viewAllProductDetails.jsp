<%-- 
    Document   : viewAllProductDetails
    Created on : 27-Nov-2019, 10:35:38
    Author     : Marcus
--%>

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
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
        <%
            Product product = (Product) request.getAttribute("product");
            int productID = product.getProductID();
            String productName = product.getName();
            String productDescription = product.getDescription();
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

        <table align="center" border="1" width = "20%" style="float: top">
            <thead>
                <tr>
                    <td align="left">Category Name</td>
                    <td align="center">Category Description</td>
                </tr>
            </thead>

            <p align="center"> This product is part of the following categories: </p>
            <%
                TreeSet<Category> categoryList = product.getProductCategories();
                for (Category category : categoryList) {
                    String categoryName = category.getName();
                    String categoryDescription = category.getDescription();
            %>
            <tr>
                <td align="left" width="20%"> <%=categoryName%> </td>
                <td align="center" width="30%"> <%=categoryDescription%> </td>
            </tr>
            <%}%>
        </table>

        <table align="center" border="1" width = "20%" style="float: top">
            <thead>
                <tr>
                    <td align="left">Attribute Name</td>
                    <td align="center">Attribute Value</td>
                </tr>
            </thead>
            <p align="center"> This product has the following attributes: </p>
            <%
                TreeSet<Attribute> attributeList = product.getProductAttributes();
                for (Attribute attribute : attributeList) {
                    String attributeName = attribute.getAttributeName();
                    String attributeValue = attribute.getAttributeValueForID(productID);
            %>
            <tr>
                <td align="left" width="20%"> <%=attributeName%> </td>
                <td align="center" width="30%"> <%=attributeValue%> </td>
            </tr>
            <%}%>
        </table>

        <table align="center" border="1" width = "20%" style="float: top">
            <thead>
                <tr>
                    <td align="left">Distributor Name</td>
                    <td align="center">Distributor Description</td>
                </tr>
            </thead>
            <p align="center"> This product has the following distributors: </p>
            <%
                TreeSet<Distributor> distributorList = product.getProductDistributors();
                for (Distributor distributor : distributorList) {
                    String distributorName = distributor.getDistributorName();
                    String distributorDescription = distributor.getDistributorDescription();
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
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllProducts" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>
        </div>
    </body>
</html>