<%-- 
    Document   : advancedSearch
    Created on : 04-Dec-2019, 14:39:34
    Author     : Marcus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Advanced search</title>
        <c:set var="PIMObjectType" value="${requestScope['PIMObjectType']}"/>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
            <h1 align="center"><c:out value="${PIMObjectType}"/> Search</h1>
            <br>

            <form action="FrontController">
                <input type="hidden" name="PIMObjectType" value="<c:out value="${PIMObjectType}"/>"/>
                <input type="hidden" name="command" value="advancedSearch" />
                <p align="center"><input type="text" name="searchKey" value="" placeholder="Search for: "/> <input type="submit" value="Search"/></p>

                <c:forEach items='${requestScope["filterList"]}' var="filter">
                    <p align="center" width="1%"> <c:out value="${filter}"/>: <input type="text" name="<c:out value="${filter}"/>Choice"></p>
                    </c:forEach>
            </form>
        </div>
    </body>
</html>