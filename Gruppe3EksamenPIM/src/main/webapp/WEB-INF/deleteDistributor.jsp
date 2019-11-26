<%-- 
    Document   : deleteDistributor
    Created on : 25-Nov-2019, 13:27:09
    Author     : Marcus
--%>

<%@page import="businessLogic.Distributor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Distributor</title>
    </head>
    <body>
        <%
            Distributor distributor = (Distributor) request.getAttribute("distributor");
            String distributorName = distributor.getDistributorName();
            int distributorID = distributor.getDistributorID();
        %>
    <center>
        <h1>Confirm Deletion</h1>
        <br>
        <h2>Please confirm deletion of this category:</h2><br>
        <h3>Category ID: <i><%=distributorID%></i></h3><br>
        <h3>Category Name: <i><%=distributorName%></i></h3>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="deleteDistributor" />
            <input type="hidden" name="distributorID" value="<%=distributorID%>" />
            <input type="submit" value="DELETE" style="background-color: red"/>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllDistributors" />
            <input type="submit" value="Go Back" />
        </form>
    </center>
    </body>
</html>