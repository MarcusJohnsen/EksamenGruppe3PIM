<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Distributor</title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
            <h1 align="center">Create New Distributor</h1>
            <br>
            <form action="FrontController" method="POST">
                <input type="hidden" name="command" value="addDistributor" />
                <p align="center">
                    Distributor Name:
                    <br>
                    <input type="text" name="Distributor Name" value="" required="required" />
                </p>

                <p align="center">
                    Distributor Description:
                    <br>
                    <textarea name="Distributor Description" rows="8" cols="40" required="required" ></textarea>
                </p>
                <c:set var="error" value='${requestScope["error"]}'/>
                <c:if test="${not empty error}">
                    <h2 style="color: red" align="center"><c:out value="${error}"/></h2>
                </c:if>
                <p align="center">
                    <input type="submit" value="Save" />
                </p>
            </form>
        </div>    
    </body>
</html>
