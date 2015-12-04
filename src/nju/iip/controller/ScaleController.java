package nju.iip.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import nju.iip.dao.ScaleDao;
import nju.iip.dto.Questions;
import nju.iip.dto.Scale;
import nju.iip.dto.ScaleRecord;
import nju.iip.dto.WeixinUser;
import nju.iip.service.OAuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScaleController {

	private static final Logger logger = LoggerFactory.getLogger(ScaleController.class);

	@Autowired
	private ScaleDao ScaleDao;

	@RequestMapping(value = "/scale_show")
	public String showScaleList(HttpServletRequest request) {
		logger.info("showScaleList called");
		OAuthService.getUerInfo(request);// 获取用户信息
		List<Scale> scale_list = ScaleDao.getScaleList();
		request.setAttribute("ScaleList", scale_list);
		return "ChooseScale.jsp";
	}

	@RequestMapping(value = "/GetScale")
	public String getScale(HttpServletRequest request) throws JSONException {
		logger.info("getScale called");
		String totalScaleId = request.getParameter("totalScaleId");
		Scale scale = ScaleDao.getScale(Integer.valueOf(totalScaleId));
		List<Questions> list = ScaleDao.getQuestions(ScaleDao.getQuestionId(Integer.valueOf(totalScaleId)));
		JSONObject json = new JSONObject();
		json.put("questions", list);
		json.put("scale", JSONObject.fromObject(scale));
		String jsonStr = json.toString();// 量表对应所有题目的json字符串
		logger.info("jsonStr=" + jsonStr);
		request.setAttribute("questions", jsonStr);
		request.getSession().setAttribute("scale", scale);
		return "scale.jsp";
	}

	/**
	 * 记录用户量表评测结果评测
	 * 
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping(value = "/SaveEvaluateResult")
	public void saveEvaluateResult(HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("saveEvaluateResult called");
		Scale scale = (Scale) request.getSession().getAttribute("scale");
		WeixinUser user = (WeixinUser) request.getSession().getAttribute("snsUserInfo");
		String openId = user.getOpenId();
		String answers = request.getParameter("answers");
		String score = request.getParameter("score");
		logger.info("answers=" + answers);
		logger.info("score=" + score);
		PrintWriter out = response.getWriter();
		if (ScaleDao.storeResult(openId, scale, score)) {
			out.print("success");
		} else {
			out.print("faied");
		}
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "/scale_record")
	public String getScaleRecord (HttpServletRequest request){
		logger.info("getScaleRecord called");
		OAuthService.getUerInfo(request);// 获取用户信息
		WeixinUser user = (WeixinUser) request.getSession().getAttribute("snsUserInfo");
		String openId = user.getOpenId();
		List<ScaleRecord> record_list = ScaleDao.getScaleRecord(openId);
		request.setAttribute("record_list", record_list);
		return "ScaleRecord.jsp";
	}

}
