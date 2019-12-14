<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.HashMap"%>
<%@page import="presentation.FrontController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
<!DOCTYPE html>

<jsp:useBean id="frontController" class="presentation.FrontController"/>
${frontController.setup()}
<c:set var="statistics" value='${FrontController.getBusinessController().getSystemStatistics()}'/>
<c:set var="productCount" value="${statistics['productCount']}"/>
<c:set var="categoryCount" value="${statistics['categoryCount']}"/>
<c:set var="distributorCount" value="${statistics['distributorCount']}"/>
<c:set var="bundleCount" value="${statistics['bundleCount']}"/>
<c:set var="topTenCategories" value="${statistics['topTenCategories']}"/> <!-- These are Lists that are sorted with largest / nr. 1 first -->
<c:set var="topTenDistributors" value="${statistics['topTenDistributors']}"/> <!-- These are Lists that are sorted with largest / nr. 1 first -->
<c:set var="topTenBundles" value="${statistics['topTenBundles']}"/> <!-- These are Lists that are sorted with largest / nr. 1 first -->

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
                    <canvas id="bundle-chart" width="100" height="80"></canvas>
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
                    <canvas id="category-chart" width="100" height="80"></canvas>

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
                    <canvas id="distributor-chart" width="100" height="80"></canvas>
                </div>    
            </div>
            <br>
            <br>
            <form action="DownloadJson" method="GET" target="_blank">
                <input type="hidden" name="JsonType" value="fullJson" />
                <p align="center"><input type="submit" style="width: 200px; height: 50px" name="btn_submit" value="Download Json for products"></p>
            </form>
        </div>
    </body> 
    <script>
        new Chart(document.getElementById("category-chart"), {
        type: 'bar',
                data: {
                labels: [<c:forEach items='${topTenCategories}' var="topTenCategory">"Top <c:out value="${topTenCategories.indexOf(topTenCategory)+1}"/>",</c:forEach>],
                        datasets: [
                        {
                        label: "Amount of products in category",
                                backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850", "#1429e3", "#9e14e3", "#e31471" , "#14e3e0", "#25e314"],
                                data: [<c:forEach items='${topTenCategories}' var="topTenCategory"><c:out value="${topTenCategory.getCategoryProducts().size()}"/>,</c:forEach>]
                        }
                        ]
                },
                options: {
                legend: {display: false},
                        title: {
                        display: true,
                                text: 'Amount of product in top 10 categories'
                        }, scales: {
                yAxes: [{
                ticks: {
                beginAtZero: true,
                }
                }]
                }
                }
        });
        new Chart(document.getElementById("distributor-chart"), {
        type: 'bar',
                data: {
                labels: [<c:forEach items='${topTenDistributors}' var="topTenDistributor">"Top <c:out value="${topTenDistributors.indexOf(topTenDistributor)+1}"/>",</c:forEach>],
                        datasets: [
                        {
                        label: "Amount of products in distributor",
                                backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850", "#1429e3", "#9e14e3", "#e31471" , "#14e3e0", "#25e314"],
                                data: [<c:forEach items='${topTenDistributors}' var="topTenDistributor"><c:out value="${topTenDistributor.getDistributorProducts().size()}"/>,</c:forEach>]
                        }
                        ]
                },
                options: {
                legend: {display: false},
                        title: {
                        display: true,
                                text: 'Amount of product in top 10 distributors'
                        }, scales: {
                yAxes: [{
                ticks: {
                beginAtZero: true,
                }
                }]
                }
                }
        });
        new Chart(document.getElementById("bundle-chart"), {
        type: 'bar',
                data: {
                labels: [<c:forEach items='${topTenBundles}' var="topTenBundle">"Top <c:out value="${topTenBundles.indexOf(topTenBundle)+1}"/>",</c:forEach>],
                        datasets: [
                        {
                        label: "Amount of products in bundle",
                                backgroundColor: ["#3e95cd", "#8e5ea2", "#3cba9f", "#e8c3b9", "#c45850", "#1429e3", "#9e14e3", "#e31471" , "#14e3e0", "#25e314"],
                                data: [<c:forEach items='${topTenBundles}' var="topTenBundle"><c:out value="${topTenBundle.getBundleProducts().size()}"/>,</c:forEach>]
                        }
                        ]
                },
                options: {
                legend: {display: false},
                        title: {
                        display: true,
                                text: 'Amount of product in top 10 bundles'
                        }, scales: {
                yAxes: [{
                ticks: {
                beginAtZero: true,
                }
                }]
                }
                }
        });
    </script>
</html>