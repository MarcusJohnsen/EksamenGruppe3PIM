<%-- 
    Document   : EditProduct
    Created on : 12-11-2019, 10:34:52
    Author     : Andreas
--%>

<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Distributor"%>
<%@page import="java.util.HashMap"%>
<%@page import="businessLogic.Attribute"%>
<%@page import="businessLogic.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Product</title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <%
            Product product = (Product) request.getAttribute("product");
            String ProductName = product.getName();
            String ProductDescription = product.getDescription();
            String picturePath = product.getPicturePath();
            int productID = product.getProductID();
        %>
        <div class="main">
        <h1 align="center">Edit Product Information for product number <%=productID%></h1>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="selectCategoriesForProduct" />
            <input type="hidden" name="productID" value="<%=productID%>" />
            <p align="center"><input type="submit" value="Add Categories to Product" /></p>
        </form>
        <form action="FrontController">
            <input type="hidden" name="productID" value="<%=productID%>" />
            <p align="center">
                Product Name:
                <br>
                <input type="text" name="Product Name" value="<%=ProductName%>" required="required"/>
            </p>
            
            <p align="center">
                Product Description:
                <br>
                <textarea name="Product Description" rows="8" cols="40" required="required"><%=ProductDescription%> </textarea>
            </p>

            <div id="myDIV" align="center"> 
            </div>

            <table align="center" border="1" width = "20%" style="float: top">
                <thead>
                    <tr>
                        <td align="left">Attribute Name</td>
                        <td align="center">Attribute Value</td>
                    </tr>
                </thead>
                <br>
                <%
                    TreeSet<Attribute> attributeList = product.getProductAttributes();
                    for (Attribute attribute : attributeList) {
                        int attributeID = attribute.getAttributeID();
                        String value = attribute.getAttributeValueForID(productID);
                        String attributeTitle = attribute.getAttributeName();
                %>
                <tr>
                    <td align="left" width="20%"> <%=attributeTitle%> </td>
                    <td align="center" width="30%"> <input type="text" style="width: 98%; text-align: center" name="AttributeID<%=attributeID%>" value="<%=value%>"/> </td>
                </tr>
                </tbody>
                <%}%>
            </table>

            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <h2 align="center" style="color: red"><%=error%></h2>
            <%}%>

            <!--<p align="center">
                Select Picture:
                <input type = "file" name = "file" size = "50" value="<%=picturePath%>"/>
            </p>-->

            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <td align="center">DistributorID ID</td>
                        <td align="center">Distributor Name</td>
                    </tr>
                </thead>

                <tbody>
                    <%
                        TreeSet<Distributor> distributorList = product.getProductDistributors();
                        for (Distributor distributor : distributorList) {
                            int distributorID = distributor.getDistributorID();
                            String distributorName = distributor.getDistributorName();
                            boolean alreadySelectedDistributor = product.getProductDistributors().contains(distributor);
                    %>  
                    <tr>
                        <td align="center" width="5%"> <%=distributorID%> </td>
                        <td align="center" width="20%"> <%=distributorName%> </td>
                        <%if(alreadySelectedDistributor){%>
                        <td align="center" width="1%"><input type="checkbox" name=distributorChoices value="<%=distributorID%>" checked></td>
                        <%} else {%>
                        <td align="center" width="1%"><input type="checkbox" name=distributorChoices value="<%=distributorID%>"></td>
                        <%}%>
                    </tr>
                </tbody>
                <%}%>
            </table>
            
                <br>
                <p align="center">
                    Save the changes:
                    <br>
                    <input type="hidden" name="command" value="editProduct" />
                    <input type="submit" value="Update"/></p>
                
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllProducts" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>
        </div>        
        <script>
            function newField() {
                var x = document.createElement("INPUT");
                var br = document.createElement('br');
                var br2 = document.createElement('br');
                x.setAttribute("type", "text");
                x.setAttribute("name", "Product Distributors");
                document.getElementById("myDIV").appendChild(x);
                document.getElementById("myDIV").appendChild(br);
                document.getElementById("myDIV").appendChild(br2);
            }
        </script>
    </body>
</html>