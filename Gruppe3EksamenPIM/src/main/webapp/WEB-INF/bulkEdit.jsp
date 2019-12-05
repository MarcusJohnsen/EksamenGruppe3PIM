<%-- 
    Document   : bulkEdit
    Created on : 28-11-2019, 10:08:51
    Author     : Andreas
--%>

<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Category"%>
<%@page import="businessLogic.Attribute"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bulk Edit</title>
        <link href="css/StyleTable.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <%
            Category category = (Category) request.getAttribute("category");
            String categoryName = category.getName();
            int categoryID = category.getCategoryID();
        %>
        <div class="main">
            <h1 align="center">Bulk edit for category "<%=categoryName%>"</h1>
            <br>
            <form action="FrontController">

                <%
                    String error = (String) request.getAttribute("error");
                    if (error != null) {
                %>
                <h2 align="center" style="color: red"><%=error%></h2>
                <%}%>

                <p align="center"><i>
                        If an attribute value should be removed for all selected products, write the word 'DELETE' in the field. <br>
                        If an attribute should not be changed for all selected products, leave the field empty.
                    </i></p>

                <table align="center" border="1" width = "20%">
                    <thead>
                        <tr>
                            <th align="left">Attribute Name</th>
                            <th align="center">Attribute Value</th>
                        </tr>
                    </thead>
                    <%

                        TreeSet<Attribute> attributeList = (TreeSet<Attribute>) category.getCategoryAttributes();
                        for (Attribute attribute : attributeList) {
                            int attributeID = attribute.getAttributeID();
                            String value = attribute.getAttributeValueForID(attributeID);
                            String attributeTitle = attribute.getAttributeName();
                    %>
                    <tr>
                        <td align="left" width="20%"> <%=attributeTitle%> </td>
                        <td align="center" width="30%"> <input type="text" style="width: 96%; text-align: center" name="AttributeID<%=attributeID%>" value="" align="center" placeholder="attribute value..."/> </td>
                    </tr>
                    <%}%>
                </table>
                <br>
                <input type="hidden" name="categoryID" value="<%=categoryID%>" />
                <input type="hidden" name="command" value="bulkEdit" />
                <p align="center"><input type="submit" value="Update All Attributes" /></p>
            </form>
            <form action="FrontController" method="POST">
                <input type="hidden" name="command" value="goToJsp" />
                <input type="hidden" name="goToJsp" value="index" />
                <p align="center"><input type="submit" value="Go Back" /></p>
            </form>
        </div>
    </body>
</html>
