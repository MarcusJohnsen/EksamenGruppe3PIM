<%-- 
    Document   : editBundle
    Created on : 27-11-2019, 08:45:29
    Author     : Andreas
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Product"%>
<%@page import="businessLogic.Bundle"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:set var="pimObjectType" value='${requestScope["PIMObjectType"]}'/>
        <c:set var="bundle" value='${requestScope["pimObject"]}'/>
        <c:set var="productList" value='${requestScope["PIMObjectList"]}'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Bundle</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
            <h1 align="center">Edit Bundle Info</h1>
            <form action="FrontController" method="POST">
                <input type="hidden" name="PIMObjectType" value="<c:out value="${pimObjectType}"/>"/>
                <input type="hidden" name="bundleID" value="<c:out value="${bundle.getObjectID()}"/>"/>

                <c:set var="error" value='${requestScope["error"]}'/>
                <c:if test="${not empty error}">
                    <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
                </c:if>

                <p align="center">
                    Bundle Name:
                    <br>
                    <input type="text" name="Bundle Name" value="<c:out value="${bundle.getObjectTitle()}"/>" required="required"/>
                </p>

                <p align="center">
                    Bundle Description:
                    <br>
                    <textarea name="Bundle Description" rows="8" cols="40" required="required"><c:out value="${bundle.getObjectDescription()}"/></textarea>
                </p>

                <table align="center" border = "1" width = "60%" style="float: top" bgcolor="fffef2">
                    <thead>
                        <tr bgcolor = "#FF4B4B">
                            <th align="center">ID</th>
                            <th align="center">Name</th>
                            <th align="center">Description</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>

                    <c:forEach items='${productList}' var="product">
                        <tr>
                            <td align="center" width="3%"> <c:out value="${product.getObjectID()}"/> </td>
                            <td align="center" width="20%"> <c:out value="${product.getObjectTitle()}"/> </td>
                            <td align="center" width="30%"> <c:out value="${product.getObjectDescription()}"/> </td>
                            <c:choose>
                                <c:when test="${bundle.getBundleProducts().keySet().contains(product)}">
                                    <td align="center" width="1%"><input type="checkbox" name="productChoice" value="<c:out value="${product.getObjectID()}"/>" checked></td>
                                    <td align="center" width="3%"><input type="text" name="ProductIDAmount<c:out value="${product.getObjectID()}"/>" value="<c:out value="${bundle.getBundleProducts().get(product)}"/>" size="1" style="text-align: center;"/></td>
                                    </c:when>
                                    <c:otherwise>
                                    <td align="center" width="1%"><input type="checkbox" name="productChoice" value="<c:out value="${product.getObjectID()}"/>"></td>
                                    <td align="center" width="3%"><input type="text" name="ProductIDAmount<c:out value="${product.getObjectID()}"/>" value="1" size="1" style="text-align: center;"/></td>
                                    </c:otherwise>
                                </c:choose>
                        </tr>
                    </c:forEach>
                </table>

                <p align="center">
                    Save the changes:
                    <br>
                    <input type="hidden" name="command" value="editBundle" />
                    <input type="submit" value="Update"/></p>
            </form>  
        </div>    
    </body>
</html>
