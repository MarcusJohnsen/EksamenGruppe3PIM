<%-- 
    Document   : newProduct
    Created on : 11-11-2019, 08:55:22
    Author     : Andreas
--%>

<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Category"%>
<%@page import="businessLogic.Distributor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PIM System</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
        <h1 align="center">Create New Product</h1>
        <!-- enctype="multipart/form-data" -->
        <form action="FrontController" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="command" value="addProduct" />
            <p align="center">
                Product Name:
                <br>
                <input type="text" name="Product Name" value="" required="required"/>
            </p>

            <p align="center">
                Product Description:
                <br>
                <textarea name="Product Description" rows="8" cols="40" required="required"></textarea>
            </p>

            <p align="center">
                Select Picture:
                <input type = "file" name = "file" size = "50" accept=".jpg, .png"/>
            </p>

            <!--<p align="center">
                Product Distributors: <img onclick="newField()" src="decorations/addPage.png" width="20" height="25" alt="addIcon"/>
                <br>
                <input type="text" name="Product Distributors" value=""/>
            </p>-->

            <div id="myDIV" align="center"> 
            </div>

            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <h2 align="center" style="color: red"><%=error%></h2>
            <%}%>

            <p align="center">
                Add Product to Distributor(s):</p>



            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <th align="center">DistributorID ID</th>
                        <th align="center">Distributor Name</th>
                        <th></th>

                    </tr>
                </thead>

                    <%
                        TreeSet<Distributor> distributorList = (TreeSet<Distributor>) request.getAttribute("distributorList");
                        for (Distributor distributor : distributorList) {
                            int distributorID = distributor.getDistributorID();
                            String distributorName = distributor.getDistributorName();
                    %>  
                    <tr>
                        <td align="center" width="5%"> <%=distributorID%> </td>
                        <td align="center" width="20%"> <%=distributorName%> </td>
                        <td align="center" width="1%"><input type="checkbox" name=distributorChoices value="<%=distributorID%>"></td>
                    </tr>
                <%}%>



            </table>

            <br>

            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <th align="center">Category ID</th>
                        <th align="center">Category Name</th>
                        <th align="center">Category Description</th>
                        <th align="center">Select</th>
                    </tr>
                </thead>

                    <%
                        TreeSet<Category> categoryList = (TreeSet<Category>) request.getAttribute("categoryList");
                        for (Category category : categoryList) {
                            int CategoryID = category.getCategoryID();
                            String CategoryName = category.getName();
                            String CategoryDescription = category.getDescription();
                    %>  
                    <tr>
                        <td align="center" width="5%"> <%=CategoryID%> </td>
                        <td align="center" width="20%"> <%=CategoryName%> </td>
                        <td align="center" width="30%"> <%=CategoryDescription%> </td>
                        <td align="center" width="1%"><input type="checkbox" name=categoryChoices value="<%=CategoryID%>"></td>
                    </tr>
                <%}%>
            </table>

            <br>
            <p align="center">
                <!--Save product:-->

                <input type="submit" value="Save Product" />
            </p>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="index" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>
        </div>     
        <!--<script>
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
        </script>-->
    </body>
</html>
