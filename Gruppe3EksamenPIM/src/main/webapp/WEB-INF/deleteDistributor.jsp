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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Distributor</title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <c:set var="distributor" value='${requestScope["distributor"]}'/>
        <div class="main">
            <center>
                <h1>Confirm Deletion</h1>
                <br>
                <h2>Please confirm deletion of this category:</h2><br>
                <h3>Category ID: <i><c:out value="${distributor.getDistributorID()}"/></i></h3><br>
                <h3>Category Name: <i><c:out value="${distributor.getDistributorName()}"/></i></h3>
                <form action="FrontController" method="POST">
                    <input type="hidden" name="command" value="deleteDistributor" />
                    <input type="hidden" name="distributorID" value="<c:out value="${distributor.getDistributorID()}"/>" />
                    <input type="submit" value="DELETE" style="background-color: red"/>
                </form>
                <form action="FrontController" method="POST">
                    <input type="hidden" name="command" value="goToJsp" />
                    <input type="hidden" name="goToJsp" value="viewAllDistributors" />
                    <input type="submit" value="Go Back" />
                </form>
            </center>
        </div>        
    </body>
</html>