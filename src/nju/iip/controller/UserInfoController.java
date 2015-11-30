package nju.iip.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nju.iip.dao.UserDao;
import nju.iip.dto.WeixinUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserInfoController {
	
	private static UserDao userDao = new UserDao();
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	/**
	 * 用户信息修改
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value = "/alterUserInfo")
	public void alterUserInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		WeixinUser snsUserInfo = (WeixinUser)request.getSession().getAttribute("snsUserInfo");
		logger.info("update name=" + name);
		logger.info("update phone=" + phone);
		snsUserInfo.setName(name);
		snsUserInfo.setPhone(phone);
		PrintWriter out = response.getWriter();
		if(userDao.updateUserInfo(snsUserInfo)) {
			out.write("修改成功！");
			logger.info("update ok");
		}
		else{
			out.write("修改失败！");
			logger.info("update falied!");
		}

	}

}
