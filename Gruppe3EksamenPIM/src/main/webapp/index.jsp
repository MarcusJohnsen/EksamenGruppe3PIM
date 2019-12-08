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
<c:set var="topTenCategories" value="${statistics['topTenCategories']}"/> <!-- These are Lists that should be sorted with largest / nr. 1 first -->
<c:set var="topTenDistributors" value="${statistics['topTenDistributors']}"/>
<c:set var="topTenBundles" value="${statistics['topTenBundles']}"/>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PIM System</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
            <h1 align="center" style="font-size: 80px">PIM System</h1>
            <h3 align="center">Product Count: <c:out value="${productCount}"/></h3>
            <br>
            <div align="center">
                <div style="display: inline-table; width:28%;">
                    <div align="center">Bundle Count: <c:out value="${bundleCount}"/><br></div>
                    <table style="width:100%;">
                        <thead>
                            <tr>
                                <th>Top</th>
                                <th>Bundles</th>
                                <th>Qty.</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%int count1 = 1;%>
                            <c:forEach items='${topTenBundles}' var="topTenBundle">
                                <tr>
                                    <td><%=count1++%></td>
                                    <td><c:out value="${topTenBundle.getObjectTitle()}"/></td>
                                    <td><c:out value="${topTenBundle.getBundleProducts().size()}"/></td>
                                </c:forEach>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div style="display: inline-table; width:28%; margin-left: 10px; margin-right: 10px">
                    <div align="center">Category Count: <c:out value="${categoryCount}"/><br></div>
                    <table style="width:100%;">
                        <thead>
                            <tr>
                                <th>Top</th>
                                <th>Category</th>
                                <th>Qty.</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%int count2 = 1;%>
                            <c:forEach items='${topTenCategories}' var="topTenCategory">
                                <tr>
                                    <td><%=count2++%></td>
                                    <td><c:out value="${topTenCategory.getObjectTitle()}"/></td>
                                    <td><c:out value="${topTenCategory.getCategoryProducts().size()}"/></td>
                                </c:forEach>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div style="display: inline-table; width:28%;">
                    <div align="center">
                        Distributor Count: <c:out value="${distributorCount}"/><br></div>
                    <table style="width:100%;">
                        <thead>
                            <tr>
                                <th>Top</th>
                                <th>Distributors</th>
                                <th>Qty.</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%int count3 = 1;%>
                            <c:forEach items='${topTenDistributors}' var="topTenDistributor">
                                <tr>
                                    <td><%=count3++%></td>
                                    <td><c:out value="${topTenDistributor.getObjectTitle()}"/></td>
                                    <td><c:out value="${topTenDistributor.getDistributorProducts().size()}"/></td>
                                </c:forEach>
                            </tr>
                        </tbody>
                    </table>
                </div>    
            </div>
            <br>
            <br>
            <form action="DownloadJson" method="GET" target="_blank">
                <p align="center"><input type="submit" style="width: 200px; height: 50px" name="btn_submit" value="Download Json for products"></p>
            </form>
        </div>
    </body>
</html>