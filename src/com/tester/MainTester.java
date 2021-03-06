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
						
					} else if (userGroup.equalsIgnoreCase("Customer")){
						
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
					
					
				case 0:
					ProgramStop = true;
					
				
			}
			
			//System.out.println("Exit switch case");
			
		} while (ProgramStop != true);
		System.out.println("Program Stop!");

	}

	
	//Home Page method ---------------------------------------------
	public static void homePage() {
		
		System.out.println("Hi Welcome to Dispur Wireless Portal!");
		System.out.println("-------------------------------------");
		System.out.println("1. Login");
		System.out.println("2. Registration");
		System.out.println("-------------------------------------");
		System.out.println("Please enter (1 or 2) or 0 to exit:");
	
	
	}
	
	//Login Page method --------------------------------------------
	public static int loginPage(Scanner s) {
		
		UserDAO dao_user = new UserDAO();
		System.out.println("\n");
		System.out.println("Login Page");
		System.out.println("-------------------------------------");
		System.out.println("Please enter your ID:");
		int id= s.nextInt();
		
		String userID	= dao_user.getUserID(id);
		//System.out.println("ID :"+userID);
		
		while(userID == null) {
			
			System.out.println("user ID not found!, Please re-enter your ID:");
			id= s.nextInt();
			userID	= dao_user.getUserID(id);
			
		}
		
		System.out.println("Please enter your password:");
		String pwd = s.next();
		
		String userPwd = dao_user.getUserPwd(id);
//		System.out.println("Password: "+userPwd);
		while(!userPwd.equalsIgnoreCase(pwd)) {
					
			System.out.println("Wrong password!");
			System.out.println("Please enter your password again:");
			pwd = s.next();
					
		}
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
		System.out.println("Enter your contact no: (eg: 0123456789012)");
		String contno= s.nextLine();
		
		//check if number is valid usually number between 11 - 12 digits
		while(contno.length() < 11 || contno.length() > 12) {
			System.out.println("Enter your contact no again: (eg: 0123456789012)");
			contno= s.nextLine();
		}
		
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
			System.out.println("Please enter new contact no: (eg: 0123456789012)");
			String userInput	= num.next();
			while(userInput.length() < 11 || userInput.length() > 12) {
				System.out.println("Enter your contact no again: (eg: 0123456789012!)");
				userInput = num.nextLine();
			}
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
		

		String getUserID = RMDAO.getUserID(input);
		
		while(getUserID == null) {
			
			System.out.println("User ID: "+input+" is not found please enter again: ");
			input	= num.nextInt();
			getUserID = allCustDAO.getUserID(input);
			
		}
		
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
		System.out.println("Please enter Cust ID to delete details or 0 to exit: ");
		
		Scanner num	= new Scanner(System.in);
		int input	= num.nextInt();
		
		if(input != 0) {
			
			String userID 	= opDAO.getUserID(input);
			int planID		= opDAO.getUserPlan(input);
 			//System.out.println("userID: "+userID);
 			//System.out.println("userPlanID: "+planID);
 			
 			while(userID == null) {
 				System.out.println("ID not found please re enter: ");
 				input	= num.nextInt();
 				userID 	= opDAO.getUserID(input);
 				planID	= opDAO.getUserPlan(input);
 			}
 			
 			if(userID != null && planID == 1) {
 				int select = 0;
 				do {
 					
 					System.out.println("Are you sure you want to delete? ID: "+userID);
 					System.out.println("1 - yes | 2 - no");
 					select	= num.nextInt();
 					switch(select) {
 					
 						case 1:
 							opDAO.deleteCustomer(input);
 							System.out.println("ID: "+input+" has succesfully deleted");
 							break;
 							
 						case 2:
 							//System.out.println("Cancel");
 							break;
 							
 						default:
 							System.out.println("Wrong input!");
 					
 					}
 					
 					
 				} while (select != 1 && select != 2);
 				
 				
 				operatorDashboard(id);
 			} else if(userID != null && planID != 1) {
 				System.out.println("ID: "+input+" is still subscribed");
 				operatorDashboard(id);
 			}
			
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
			addNewPlanPage(id);
			
			
		} else if (input == 2) {
			
			//update plan page
			updatePlanPage(id);
			
			
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
	
	public static void addNewPlanPage(int id) {
		
		
		UserDAO opDAO 			= new UserDAO();
		ArrayList<User> list 	= new ArrayList<User>();
		list	= opDAO.fetchUser(id);
		
		for(User u : list) {
			System.out.println("Hi "+u.getName()+" Welcome to Admin Dashboard!");
			System.out.println("----------------------------------");
			System.out.println("ID\t\t: "+id);
			System.out.println("Name\t\t: "+u.getName());
			System.out.println("----------------------------------");
		}
		
		System.out.println("Please enter the following: ");
		
		//Insert Customer with random no
		int min	= 9000;
		int max = 9999;
		int planID = ThreadLocalRandom.current().nextInt(min, max + 1);
		//System.out.println("Random Number: "+customerID);
		String userGroup2 = "Customer";
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("\n");
		System.out.println("----------------------------------------------------");
		System.out.println("Enter plan name:");
		String name		= s.nextLine();
		System.out.println("Enter type of plan (Data/Voice):");
		String type		= s.nextLine();
		System.out.println("Enter Tariff (Rate/Min):");
		String tariff	= s.nextLine();
		System.out.println("Enter Validity: ");
		int validity	= s.nextInt();
		System.out.println("Enter Rental (RM):");
		double rental 	= s.nextDouble();
		System.out.println("----------------------------------------------------");
		System.out.println("Added new plan Complete !");
		System.out.println("\n");
		System.out.println("New Plan ID: "+planID+" for Plan Name: "+name);
		
		Plan plan		= new Plan(planID, name, type, tariff, validity, rental);
		
		PlanDAO	daoPlan	= new PlanDAO();
		daoPlan.addPlan(plan);
		
		adminDashboard(id);
		
		
	}
	
	public static void updatePlanPage(int id) {
		
		System.out.println("----------------------------------");
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
		System.out.println("1. Edit tariff of existing plan");
		System.out.println("2. Delete existing plan");
		System.out.println("Please enter a number (1 or 2) or 0 to exit: ");
		
		Scanner s = new Scanner(System.in);
		int input = s.nextInt();
		
		while(input < 0 || input > 2) {
			System.out.println("Please enter a valid number (1 or 2) or 0 to exit");
			input	= s.nextInt();
		}
		
		if(input == 1) {
			
			//edit tariff
			System.out.println("Please enter the plan ID to edit: ");
			int planID	= s.nextInt();
			
			String getplan	= allPlanDAO.getPlanID(planID);
						
			while(getplan == null) {
				
				System.out.println("Plan ID: "+planID+" is not found please enter again: ");
				planID	= s.nextInt();
				getplan	= allPlanDAO.getPlanID(planID);
				
			}
			
			System.out.println("Please enter the new tariff for planID: "+planID);
			String tariff	= s.next();
			allPlanDAO.updateTariff(planID, tariff);
			
			System.out.println("New tariff for plan "+planID+" is "+tariff);
			adminDashboard(id);
			
		} else if (input == 2) {
			
			//delete plan 
			System.out.println("Please enter the plan ID to delete: ");
			int planID	= s.nextInt();
			
			String getplan	= allPlanDAO.getPlanID(planID);
			
			while(getplan == null) {
				
				System.out.println("Plan ID: "+planID+" is not found please enter again: ");
				planID	= s.nextInt();
				getplan	= allPlanDAO.getPlanID(planID);
				
			}
			
			if(getplan != null) {
 				int select = 0;
 				do {
 					
 					System.out.println("Are you sure you want to delete? ID: "+planID);
 					System.out.println("1 - yes | 2 - no");
 					select	= s.nextInt();
 					switch(select) {
 					
 						case 1:
 							allPlanDAO.deletePlan(planID);
 							System.out.println("ID: "+planID+" has succesfully deleted");
 							break;
 							
 						case 2:
 							//System.out.println("Cancel");
 							break;
 							
 						default:
 							System.out.println("Wrong input!");
 					
 					}
 					
 					
 				} while (select != 1 && select != 2);
 				
 				
 				adminDashboard(id);
 			} 
			
//			allPlanDAO.deletePlan(planID);
//			
//			System.out.println("Plan "+planID+" has sucessfully deleted!");
//			adminDashboard(id);
			
		} else {
			
			System.out.println("\n\n\n");
			
		}
		
	}
	
}


