<%-- 
    Document   : deleteDistributor
    Created on : 25-Nov-2019, 13:27:09
    Author     : Marcus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="businessLogic.Distributor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:set var="pimObjectType" value='${requestScope["PIMObjectType"]}'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete <c:out value="${pimObjectType}"/></title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <c:set var="pimObject" value='${requestScope["pimObject"]}'/>
        <div class="main">
            <center>
                <h1>Confirm Deletion</h1>
                <br>
                <h2>Please confirm deletion of this <c:out value="${pimObjectType.toLowerCase()}"/>:</h2><br>
                <h3><c:out value="${pimObjectType}"/> ID: <i><c:out value="${pimObject.getObjectID()}"/></i></h3><br>
                <h3><c:out value="${pimObjectType}"/> Name: <i><c:out value="${pimObject.getObjectTitle()}"/></i></h3>
                <form action="FrontController" method="POST">
                    <input type="hidden" name="command" value="deleteDistributor" />
                    <input type="hidden" name="PIMObjectID" value="<c:out value="${pimObject.getObjectID()}"/>" />
                    <input type="hidden" name="PIMObjectType" value="<c:out value="${pimObjectType}"/>"/>
                    <input type="submit" value="DELETE" style="background-color: red"/>
                </form>
            </center>
        </div>        
    </body>
</html>