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
    
    /*
    This insert code beneath is to help with developement, by creating objects that can be worked with during development, 
    and can be commented out when system goes live
    */
    INSERT INTO Categories (Category_ID, Category_Name, Category_Description) VALUES 
    (1, 'Furniture','These products can be used as furniture, and is meant to be inside, and not subject to direct weather.'),
    (2, 'Electronics','These products run on electricity, and will not function without a charger.'),
    (3, 'Consumables','These products are meant to be consumed.'),
    (4, 'Test','These are products meant for testing.'),
    (5, 'Transport', 'These products are related to means of transportation.'),
    (6, 'Entertainment', 'These products are put in the world to be entertaining.'),
    (7, 'Music', 'These products are listened to and should bring forth a wide array of emotions.'),
    (8, 'Time management', 'These products are meant to help the owner keep track of time.'),
    (9, 'Football', 'These are products related to football. Not the American kind.'),
    (10, 'Writing', 'These products are anything you might need for writing.'),
    (11, 'Flora', 'These products are related to plant life.'),
    (12, 'Fauna', 'These products are related to animal life.'),
    (13, 'Pets', 'These products are more specifically targetted towards common pets and products they would need.'),
    (14, 'Clothes', 'These products are anything that is worn as clothes or used for clothes maintenance.'),
    (15, 'Shoes', 'These products are any type of shoes or products used for shoe maintenance.');
    
	INSERT INTO Product (Product_ID, Product_Name, Product_Description, picturePath) VALUES 
    (1, 'ROHAN Bookshelf', 'This bookshelf is called ROHAN, and loos great in most rooms.', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/ROHAN.png_cjfwko.jpg'),
    (2, 'Sony KD85ZG9', 'TV that delivers eye-popping visuals that actually match creative intent.', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366571/Sony_o2ni5c.png'),
    (3, 'RAMSELE', 'A lamp from Ikea with an interresting new design', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/lamp.png_bqr8wq.jpg'),
    (4, 'Coca-cola', 'This legendary product needs no introduction', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/cola.png_kmc0bk.jpg'),
    (5, 'Coca-cola zero', 'This legendary product needs no introduction', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/colaZ.png_etxlhn.jpg'),
    (6, 'Rolex Submariner Hulk', 'Cool Submariner watch with a green dial', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Hulk_xxui2s.png'),
    (7, 'Rolex GMT-MASTER II Pepsi', 'Cool GMT-MASTER II watch with a red and blue dial', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Pepsi_sej7u7.png'),
    (8, 'Rolex Cosmograph Daytona', 'Cool Cosmograph Daytona in platinum', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Cosmograph_Daytona_rmgpa6.png'),
    (9, 'Adidas Firebird tracksuit', 'Championed by Stormzy. Seen in red but available in many colours', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Adidas_Tracksuit_Firebird_dnzz7r.png'),
    (10, 'Adidas Originals SPRT tracksuit', 'Designed by UK rapper Stormzy. Seen in white but available in many colours', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/stormzy-adidas-originals_qy38ax.png'),
    (11, 'UNIFORIA PRO Adidas football', 'Official football of the Euro 2020', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Uniforia_Pro_Football_vdlnnk.png'),
    (12, 'Strike Pro Nike football', 'Widely used football of good quality', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Strike_Pro_Nike_Football_gjf29j.png'),
    (13, 'Mince pie', 'Traditional British Christmas pie', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Mince_Pie_djjc2z.png'),
    (14, 'Psychodrama CD', 'Debut album by UK rapper Dave', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Psychodrama_xxbeaz.png'),
    (15, '2018 Mercedes-Benz G-Class', 'mid-size luxury SUV. Here seen in grey', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/2018_Mercedes_Benz_G-Class_rmuavw.png'),
	(16, 'Huawei P9 Lite', 'Lite version of the P9 phone produced by Huawei', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Huawei_P9_Lite_id2yto.png'),
    (17, 'Nike Air Force 1', 'Popular sneakers made by Nike. Customizable.', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Nike_Air_Force_1_ojpdkj.png'),
    (18, 'Cyberpunk 2077 pre-order', 'RPG. Potential game of the year 2020', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Cyberpunk_bxl9lu.png'),
    (19, 'Pepernoot', 'Small Christmas cookie', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Pepernoot_bexhnz.png'),
    (20, 'Asus ROG GL552V', 'Gaming computer used by the individual updating the database', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Asus_Rog_GL552_wkdfrr.png'),
    (21, 'World of Warcraft Classic', 'The biggest MMORPG of all time. Original game now available again!', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/WoW_Classic_rfwxze.png'),
    (22, 'World of Warcraft Shadowlands pre-order', 'Expansion for the WoW series. Playing Classic instead is recommended', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/WoW_Shadowlands_rouy0s.png'),
    (23, 'Trek Full Stache 8', 'Good quality bike from the famous bike manufacturer', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Trek_Full_Stache_8_ageobc.png'),
    (24, 'Trek Marlin 4', 'Another good quality bike from the famous bike manufacturer', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Trek_Marlin_4_biyezv.png'),
    (25, 'Haribo Goldbears', 'Everyone knows this product. Honestly', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/Haribo_Goldbears_m8b6pz.png'),
	(26, 'Trek Full Stache 8', 'Good quality bike from the famous bike manufacturer', 'https://res.cloudinary.com/dousnil0k/image/upload/v1575366570/no-img_vlrttr.png');
    
	INSERT INTO Attributes (Attribute_ID, Attribute_Name) VALUES
    (1, 'Length'), (2, 'Width/Depth'), (3, 'Height'), (4, 'Weight'), (5, 'Color'),
    (6, 'Material'), (7, 'Cable length'), (8, 'Power usage'), (9, 'Size'), (10, 'Calories'),
    (11, 'Ingredients'), (12, 'Lifetime');
    
    INSERT INTO Distributor (Distributor_ID, Distributor_Name, Distributor_Description) VALUES 
    (1, 'Ikea', 'The Swedish retailer and manufacturer of furniture.'),
    (2, 'Sony', 'Japanese tech-company with a wide variety of electronic products.'),
    (3, 'The Coca-Cola Company', 'The manufacturers of the legendary Coca-Cola product.'),
    (4, 'Huawei', 'Chinese tech-company that makes phones that look a lot like American ones.'),
    (5, 'Haribo', 'German confectionery company that has an easily recognizable yellow bear as their logo.'),
    (6, 'CD Projekt S.A.', 'Polish video game developers, known for The Witcher series and the long-anticipated game "Cyberpunk 2077".'),
    (7, 'House of Staunton', 'American manufacturer of quality chess boards, pieces and more.'),
    (8, 'Daimler AG', 'German automotive corporation headquartered in Stuttgart. Makes Mercedes-Benz and more.'),
    (9, 'Fred Perry', 'British clothes manufacturer of clothes based on the former tennis player of the same name.'),
    (10, 'Adidas', 'German sportswear and sports equipment manufacturer.'),
    (11, 'Nike', 'American sportswear and sports equipment manufacturer.'),
    (12, 'Rolex', 'Legendary luxury watch manufacturer originally from London but headquartered in Switzerland.'),
    (13, 'Asus', 'Taiwanese computer and electronics manufacturer.'),
    (14, 'Montblanc International', 'German quality pen manufacturer headquartered in Hamburg.'),
    (15, 'Trek', 'American bicycle manufacturer.'),
    (16, 'Independent music artist', 'not a specific distributor, but a solo musician on their own.'),
    (17, 'Blizzard', 'Game developers from USA, mostly known for their series "Warcraft".'),
    (18, 'Icebox Diamonds and Watches', 'The jewelry store all the rappers go to in Atlanta.'),
    (19, 'Tesco', 'British grocery retailer.');
    
	INSERT INTO Bundles(Bundle_ID, Bundle_Name, Bundle_Description) VALUES
    (1, 'The Home Package', 'This package includes everything a new home needs'),
    (2, 'The Home Package+', 'This package includes everything a new home would ever need'),
    (3, 'The Cola Bundle', 'This is a bundle of cola'),
    (4, 'The Gamer Bundle', 'This bundle is everything a new gamer would need for days of entertainment'),
    (5, 'The Music Bundle', 'All the good music ever created in the world'),
    (6, 'The Tech Bundle', 'Everything you need to get started in gaming or any online business'),
    (7, 'The Watch Bundle', 'A watch for every single occasion!'),
    (8, 'The Sportswear Bundle', 'Anything you need to get started in any sport'),
    (9, 'The Rapper Bundle', 'All the clothes and accessories you need to look like your favourite rapper!'),
    (10, 'The Christmas Bundle', 'Everything you need to eat, drink and wear at Christmas!'),
    (11, 'The Eid al-Fitr Bundle', 'Everything you need to eat, drink and wear at Eid!'),
    (12, 'The Candy Bundle', 'A wide variety of diverse candy for your taste!'),
    (13, 'The Clothes & Shoes Bundle', 'Everything you need to complete the look!'),
    (14, 'The Transport Bundle', 'Everything you need to get from point A to B conveniently!');
    
    INSERT INTO Category_Attributes(Category_ID, Attribute_ID) VALUES
    (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), 
    (2, 1), (2, 2), (2, 3), (2, 4), (2, 7), (2, 8), 
    (3, 3), (3, 4), (3, 9), (3, 10), (3, 11), (4, 12),
    (5, 1), (5, 2), (5, 3), (5, 4), (5, 5), (5, 6), (5, 9),
    (6, 9),
    (7, 1), 
    (8, 2), (8, 5), (8, 6),
    (9, 1), (9, 2), (9, 3), (9, 4), (9, 5), (9, 6), (9, 9),
    (10, 1), (10, 2), (10, 4), (10, 5), (10, 6),
    (11, 3), (11, 4), (11, 5), (11, 9), (11, 12),
    (12, 1), (12, 3), (12, 4), (12, 5), (12, 9), (12, 12),
    (13, 1), (13, 3), (13, 4), (13, 5), (13, 9), (13, 12),
    (14, 1), (14, 5), (14, 6), (14, 9),
    (15, 5), (15, 6), (15, 9);
    
    INSERT INTO Product_Categories(Product_ID, Category_ID) VALUES
    (1, 1), (2, 1), (2, 2), (3, 2), (4, 3), (5, 3), (6, 8), (7, 8), (8, 8),
    (9, 9), (9, 14), (10, 9), (10, 14), (11, 9), (12, 9), (13, 3), (14, 7), (15, 5), 
    (16, 2), (17, 15), (18, 6), (19, 3), (20, 2), (21, 6), (22, 6), (23, 5), (24, 5),
    (25, 3);
    
    INSERT INTO Product_Attributes(Product_ID, Attribute_ID, Attribute_Info) VALUES
    (1, 1, '60 cm.'), (1, 2, '40 cm.'), (1, 3, '180 cm.'), (1, 4, '15 kg.'), (1, 5, 'Brown'), (1, 6, 'Mahogany'),
    (2, 1, '1913 mm.'), (2, 2, '432 mm.'), (2, 3, '1226 mm.'), (2, 4, '20 kg.'), (2, 7, '2 meter'), (2, 8, '900 W'),
    (3, 1, '25 cm'), (3, 2, '25 cm'), (3, 3, '25 cm'), (3, 4, '2 kg.'), (3, 7, '5 meter'), (3, 8, '20 W'),
    (4, 3, '15 cm'), (4, 4, '0,5 kg'), (4, 9, '0,5 liter'), (4, 10, '200 cal.'), (4, 11, 'Sugar'),
    (5, 3, '15 cm'), (5, 4, '0,5 kg'), (5, 9, '0,5 liter'), (5, 10, '0,5 cal.'), (5, 11, 'Zero-sugar'), 
    (6, 2, '40 mm.'), (6, 5, 'grey & green'), (6, 6, 'Oystersteel'),
    (7, 2, '40 mm.'), (7, 5, 'grey & blue/red'), (7, 6, 'Oystersteel'),
    (8, 2, '40 mm.'), (8, 5, 'grey, black & blue'), (8, 6, 'Platinum'),
    (9, 5, 'Red'), (9, 6, 'Stof'), (9, 9, 'Large'),
    (10, 5, 'White'), (10, 6, 'Stof'), (10, 9, 'Large'),
    (11, 4, 'Light'), (11, 5, 'Multicolour, white'), (11, 9, '32'),
    (12, 4, 'Standard'), (12, 5, 'Multicolour, yellow'), (12, 9, '34'),
    (13, 4, '40g'), (13, 10, '243 kcal'), (13, 11, 'mincemeat and pie'),
    (14, 1, '51:12 minutes'),
    (15, 5, 'Grey'), (15, 6, 'Stof'), (15, 9, 'Mid-size'),
    (16, 1, '11 cm'), (16, 2, '5 cm'), (16, 4, '42 g'), (16, 7, '1 m'), (16, 8, '12 hours'),
    (17, 5, 'White'), (17, 6, 'Stof'), (17, 9, '43'),
    (18, 9, '42 GB'),
    (19, 4, '0,22 g'), (19, 9, 'pretty small'), (19, 10, '512 /100 g'), (19, 11, 'sugar and butter'), 
    (20, 1, '38 cm'), (20, 2, '45 cm'), (20, 3, '38 cm'), (20, 4, '1,7 kg'), (20, 7, '2m'), (20, 8, '2 hours'),
    (21, 9, '65 GB'),
    (22, 9, '73 GB'),
    (23, 1, '1 m 40 cm'), (23, 2, '5 cm'), (23, 3, '67 cm'), (23, 4, '4,2 kg'), (23, 5, 'Grey and green'), (23, 6, 'steel'), (23, 9, 'adult'),
    (24, 1, '1 m 45 cm'), (24, 2, '4 cm'), (24, 3, '65 cm'), (24, 4, '3,9 kg'), (24, 5, 'White and blue'), (24, 6, 'steel'), (24, 9, 'adult'),
    (25, 4, 'negligent'), (25, 9, 'very small'), (25, 10, '451 kcal'), (25, 11, 'gelatine, sugar');
    
	INSERT INTO Product_Distributor(Product_ID, Distributor_ID) VALUES
    (1, 1), (2, 2), (3, 1), (4, 1), (4, 3), (5, 1), (5, 3), (6, 4), 
    (6, 12), (7, 12), (8, 12), (9, 10), (9, 11), (9, 12), (12, 11),
    (13, 6), (14, 16), (15, 8), (16, 4), (17, 11), (18, 6), (19, 5), 
    (19, 19), (20, 13), (21, 17), (22, 17), (23, 15), (24, 15),
    (25, 5);
    
    INSERT INTO Product_Bundles(Product_ID, Bundle_ID, Product_amount) VALUES
    (1, 1, 1), (1, 2, 2), (2, 2, 1), (3, 1, 1), (3, 2, 1), (4, 3, 1), (5, 3, 1),
    (6, 7, 1), (6, 9, 1), (7, 7, 1), (7, 9, 1), (8, 9, 1), (8, 7, 1), (9, 8, 1),
    (9, 9, 1), (10, 8, 1), (10, 9, 1), (13, 4, 1), (13, 6, 1), (14, 5, 1), (15, 9, 1),
    (16, 6, 1), (16, 4, 1), (17, 8, 1), (17, 9, 1), (17, 13, 1), (18, 4, 1),
    (19, 10, 14), (19, 12, 8), (20, 4, 1), (20, 6, 1), (21, 4, 1), (22, 4, 1),
    (23, 14, 1), (24, 14, 1), (25, 12, 40);  