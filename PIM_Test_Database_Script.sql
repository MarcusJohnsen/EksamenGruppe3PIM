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
        
    CREATE TABLE Fake_PIM_Database.Attributes_Test like PIM_Database.Attributes;
    INSERT INTO Attributes_Test (Attribute_ID, Attribute_Name) VALUES
    (1,'Size'),
    (2,'Color'),
    (3,'Height'),
    (4,'Weight'),
    (5,'Length');
    
    CREATE TABLE Fake_PIM_Database.Product_Distributor_Test like PIM_Database.Product_Distributor;
    ALTER TABLE Product_Distributor_Test ADD FOREIGN KEY(Product_ID) REFERENCES Product_Test(Product_ID);
    INSERT INTO Product_Distributor_Test(Product_ID, Product_Distributor_Name) VALUES
    (1,'Mr. Programmer'),
    (2,'Miss. Tester'),
    (2,'Mr. Tester'),
    (3,'Mr. Programmer'),
    (3,'Mr. Tester'),
    (3,'Miss. Tester');
    
    CREATE TABLE Fake_PIM_Database.Product_Categories_Test like PIM_Database.Product_Categories;
    ALTER TABLE Product_Categories_Test ADD FOREIGN KEY(Category_ID) REFERENCES Categories_Test(Category_ID);
    ALTER TABLE Product_Categories_Test ADD FOREIGN KEY(Product_ID) REFERENCES Product_Test(Product_ID);
    INSERT INTO Product_Categories_Test (Product_ID, Category_ID) VALUES
    (1,1),
    (1,2),
    (1,3),
    (2,3),
    (3,1);
    
    CREATE TABLE Fake_PIM_Database.Category_Attributes_Test like PIM_Database.Category_attributes;
    ALTER TABLE Category_Attributes_Test ADD FOREIGN KEY(Category_ID) REFERENCES Categories_Test(Category_ID);
    ALTER TABLE Category_Attributes_Test ADD FOREIGN KEY(Attribute_ID) REFERENCES Attributes_Test(Attribute_ID);
    INSERT INTO Category_Attributes_Test (Category_ID, Attribute_ID) VALUES
    (1,1),
    (1,2),
    (1,3),
    (2,3),
    (3,1);
    
    CREATE TABLE Fake_PIM_Database.Product_Attributes_Test like PIM_Database.Product_Attributes;
    ALTER TABLE Product_Attributes_Test ADD FOREIGN KEY(Product_ID) REFERENCES Product_Test(Product_ID);
    ALTER TABLE Product_Attributes_Test ADD FOREIGN KEY(Attribute_ID) REFERENCES Attributes_Test(Attribute_ID);
    INSERT INTO Product_Attributes_Test (Product_ID, Attribute_ID, Attribute_Info) VALUES
    (1,1,"Bla"),
    (1,2,"Blabla"),
    (1,3,"Blablabla"),
    (2,3,"Blablablabla"),
    (3,1,"Blablablablabla");
    