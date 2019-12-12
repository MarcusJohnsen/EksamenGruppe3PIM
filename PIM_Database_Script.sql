DROP DATABASE IF EXISTS PIM_Database;
    CREATE DATABASE PIM_Database;
    USE PIM_Database;
    
    CREATE TABLE Categories(
	Category_ID int unique not null auto_increment,
	Category_Name varchar(255) unique not null,
	Category_Description TEXT not null,
	primary key(Category_ID)
	);
    
	CREATE TABLE Attributes(
    Attribute_ID int unique auto_increment not null,
	Attribute_Name TEXT not null,
	primary key(Attribute_ID)
	);
    
    CREATE TABLE Distributor(
    Distributor_ID int unique not null auto_increment,
    Distributor_Name TEXT not null,
    Distributor_Description TEXT not null,
    primary key(Distributor_ID)
    );
    
    CREATE TABLE Bundles(
    Bundle_ID int unique not null auto_increment,
	Bundle_Name TEXT not null,
    Bundle_Description TEXT not null,
    primary key(Bundle_ID)
    );
    
	CREATE TABLE Product(
    Product_ID int unique not null auto_increment,
	Product_Name TEXT not null,
    Product_Description TEXT not null,
    picturePath TEXT,
    primary key(Product_ID)
    );
    
    CREATE TABLE Category_Attributes(
    Category_ID int not null,
    Attribute_ID int not null,
    foreign key(Category_ID) references Categories(Category_ID),
    foreign key(Attribute_ID) references Attributes(Attribute_ID),
    primary key(Category_ID, Attribute_ID)
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
    Attribute_Info TEXT,
    foreign key(Product_ID) references Product(Product_ID),
    foreign key(Attribute_ID) references Attributes(Attribute_ID),
    primary key(Product_ID, Attribute_ID)
    );
    
	CREATE TABLE Product_Distributor(
    Product_ID int not null,
    Distributor_ID int not null,
    foreign key(Product_ID) references Product(Product_ID),
    foreign key(Distributor_ID) references Distributor(Distributor_ID),
    primary key(Product_ID, Distributor_ID)
    );
    
    CREATE TABLE Product_Bundles(
    Product_ID int not null,
    Bundle_ID int not null,
    Product_amount int not null,
    foreign key(Product_ID) references Product(Product_ID),
    foreign key(Bundle_ID) references Bundles(Bundle_ID),
    primary key(Product_ID, Bundle_ID)
    );