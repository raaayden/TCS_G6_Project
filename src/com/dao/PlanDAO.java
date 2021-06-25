package com.dao;

import com.bean.User;
import com.bean.Plan;
import com.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlanDAO {

	Connection con			= null;
	PreparedStatement ps	= null;
	ResultSet rs			= null;
	
	public int addPlan(Plan p) {
		
		int result	= 0;
		con	= DatabaseUtil.getConnection();
		
		try {
			ps	= con.prepareStatement("INSERT INTO Plan VALUES (?,?,?,?,?,?)");
			ps.setInt(1, p.getPlanID());
			ps.setString(2, p.getPlanName());
			ps.setString(3, p.getTypeOfPlan());
			ps.setString(4, p.getTariff());
			ps.setInt(5, p.getValidity());
			ps.setDouble(6, p.getRental());
			
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
	
	public ArrayList<Plan> getAllPlans() {
		
		ArrayList<Plan> list	= new ArrayList<Plan>();
		con						= DatabaseUtil.getConnection();
		
		try {
			ps	= con.prepareStatement("SELECT * from Plan");
			rs	= ps.executeQuery();
			while(rs.next()) {
				
				int planID		= rs.getInt(1);
				String name		= rs.getString(2);
				String type		= rs.getString(3);
				String tariff	= rs.getString(4);
				int validity	= rs.getInt(5);
				double rental	= rs.getDouble(6);
				
				Plan p 			= new Plan(planID, name, type, tariff, validity, rental);
				list.add(p);
				
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
	
	
	
	
}