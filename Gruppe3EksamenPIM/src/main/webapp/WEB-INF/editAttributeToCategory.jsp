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
        <c:set var="attributeList" value='${requestScope["attributeList"]}'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Connect attributes</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
            <h1 align="center">Add attribute to category</h1>
            <h3 align="center"><c:out value="${category.getObjectTitle()}"/>, product ID: <c:out value="${category.getObjectID()}"/></h3>
            <form action="FrontController" method="POST">
                <input type="hidden" name="PIMObjectType" value="<c:out value="${pimObjectType}"/>"/>
                <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                    <thead>
                        <tr bgcolor = "#FF4B4B">
                            <th align="center">Attribute ID</th>
                            <th align="center">Attribute Name</th>
                            <th align="center">Attribute Value</th>
                        </tr>
                    </thead>


                    <c:forEach items='${attributeList}' var="attribute">
                        <tr>
                            <td align="center" width="5%"> <c:out value="${attribute.getObjectID()}"/> </td>
                            <td align="center" width="20%"> <c:out value="${attribute.getObjectTitle()}"/> </td>
                            <c:choose>
                                <c:when test="${category.getCategoryAttributes().contains(attribute)}">
                                    <td align="center" width="1%"><input type="checkbox" name=attributeChoices value="<c:out value="${attribute.getObjectID()}"/>" checked></td>
                                    </c:when>
                                    <c:otherwise>
                                    <td align="center" width="1%"><input type="checkbox" name=attributeChoices value="<c:out value="${attribute.getObjectID()}"/>"></td>
                                    </c:otherwise>
                                </c:choose>
                        </tr>
                    </c:forEach>
                </table>

                <c:set var="error" value='${requestScope["error"]}'/>
                <c:if test="${not empty error}">
                    <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
                </c:if>

                <br>
                <input type="hidden" name="pimObjectID" value="<c:out value="${category.getObjectID()}"/>" />
                <input type="hidden" name="command" value="editAttributesToCategory" />
                <p align="center"><input type="submit" value="Submit Changes" /></p>
            </form>
        </div>
    </body>
</html>
