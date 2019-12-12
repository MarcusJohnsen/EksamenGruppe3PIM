<%-- 
    Document   : editAttribute
    Created on : 27-Nov-2019, 08:53:16
    Author     : Marcus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="businessLogic.Attribute"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:set var="pimObjectType" value='${requestScope["PIMObjectType"]}'/>
        <c:set var="attribute" value='${requestScope["pimObject"]}'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit attribute</title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
            <h1 align="center">Edit Attribute Info</h1>
            <form action="FrontController" method="POST">
                <input type="hidden" name="PIMObjectType" value="<c:out value="${pimObjectType}"/>"/>
                <input type="hidden" name="attributeID" value="<c:out value="${attribute.getObjectID()}"/>"/>
                <p align="center">
                    Attribute Name:
                    <br>
                    <input type="text" name="Attribute Name" value="<c:out value="${attribute.getObjectTitle()}"/>" required="required"/>
                </p>

                <c:set var="error" value='${requestScope["error"]}'/>
                <c:if test="${not empty error}">
                    <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
                </c:if>

                <p align="center">
                    Save the changes:
                    <br>
                    <input type="hidden" name="command" value="editAttribute" />
                    <input type="submit" value="Update"/></p>
            </form>
        </div>    
    </body>
</html>
