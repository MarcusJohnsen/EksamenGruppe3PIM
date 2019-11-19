<%-- 
    Document   : addAttributeToCategory
    Created on : 19-11-2019, 12:23:44
    Author     : Marcus
--%>

<%@page import="businessLogic.Category"%>
<%@page import="businessLogic.Attribute"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Connect attributes</title>
    </head>
    <body>
        <%
            //Category category = (Category) request.getAttribute("category");
            ArrayList<Attribute> attributeList = (ArrayList<Attribute>) request.getAttribute("attributeList");
        %>
        <h1 align="center">Connect attributes to Categories</h1>
        <form action="FrontController">
            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <td align="center">Attribute ID</td>
                        <td align="center">Attribute Title</td>
                        <td align="center">Attribute Value</td>
                    </tr>
                </thead>

                <tbody>
                    <%
                            for (Attribute attribute : attributeList) {
                            int AttributeID = attribute.getAttributeID();
                            String AttributeTitle = attribute.getAttributeTitle();
                            //String AttributeValue = attribute.getAttributeValues();
                    %>  
                    <tr>
                        <td align="center" width="5%"> <%=AttributeID%> </td>
                        <td align="center" width="20%"> <%=AttributeTitle%> </td>
                        <td align="center" width="1%"><input type="radio" name=categoryChoice value="<%=AttributeID%>"></td>
                    </tr>
                </tbody>
                <%}%>
            </table>
        </form>
    </body>
</html>
