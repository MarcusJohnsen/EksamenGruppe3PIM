<%-- 
    Document   : deleteProduct
    Created on : 13-11-2019, 09:13:43
    Author     : Michael N. Korsgaard
--%>

<%@page import="businessLogic.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <%
            Product product = (Product) request.getAttribute("product");
            String productName = product.getName();
            int productID = product.getProductID();
        %>
    <div class="main">
    <center>
        <h1>Confirm Deletion</h1>
        <br>
        <h2>Please confirm deletion of this product:</h2><br>
        <h3>Product ID: <i><%=productID%></i></h3><br>
        <h3>Product Name: <i><%=productName%></i></h3>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="deleteProduct" />
            <input type="hidden" name="productID" value="<%=productID%>" />
            <input type="submit" value="DELETE" style="background-color: red"/>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllProducts" />
            <input type="submit" value="Go Back" />
        </form>
    </center>
    </div>        
</body>
</html>
