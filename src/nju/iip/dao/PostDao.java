package nju.iip.dao;

import java.util.List;

import nju.iip.dto.Comment;
import nju.iip.dto.Love;
import nju.iip.dto.Post;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
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
			Query query = getSession().createQuery("from Post where isUp=0 order by id desc").setFirstResult(n).setMaxResults(10);
			list = query.list();
		}catch (HibernateException e) {
			rollback();
			logger.info("PostDao-->getAllPostLimit",e);
		}
		finally{
			close();
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
		finally{
			close();
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
		}catch (HibernateException e) {
			rollback();
			logger.info("PostDao-->getPostById",e);
		}
		finally{
			close();
		}
		return post;
	}
	
	
	/**
	 * 保存帖子
	 * @param post
	 */
	public void savePost(Post post) {
		try{
			begin();
			getSession().save(post);
			commit();
		}catch (HibernateException e) {
			rollback();
			logger.info("PostDao-->savePost",e);
		}finally{
			close();
		}
	}
	
	/**
	 * 取得帖子的所有评论
	 * 
	 * @param postId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Comment> getAllComment(int postId) {
		List<Comment> list = null;
		try{
			begin();
			Query query = getSession().createQuery("from Comment where postId=:postId order by id desc");
			query.setInteger("postId", postId);
			list = query.list();
			commit();
		}catch (HibernateException e) {
			rollback();
			logger.info("PostDao-->getAllComment",e);
		}finally{
			close();
		}
		return list;
	}
	
	/**
	 * 判断某个人是否点过赞
	 * 
	 * @return
	 */
	public boolean isLove(String openId, int postId) {
		boolean flag = false;
		try{
			begin();
			Query query = getSession().createQuery("from Love where openId=:openId and postId=:postId");
			query.setString("openId",openId);
			query.setInteger("postId", postId);
			Love love = (Love)query.uniqueResult();
			commit();
			if(love!=null) {
				flag = true;
			}
		}catch (HibernateException e) {
			rollback();
			logger.info("PostDao-->isLove",e);
		}finally{
			close();
		}
		return flag;
	}
	
	
	/**
	 * weixin_post和weixin_love表中增加一个点赞数
	 * 
	 * @param postId
	 * @return
	 */
	public void addLike(Love love) {
		try{
			begin();
			Query query = getSession().createQuery("update Post p set p.love = p.love+1 where id=:id");
			query.setInteger("id", love.getPostId());
			query.executeUpdate();
			getSession().save(love);
			commit();
		}catch (HibernateException e) {
			rollback();
			logger.info("PostDao-->addLike",e);
		}finally{
			close();
		}
	}
	
	
	
	public static void main(String[] args) {
		PostDao pd = new PostDao();
		logger.info(pd.isLove("o9goJv0zM7MSaU58C8ZFkH0NrsFk",2)+"");
	}

}
