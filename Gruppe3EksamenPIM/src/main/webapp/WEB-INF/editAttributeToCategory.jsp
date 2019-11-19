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
            Category category = (Category) request.getAttribute("category");
            ArrayList<Attribute> attributeList = (ArrayList<Attribute>) request.getAttribute("attributeList");
            int categoryID = category.getCategoryID();
        %>
        <h1 align="center">Add attribute to category</h1>
        <h3 align="center"><%=category.getName()%>, product ID: <%=categoryID%></h3>
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
                            boolean alreadyOnCategory = category.getCategoryAttributes().contains(attribute);
                    %>  
                    <tr>
                        <td align="center" width="5%"> <%=AttributeID%> </td>
                        <td align="center" width="20%"> <%=AttributeTitle%> </td>
                        <%if (alreadyOnCategory) {%>
                        <td align="center" width="1%"><input type="checkbox" name=attributeChoices value="<%=AttributeID%>" checked></td>
                            <%} else {%>
                        <td align="center" width="1%"><input type="checkbox" name=attributeChoices value="<%=AttributeID%>"></td>
                            <%}%>
                    </tr>
                </tbody>
                <%}%>
            </table>
            <br>
            <input type="hidden" name="categoryID" value="<%=categoryID%>" />
            <input type="hidden" name="command" value="editAttributesToCategory" />
            <p align="center"><input type="submit" value="Submit Changes" /></p>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllCategories" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>
    </body>
</html>
