<%-- 
    Document   : editBundle
    Created on : 27-11-2019, 08:45:29
    Author     : Andreas
--%>

<%@page import="businessLogic.Bundle"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Bundle</title>
    </head>
    <body>
        <%
            Bundle bundle = (Bundle) request.getAttribute("bundle");
            String bundleName = bundle.getBundleName();
            String bundleDist = bundle.getBundleDescription();
            int bundleID = bundle.getBundleID();
        %>
        <h1 align="center">Edit Bundle Info</h1>
        <form action="FrontController" method="POST">
            <input type="hidden" name="bundleID" value="<%=bundleID%>" />
        </form>
        <form action="FrontController">
            <input type="hidden" name="bundleID" value="<%=bundleID%>"/>
            <p align="center">
                Bundle Name:
                <br>
                <input type="text" name="Bundle Name" value="<%=bundleName%>" required="required"/>
            </p>
            
            <p align="center">
                Bundle Description:
                <br>
                <textarea name="Bundle Description" rows="8" cols="40" required="required"><%=bundleDist%></textarea>
            </p>
            
            <p align="center">
                Save the changes:
                <br>
                <input type="hidden" name="command" value="editBundle" />
                <input type="submit" value="Update"/></p>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllBundles" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>    
    </body>
</html>
