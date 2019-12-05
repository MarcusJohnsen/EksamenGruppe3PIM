<%-- 
    Document   : viewAllProducts
    Created on : 11-Nov-2019, 14:06:02
    Author     : Marcus
--%>

<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products view</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
        <h2 align="center">Products list</h2>

        <br><br>

        <form action="FrontController" method="POST"> 
            <input type="hidden" name="command" value="selectProduct" />
            <table align="center" width = "50%">
                <thead>
                    <tr>
                        <th align="center">ID</th>
                        <th align="center">Name</th>
                        <th align="center">Description</th>
                        <th></th>
                    </tr>
                </thead>

                    <%
                        
                        TreeSet<Product> productList = (TreeSet<Product>) request.getAttribute("productList");
                        for (Product product : productList) {
                            int ProductID = product.getProductID();
                            String ProductName = product.getName();
                            String ProductDescription = product.getDescription();
                    %>  
                    <tr>
                        <td align="center" width="3%"> <%=ProductID%> </td>
                        <td align="center" width="20%"> <%=ProductName%> </td>
                        <td align="center" width="30%"> <%=ProductDescription%> </td>
                        <td align="center" width="1%"><input type="radio" name=productChoice value="<%=ProductID%>"></td>
                    </tr>
                <%}%>
            </table>

            <br><br>

            <p align="center"><input type="submit" name="submitButton" value="Edit Product"/></p>
            <p align="center"><input type="submit" name="submitButton" value="Delete Product"/></p>
            <p align="center"><input type="submit" name="submitButton" value="View all product details"/></p>
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