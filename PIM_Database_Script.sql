DROP DATABASE IF EXISTS PIM_Database;
    CREATE DATABASE PIM_Database;
    USE PIM_Database;
    
    CREATE TABLE Categories(
	Category_ID int unique not null auto_increment,
	Category_Name varchar(255) unique not null,
	Category_Description varchar(2550) not null,
	primary key(Category_ID)
	);
    
	CREATE TABLE Attributes(
    Attribute_ID int unique auto_increment not null,
	Attribute_Name varchar(255) not null,
	primary key(Attribute_ID)
	);
    
    CREATE TABLE Distributor(
    Distributor_ID int unique not null auto_increment,
    Distributor_Name varchar(255) not null,
    Distributor_Description varchar(2550) not null,
    primary key(Distributor_ID)
    );
    
    CREATE TABLE Bundles(
    Bundle_ID int unique not null auto_increment,
	Bundle_Name varchar(255) not null,
    Bundle_Description varchar(2550) not null,
    primary key(Bundle_ID)
    );
    
	CREATE TABLE Product(
    Product_ID int unique not null auto_increment,
	Product_Name varchar(255) not null,
    Product_Description varchar(2550) not null,
    picturePath varchar(100),
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
    Attribute_Info varchar (2550),
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
    
    /*
    This insert code beneath is to help with developement, by creating objects that can be worked with during development, 
    and can be commented out when system goes live
    */
    INSERT INTO Categories (Category_ID, Category_Name, Category_Description) VALUES 
    (1, 'Furniture','These products can be used as furniture, and and is meant to be inside, and not subject to direct weather.'),
    (2, 'Electronics','These products run on electricity, and will not function without a charger.'),
    (3, 'Consumables','These products are meant to be consumed.'),
    (4, 'Test','These are products meant for testing.');
    
    INSERT INTO Attributes (Attribute_ID, Attribute_Name) VALUES
    (1, 'Length'),
    (2, 'Width/Depth'),
    (3, 'Height'),
    (4, 'Weight'),
    (5, 'Color'),
    (6, 'Material'),
    (7, 'Cable length'),
    (8, 'Power usage'),
    (9, 'Size'),
    (10, 'Calories'),
    (11, 'Ingredients'),
    (12, 'Complete');
    
    INSERT INTO Distributor (Distributor_ID, Distributor_Name, Distributor_Description) VALUES 
    (1, 'Ikea', 'Ikea, the swedish retailer and manufacturer of furniture.'),
    (2, 'Sony', 'Japanese tech-company with a wide variety in electronic products.'),
    (3, 'The Coca-Cola Company', 'The manufacturers of the legendary Coca-Cola product.'),
    (4, 'Programmers', 'These products was placed here by the programmers for tests');
    
	INSERT INTO Bundles(Bundle_ID, Bundle_Name, Bundle_Description) VALUES
    (1, 'The Home Package', 'This package includes everying a new home needs'),
    (2, 'The Home Package+', 'This package includes everying a new home would ever need'),
    (3, 'The Cola Bundle', 'This is a bundle of cola');
    
    INSERT INTO Product (Product_ID, Product_Name, Product_Description, picturePath) VALUES 
    (1, 'ROHAN Bookshelf', 'This bookshelf is called ROHAN, and loos great in most rooms.', 'ROHAN.img'),
    (2, 'Sony KD85ZG9', 'TV that delivers eye-popping visuals that actually match creative intent.', 'Sony.img'),
    (3, 'RAMSELE', 'A lamp from Ikea with an interresting new design', 'lamp.img'),
    (4, 'Coca-cola', 'This legendary product needs no introduction', 'cola.img'),
    (5, 'Coca-cola zero', 'This legendary product needs no introduction', 'colaZ.img'),
    (6, 'Test Product', 'This product is for testing only', 'test.img');
    
    INSERT INTO Category_Attributes(Category_ID, Attribute_ID) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (2, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (2, 7),
    (2, 8),
    (3, 3),
    (3, 4),
    (3, 9),
    (3, 10),
    (3, 11),
    (4, 12);
        
    INSERT INTO Product_Categories(Product_ID, Category_ID) VALUES
    (1, 1),
    (2, 1),
    (2, 2),
    (3, 2),
    (4, 3),
    (5, 3),
    (6, 4);
        
    INSERT INTO Product_Attributes(Product_ID, Attribute_ID, Attribute_Info) VALUES
    (1, 1, '60 cm.'),
    (1, 2, '40 cm.'),
    (1, 3, '180 cm.'),
    (1, 4, '15 kg.'),
    (1, 5, 'Brown'),
    (1, 6, 'Mahogany'),
    (2, 1, '1913 mm.'),
    (2, 2, '432 mm.'),
    (2, 3, '1226 mm.'),
    (2, 4, '20 kg.'),
    (2, 7, '2 meter'),
    (2, 8, '900 W'),
    (3, 1, '25 cm'),
    (3, 2, '25 cm'),
    (3, 3, '25 cm'),
    (3, 4, '2 kg.'),
    (3, 7, '5 meter'),
    (3, 8, '20 W'),
    (4, 3, '15 cm'),
    (4, 4, '0,5 kg'),
    (4, 9, '0,5 liter'),
    (4, 10, '200 cal.'),
    (4, 11, 'Sugar'),
    (5, 3, '15 cm'),
    (5, 4, '0,5 kg'),
    (5, 9, '0,5 liter'),
    (5, 10, '0,5 cal.'),
    (5, 11, 'Zero-sugar'),
    (6, 12, 'Not Complete');
    
	INSERT INTO Product_Distributor(Product_ID, Distributor_ID) VALUES
    (1, 1),
    (2, 2),
    (3, 1),
    (4, 1),
    (4, 3),
    (5, 1),
    (5, 3),
    (6, 4);
        
    INSERT INTO Product_Bundles(Product_ID, Bundle_ID, Product_amount) VALUES
    (1, 1, 1), (1, 2, 2), (2, 2, 1), (3, 1, 1), (3, 2, 1), (4, 3, 1), (5, 3, 1);  
