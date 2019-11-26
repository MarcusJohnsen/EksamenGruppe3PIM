<%-- 
    Document   : index
    Created on : 11-11-2019, 08:41:54
    Author     : Andreas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PIM System</title>
    </head>
    <body>
        <h1 align="center">Products</h1>
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
        
        <form action="FrontController">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="newAttribute" />
            <p align="center"><input type="submit" value="New Attribute" style="width: 150px; height: 25px"/></p>
        </form>
        
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
        
    </body>
</html>
