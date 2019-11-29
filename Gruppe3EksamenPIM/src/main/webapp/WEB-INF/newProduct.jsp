<%-- 
    Document   : newProduct
    Created on : 11-11-2019, 08:55:22
    Author     : Andreas
--%>

<%@page import="businessLogic.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="businessLogic.Distributor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PIM System</title>
    </head>
    <body>

        <h1 align="center">Create New Product</h1>
        <!-- enctype="multipart/form-data" -->
        <form action="FrontController" method="POST">
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
                        <td align="center">DistributorID ID</td>
                        <td align="center">Distributor Name</td>

                    </tr>
                </thead>

                <tbody>
                    <%
                        ArrayList<Distributor> distributorList = (ArrayList<Distributor>) request.getAttribute("distributorList");
                        for (Distributor distributor : distributorList) {
                            int distributorID = distributor.getDistributorID();
                            String distributorName = distributor.getDistributorName();
                    %>  
                    <tr>
                        <td align="center" width="5%"> <%=distributorID%> </td>
                        <td align="center" width="20%"> <%=distributorName%> </td>
                        <td align="center" width="1%"><input type="checkbox" name=distributorChoices value="<%=distributorID%>"></td>
                    </tr>
                </tbody>
                <%}%>



            </table>

            <br>
            
            <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                <thead>
                    <tr bgcolor = "#FF4B4B">
                        <td align="center">Category ID</td>
                        <td align="center">Category Name</td>
                        <td align="center">Category Description</td>
                    </tr>
                </thead>

                <tbody>
                    <%
                        ArrayList<Category> categoryList = (ArrayList<Category>) request.getAttribute("categoryList");
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
                </tbody>
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
