<%-- 
    Document   : index
    Created on : 11-11-2019, 08:41:54
    Author     : Andreas
--%>

<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.HashMap"%>
<%@page import="presentation.FrontController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:useBean id="frontController" class="presentation.FrontController"/>
${frontController.setup()}
<c:set var="statistics" value='${FrontController.getBusinessController().getSystemStatistics()}'/>
<c:set var="productCount" value="${statistics['productCount']}"/>
<c:set var="categoryCount" value="${statistics['categoryCount']}"/>
<c:set var="distributorCount" value="${statistics['distributorCount']}"/>
<c:set var="bundleCount" value="${statistics['bundleCount']}"/>
<c:set var="topTenCategories" value="${statistics['topTenCategories']}"/> <!-- These is a List of Pairs, use a for each loop, and use .getKey() to get category, and .getValue() to get amount. List should be sorted with largest / nr. 1 first -->
<c:set var="topTenDistributors" value="${statistics['topTenDistributors']}"/>
<c:set var="topTenBundles" value="${statistics['topTenBundles']}"/>
<%
    HashMap<String, Object> statistics = FrontController.getBusinessController().getSystemStatistics();
    int productCount = (int) statistics.get("productCount");
    int categoryCount = (int) statistics.get("categoryCount");
    int distributorCount = (int) statistics.get("distributorCount");
    int bundleCount = (int) statistics.get("bundleCount");
    request.setAttribute("topTenCategories", statistics.get("topTenCategories"));
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PIM System</title>
    </head>
    <body>
        Top 10 Categories:<br>
        <c:forEach items='${requestScope["topTenCategories"]}' var="topTenCategories">
            <c:out value="${topTenCategories.getKey().getName()}"/> has <c:out value="${topTenCategories.getValue()}"/> products<br>
        </c:forEach>
        <br>
        Product Count: <c:out value="${productCount}"/>

        <h1 align="center">Products</h1>
        <br>
        <form action="FrontController">
            <input type="hidden" name="command" value="simpleSearch" />
            <p align="center"><input type="text" name="search" value="" placeholder="Search..."/> <input type="submit" value="Search" /></p>
        </form>
        <form action="FrontController">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="newProduct" />
            <p align="center"><input type="submit" value="New Product" style="width: 150px; height: 25px"/></p>
        </form>

        <form action="FrontController">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllProducts" />
            <p align="center"><input type="submit" value="View Products" style="width: 150px; height: 25px"/></p>
        </form>
        <br>
        <form action="FrontController">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="newCategory" />
            <p align="center"><input type="submit" value="New Category" style="width: 150px; height: 25px"/></p>
        </form>

        <form action="FrontController">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllCategories" />
            <p align="center"><input type="submit" value="View Categories" style="width: 150px; height: 25px"/></p>
        </form>
        <br>
        <form action="FrontController">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="newAttribute" />
            <p align="center"><input type="submit" value="New Attribute" style="width: 150px; height: 25px"/></p>
        </form>

        <form action="FrontController">
            <input type="hidden" name="command" value="goToJsp" />    
            <input type="hidden" name="goToJsp" value="viewAllAttributes" />
            <p align="center"><input type="submit" value="View Attributes" style="width: 150px; height: 25px"/></p>
        </form> 
        <br>
        <form action="FrontController">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="newDistributor" />
            <p align="center"><input type="submit" value="New Distributor" style="width: 150px; height: 25px"/></p>
        </form>

        <form action="FrontController">
            <input type="hidden" name="command" value="goToJsp" />    
            <input type="hidden" name="goToJsp" value="viewAllDistributors" />
            <p align="center"><input type="submit" value="View Distributors" style="width: 150px; height: 25px"/></p>
        </form>   
        <br>
        <form action="FrontController">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="newBundle" />
            <p align="center"><input type="submit" value="New Bundle" style="width: 150px; height: 25px"/></p>
        </form>

        <form action="FrontController">   
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="viewAllBundles" />
            <p align="center"><input type="submit" value="View Bundles" style="width: 150px; height: 25px"/></p>
        </form>



    </body>
</html>