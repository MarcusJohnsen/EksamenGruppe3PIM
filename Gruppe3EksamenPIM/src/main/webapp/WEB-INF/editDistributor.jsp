<%-- 
    Document   : editDistributor
    Created on : 25-Nov-2019, 13:24:05
    Author     : Marcus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="businessLogic.Distributor"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:set var="pimObjectType" value='${requestScope["PIMObjectType"]}'/>
        <c:set var="distributor" value='${requestScope["pimObject"]}'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Distributors</title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
            <h1 align="center">Edit Distributor Info</h1>
            <form action="FrontController" method="POST">
                <input type="hidden" name="PIMObjectType" value="<c:out value="${pimObjectType}"/>"/>
                <input type="hidden" name="distributorID" value="<c:out value="${distributor.getObjectID()}"/>"/>
                <p align="center">
                    Distributor Name:
                    <br>
                    <input type="text" name="Distributor Name" value="<c:out value="${distributor.getObjectTitle()}"/>" required="required"/>
                </p>

                <p align="center">
                    Distributor Description:
                    <br>
                    <textarea name="Distributor Description" rows="8" cols="40" required="required"><c:out value="${distributor.getObjectDescription()}"/></textarea>
                </p>

                <c:set var="error" value='${requestScope["error"]}'/>
                <c:if test="${not empty error}">
                    <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
                </c:if>

                <p align="center">
                    Save the changes:
                    <br>
                    <input type="hidden" name="command" value="editDistributor" />
                    <input type="submit" value="Update"/></p>
            </form> 
        </div>
    </body>
</html>