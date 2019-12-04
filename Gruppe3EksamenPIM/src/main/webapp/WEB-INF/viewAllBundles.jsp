<%-- 
    Document   : viewAllBundles
    Created on : 25-11-2019, 13:29:26
    Author     : andre
--%>

<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Bundle"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Bundles</title>
        <link href="css/StyleSheet.css" rel="stylesheet">
        <!--<link href="JavaScript/myScript.js" rel="myscript">-->
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
        <h1 align="center">View All Bundles</h1>
        <form action="FrontController">
            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <td align="center">Bundle ID</td>
                        <td align="center">Bundle Name</td>
                        <td align="center">Bundle Description</td>
                    </tr>
                </thead>

                <tbody>
                    <%
                        TreeSet<Bundle> bundleList = (TreeSet<Bundle>) request.getAttribute("bundleList");
                        for (Bundle bundle : bundleList) {
                            int BundleID = bundle.getBundleID();
                            String BundleName = bundle.getBundleName();
                            String BundleDescription = bundle.getBundleDescription();
                    %>  
                    <tr>
                        <td align="center" width="5%"> <%=BundleID%> </td>
                        <td align="center" width="20%"> <%=BundleName%> </td>
                        <td align="center" width="30%"> <%=BundleDescription%> </td>
                        <td align="center" width="1%"><input type="radio" name=bundleChoice value="<%=BundleID%>"></td>
                    </tr>
                </tbody>
                <%}%>
            </table>
            <input type="hidden" name="command" value="selectBundle" />
            <p align="center"><input type="submit" name="submitButton" value="Edit Bundle"/></p>
            <p align="center"><input type="submit" name="submitButton" value="Delete Bundle"/></p>
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
        </div>
    </body>
</html>
