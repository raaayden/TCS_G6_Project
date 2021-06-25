package com.dao;

import com.bean.User;
import com.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class UserDAO {

	Connection con			= null;
	PreparedStatement ps	= null;
	ResultSet rs			= null;
	
	public int addUser(User usr) {
		int result	= 0;
		con	= DatabaseUtil.getConnection();
		
		try {
			ps	= con.prepareStatement("INSERT INTO DispurUser values (?,?,?,?,?,?,?,?)");
			ps.setInt(1, usr.getUserID());
			ps.setString(2, usr.getUserPWD());
			ps.setString(3, usr.getUserGROUP());
			ps.setString(4, usr.getName());
			ps.setString(5, usr.getAddress());
			ps.setString(6, usr.getEmail());
			ps.setInt(7, usr.getContact_No());
			ps.setInt(8, usr.getPlanID());
			
			result	= ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			DatabaseUtil.closeConnection(con);
		}
		
		return result;
		
	}
	
	public String getUserGroup (int userID) {
		
		con	= DatabaseUtil.getConnection();
		String selectedGroup = null;
		
		try {
			ps	= con.prepareStatement("SELECT userGroup from DispurUser where userID = ?");
			ps.setInt(1, userID);
			rs	= ps.executeQuery();
			
			while(rs.next()) {
				selectedGroup = rs.getString(1);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			DatabaseUtil.closeConnection(con);
		}
	
		
		return selectedGroup;
	}
	
	
	public ArrayList<User> fetchUser(int userID) {
		
		ArrayList<User> list	= new ArrayList<User>();
		con	= DatabaseUtil.getConnection();
		
		try {
			ps	= con.prepareStatement("SELECT u.NAME, u.ADDRESS, u.EMAIL, u.CONTACT_NO, p.PLAN_NAME from DispurUser u LEFT JOIN Plan p ON p.planID = u.planID WHERE u.userID = ?");
			ps.setInt(1, userID);
			rs	= ps.executeQuery();
			
			while(rs.next()) {
				
				String name		= rs.getString(1);
				String add		= rs.getString(2);
				String email	= rs.getString(3);
				int cont_no		= rs.getInt(4);
				String plan		= rs.getString(5);
				
				User e 		= new User(name, add, email, cont_no, plan);
				list.add(e);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			DatabaseUtil.closeConnection(con);
		}
		
		return list;
		
	}
	
	public ArrayList<User> getAllCustomers() {
		
		ArrayList<User> list	= new ArrayList<User>();
		con	= DatabaseUtil.getConnection();
		
		try {
			ps	= con.prepareStatement("SELECT u.userID, u.NAME, u.EMAIL, u.CONTACT_NO, p.PLAN_NAME from DispurUser u LEFT JOIN Plan p ON p.planID = u.planID where USERGROUP = 'Customer'");
			rs	= ps.executeQuery();
			while(rs.next()) {
				
				int userID		= rs.getInt(1);
				String name		= rs.getString(2);
				String email	= rs.getString(3);
				int cont_no		= rs.getInt(4);
				String plan		= rs.getString(5);
				
				User e 		= new User(userID, name, email, cont_no, plan);
				list.add(e);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			DatabaseUtil.closeConnection(con);
		}
		
		return list;
		
	}
	
	public int deleteCustomer(int id) {
		
		int rows	= 0;
		con 		= DatabaseUtil.getConnection();
		
		
		try {
			ps	= con.prepareStatement("DELETE from DispurUser WHERE userid = ?");
			ps.setInt(1, id);
			rows= ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			DatabaseUtil.closeConnection(con);
		}
		
		return rows;
		
		
	}
	
	public int updateCustomerName(int id, String name) {
		
		int res	= 0;
		con		= DatabaseUtil.getConnection();
		
		try {
			ps		= con.prepareStatement("UPDATE DispurUser SET name = ? WHERE userid = ?");
			ps.setString(1, name);
			ps.setInt(2, id);
			res		= ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			DatabaseUtil.closeConnection(con);
		}
		
		return res;
		
	}
	
	public int updateCustomerAdd(int id, String add) {
		
		int res	= 0;
		con		= DatabaseUtil.getConnection();
		
		try {
			ps		= con.prepareStatement("UPDATE DispurUser SET ADDRESS = ? WHERE userid = ?");
			ps.setString(1, add);
			ps.setInt(2, id);
			res		= ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			DatabaseUtil.closeConnection(con);
		}
		
		return res;
		
	}
	
	public int updateCustomerEmail(int id, String email) {
		
		int res	= 0;
		con		= DatabaseUtil.getConnection();
		
		try {
			ps		= con.prepareStatement("UPDATE DispurUser SET EMAIL = ? WHERE userid = ?");
			ps.setString(1, email);
			ps.setInt(2, id);
			res		= ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			DatabaseUtil.closeConnection(con);
		}
		
		return res;
		
	}
	
	public int updateCustomerContact(int id, int contact) {
		
		int res	= 0;
		con		= DatabaseUtil.getConnection();
		
		try {
			ps		= con.prepareStatement("UPDATE DispurUser SET contact_no = ? WHERE userid = ?");
			ps.setInt(1, contact);
			ps.setInt(2, id);
			res		= ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			DatabaseUtil.closeConnection(con);
		}
		
		return res;
		
	}
	
	
	
	
}
