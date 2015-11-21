package nju.iip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nju.iip.util.DBConnection;

/**
 * 与用户位置有关的数据库操作
 * @author mrpod2g
 *
 */
public class LocationDaoImpl {
	
	 private Connection conn  = null;
	 private Statement sm = null;
	 private ResultSet rs = null;
	 private PreparedStatement ps = null;
	 
	 
	    /**
		 * 判断用户位置是否被记录过
		 * @param openId
		 * @return
		 */
		public  boolean isLocated(String openId) {
			 String sql = "select * from weixin_location where openId='"+openId+"'";
			 try{
				 conn = DBConnection.getConn(); 
				 sm=conn.createStatement();
				 rs=sm.executeQuery(sql);
				 return rs.next() == true;
			 }catch(Exception e){
					e.printStackTrace();
					return false;
			 }
			finally {
				closeDB();
			}
		}
		
		/**
		 * 更新用户的坐标
		 * @param Latitude
		 * @param Longitude
		 * @param openId
		 * @return
		 */
		public  boolean updateUserLocation(String Latitude,String Longitude,String openId) {
			Date now = new Date();
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );//可以方便地修改日期格式
	    	String time = dateFormat.format(now);
			String sql="update weixin_location set Latitude='"+Latitude+"',Longitude='"+Longitude+"',time='"+time+"' where openId='"+openId+"'";
			try {
				conn = DBConnection.getConn(); 
				ps = conn.prepareStatement(sql);
				return ps.executeUpdate() == 1 ? true : false;
			}catch (SQLException e) {
				e.printStackTrace();
				return false;
			} finally {
				closeDB();
			}
		}
		
		
		
		/**
		 * 记录用户位置信息
		 * @param Latitude (纬度)
		 * @param Longitude (经度)
		 * @param openId
		 * @return
		 */
		public  boolean locationUser(String Latitude,String Longitude,String openId) {
			Date now = new Date();
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );//可以方便地修改日期格式
	    	String time = dateFormat.format(now);
			conn =DBConnection.getConn();
	    	ps = null;
	    	String sql = "insert into weixin_location(Latitude,Longitude,openId,time) values(?,?,?,?)";
	    	try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, Latitude);
				ps.setString(2, Longitude);
				ps.setString(3,openId);
				ps.setString(4, time);
				return ps.executeUpdate() == 1 ? true : false;
			}catch (SQLException e) {
				e.printStackTrace();
				return false;
			} finally {
				closeDB();
			}
		}
		
		/**
		 * 取出所有用户的坐标
		 * @return
		 */
		public  List<Map<String, String>> getAllUserLocation() {
			List<Map<String, String>> locationMaps = new ArrayList<Map<String,String>>();
			String sql = "select * from weixin_location";
			try{
				conn = DBConnection.getConn(); 
				sm=conn.createStatement();
				rs=sm.executeQuery(sql);
			    while(rs.next()){
			    	Map<String, String> locationMap = new HashMap<String,String>();
					String Latitude = rs.getString("Latitude");
	    			String Longitude = rs.getString("Longitude");
	    			String openId = rs.getString("openId");
	    			locationMap.put("Latitude", Latitude);
	    			locationMap.put("Longitude", Longitude);
	    			locationMap.put("openId", openId);
	    			locationMaps.add(locationMap);
				}
			}catch(Exception e){
				e.printStackTrace();
				}
			finally {
				closeDB();
			}
			return locationMaps;
		}
		
		
		
		/**
		 * 关闭数据库
		 */
		public  void closeDB() {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (sm != null) {
				try {
					sm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		public static void main(String[] args) {
		}

}
