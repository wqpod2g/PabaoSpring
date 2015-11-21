package nju.iip.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PostController {
	
	private static final Logger logger = LoggerFactory.getLogger(PostController.class);
	
	@RequestMapping(value = "/post_list")
	public String showPostList() {
		logger.info("showPostList called");
		return "post_list.jsp";
	}

}
