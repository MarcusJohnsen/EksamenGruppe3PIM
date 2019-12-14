<%-- 
    Document   : viewAllDistributors
    Created on : 25-Nov-2019, 12:22:42
    Author     : Marcus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Distributor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
        <title>JSP Page</title>
        <c:set var="pimObjectType" value='${requestScope["PIMObjectType"]}'/>
        <c:set var="pimObjectList" value='${requestScope["PIMObjectList"]}'/>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
            <h1 align="center"><c:out value="${pimObjectType}"/> list</h1>
            <c:choose>
                <c:when test="${pimObjectList.size() != 0}">
                    <table align="center" border = "1" width = "75%" style="float: top" bgcolor="fffef2">
                        <thead>
                            <tr bgcolor = "#FF4B4B">
                                <th align="center">ID</th>
                                <th align="center">Title</th>
                                    <c:if test = "${pimObjectType != 'Attribute'}">
                                    <th align="center">Description</th>
                                    </c:if>
                                    <c:if test = "${pimObjectType == 'Product'}"><th></th></c:if>
                                <c:if test = "${pimObjectType == 'Category'}"><th></th><th></th></c:if>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>
                        <c:forEach items='${pimObjectList}' var="pimobject">
                            <form action="FrontController" method="POST">
                                <input type="hidden" name="command" value="selectPIMObject" />
                                <input type="hidden" name="PIMObjectType" value="<c:out value="${pimObjectType}"/>"/>
                                <tr>
                                    <td align="center" width="5%"> <c:out value="${pimobject.getObjectID()}"/> </td>
                                    <td align="center" width="20%"> <c:out value="${pimobject.getObjectTitle()}"/> </td>
                                    <c:if test = "${pimObjectType != 'Attribute'}">
                                        <td align="center" width="30%"> <c:out value="${pimobject.getObjectDescription()}"/> </td>
                                    </c:if>
                                <input type="hidden" name=PIMObjectChoice value="<c:out value="${pimobject.getObjectID()}"/>">
                                <c:if test = "${pimObjectType == 'Product'}"><td align="center" width="1%"><input type="submit" name="submitButton" value="Select"/></td></c:if>
                                <c:if test = "${pimObjectType == 'Category'}"><td align="center" width="1%"><input type="submit" name="submitButton" value="Bulk Edit"/></td></c:if>
                                    <td align="center" width="1%"><input type="submit" name="submitButton" value="Edit"/></td>
                                    <td align="center" width="1%"><input type="submit" name="submitButton" value="Delete"/></td>
                                </form>
                            <c:if test = "${pimObjectType == 'Category'}">
                                <td align="center" width="1%">
                                    <form action="DownloadJson" method="GET" target="_blank">
                                        <input type="hidden" name="JsonType" value="categoryJson" />
                                        <input type="hidden" name="PIMObjectID" value="<c:out value="${pimobject.getObjectID()}"/>" />
                                        <p align="center"><input type="submit" value="Json"></p>
                                    </form>
                                </td>
                            </c:if>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    <br>
                    <h2 style="color: red" align="center">No <c:out value="${pimObjectType}"/> to display!</h2>
                </c:otherwise>
            </c:choose>

            <c:set var="error" value='${requestScope["error"]}'/>
            <c:if test="${not empty error}">
                <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
            </c:if>
        </div>
    </body>
</html>
