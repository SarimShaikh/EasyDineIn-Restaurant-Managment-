INSERT INTO ROLE(designation) VALUES ('ROLE_EMP');
INSERT INTO EMPLOYEE(email, enabled, name, password, role_id) VALUES ('easydinein@test.com', true, 'admin', '123456',1);

INSERT INTO EMPLOYEE(email, enabled, name, password, role_id) VALUES ('test@test.com', true, 'admin', '123456',1);
INSERT INTO EMPLOYEE(email, enabled, name, password, role_id) VALUES ('sarim1@gmail.com', true, 'admin', '123456',1);
INSERT INTO EMPLOYEE(email, enabled, name, password, role_id) VALUES ('test2@gmail.com', true, 'admin', '123456',1);
INSERT INTO EMPLOYEE(email, enabled, name, password, role_id) VALUES ('taimoor1@gmail.com', true, 'admin', '123456',1);

INSERT INTO User(FIRST_Name,LAST_Name,NUMBER,EMAIL,USERNAME,PASSWORD,AUTHKEY,SESSION) VALUES ('a','shaikh','0334567891','sarim1@gmail.com','a','1','123a7t12',true);

INSERT INTO Restaurant(name, address, Contact_number,Employee_id) VALUES ('Pizza Track','Autobhan Road','0332-3457812',2);
INSERT INTO Restaurant(name, address, Contact_number,Employee_id) VALUES ('Mc donald','Autobhan Road','0332-3457462',3);
INSERT INTO Restaurant(name, address, Contact_number,Employee_id) VALUES ('Piatto','Autobhan Road','0332-3453812',4);
INSERT INTO Restaurant(name, address, Contact_number,Employee_id) VALUES ('Optp','Boulevard mall','0332-3257812',5);
INSERT INTO Restaurant(name, address, Contact_number) VALUES ('Subway','Boulevard mall','0332-3297812');
INSERT INTO Restaurant(name, address, Contact_number) VALUES ('Burger House','Autobhan Road','0322-3450812');

INSERT INTO Dish(name, price, Description,ESTIMATED_TIME_TO_PREPARE,image,Restaurant_Id)VALUES ('Burger','150','cream cheese','10min','2-images.jpg',1);
INSERT INTO Dish(name, price, Description,ESTIMATED_TIME_TO_PREPARE,image,Restaurant_Id)VALUES ('Chicken Faujita','500','cheese chicken,red sause','20min','2-pizza-with-olives-hd-1080p-wallpapers-download.jpg',1);
INSERT INTO Dish(name, price, Description,ESTIMATED_TIME_TO_PREPARE,image,Restaurant_Id)VALUES ('Twister','250','cheese crunch chicken','15min','2-Chicken-Roll-(2).jpg',1);
INSERT INTO Dish(name, price, Description,ESTIMATED_TIME_TO_PREPARE,image,Restaurant_Id)VALUES ('Kebab','120','beef mince','10min','2-chicken_legs_cabbage_lemon_tomatoes_hd-wallpaper-75147-(1).jpg',1);

