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


create table Plan (
planID number(10) primary key,
Plan_Name varchar(255),
Type_Of_Plan varchar(255),
Tariff varchar(30),
Validity number(3),
Rental number(3,2)
);

INSERT INTO Plan 
(planID, Plan_Name, Type_Of_Plan, Tariff, Validity, Rental) 
VALUES 
(1, 'NONE', 'NONE', '0/0', 0, 0.0);

select * from Plan left join DispurUser ON DispurUser.planID = Plan.planID;
SELECT u.NAME, u.ADDRESS, u.EMAIL, u.CONTACT_NO, p.PLAN_NAME from DispurUser u LEFT JOIN Plan p ON p.planID = u.planID
SELECT u.userID, u.NAME, u.EMAIL, u.CONTACT_NO, p.PLAN_NAME from DispurUser u LEFT JOIN Plan p ON p.planID = u.planID where USERGROUP = 'Customer'

select * from Plan;
select * from DispurUser;

DROP TABLE DispurUser;