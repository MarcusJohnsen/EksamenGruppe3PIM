<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.TreeSet"%>
<%@page import="businessLogic.Attribute"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <c:set var="attributeList" value='${requestScope["attributeList"]}'/>
        <title>New Category</title>
        <link href="css/StyleTable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <div class="main">
            <h1 align="center">Create New Category</h1>
            <br>
            <form action="FrontController" method="POST">
                <p align="center">
                    Category Name:
                    <br>
                    <input type="text" name="Category Name" value="" required="required" />
                </p>

                <p align="center">
                    Category Description:
                    <br>
                    <textarea name="Category Description" rows="8" cols="40" required="required" ></textarea>
                </p>

                <input type="hidden" name="command" value="addCategory" />
                <table align="center" border = "1" width = "50%" style="float: top" bgcolor="fffef2">
                    <thead>
                        <tr bgcolor = "#FF4B4B">
                            <th align="center">Attribute ID</th>
                            <th align="center">Attribute Name</th>
                            <th></th>
                        </tr>
                    </thead>
                    <c:forEach items='${attributeList}' var="attribute">
                        <tr>
                            <td align="center" width="5%"> <c:out value="${attribute.getObjectID()}"/> </td>
                            <td align="center" width="20%"> <c:out value="${attribute.getObjectTitle()}"/> </td>
                            <td align="center" width="1%"><input type="checkbox" name=attributeChoice value="<c:out value="${attribute.getObjectID()}"/>"></td>
                        </tr>
                    </c:forEach>
                </table>

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
