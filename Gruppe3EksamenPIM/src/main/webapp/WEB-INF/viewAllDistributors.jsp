<%-- 
    Document   : viewAllDistributors
    Created on : 25-Nov-2019, 12:22:42
    Author     : Marcus
--%>

<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Distributor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1 align="center">Distributor list</h1>
        <form action="FrontController">
            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <td align="center">Distributor ID</td>
                        <td align="center">Distributor Name</td>
                        <td align="center">Distributor Description</td>
                    </tr>
                </thead>

                <tbody>
                    <%
                        TreeSet<Distributor> distributorList = (TreeSet<Distributor>) request.getAttribute("distributorList");
                        for (Distributor distributor : distributorList) {
                            int DistributorID = distributor.getDistributorID();
                            String DistributorName = distributor.getDistributorName();
                            String DistributorDescription = distributor.getDistributorDescription();
                    %>  
                    <tr>
                        <td align="center" width="5%"> <%=DistributorID%> </td>
                        <td align="center" width="20%"> <%=DistributorName%> </td>
                        <td align="center" width="30%"> <%=DistributorDescription%> </td>
                        <td align="center" width="1%"><input type="radio" name=distributorChoice value="<%=DistributorID%>"></td>
                    </tr>
                </tbody>
                <%}%>
            </table>
            <input type="hidden" name="command" value="selectDistributor" />
            <p align="center"><input type="submit" name="submitButton" value="Edit Distributor"/></p>
            <p align="center"><input type="submit" name="submitButton" value="Delete Distributor"/></p>
        </form>
        <%
                String error = (String) request.getAttribute("error");
                if (error != null) {%>
        <h2 style="color: red" align="center"><%=error%></h2>
        <%}%>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="index" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>
    </body>
</html>
