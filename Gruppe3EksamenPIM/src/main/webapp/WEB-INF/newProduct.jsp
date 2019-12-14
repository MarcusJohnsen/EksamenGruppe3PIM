<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Category"%>
<%@page import="businessLogic.Distributor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <c:set var="distributorList" value='${requestScope["distributorList"]}'/>
        <c:set var="categoryList" value='${requestScope["categoryList"]}'/>
        <title>PIM System</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
            <h1 align="center">Create New Product</h1>
            <form action="FrontController" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="command" value="addProduct" />

                <c:set var="error" value='${requestScope["error"]}'/>
                <c:if test="${not empty error}">
                    <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
                </c:if>

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

                <div id="myDIV" align="center"> 
                </div>

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

                    <c:forEach items='${distributorList}' var="distributor">
                        <tr>
                            <td align="center" width="5%"> <c:out value="${distributor.getObjectID()}"/> </td>
                            <td align="center" width="20%"> <c:out value="${distributor.getObjectTitle()}"/> </td>
                            <td align="center" width="1%"><input type="checkbox" name=distributorChoices value="<c:out value="${distributor.getObjectID()}"/>"></td>
                        </tr>
                    </c:forEach>



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

                    <c:forEach items='${categoryList}' var="category">
                        <tr>
                            <td align="center" width="5%"> <c:out value="${category.getObjectID()}"/> </td>
                            <td align="center" width="20%"> <c:out value="${category.getObjectTitle()}"/> </td>
                            <td align="center" width="30%"> <c:out value="${category.getObjectDescription()}"/> </td>
                            <td align="center" width="1%"><input type="checkbox" name=categoryChoices value="<c:out value="${category.getObjectID()}"/>"></td>
                        </tr>
                    </c:forEach>
                </table>

                <br>
                <p align="center">
                    <!--Save product:-->

                    <input type="submit" value="Save Product" />
                </p>
            </form>

        </div>     
    </body>
</html>
