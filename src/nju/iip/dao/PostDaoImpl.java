package nju.iip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nju.iip.dto.Comment;
import nju.iip.dto.Love;
import nju.iip.dto.Post;
import nju.iip.util.DBConnection;

/**
 * 与发帖有关的数据库操�?
 * 
 * @author wangqiang
 * 
 */
public class PostDaoImpl {

	private Connection conn = null;
	private Statement sm = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;

	/**
	 * 添加�?��帖子
	 * 
	 * @param post
	 * @return
	 */
	public boolean addPost(Post post) {
		String sql = "insert into weixin_post(title,content,postTime,author,reply,openId,headImgUrl,love,pictureUrl,isUp) values(?,?,?,?,?,?,?,?,?,?)";
		try {
			conn = DBConnection.getConn();
			ps = conn.prepareStatement(sql);
			ps.setString(1, post.getTitle());
			ps.setString(2, post.getContent());
			ps.setString(3, post.getPostTime());
			ps.setString(4, post.getAuthor());
			ps.setInt(5, post.getReply());
			ps.setString(6, post.getOpenId());
			ps.setString(7, post.getHeadImgUrl());
			ps.setInt(8, post.getLove());
			ps.setString(9, post.getPictureUrl());
			ps.setInt(10, 0);
			return ps.executeUpdate() == 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDB();
		}
	}

