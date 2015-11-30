package nju.iip.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import nju.iip.dto.WeixinUser;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao extends DAO{
	
	private static final Logger logger = LoggerFactory.getLogger(PostDao.class);
	
	/**
	 * 通过openId判断用户是否绑定微信
	 * @param openId
	 * @return
	 */
	public boolean checkBind(String openId) {
		boolean flag = false;
		try{
			begin();
			Query query = getSession().createQuery("from WeixinUser where openId=:openId");
			query.setString("openId", openId);
			WeixinUser user = (WeixinUser)query.uniqueResult();
			commit();
			if(user!=null) {
				flag = true;
			}
		}catch (HibernateException e) {
			rollback();
			logger.info("UserDao-->checkBind",e);
		}
		return flag;
	}
	
	/**
	 * 增加用户信息
	 * @param user
	 * @return
	 */
	public void addUserInfo(WeixinUser user) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );//可以方便地修改日期格式
    	String time = dateFormat.format(now);
    	user.setRegistdate(time);
		try{
			begin();
			getSession().save(user);
			commit();
		}catch (HibernateException e) {
			rollback();
			logger.info("UserDao-->addUserInfo",e);
		}
	}
	
	/**
	 * 根据openId获得用户的信息
	 * @param openId
	 * @return
	 */
	public  WeixinUser getUser(String openId) {
		WeixinUser user = null;
		try{
			begin();
			Query query = getSession().createQuery("from WeixinUser where openId=:openId");
			query.setString("openId", openId);
			user = (WeixinUser)query.uniqueResult();
			commit();
		}catch (HibernateException e) {
			rollback();
			logger.info("UserDao-->getUser",e);
		}
		return user;
	}
	
	
	public static void main(String[] args) {
		UserDao ud = new UserDao();
		logger.info(ud.checkBind("o9goJv0zM7MSaU58C8ZFkH0NrsF")+"");
	}

}
