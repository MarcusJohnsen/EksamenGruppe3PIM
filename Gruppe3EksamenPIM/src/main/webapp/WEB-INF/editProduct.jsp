<%-- 
    Document   : EditProduct
    Created on : 12-11-2019, 10:34:52
    Author     : Andreas
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Distributor"%>
<%@page import="java.util.HashMap"%>
<%@page import="businessLogic.Attribute"%>
<%@page import="businessLogic.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:set var="pimObjectType" value='${requestScope["PIMObjectType"]}'/>
        <c:set var="product" value='${requestScope["pimObject"]}'/>
        <c:set var="pimObjectList" value='${requestScope["PIMObjectList"]}'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Product</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <%
            Product product = (Product) request.getAttribute("pimObject");
        %>
        <div class="main">
            <h1 align="center">Edit Product Information for product nr: <c:out value="${product.getObjectID()}"/></h1>
            <form action="FrontController" method="POST">
                <input type="hidden" name="PIMObjectType" value="<c:out value="${pimObjectType}"/>"/>
                <input type="hidden" name="command" value="selectCategoriesForProduct" />
                <input type="hidden" name="productID" value="<c:out value="${product.getObjectID()}"/>" />
                <p align="center"><input type="submit" value="Add Categories to Product" /></p>
            </form>
            <form action="FrontController" method="POST">
                <input type="hidden" name="PIMObjectType" value="<c:out value="${pimObjectType}"/>"/>
                <input type="hidden" name="productID" value="<c:out value="${product.getObjectID()}"/>" />
                <p align="center">
                    Product Name:
                    <br>
                    <input type="text" name="Product Name" value="<c:out value="${product.getObjectTitle()}"/>" required="required"/>
                </p>

                <p align="center">
                    Product Description:
                    <br>
                    <textarea name="Product Description" rows="8" cols="40" required="required"><c:out value="${product.getObjectDescription()}"/> </textarea>
                </p>

                <div id="myDIV" align="center"> 
                </div>

                <table align="center" border="1" width = "20%" style="float: top">
                    <thead>
                        <tr>
                            <th align="left">Attribute Name</th>
                            <th align="center">Attribute Value</th>
                        </tr>
                    </thead>
                    <br>
                    <c:forEach items='${product.getProductAttributes()}' var="attribute">
                        <tr>
                            <td align="left" width="20%"> <c:out value="${attribute.getObjectTitle()}"/> </td>
                            <td align="center" width="30%"> <input type="text" style="width: 96%; text-align: center" name="AttributeID<c:out value="${attribute.getObjectID()}"/>" value="<c:out value="${attribute.getAttributeValueForID(product.getObjectID())}"/>"/> </td>
                        </tr>
                    </c:forEach>
                </table>

                <c:set var="error" value='${requestScope["error"]}'/>
                <c:if test="${not empty error}">
                    <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
                </c:if>
                <br>

                <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                    <thead>
                        <tr bgcolor = "#FF4B4B">
                            <th align="center">Distributor ID</th>
                            <th align="center">Distributor Name</th>
                            <th></th>
                        </tr>
                    </thead>
                    <c:forEach items='${pimObjectList}' var="distributor">
                        <tr>
                            <td align="center" width="5%"> <c:out value="${distributor.getObjectID()}"/> </td>
                            <td align="center" width="20%"> <c:out value="${distributor.getObjectTitle()}"/> </td>
                            <c:choose>
                                <c:when test="${product.getProductDistributors().contains(distributor)}">
                                    <td align="center" width="1%"><input type="checkbox" name=distributorChoices value="<c:out value="${distributor.getObjectID()}"/>" checked></td>
                                    </c:when>
                                    <c:otherwise>
                                    <td align="center" width="1%"><input type="checkbox" name=distributorChoices value="<c:out value="${distributor.getObjectID()}"/>"></td>
                                    </c:otherwise>
                                </c:choose>
                        </tr>
                    </c:forEach>
                </table>

                <br>
                <p align="center">
                    Save the changes:
                    <br>
                    <input type="hidden" name="command" value="editProduct" />
                    <input type="submit" value="Update"/>
                </p>
            </form>
        </div>        
        <script>
            function newField() {
                var x = document.createElement("INPUT");
                var br = document.createElement('br');
                var br2 = document.createElement('br');
                x.setAttribute("type", "text");
                x.setAttribute("name", "Product Distributors");
                document.getElementById("myDIV").appendChild(x);
                document.getElementById("myDIV").appendChild(br);
                document.getElementById("myDIV").appendChild(br2);
            }
        </script>
    </body>
</html>