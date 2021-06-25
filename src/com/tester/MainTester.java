package com.tester;

import com.bean.User;
import com.bean.Plan;
import com.dao.PlanDAO;
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
						
						//Relationship manager dashboard
						relManDashboard(id);
						break;
						
					} else if (userGroup.equalsIgnoreCase("Operator")) {
						
						//Operator dashboard
						operatorDashboard(id);
						break;
						
					} else if (userGroup.equalsIgnoreCase("Admin")) {
						
						//Admin dashboard
						adminDashboard(id);
						break;
						
					} else {
						
						//Customer Dashboard
						custDashboard(id);
						break;
						
					}
				
				//If user click register(2) --------------------------------
				case 2:
					
					//Customer Registration
					Scanner s1	= new Scanner(System.in);
					int newCust = RegPage(s1);
					custDashboard(newCust);
					
				
			}
			
			//System.out.println("Exit switch case");
			
		} while (ProgramStop != true);
		

	}

	
	//Home Page method ---------------------------------------------
	public static void homePage() {
		
		System.out.println("Hi Welcome to Dispur Wireless Portal!");
		System.out.println("-------------------------------------");
		System.out.println("1. Login");
		System.out.println("2. Registration");
		System.out.println("-------------------------------------");
		System.out.println("Please enter a number:");
	
	}
	
	//Login Page method --------------------------------------------
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
	
	
	//Registration Page method ---------------------------------------
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
		System.out.println("Enter your contact no: ");
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
	
	
	//Customer Dashboard method ----------------------------------------
	public static void custDashboard(int id) {
		
		
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
		System.out.println("Please enter a number (1 or 2) or 0 to exit: ");
		int input	= num.nextInt();
		
		while(input < 0 || input > 2) {
			System.out.println("Please enter a valid number (1 or 2) or 0 to exit");
			input	= num.nextInt();
		}
		
		if(input == 1) {
			
			//customer update details
			updateCust(id);
			
		} else if (input == 2) {
			
			//customer update network plan
			PlanPage(id);
			
		} else {
			
			System.out.println("\n\n\n");
			
		}
		
		
	}
	
	
	//Update Customer method ----------------------------------------
	public static void updateCust(int id) {
		
		UserDAO custDAO 		= new UserDAO();
		ArrayList<User> list 	= new ArrayList<User>();
		list	= custDAO.fetchUser(id);
		
		for(User u : list) {
			System.out.println("Hi "+u.getName()+", Update Detail Page");
			System.out.println("----------------------------------");
			System.out.println("1.Name\t\t\t: "+u.getName());
			System.out.println("2.Email\t\t\t: "+u.getEmail());
			System.out.println("3.Address\t\t: "+u.getAddress());
			System.out.println("4.Contact number\t: "+u.getContact_No());
			System.out.println("----------------------------------");
		}
		Scanner num	= new Scanner(System.in);
		System.out.println("Please select which to update (1 to 4) or 5 to back: ");
		int input	= num.nextInt();
		
		while(input < 1 || input > 5) {
			System.out.println("Please enter a valid number (1 to 4) or 5 to back");
			input	= num.nextInt();
		}
		
		if(input == 1) {//update name
			System.out.println("Please enter new name: ");
			String userInput	= num.next();
			custDAO.updateCustomerName(id, userInput);
			System.out.println("Name sucessfully changed!");
			custDashboard(id);
		} else if (input == 3) {//update email
			System.out.println("Please enter new address: ");
			String userInput	= num.next();
			custDAO.updateCustomerEmail(id, userInput);
			System.out.println("Email sucessfully changed!");
			custDashboard(id);
		} else if (input == 2) {
			System.out.println("Please enter new email: ");
			String userInput	= num.next();
			custDAO.updateCustomerAdd(id, userInput);
			System.out.println("Address sucessfully changed!");
			custDashboard(id);
		} else if (input == 4) {
			System.out.println("Please enter new contact no: ");
			int userInput	= num.nextInt();
			custDAO.updateCustomerContact(id, userInput);
			System.out.println("ContactNo sucessfully changed!");
			custDashboard(id);
		} else {
			custDashboard(id);
		}
		
	}
	
	
	//Relationship Manager Dashboard method -------------------------------------
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
			System.out.println("Details for Customer "+u.getName()+" !");
			System.out.println("----------------------------------");
			System.out.println("ID\t\t: "+input);
			System.out.println("Name\t\t: "+u.getName());
			System.out.println("Email\t\t: "+u.getEmail());
			System.out.println("Address\t\t: "+u.getAddress());
			System.out.println("Contact number\t: "+u.getContact_No());
			System.out.println("Plan\t\t: "+u.getPlanName());
			System.out.println("----------------------------------");
		}
		
		System.out.println("\n\n");
		System.out.println("Please press any key to continue or x to exit");
		String cont	= num.next();
		if(!cont.equalsIgnoreCase("x")) {
			
			relManDashboard(id);
			
		}
		
	}
	
	
	//Operator Dashboard method ----------------------------------------
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
		
		System.out.println("\n\n");
		System.out.println("Please press any key to continue or x to exit");
		String cont	= num.next();
		if(!cont.equalsIgnoreCase("x")) {
			
			operatorDashboard(id);
			
		}
		
	}
	
	
	//Admin Dashboard method ----------------------------------------
	public static void adminDashboard(int id) {
		
		UserDAO opDAO 			= new UserDAO();
		ArrayList<User> list 	= new ArrayList<User>();
		list	= opDAO.fetchUser(id);
		
		for(User u : list) {
			System.out.println("Hi "+u.getName()+" Welcome to Admin Dashboard!");
			System.out.println("----------------------------------");
			System.out.println("ID\t\t: "+id);
			System.out.println("Name\t\t: "+u.getName());
			System.out.println("Email\t\t: "+u.getEmail());
			System.out.println("Contact number\t: "+u.getContact_No());
			System.out.println("----------------------------------");
		}
		
		
		PlanDAO allPlanDAO		= new PlanDAO();
		ArrayList<Plan> allPlan	= new ArrayList<Plan>();
		allPlan					= allPlanDAO.getAllPlans();
		System.out.println("Available Plan");
		System.out.println("PlanID\t\t|Plan Name\t\t|Type Of Plan\t\t|Tariff\t\t|Validity\t\t|Rental");
		for(Plan p : allPlan) {
			
			System.out.println(p.getPlanID()+"\t\t "+p.getPlanName()+"\t\t "+p.getTypeOfPlan()+"\t\t\t "+p.getTariff()+"\t\t "+p.getValidity()+"\t\t\t "+p.getRental());
			//System.out.println("Cust_ID: "+u.getUserID()+" Cust_Name: "+u.getName()+" \tCust_Plan: "+u.getPlanName());
			
		}
		System.out.println("----------------------------------");
		System.out.println("1. To add new network plan");
		System.out.println("2. To edit existing network plan");
		
		Scanner num	= new Scanner(System.in);
		System.out.println("Please enter a number (1 or 2) or 0 to exit: ");
		int input	= num.nextInt();
		
		while(input < 0 || input > 2) {
			System.out.println("Please enter a valid number (1 or 2) or 0 to exit");
			input	= num.nextInt();
		}
		
		if(input == 1) {
			
			//add plan page
			
			
			
		} else if (input == 2) {
			
			//update plan page
			
			
		} else {
			
			System.out.println("\n\n\n");
			
		}
		
//		System.out.println("\n\n");
//		System.out.println("Please press any key to continue or x to exit");
//		String cont	= num.next();
//		if(!cont.equalsIgnoreCase("x")) {
//			
//			adminDashboard(id);
//			
//		}
		
		
		
	}
	
	public static void PlanPage(int id) {
		
		UserDAO custDAO 		= new UserDAO();
		ArrayList<User> list2 	= new ArrayList<User>();
		list2	= custDAO.fetchUser(id);
		//List user details
		for(User u : list2) {
			System.out.println("Hi "+u.getName()+" Welcom to Dispur Wireless Plan Page!");
			System.out.println("----------------------------------");
			System.out.println("ID\t\t: "+id);
			System.out.println("Name\t\t: "+u.getName());
			System.out.println("----------------------------------");
			System.out.println(u.getName()+"'s current Plan");
		}
		
		
		//Plan for the specific user
		PlanDAO allPlanDAO		= new PlanDAO();
		ArrayList<Plan> allPlan	= new ArrayList<Plan>();
		allPlan					= allPlanDAO.custPlan(id);
		System.out.println("PlanID\t\t|Plan Name\t\t|Type Of Plan\t\t|Tariff\t\t|Validity\t\t|Rental");
		for(Plan p : allPlan) {
			
			System.out.println(p.getPlanID()+"\t\t "+p.getPlanName()+"\t\t "+p.getTypeOfPlan()+"\t\t\t "+p.getTariff()+"\t\t "+p.getValidity()+"\t\t\t "+p.getRental());
			//System.out.println("Cust_ID: "+u.getUserID()+" Cust_Name: "+u.getName()+" \tCust_Plan: "+u.getPlanName());
			
		}
		System.out.println("----------------------------------");
		
		
		//Plan that available to be subscribed
		System.out.println("Available Plan for you!");
		ArrayList<Plan> custPlan= new ArrayList<Plan>();
		custPlan				= allPlanDAO.availablePlan(id);
		System.out.println("PlanID\t\t|Plan Name\t\t|Type Of Plan\t\t|Tariff\t\t|Validity\t\t|Rental");
		for(Plan p : custPlan) {
			
			System.out.println(p.getPlanID()+"\t\t "+p.getPlanName()+"\t\t "+p.getTypeOfPlan()+"\t\t\t "+p.getTariff()+"\t\t "+p.getValidity()+"\t\t\t "+p.getRental());
			//System.out.println("Cust_ID: "+u.getUserID()+" Cust_Name: "+u.getName()+" \tCust_Plan: "+u.getPlanName());
			
		}
		System.out.println("----------------------------------");
		
		System.out.println("To subscribe a new network plan please enter the plan id");
		System.out.println("Press 0 to back");
		
		Scanner num	= new Scanner(System.in);
		int planID	= num.nextInt();
		
		if(planID != 0) {
			
			//subscribe plan 
			System.out.println("Succesfully subscribe to new plan! ");
			allPlanDAO.subscribePlan(planID, id);
			custDashboard(id);
			
			
		} else {
			
			custDashboard(id);
			
		}
		
	}
	
}


