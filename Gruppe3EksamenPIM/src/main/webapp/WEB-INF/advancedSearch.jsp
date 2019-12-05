<%-- 
    Document   : advancedSearch
    Created on : 04-Dec-2019, 14:39:34
    Author     : Marcus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="/JSP Header/JSP-menu.jsp"/>
        <h1 align="center">Do your advanced search here</h1>
        <br>
        
        <h3 align="center">Choose what you would like to search for:</h3>
        
        <p align="center" width="1%"> Category: <input type="radio" name=categoryChoice value="Category"></p>
        <p align="center" width="1%"> Distributor: <input type="radio" name=distributorChoice value="Distributor"></p>
        <p align="center" width="1%"> Bundle: <input type="radio" name=bundleChoice value="Bundle"></p>
        
        <p align="center"><input type="text" name="advancedSearch" value="" placeholder="Search..."/> <input type="submit" value="Search"/></p>
    </body>
</html>