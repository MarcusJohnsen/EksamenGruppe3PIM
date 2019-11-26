<%-- 
    Document   : newDistributor
    Created on : 25-Nov-2019, 12:22:28
    Author     : Ashayla
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Distributor</title>
    </head>
    <body>
        <h1 align="center">Create New Distributor</h1>
        <br>
        <form action="FrontController">
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
            <%
            String error = (String) request.getAttribute("error");
            if(error != null){
            %>
            <h2  align="center" style="color: red"><%=error%></h2>
            <%}%>
            <p align="center">
                <input type="submit" value="Save" />
            </p>
        </form>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="index" />
            <p align="center"><input type="submit" value="Go Back" /></p>
        </form>
    </body>
</html>
