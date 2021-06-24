package com.dao;

import com.bean.User;
import com.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class UserDAO {

	Connection con	= null;
	PreparedStatement ps	= null;
	ResultSet rs	= null;
	
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
	
		
		return selectedGroup;
	}
	
}
