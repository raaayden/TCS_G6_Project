package com.tester;

import com.bean.User;
import com.dao.UserDAO;
import com.util.DatabaseUtil;

import java.util.concurrent.ThreadLocalRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class MainTester {

	public static void main(String[] args) {
		
		//Insert for Relationship Manager (RM)
//		User RM	= new User(1001,"123","Relationship Manager","Amir","KL","amir_RM@dispur.com",123,1);
//		User RM2	= new User(2001,"123","Operator","Anis","Singapore","anis_OR@dispur.com",9827332,1);
//		User RM3	= new User(3001,"123","Admin","Ramu","India","ramu_admin@dispur.com",8827123,1);
//		
//		UserDAO	dao_RM	= new UserDAO();
//		dao_RM.addUser(RM2);
//		dao_RM.addUser(RM3);
//		System.out.println("Insert Relationship Manager Sucessfully: "+dao_RM.addUser(RM));
		
		//START SYSTEM
		
		boolean ProgramStop = false;
		
		do {
			
			Scanner s =new Scanner(System.in);
			//Home Page ------------------------------------------------
			homePage();
			int x = s.nextInt();
			
			switch(x) {
				
				//If user click Login(1) ----------------------------------
				case 1:
					
					int id = loginPage(s);
					UserDAO dao_usergroup = new UserDAO();
					String userGroup = dao_usergroup.getUserGroup(id);
					//System.out.println("usergroup: "+userGroup);
					
					
					if(userGroup.equalsIgnoreCase("Relationship Manager")) {
						
						relManDashboard(id);
						break;
						
					} else if (userGroup.equalsIgnoreCase("Operator")) {
						
						operatorDashboard(id);
						break;
						
					} else if (userGroup.equalsIgnoreCase("Admin")) {
						
					} else {
						
						int userInput	= custDashboard(id);
						break;
						
					}
				
				//If user click register(2) --------------------------------
				case 2:
					
					Scanner s1	= new Scanner(System.in);
					int newCust = RegPage(s1);
					int userInput	= custDashboard(newCust);
					
					break;
				
			}
			
			System.out.println("Exit switch case");
			
		} while (ProgramStop != true);
		

	}

	public static void homePage() {
		
		System.out.println("Hi Welcome to Dispur Wireless Portal!");
		System.out.println("-------------------------------------");
		System.out.println("1. Login");
		System.out.println("2. Registration");
		System.out.println("-------------------------------------");
		System.out.println("Please enter a number:");
	
	}
	
	public static int loginPage(Scanner s) {
		
		System.out.println("\n");
		System.out.println("Login Page");
		System.out.println("-------------------------------------");
		System.out.println("Please enter your ID:");
		int id= s.nextInt();
		System.out.println("Please enter your password:");
		String pwd = s.next();
		System.out.println("-------------------------------------");
		System.out.println("Login Successfully !");
		System.out.println("\n");
		
		return id;
		
	}
	
	public static int RegPage(Scanner s) {
		
		//Insert Customer with random no
		int min	= 5000;
		int max = 6000;
		int customerID = ThreadLocalRandom.current().nextInt(min, max + 1);
		//System.out.println("Random Number: "+customerID);
		String userGroup2 = "Customer";
		
		System.out.println("\n");
		System.out.println("Registration Page");
		System.out.println("----------------------------------------------------");
		System.out.println("Enter your name:");
		String name= s.nextLine();
		System.out.println("Enter your address:");
		String address= s.nextLine();
		System.out.println("Enter your email:");
		String email= s.nextLine();
		System.out.println("Enter your contact no:");
		int contno= s.nextInt();
		System.out.println("Enter your password:");
		String pwds = s.next();
		System.out.println("----------------------------------------------------");
		System.out.println("Registration Complete !");
		System.out.println("\n");
		
		User customer1 	= new User(customerID, pwds, userGroup2, name, address, email,contno,1);
		
		System.out.println("Please login with your new ID: "+customerID);
		UserDAO	dao_cust1	= new UserDAO();
		dao_cust1.addUser(customer1);
		
		//System.out.println("Insert Customer Sucessfully: "+dao_cust1.addUser(customer1));
		
		return customerID;
		
	}
	
	public static int custDashboard(int id) {
		
		
		UserDAO custDAO 		= new UserDAO();
		ArrayList<User> list 	= new ArrayList<User>();
		list	= custDAO.fetchUser(id);
		
		for(User u : list) {
			System.out.println("Hi "+u.getName()+" Welcome to Dispur Customer Dashboard");
			System.out.println("----------------------------------");
			System.out.println("ID\t\t: "+id);
			System.out.println("Name\t\t: "+u.getName());
			System.out.println("Email\t\t: "+u.getEmail());
			System.out.println("Address\t\t: "+u.getAddress());
			System.out.println("Contact number\t: "+u.getContact_No());
			System.out.println("Plan\t\t: "+u.getPlanName());
			System.out.println("----------------------------------");
		}
		System.out.println("1. To update your detail");
		System.out.println("2. To update your network plan");
		System.out.println("\n");
		
		Scanner num	= new Scanner(System.in);
		System.out.println("Please enter a number (1 or 2): ");
		int input	= num.nextInt();
		
		while(input < 1 || input > 2) {
			System.out.println("Please enter a valid number (1 or 2)");
			input	= num.nextInt();
		}
		
		if(input == 1) {
			
			//updateCust(id);
			
		} 
		
		return input;
		
	}
	
//	public static int updateCust(int id) {
//		
//		UserDAO custDAO 		= new UserDAO();
//		ArrayList<User> list 	= new ArrayList<User>();
//		list	= custDAO.fetchUser(id);
//		
//		for(User u : list) {
//			System.out.println("Hi "+u.getName()+" Welcome to Dispur Customer Dashboard");
//			System.out.println("----------------------------------");
//			System.out.println("Name\t\t: "+u.getName());
//			System.out.println("Email\t\t: "+u.getEmail());
//			System.out.println("Address\t\t: "+u.getAddress());
//			System.out.println("Contact number\t: "+u.getContact_No());
//			System.out.println("----------------------------------");
//		}
//		
//	}
	
	public static void relManDashboard(int id) {
		
		UserDAO RMDAO 			= new UserDAO();
		ArrayList<User> list 	= new ArrayList<User>();
		list	= RMDAO.fetchUser(id);
		
		for(User u : list) {
			System.out.println("Hi "+u.getName()+" Welcome to Relationship Manager Dashboard!");
			System.out.println("----------------------------------");
			System.out.println("ID\t\t: "+id);
			System.out.println("Name\t\t: "+u.getName());
			System.out.println("Email\t\t: "+u.getEmail());
			System.out.println("Contact number\t: "+u.getContact_No());
			System.out.println("----------------------------------");
		}
		
		UserDAO allCustDAO		= new UserDAO();
		ArrayList<User> allCust	= new ArrayList<User>();
		allCust	= allCustDAO.getAllCustomers();	
		System.out.println("USERID          |NAME          ");
		for(User u : allCust) {
			
			System.out.println(u.getUserID()+"             "+u.getName());
			
		}
		System.out.println("----------------------------------");
		System.out.println("Please enter userid to view more details: ");
		
		Scanner num	= new Scanner(System.in);
		int input	= num.nextInt();
		
		UserDAO custDAO 		= new UserDAO();
		ArrayList<User> list2 	= new ArrayList<User>();
		list2	= custDAO.fetchUser(input);
		for(User u : list2) {
			System.out.println("----------------------------------");
			System.out.println("ID\t\t: "+input);
			System.out.println("Name\t\t: "+u.getName());
			System.out.println("Email\t\t: "+u.getEmail());
			System.out.println("Contact number\t: "+u.getContact_No());
			System.out.println("Plan\t\t: "+u.getPlanName());
			System.out.println("----------------------------------");
		}
		
	}
	
	public static void operatorDashboard(int id) {
		
		UserDAO opDAO 			= new UserDAO();
		ArrayList<User> list 	= new ArrayList<User>();
		list	= opDAO.fetchUser(id);
		
		for(User u : list) {
			System.out.println("Hi "+u.getName()+" Welcome to Operator Dashboard!");
			System.out.println("----------------------------------");
			System.out.println("ID\t\t: "+id);
			System.out.println("Name\t\t: "+u.getName());
			System.out.println("Email\t\t: "+u.getEmail());
			System.out.println("Contact number\t: "+u.getContact_No());
			System.out.println("----------------------------------");
		}
		
		UserDAO allCustDAO		= new UserDAO();
		ArrayList<User> allCust	= new ArrayList<User>();
		allCust	= allCustDAO.getAllCustomers();
		System.out.println("USERID\t\t|NAME\t\t|Plan");
		for(User u : allCust) {
			
			System.out.println(u.getUserID()+"\t\t "+u.getName()+"\t\t "+u.getPlanName());
			//System.out.println("Cust_ID: "+u.getUserID()+" Cust_Name: "+u.getName()+" \tCust_Plan: "+u.getPlanName());
			
		}
		System.out.println("----------------------------------");
		System.out.println("Please enter Cust ID to delete details: ");
		
		Scanner num	= new Scanner(System.in);
		int input	= num.nextInt();
		
		opDAO.deleteCustomer(input);
		System.out.println("Cust ID: "+input+" has sucessfully removed!");
		
	}
	
}


