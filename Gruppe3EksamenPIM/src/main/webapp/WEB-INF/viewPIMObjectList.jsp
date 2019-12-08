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
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
            <h1 align="center"><c:out value="${pimObjectType}"/> list</h1>
            <form action="FrontController">
                <table align="center" border = "1" width = "75%" style="float: top" bgcolor="fffef2">
                    <thead>
                        <tr bgcolor = "#FF4B4B">
                            <th align="center">ID</th>
                            <th align="center">Title</th>
                                <c:if test = "${pimObjectType != 'Attribute'}">
                                <th align="center">Description</th>
                                </c:if>
                            <th></th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach items='${requestScope["PIMObjectList"]}' var="pimobject">
                            <tr>
                                <td align="center" width="5%"> <c:out value="${pimobject.getObjectID()}"/> </td>
                                <td align="center" width="20%"> <c:out value="${pimobject.getObjectTitle()}"/> </td>
                                <c:if test = "${pimObjectType != 'Attribute'}">
                                    <td align="center" width="30%"> <c:out value="${pimobject.getObjectDescription()}"/> </td>
                                </c:if>
                                <td align="center" width="1%"><input type="radio" name=PIMObjectChoice value="<c:out value="${pimobject.getObjectID()}"/>"></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <input type="hidden" name="command" value="selectPIMObject" />
                <input type="hidden" name="PIMObjectType" value="<c:out value="${pimObjectType}"/>"/>
                <c:if test = "${pimObjectType == 'Product'}">
                    <p align="center"><input type="submit" name="submitButton" value="Select"/></p>
                    </c:if>
                    <c:if test = "${pimObjectType == 'Category'}">
                    <p align="center"><input type="submit" name="submitButton" value="Bulk Edit"/></p>
                    </c:if>
                <p align="center"><input type="submit" name="submitButton" value="Edit"/></p>
                <p align="center"><input type="submit" name="submitButton" value="Delete"/></p>
            </form>
            <c:set var="error" value='${requestScope["error"]}'/>
            <c:if test="${not empty error}">
                <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
            </c:if>
        </div>
    </body>
</html>
