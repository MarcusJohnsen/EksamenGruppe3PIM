<%-- 
    Document   : bulkEdit
    Created on : 28-11-2019, 10:08:51
    Author     : Andreas
--%>

<%@page import="businessLogic.Category"%>
<%@page import="businessLogic.Attribute"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bulk Edit</title>
    </head>
    <body>
        <%
            Category category = (Category) request.getAttribute("category");
            String categoryName = category.getName();

        %>
        <h1 align="center">Bulk edit for category "<%=categoryName%>"</h1>
        <br>
        <form action="FrontController">
            <table align="center" border="1" width = "20%" style="float: top">
                <thead>
                    <tr>
                        <td align="left">Attribute Name</td>
                        <td align="center">Attribute Value</td>
                    </tr>
                </thead>
                <%

                    ArrayList<Attribute> attributeList = (ArrayList<Attribute>) category.getCategoryAttributes();
                    for (Attribute attribute : attributeList) {
                        int attributeID = attribute.getAttributeID();
                        String value = attribute.getAttributeValueForID(attributeID);
                        String attributeTitle = attribute.getAttributeName();
                %>
                <tr>
                    <td align="left" width="20%"> <%=attributeTitle%> </td>
                    <td align="center" width="30%"> <input type="text" style="width: 98%; text-align: center" name="AttributeID<%=attributeID%>" value="*Placeholder*" align="center"/> </td>
                </tr>
                </tbody>
                <%}%>
            </table>
            <br>
            <p align="center"><input type="submit" value="Update All Attributes" /></p>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="index" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>
    </body>
</html>
