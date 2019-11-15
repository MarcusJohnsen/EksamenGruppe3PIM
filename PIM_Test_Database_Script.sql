DROP DATABASE IF EXISTS Fake_PIM_Database;
    CREATE DATABASE Fake_PIM_Database;
    USE Fake_PIM_Database;
    
    CREATE TABLE Categories_Test(
	Category_ID int unique not null auto_increment,
	Category_Name varchar(255) unique not null,
	Category_Description varchar(2550) not null,
	primary key(Category_ID)
	);
    
    INSERT INTO Categories_Test (Category_Name, Category_Description) VALUES 
    ('Tests','These are tests'),
    ('Products','These are products'),
    ('Unknowns','We are not sure what these are');
    
    
    CREATE TABLE Product_Test(
    Product_ID int unique not null auto_increment,
	Product_Name varchar(255) not null,
    Product_Description varchar(2550) not null,
    picturePath varchar(100),
    primary key(Product_ID)
    );
    INSERT INTO Product_Test (Product_Name, Product_Description, picturePath) VALUES 
    ('Test Product', 'This product is for testing', 'test.jpg'),
    ('Test Nr. 2', 'This is the 2nd testing product', 'test.img'),
    ('Final Test', 'This is the ultimate testing product', 'test.png');
    
    
    CREATE TABLE Product_Distributor_Test(
    Product_ID int not null,
    Product_Distributor_Name varchar(255) not null,
    foreign key(Product_ID) references Product_Test(Product_ID),
    primary key(Product_ID, Product_Distributor_Name)
    );
    INSERT INTO Product_Distributor_Test(Product_ID, Product_Distributor_Name) VALUES
    (1,'Mr. Programmer'),
    (2,'Miss. Tester'),
    (2,'Mr. Tester'),
    (3,'Mr. Programmer'),
    (3,'Mr. Tester'),
    (3,'Miss. Tester');
    