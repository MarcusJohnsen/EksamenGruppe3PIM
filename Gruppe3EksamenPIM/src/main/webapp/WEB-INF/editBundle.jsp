<%-- 
    Document   : editBundle
    Created on : 27-11-2019, 08:45:29
    Author     : Andreas
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Product"%>
<%@page import="businessLogic.Bundle"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:set var="pimObjectType" value='${requestScope["PIMObjectType"]}'/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Bundle</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <%
            Bundle bundle = (Bundle) request.getAttribute("pimObject");
            String bundleName = bundle.getObjectTitle();
            String bundleDist = bundle.getObjectDescription();
            int bundleID = bundle.getObjectID();
        %>
        <div class="main">
            <h1 align="center">Edit Bundle Info</h1>
            <form action="FrontController" method="POST">
                <input type="hidden" name="bundleID" value="<%=bundleID%>" />
            </form>
            <form action="FrontController">
                <input type="hidden" name="PIMObjectType" value="<c:out value="${pimObjectType}"/>"/>
                <input type="hidden" name="bundleID" value="<%=bundleID%>"/>

                <%
                    String error = (String) request.getAttribute("error");
                    if (error != null) {
                %>
                <h2  align="center" style="color: red"><%=error%></h2>
                <%}%>

                <p align="center">
                    Bundle Name:
                    <br>
                    <input type="text" name="Bundle Name" value="<%=bundleName%>" required="required"/>
                </p>

                <p align="center">
                    Bundle Description:
                    <br>
                    <textarea name="Bundle Description" rows="8" cols="40" required="required"><%=bundleDist%></textarea>
                </p>

                <table align="center" border = "1" width = "60%" style="float: top" bgcolor="fffef2">
                    <thead>
                        <tr bgcolor = "#FF4B4B">
                            <th align="center">ID</th>
                            <th align="center">Name</th>
                            <th align="center">Description</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>

                    <%
                        TreeSet<Product> productList = (TreeSet<Product>) request.getAttribute("PIMObjectList");
                        for (Product product : productList) {
                            int ProductID = product.getObjectID();
                            String ProductName = product.getObjectTitle();
                            String ProductDescription = product.getObjectDescription();
                            boolean alreadySelectedProduct = bundle.getBundleProducts().keySet().contains(product);
                    %>  
                    <tr>
                        <td align="center" width="3%"> <%=ProductID%> </td>
                        <td align="center" width="20%"> <%=ProductName%> </td>
                        <td align="center" width="30%"> <%=ProductDescription%> </td>
                        <%if (alreadySelectedProduct) {%>
                        <td align="center" width="1%"><input type="checkbox" name="productChoice" value="<%=ProductID%>" checked></td>
                        <td align="center" width="3%"><input type="text" name="ProductIDAmount<%=ProductID%>" value="<%=bundle.getBundleProducts().get(product)%>" size="1" style="text-align: center;"/></td>
                            <%} else {%>
                        <td align="center" width="1%"><input type="checkbox" name="productChoice" value="<%=ProductID%>"></td>
                        <td align="center" width="3%"><input type="text" name="ProductIDAmount<%=ProductID%>" value="1" size="1" style="text-align: center;"/></td>
                            <%}%>

                    </tr>
                    <%}%>
                </table>

                <p align="center">
                    Save the changes:
                    <br>
                    <input type="hidden" name="command" value="editBundle" />
                    <input type="submit" value="Update"/></p>
            </form>
            <form action="FrontController" method="POST">
                <input type="hidden" name="command" value="goToJsp" />
                <input type="hidden" name="goToJsp" value="viewAllBundles" />
                <p align="center"><input type="submit" value="Go Back" /></p>
            </form>    
        </div>    
    </body>
</html>
