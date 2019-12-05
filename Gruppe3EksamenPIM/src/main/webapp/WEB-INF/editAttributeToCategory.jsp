<%-- 
    Document   : addAttributeToCategory
    Created on : 19-11-2019, 12:23:44
    Author     : Marcus
--%>

<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Category"%>
<%@page import="businessLogic.Attribute"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Connect attributes</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <%
            Category category = (Category) request.getAttribute("category");
            TreeSet<Attribute> attributeList = (TreeSet<Attribute>) request.getAttribute("attributeList");
            int categoryID = category.getCategoryID();
        %>
        <div class="main">
        <h1 align="center">Add attribute to category</h1>
        <h3 align="center"><%=category.getName()%>, product ID: <%=categoryID%></h3>
        <form action="FrontController">
            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <th align="center">Attribute ID</th>
                        <th align="center">Attribute Name</th>
                        <th align="center">Attribute Value</th>
                    </tr>
                </thead>

                    <%
                            for (Attribute attribute : attributeList) {
                            int AttributeID = attribute.getAttributeID();
                            String AttributeName = attribute.getAttributeName();
                            boolean alreadyOnCategory = category.getCategoryAttributes().contains(attribute);
                    %>  
                    <tr>
                        <td align="center" width="5%"> <%=AttributeID%> </td>
                        <td align="center" width="20%"> <%=AttributeName%> </td>
                        <%if (alreadyOnCategory) {%>
                        <td align="center" width="1%"><input type="checkbox" name=attributeChoices value="<%=AttributeID%>" checked></td>
                            <%} else {%>
                        <td align="center" width="1%"><input type="checkbox" name=attributeChoices value="<%=AttributeID%>"></td>
                            <%}%>
                    </tr>
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
        </div>
    </body>
</html>
