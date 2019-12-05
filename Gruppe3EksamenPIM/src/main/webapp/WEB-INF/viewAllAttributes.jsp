<%-- 
    Document   : viewAllAttributes
    Created on : 27-Nov-2019, 08:58:32
    Author     : Ashayla
--%>

<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Attribute"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Attributes</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
        <h1 align="center">Attributes list</h1>
        <form action="FrontController">
            <table align="center" border = "1" width = "50%">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <th align="center">Attribute ID</th>
                        <th align="center">Attribute Name</th>
                        <th></th>
                    </tr>
                </thead>

                    <%
                        TreeSet<Attribute> attributeList = (TreeSet<Attribute>) request.getAttribute("attributeList");
                        for (Attribute attribute : attributeList) {
                            int AttributeID = attribute.getAttributeID();
                            String AttributeName = attribute.getAttributeName();
                    %>  
                    <tr>
                        <td align="center" width="2%"> <%=AttributeID%> </td>
                        <td align="center" width="20%"> <%=AttributeName%> </td>
                        <td align="center" width="1%"><input type="radio" name=attributeChoice value="<%=AttributeID%>"></td>
                    </tr>
                <%}%>
            </table>
            <input type="hidden" name="command" value="selectAttribute" />
            <p align="center"><input type="submit" name="submitButton" value="Edit Attribute"/></p>
            <p align="center"><input type="submit" name="submitButton" value="Delete Attribute"/></p>
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
