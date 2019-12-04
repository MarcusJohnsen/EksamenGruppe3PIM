<%-- 
    Document   : deleteBundle
    Created on : 27-11-2019, 08:45:50
    Author     : Andreas
--%>

<%@page import="businessLogic.Bundle"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Bundle</title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <%
            Bundle bundle = (Bundle) request.getAttribute("bundle");
            String bundleName = bundle.getBundleName();
            int bundleID = bundle.getBundleID();
        %>
        <div class="main">    
            <center>
                <h1>Confirm Deletion</h1>
                <br>
                <h2>Please confirm deletion of this bundle</h2><br>
                <h3>Bundle ID: <i><%=bundleID%></i></h3><br>
                <h3>Bundle Name: <i><%=bundleName%></i></h3>
                <form action="FrontController" method="POST">
                    <input type="hidden" name="command" value="deleteBundle" />
                    <input type="hidden" name="bundleID" value="<%=bundleID%>" />
                    <input type="submit" value="DELETE" style="background-color: red"/>
                </form>
                <form action="FrontController" method="POST">
                    <input type="hidden" name="command" value="goToJsp" />
                    <input type="hidden" name="goToJsp" value="viewAllBundles" />
                    <input type="submit" value="Go Back" />
                </form>
            </center>
        </div>
    </body>
</html>
