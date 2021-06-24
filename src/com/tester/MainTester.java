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
//		
//		UserDAO	dao_RM	= new UserDAO();
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
						System.out.println("Relationship Manager !");
						break;
					} else {
						custDashboard(id);
						break;
					}
				
				//If user click register(2) --------------------------------
				case 2:
					
					//Insert Customer with random no
					int min	= 5000;
					int max = 6000;
					int customerID = ThreadLocalRandom.current().nextInt(min, max + 1);
					//System.out.println("Random Number: "+customerID);
					String userGroup2 = "Customer";
					
					Scanner s1	= new Scanner(System.in);
					System.out.println("\n");
					System.out.println("Registration Page");
					System.out.println("----------------------------------------------------");
					System.out.println("Enter your name:");
					String name= s1.nextLine();
					System.out.println("Enter your address:");
					String address= s1.nextLine();
					System.out.println("Enter your email:");
					String email= s1.nextLine();
					System.out.println("Enter your contact no:");
					int contno= s1.nextInt();
					System.out.println("Enter your password:");
					String pwds = s1.next();
					System.out.println("----------------------------------------------------");
					System.out.println("\n");
					
					User customer1 	= new User(customerID, pwds, userGroup2, name, address, email,contno,1);
					UserDAO	dao_cust1	= new UserDAO();
					System.out.println("Please login with your new ID: "+customerID);
					System.out.println("Insert Customer Sucessfully: "+dao_cust1.addUser(customer1));
					break;
				
			}
			
			System.out.println("Exit switch case");
			ProgramStop = true;
			
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
	
	public static void custDashboard(int id) {
		
		
		UserDAO custDAO 		= new UserDAO();
		ArrayList<User> list 	= new ArrayList<User>();
		list	= custDAO.fetchCustomer(id);
		
		for(User u : list) {
			System.out.println("Hi "+u.getName()+" Welcome to Dispur Customer Dashboard");
			System.out.println("----------------------------------");
			System.out.println("ID : "+id);
			System.out.println("Name : "+u.getName());
			System.out.println("Email : "+u.getEmail());
			System.out.println("Contact number : "+u.getContact_No());
			System.out.println("Plan : "+u.getPlanName());
			System.out.println("----------------------------------");
		}
		
		
		
	}
}


