<%-- 
    Document   : newAttribute
    Created on : 18-11-2019, 13:14:28
    Author     : Andreas
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Attribute</title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
            <h1 align="center">New Attribute</h1>
            <br>
            <form action="FrontController">
                <p align="center">
                    Attribute name:
                    <br>
                    <input type="hidden" name="command" value="addAttribute" />
                    <input type="text" name="attributeName" value="" required="required" /></p>
                <p align="center"><input type="submit" value="Save" /></p>
            </form>
        </div>
    </body>
</html>
