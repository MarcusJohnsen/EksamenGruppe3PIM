<%-- 
    Document   : editDistributor
    Created on : 25-Nov-2019, 13:24:05
    Author     : Marcus
--%>

<%@page import="businessLogic.Distributor"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Distributors</title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <%
                Distributor distributor = (Distributor) request.getAttribute("distributor");
                int DistributorID = distributor.getDistributorID();
                String DistributorName = distributor.getDistributorName();
                String DistributorDescription = distributor.getDistributorDescription();
        %>
        <div class="main">
        <h1 align="center">Edit Distributor Info</h1>
        <form action="FrontController" method="POST">
            <input type="hidden" name="distributorID" value="<%=DistributorID%>"/>
            <p align="center">
                Distributor Name:
                <br>
                <input type="text" name="Distributor Name" value="<%=DistributorName%>" required="required"/>
            </p>
            
            <p align="center">
                Distributor Description:
                <br>
                <textarea name="Distributor Description" rows="8" cols="40" required="required"><%=DistributorDescription%></textarea>
            </p>
            
            <p align="center">
                Save the changes:
                <br>
                <input type="hidden" name="command" value="editDistributor" />
                <input type="submit" value="Update"/></p>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllDistributors" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>    
            </div>
    </body>
</html>