	/**
	 * 获得�?��非置顶帖�?
	 * 
	 * @return
	 */
	public List<Post> getAllPostLimit(int n) {
		List<Post> post_list = new ArrayList<Post>();
		String sql = "select * from weixin_post where isUp=0 order by id desc limit "+n+",10";
		try {
			conn = DBConnection.getConn();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				Post post = new Post();
				post.setAuthor(rs.getString("author"));
				post.setContent(rs.getString("content"));
				post.setId(rs.getInt("id"));
				post.setPostTime(rs.getString("postTime"));
				post.setTitle(rs.getString("title"));
				post.setReply(rs.getInt("reply"));
				post.setLove(rs.getInt("love"));
				post.setPictureUrl(rs.getString("pictureUrl"));
				post_list.add(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}

		return post_list;
	}

	/**
	 * 获得�?��非置顶帖�?
	 * 
	 * @return
	 */
	public List<Post> getAllPost() {
		List<Post> post_list = new ArrayList<Post>();
		String sql = "select * from weixin_post where isUp=0 order by id desc";
		try {
			conn = DBConnection.getConn();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				Post post = new Post();
				post.setAuthor(rs.getString("author"));
				post.setContent(rs.getString("content"));
				post.setId(rs.getInt("id"));
				post.setPostTime(rs.getString("postTime"));
				post.setTitle(rs.getString("title"));
				post.setReply(rs.getInt("reply"));
				post.setLove(rs.getInt("love"));
				post.setPictureUrl(rs.getString("pictureUrl"));
				post_list.add(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}

		return post_list;
	}

	/**
	 * 获得�?��置顶帖子
	 * 
	 * @return
	 */
	public List<Post> getAllUpPost() {
		List<Post> post_list = new ArrayList<Post>();
		String sql = "select * from weixin_post where isUp=1 order by id desc";
		try {
			conn = DBConnection.getConn();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				Post post = new Post();
				post.setAuthor(rs.getString("author"));
				post.setContent(rs.getString("content"));
				post.setId(rs.getInt("id"));
				post.setPostTime(rs.getString("postTime"));
				post.setTitle(rs.getString("title"));
				post.setReply(rs.getInt("reply"));
				post.setLove(rs.getInt("love"));
				post.setPictureUrl(rs.getString("pictureUrl"));
				post_list.add(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}

		return post_list;
	}

	/**
	 * 根据帖子id取出帖子
	 * 
	 * @param id
	 * @return
	 */
	public Post getPostById(int id) {
		Post post = new Post();
		String sql = "select * from weixin_post where id='" + id + "'";
		try {
			conn = DBConnection.getConn();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next()) {
				post.setId(id);
				post.setAuthor(rs.getString("author"));
				post.setContent(rs.getString("content"));
				post.setTitle(rs.getString("title"));
				post.setHeadImgUrl(rs.getString("headImgUrl"));
				post.setPostTime(rs.getString("postTime"));
				post.setReply(rs.getInt("reply"));
				post.setLove(rs.getInt("love"));
				post.setPictureUrl(rs.getString("pictureUrl"));
				post.setOpenId(rs.getString("openId"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return post;
	}

	/**
	 * 增加�?��评论
	 * 
	 * @param comment
	 * @return
	 */
	public boolean addComment(Comment comment) {
		String sql = "insert into weixin_comment(postId,comment,commentTime,author,openId,headImgUrl) values(?,?,?,?,?,?)";
		try {
			conn = DBConnection.getConn();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, comment.getPostId());
			ps.setString(2, comment.getComment());
			ps.setString(3, comment.getCommentTime());
			ps.setString(4, comment.getAuthor());
			ps.setString(5, comment.getOpenId());
			ps.setString(6, comment.getHeadImgUrl());
			return ps.executeUpdate() == 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDB();
		}
	}

	/**
	 * 评论�?1操作
	 * 
	 * @return
	 */
	public boolean addReplyNum(int postId) {
		String sql = "update weixin_post set reply=reply+1 where id='" + postId
				+ "'";
		try {
			conn = DBConnection.getConn();
			ps = conn.prepareStatement(sql);

			return ps.executeUpdate() == 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDB();
		}
	}

	/**
	 * weixin_post和weixin_love表中增加�?��点赞�?
	 * 
	 * @param postId
	 * @return
	 */
	public boolean addLike(Love love) {
		String sql = "update weixin_post set love=love+1 where id='"
				+ love.getPostId() + "'";
		String sql2 = "insert into weixin_love(postId,loveTime,author,openId,headImgUrl) values(?,?,?,?,?)";
		PreparedStatement ps2 = null;
		try {
			conn = DBConnection.getConn();
			ps = conn.prepareStatement(sql);
			ps2 = conn.prepareStatement(sql2);
			ps2.setInt(1, love.getPostId());
			ps2.setString(2, love.getLoveTime());
			ps2.setString(3, love.getAuthor());
			ps2.setString(4, love.getOpenId());
			ps2.setString(5, love.getHeadImgUrl());
			return (ps.executeUpdate() == 1) && (ps2.executeUpdate() == 1) ? true
					: false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDB();
			if (ps2 != null) {
				try {
					ps2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * 判断某个人是否点过赞
	 * 
	 * @return
	 */
	public boolean isLove(String openId, int postId) {
		String sql = "select * from weixin_love where postId='" + postId
				+ "' and openId='" + openId + "'";
		try {
			conn = DBConnection.getConn();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDB();
		}
	}

	/**
	 * 将帖子所有赞取出
	 * 
	 * @param postId
	 * @return
	 */
	public List<Love> getAllLove(int postId) {
		List<Love> love_list = new ArrayList<Love>();
		String sql = "select * from weixin_love where postId='" + postId + "'";
		try {
			conn = DBConnection.getConn();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				Love love = new Love();
				love.setAuthor(rs.getString("author"));
				love.setHeadImgUrl(rs.getString("headImgUrl"));
				love.setOpenId(rs.getString("openId"));
				love_list.add(love);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return love_list;
	}

	/**
	 * 取得帖子的所有评�?
	 * 
	 * @param postId
	 * @return
	 */
	public List<Comment> getAllComment(int postId) {
		List<Comment> comment_list = new ArrayList<Comment>();
		String sql = "select * from weixin_comment where postId='" + postId
				+ "' order by id";
		try {
			conn = DBConnection.getConn();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setAuthor(rs.getString("author"));
				comment.setComment(rs.getString("comment"));
				comment.setCommentTime(rs.getString("commentTime"));
				comment.setHeadImgUrl(rs.getString("headImgUrl"));
				comment.setOpenId(rs.getString("openId"));
				comment_list.add(comment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return comment_list;
	}

	/**
	 * 关闭数据�?
	 */
	public void closeDB() {
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
		PostDaoImpl PDI = new PostDaoImpl();
		System.out.println("size="+PDI.getAllPostLimit(30).size());
//		for (Post post : PDI.getAllPostLimit(5)) {
//			System.out.println(post.getId());
//		}
	}

}
