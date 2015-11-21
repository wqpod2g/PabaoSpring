package nju.iip.dto;

/**
 * 评论类
 * @author wangqiang
 *
 */
public class Comment {
	
	//评论对应的postId
	private int postId;
	
	//评论内容
	private String comment_content;
	
	//评论人的微信名
	private String author;
	
	// 评论用户头像链接
	private String headImgUrl;
	
	//评论时间
	private String commentTime;
	
	//发帖人的openId
	private String openId;
	
	
	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
}
