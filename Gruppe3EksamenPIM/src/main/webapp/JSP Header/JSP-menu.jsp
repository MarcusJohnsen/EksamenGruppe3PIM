<%-- 
    Document   : JSP-menu
    Created on : 04-12-2019, 10:54:40
    Author     : Andreas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <link href="css/StyleMenu.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        form{
            display: inline; 
            margin: 0;
        }
    </style>
</head>
<body>
    <div class="sidenav" >
        <p align="center"><img src="decorations/PimLogo.png" style="width: 120px; height: 120px;"/></p>
        <form action="FrontController" method="POST">
            <input type="hidden" name="command" value="simpleSearch" />
            <p align="center"><input type="text" name="search" value="" placeholder="Search..."/> <input type="submit" value="Search" /></p> 
        </form>
        <form action="FrontController" method="POST" id="home">
            <input type="hidden" name="command" value="goToJsp" />
            <input type="hidden" name="goToJsp" value="index" />
            <a href="#" onclick="document.getElementById('home').submit()">Home Page</a>
        </form>

        <button class="dropdown-btn">Products 
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-container">
            <form action="FrontController" id="viewProd" method="POST">
                <input type="hidden" name="command" value="goToJsp" />
                <input type="hidden" name="goToJsp" value="viewPIMObjectList" />
                <input type="hidden" name="PIMObjectType" value="Product"/>
                <a href="#" onclick="document.getElementById('viewProd').submit()">Product List</a>
            </form>
            <form action="FrontController" id="searchProd" method="POST">
                <input type="hidden" name="command" value="searchSelection" />
                <input type="hidden" name="PIMObjectType" value="Product"/>
                <a href="#" onclick="document.getElementById('searchProd').submit()">Search Product</a>
            </form>
            <form action="FrontController" id="newProd" method="POST">
                <input type="hidden" name="command" value="goToJsp" />
                <input type="hidden" name="goToJsp" value="newProduct" />
                <a href="#" onclick="document.getElementById('newProd').submit()">New Product</a>
            </form>
        </div>
        <button class="dropdown-btn">Categories 
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-container">
            <form action="FrontController" id="viewCat" method="POST">
                <input type="hidden" name="command" value="goToJsp" />
                <input type="hidden" name="goToJsp" value="viewPIMObjectList" />
                <input type="hidden" name="PIMObjectType" value="Category"/>
                <a href="#" onclick="document.getElementById('viewCat').submit()">Category List</a>
            </form>
            <form action="FrontController" id="searchCat" method="POST">
                <input type="hidden" name="command" value="searchSelection" />
                <input type="hidden" name="PIMObjectType" value="Category"/>
                <a href="#" onclick="document.getElementById('searchCat').submit()">Search Category</a>
            </form>
            <form action="FrontController" id="newCat" method="POST">
                <input type="hidden" name="command" value="goToJsp" />
                <input type="hidden" name="goToJsp" value="newCategory" />
                <a href="#" onclick="document.getElementById('newCat').submit()">New Category</a>
            </form>
        </div>
        <button class="dropdown-btn">Attributes 
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-container">
            <form action="FrontController" id="viewAtt" method="POST">
                <input type="hidden" name="command" value="goToJsp" />
                <input type="hidden" name="goToJsp" value="viewPIMObjectList" />
                <input type="hidden" name="PIMObjectType" value="Attribute"/>
                <a href="#" onclick="document.getElementById('viewAtt').submit()">Attribute List</a>
            </form>
            <form action="FrontController" id="newAtt" method="POST">
                <input type="hidden" name="command" value="goToJsp" />
                <input type="hidden" name="goToJsp" value="newAttribute" />
                <a href="#" onclick="document.getElementById('newAtt').submit()">New Attribute</a>
            </form>
        </div>
        <button class="dropdown-btn">Distributors 
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-container">
            <form action="FrontController" id="viewDist" method="POST">
                <input type="hidden" name="command" value="goToJsp" />
                <input type="hidden" name="goToJsp" value="viewPIMObjectList" />
                <input type="hidden" name="PIMObjectType" value="Distributor"/>
                <a href="#" onclick="document.getElementById('viewDist').submit()">Distributor List</a>
            </form>
            <form action="FrontController" id="searchDist" method="POST">
                <input type="hidden" name="command" value="searchSelection" />
                <input type="hidden" name="PIMObjectType" value="Distributor"/>
                <a href="#" onclick="document.getElementById('searchDist').submit()">Search Distributor</a>
            </form>
            <form action="FrontController" id="newDist" method="POST">
                <input type="hidden" name="command" value="goToJsp" />
                <input type="hidden" name="goToJsp" value="newDistributor" />
                <a href="#" onclick="document.getElementById('newDist').submit()">New Distributor</a>
            </form>
        </div>
        <button class="dropdown-btn">Bundles 
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-container">
            <form action="FrontController" id="viewBundle" method="POST">
                <input type="hidden" name="command" value="goToJsp" />
                <input type="hidden" name="goToJsp" value="viewPIMObjectList" />
                <input type="hidden" name="PIMObjectType" value="Bundle"/>
                <a href="#" onclick="document.getElementById('viewBundle').submit()">Bundle List</a>
            </form>
            <form action="FrontController" id="searchBundle" method="POST">
                <input type="hidden" name="command" value="searchSelection" />
                <input type="hidden" name="PIMObjectType" value="Bundle"/>
                <a href="#" onclick="document.getElementById('searchBundle').submit()">Search Bundle</a>
            </form>
            <form action="FrontController" id="newBundle" method="POST">
                <input type="hidden" name="command" value="goToJsp" />
                <input type="hidden" name="goToJsp" value="newBundle" />
                <a href="#" onclick="document.getElementById('newBundle').submit()">New Bundle</a>
            </form>
        </div>
    </div>
    <script>
        var dropdown = document.getElementsByClassName("dropdown-btn");
        var i;

        for (i = 0; i < dropdown.length; i++) {
            dropdown[i].addEventListener("click", function () {
                this.classList.toggle("active");
                var dropdownContent = this.nextElementSibling;
                if (dropdownContent.style.display === "block") {
                    dropdownContent.style.display = "none";
                } else {
                    dropdownContent.style.display = "block";
                }
            });
        }
    </script>