<%-- 
    Document   : editAttribute
    Created on : 27-Nov-2019, 08:53:16
    Author     : Marcus
--%>

<%@page import="businessLogic.Attribute"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit attribute</title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <%
            Attribute attribute = (Attribute) request.getAttribute("attribute");
            int AttributeID = attribute.getAttributeID();
            String AttributeName = attribute.getAttributeName();
        %>
        <div class="main">
        <h1 align="center">Edit Attribute Info</h1>
        <form action="FrontController">
            <input type="hidden" name="attributeID" value="<%=AttributeID%>"/>
            <p align="center">
                Attribute Name:
                <br>
                <input type="text" name="Attribute Name" value="<%=AttributeName%>" required="required"/>
            </p>
            
            <p align="center">
                Save the changes:
                <br>
                <input type="hidden" name="command" value="editAttribute" />
                <input type="submit" value="Update"/></p>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllAttributes" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>
        </div>    
    </body>
</html>
