<%-- 
    Document   : deleteAttribute
    Created on : 27-Nov-2019, 08:53:03
    Author     : Marcus
--%>

<%@page import="businessLogic.Attribute"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Attribute</title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <%
            Attribute attribute = (Attribute) request.getAttribute("attribute");
            String attributeName = attribute.getAttributeName();
            int attributeID = attribute.getAttributeID();
        %>
    <div class="main">
    <center>
        <h1>Confirm Deletion</h1>
        <br>
        <h2>Please confirm deletion of this attribute:</h2><br>
        <h3>Attribute ID: <i><%=attributeID%></i></h3><br>
        <h3>Attribute Name: <i><%=attributeName%></i></h3>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="deleteAttribute" />
            <input type="hidden" name="attributeID" value="<%=attributeID%>" />
            <input type="submit" value="DELETE" style="background-color: red"/>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllAttributes" />
            <input type="submit" value="Go Back" />
        </form>
    </center>
    </div>
    </body>
</html>
