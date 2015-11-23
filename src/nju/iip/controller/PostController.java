package nju.iip.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import nju.iip.dao.PostDao;
import nju.iip.dto.Post;
import nju.iip.service.OAuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PostController {
	
	private static PostDao PD = new PostDao();

	private static final Logger logger = LoggerFactory.getLogger(PostController.class);

	@RequestMapping(value = "/post_list")
	public String showPostList(HttpServletRequest request) {
		logger.info("showPostList called");
		OAuthService.getUerInfo(request);//获取用户信息
		return "post_list.jsp";
	}

	/**
	 * 下拉获取更多帖子
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value = "/GetMorePosts")
	public void getMorePost(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String count = request.getParameter("count");
		logger.info("count=" + count);
		List<Post> post_list = PD.getAllPostLimit(Integer.valueOf(count) * 10);
		JSONObject json = new JSONObject();
		json.put("post", post_list);
		logger.info("json=" + json.toString());
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "/ShowPost")
	public String showPost(HttpServletRequest request) {
		logger.info("showPost called");
		String postId = request.getParameter("id");
		logger.info("postId="+postId);
		Post post = PD.getPostById(Integer.valueOf(postId));
		request.setAttribute("post", post);
		request.getSession().setAttribute("postId", postId);
		return "post.jsp";
	}

}
