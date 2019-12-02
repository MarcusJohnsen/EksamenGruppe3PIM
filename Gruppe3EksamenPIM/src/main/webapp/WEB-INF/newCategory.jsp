<%-- 
    Document   : newCategory
    Created on : 13-11-2019, 11:47:28
    Author     : Andreas
--%>

<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Attribute"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Category</title>
    </head>
    <body>
        <h1 align="center">Create New Category</h1>
        <br>
        <form action="FrontController">
            <p align="center">
                Category Name:
                <br>
                <input type="text" name="Category Name" value="" required="required" />
            </p>

            <p align="center">
                Category Description:
                <br>
                <textarea name="Category Description" rows="8" cols="40" required="required" ></textarea>
            </p>

            <input type="hidden" name="command" value="addCategory" />
            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <td align="center">Attribute ID</td>
                        <td align="center">Attribute Name</td>
                    </tr>
                </thead>

                <tbody>
                    <%
                        TreeSet<Attribute> attributeList = (TreeSet<Attribute>) request.getAttribute("attributeList");
                        for (Attribute attribute : attributeList) {
                            int AttributeID = attribute.getAttributeID();
                            String AttributeName = attribute.getAttributeName();
                    %>  
                    <tr>
                        <td align="center" width="5%"> <%=AttributeID%> </td>
                        <td align="center" width="20%"> <%=AttributeName%> </td>
                        <td align="center" width="1%"><input type="checkbox" name=attributeChoice value="<%=AttributeID%>"></td>
                    </tr>
                </tbody>
                <%}%>
            </table>
            <%
            String error = (String) request.getAttribute("error");
            if(error != null){
            %>
            <h2  align="center" style="color: red"><%=error%></h2>
            <%}%>
            <p align="center">
                <input type="submit" value="Save" />
            </p>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="index" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>
    </body>
</html>
