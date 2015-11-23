package nju.iip.dao;

import java.util.List;
import nju.iip.dto.Post;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostDao extends DAO{
	
	private static final Logger logger = LoggerFactory.getLogger(PostDao.class);
	
	/**
	 * 获取非置顶帖
	 * @param n
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Post> getAllPostLimit(int n) { 
		List<Post> list = null;
		try{
			begin();
			Query query = getSession().createQuery("from Post where isUp=0").setFirstResult(n).setMaxResults(10);
			list = query.list();
		}catch (HibernateException e) {
			rollback();
			logger.info("PostDao-->getAllPostLimit",e);
		}
		return list;
	}
	
	/**
	 * 获取所有置顶帖
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Post> getAllUpPost() {
		List<Post> list = null;
		try{
			begin();
			Query query = getSession().createQuery("from Post where isUp=1 order by id desc");
			list = query.list();
		}catch (HibernateException e) {
			rollback();
			logger.info("PostDao-->getAllUpPost",e);
		}
		return list;
	}
	
	/**
	 * 根据帖子id取出帖子
	 * 
	 * @param id
	 * @return
	 */
	public Post getPostById(int id) {
		Post post = null;
		try{
			begin();
			Query query = getSession().createQuery("from Post where id=:id");
			query.setInteger("id", id);
			post = (Post)query.uniqueResult();
			commit();
		}catch (HibernateException e) {
			rollback();
			logger.info("PostDao-->getPostById",e);
		}
		return post;
	}

}
