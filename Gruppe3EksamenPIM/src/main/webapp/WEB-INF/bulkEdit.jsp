<%-- 
    Document   : bulkEdit
    Created on : 28-11-2019, 10:08:51
    Author     : Andreas
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Category"%>
<%@page import="businessLogic.Attribute"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:set var="pimObjectType" value='${requestScope["PIMObjectType"]}'/>
        <c:set var="category" value='${requestScope["category"]}'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bulk Edit</title>
        <link href="css/StyleTable.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
            <h1 align="center">Bulk edit for category <c:out value="${category.getObjectTitle()}"/></h1>
            <br>
            <form action="FrontController" method="POST">

                <c:set var="error" value='${requestScope["error"]}'/>
                <c:if test="${not empty error}">
                    <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
                </c:if>

                <p align="center"><i>
                        If an attribute value should be removed for all selected products, write the word 'DELETE' in the field. <br>
                        If an attribute should not be changed for all selected products, leave the field empty.
                    </i></p>

                <table align="center" border="1" width = "20%">
                    <thead>
                        <tr>
                            <th align="left">Attribute Name</th>
                            <th align="center">Attribute Value</th>
                        </tr>
                    </thead>
                    <c:forEach items='${category.getCategoryAttributes()}' var="attribute">
                        <tr>
                            <td align="left" width="20%"> <c:out value="${attribute.getObjectTitle()}"/> </td>
                            <td align="center" width="30%"> <input type="text" style="width: 96%; text-align: center" name="AttributeID<c:out value="${attribute.getObjectID()}"/>" value="" align="center" placeholder="attribute value..."/> </td>
                        </tr>
                    </c:forEach>
                </table>
                <br>
                <input type="hidden" name="categoryID" value="<c:out value="${category.getObjectID()}"/>" />
                <input type="hidden" name="command" value="bulkEdit" />
                <p align="center"><input type="submit" value="Update All Attributes" /></p>
            </form>
        </div>
    </body>
</html>
