package nju.iip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import nju.iip.dto.WeixinUser;
import nju.iip.util.DBConnection;


/**
 * 与用户有关的数据库操作
 * @author wangqiang
 *
 */
public class UserDaoImpl {
	
	 private Connection conn  = null;
	 private Statement sm = null;
	 private ResultSet rs = null;
	 private PreparedStatement ps = null;
	
	/**
	 * 通过openId判断用户是否绑定微信
	 * @param openId
	 * @return
	 */
	public  boolean checkBind(String openId) {
		 boolean flag=false;
		 String sql = "select * from weixin_userinfo where openId='"+openId+"'";
		 try{
			 conn = DBConnection.getConn(); 
			 sm=conn.createStatement();
			 rs=sm.executeQuery(sql);
			 flag=rs.next();
		 }catch(Exception e){
				e.printStackTrace();
				}
			finally {
				closeDB();
			}
		 return flag;
	}
	
	/**
	 * 增加用户
	 * @param user
	 * @return
	 */
	public  boolean addUser(WeixinUser user) {
		conn =DBConnection.getConn();
    	ps = null;
    	String sql = "insert into weixin(openId,cardID,name,phone) values(?,?,?,?)";
    	try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getOpenId());
			ps.setString(2, user.getCardID());
			ps.setString(3, user.getName());
			ps.setString(4, user.getPhone());
			return ps.executeUpdate() == 1 ? true : false;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDB();
		}
	}
	
	public  boolean addUserInfo(WeixinUser user) {
		Date now = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );//可以方便地修改日期格式
    	String time = dateFormat.format(now);
		conn =DBConnection.getConn();
    	ps = null;
    	String sql = "insert into weixin_userinfo(openId,name,phone,nickname,sex,country,province,city,headImgUrl,registdate) values(?,?,?,?,?,?,?,?,?,?)";
    	try {
    		ps = conn.prepareStatement(sql);
			ps.setString(1, user.getOpenId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getPhone());
			ps.setString(4, user.getNickname());
			ps.setInt(5, user.getSex());
			ps.setString(6, user.getCountry());
			ps.setString(7, user.getProvince());
			ps.setString(8, user.getCity());
			ps.setString(9, user.getHeadImgUrl());
			ps.setString(10, time);
			return ps.executeUpdate() == 1 ? true : false;
    	}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDB();
		}
	}
	
	public boolean updateUserInfo(WeixinUser user) {
		String sql="update weixin_userinfo set name='"+user.getName()+"',phone='"+user.getPhone()+"' where openId='"+user.getOpenId()+"'";
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
	 * 根据openId获得用户的信息
	 * @param openId
	 * @return
	 */
	public  WeixinUser getUser(String openId) {
		WeixinUser user = new WeixinUser();
		String sql = "select * from weixin where openId='"+openId+"'";
		try{
			conn = DBConnection.getConn(); 
			sm=conn.createStatement();
			rs=sm.executeQuery(sql);
			if(rs.next()){
				String name = rs.getString("name");
    			String cardID = rs.getString("cardID");
    			String phone = rs.getString("phone");
    			user.setName(name);
    			user.setCardID(cardID);
    		    user.setPhone(phone);
			}
		}catch(Exception e){
			e.printStackTrace();
			}
		finally {
			closeDB();
		}
		return user;
	}
	
	/**
	 * 根据openId获得用户的信息
	 * @param openId
	 * @return
	 */
	public  WeixinUser getUserInfo(String openId) {
		WeixinUser user = new WeixinUser();
		String sql = "select * from weixin_userinfo where openId='"+openId+"'";
		try{
			conn = DBConnection.getConn(); 
			sm=conn.createStatement();
			rs=sm.executeQuery(sql);
			if(rs.next()){
				String name = rs.getString("name");
    			String cardID = rs.getString("cardID");
    			String phone = rs.getString("phone");
    			String nickname = rs.getString("nickname");
    			String headImgUrl = rs.getString("headImgUrl");
    			String province = rs.getString("province");
    			String city = rs.getString("city");
    			int sex = rs.getInt("sex");
    			user.setSex(sex);
    			user.setCity(city);
    			user.setProvince(province);
    			user.setName(name);
    			user.setCardID(cardID);
    		    user.setPhone(phone);
    		    user.setNickname(nickname);
    		    user.setHeadImgUrl(headImgUrl);
			}
		}catch(Exception e){
			e.printStackTrace();
			}
		finally {
			closeDB();
		}
		return user;
	}
	
	
	/**
	 * 根据openId删除用户即解绑
	 * @param openId
	 * @return
	 */
	public  boolean deleteUser(String openId) {
		conn =DBConnection.getConn();
    	ps = null;
    	String sql = "delete from weixin where openId=?";
    	try{
    		ps = conn.prepareStatement(sql);
    		ps.setString(1, openId);
    		return ps.executeUpdate() == 1 ? true : false;
    	}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDB();
		}
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
