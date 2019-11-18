DROP DATABASE IF EXISTS Fake_PIM_Database;
    CREATE DATABASE Fake_PIM_Database;
    USE Fake_PIM_Database;
    
    CREATE TABLE Fake_PIM_Database.Categories_Test like PIM_Database.Categories;
    INSERT INTO Categories_Test (Category_Name, Category_Description) VALUES 
    ('Tests','These are tests'),
    ('Products','These are products'),
    ('Unknowns','We are not sure what these are');
    
    CREATE TABLE Fake_PIM_Database.Product_Test like PIM_Database.Product;
    INSERT INTO Product_Test (Product_Name, Product_Description, picturePath) VALUES 
    ('Test Product', 'This product is for testing', 'test.jpg'),
    ('Test Nr. 2', 'This is the 2nd testing product', 'test.img'),
    ('Final Test', 'This is the ultimate testing product', 'test.png');
    
    CREATE TABLE Fake_PIM_Database.Product_Distributor_Test like PIM_Database.Product_Distributor;
    INSERT INTO Product_Distributor_Test(Product_ID, Product_Distributor_Name) VALUES
    (1,'Mr. Programmer'),
    (2,'Miss. Tester'),
    (2,'Mr. Tester'),
    (3,'Mr. Programmer'),
    (3,'Mr. Tester'),
    (3,'Miss. Tester');
    