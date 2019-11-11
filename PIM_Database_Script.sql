DROP DATABASE IF EXISTS PIM_Database;
    CREATE DATABASE PIM_Database;
    USE PIM_Database;
    
    CREATE TABLE Categories(
	Category_ID int unique not null,
	Catagory_Name varchar(255) not null,
	Catagory_Description varchar(2550) not null,
	primary key(Category_ID)
	);
    
	CREATE TABLE Attributes(
    Attribute_ID int unique not null,
	Attribute_Name varchar(255) not null,
    Attribute_Description varchar(2550) not null,
	primary key(Attribute_ID)
	);
    
    CREATE TABLE Category_Attributes(
    Category_ID int not null,
    Attribute_ID int not null,
    foreign key(Category_ID) references Categories(Category_ID),
    foreign key(Attribute_ID) references Attributes(Attribute_ID),
    primary key(Category_ID, Attribute_ID)
    );
    
    CREATE TABLE Product(
    Product_ID int unique not null auto_increment,
	Product_Name varchar(255) not null,
    Product_Description varchar(2550) not null,
    primary key(Product_ID)
    );
    
    CREATE TABLE Supplier(
    Supplier_ID int unique not null,
    Supplier_Name varchar(255) not null,
    Supplier_Description varchar(2550) not null,
    primary key(Supplier_ID)
    );
    
    CREATE TABLE Product_Supplier(
    Product_ID int unique not null,
    Supplier_ID int unique not null,
    Product_Supplier_Name varchar(255) not null,
    foreign key(Product_ID) references Product(Product_ID),
    foreign key(Supplier_ID) references Supplier(Supplier_ID),
    primary key(Product_ID, Supplier_ID)
    );
    
    CREATE TABLE Product_Categories(
    Product_ID int not null,
    Category_ID int not null,
    foreign key(Product_ID) references Product(Product_ID),
    foreign key(Category_ID) references Categories(Category_ID),
    primary key(Product_ID, Category_ID)
    );
    
    CREATE TABLE Product_Attributes(
    Product_ID int not null,
    Attribute_ID int not null,
    Attribute_Name varchar (255) not null,
    Attribute_Info varchar (2550) not null,
    foreign key(Product_ID) references Product(Product_ID),
    foreign key(Attribute_ID) references Attributes(Attribute_ID),
    primary key(Product_ID, Attribute_ID)
    );
    