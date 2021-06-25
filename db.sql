-- Create table for User
create table DispurUser (
userID number(10) primary key,
userPWD varchar(255),
userGroup varchar(255),
Name varchar(255),
Address varchar(255),
Email varchar(255),
Contact_No number(12),
planID number(10) references Plan(planID)
);

-- Create table for Plan
create table Plan (
planID number(10) primary key,
Plan_Name varchar(255),
Type_Of_Plan varchar(255),
Tariff varchar(30),
Validity number(3),
Rental number(6,2)
);


-- Insert Relationship Manager, Operator and Admin
INSERT INTO DispurUser
(userID, userPWD, userGroup, Name, Address, Email, Contact_No, planID)
VALUES
(1001,'123','Relationship Manager','Amir','KL','amir_RM@dispur.com',123,1)

INSERT INTO DispurUser
(userID, userPWD, userGroup, Name, Address, Email, Contact_No, planID)
VALUES
(2001,'123','Operator','Anis','Singapore','anis_OR@dispur.com',9827332,1)

INSERT INTO DispurUser
(userID, userPWD, userGroup, Name, Address, Email, Contact_No, planID)
VALUES
(3001,'123','Admin','Kumar','India','ramu_admin@dispur.com',8827123,1)


-- Insert Plan none
INSERT INTO Plan 
(planID, Plan_Name, Type_Of_Plan, Tariff, Validity, Rental) 
VALUES 
(1, 'NONE', 'NONE', '0/0', 0, 0.0);

INSERT INTO Plan 
(planID, Plan_Name, Type_Of_Plan, Tariff, Validity, Rental) 
VALUES 
(9125, 'Dispur5G', 'Data', '0.5/60', 90, 100.50);

-- Select function
select * from Plan left join DispurUser ON DispurUser.planID = Plan.planID;
SELECT u.NAME, u.ADDRESS, u.EMAIL, u.CONTACT_NO, p.PLAN_NAME from DispurUser u LEFT JOIN Plan p ON p.planID = u.planID
SELECT u.userID, u.NAME, u.EMAIL, u.CONTACT_NO, p.PLAN_NAME from DispurUser u LEFT JOIN Plan p ON p.planID = u.planID where USERGROUP = 'Customer'
select * from Plan;
select * from DispurUser;

SELECT p.planID, p.plan_name, p.type_of_plan, p.tariff, p.validity, p.rental from Plan p LEFT JOIN DispurUser u ON u.planID = p.planID WHERE p.planID != (SELECT planID FROM DispurUser WHERE userID = 5405)

DROP TABLE DispurUser;
DROP TABLE Plan;
