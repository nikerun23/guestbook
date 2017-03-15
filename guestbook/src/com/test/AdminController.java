package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {
	
	@Autowired
	private GuestBookDAO dao;

	//로그인 성공시 접근할 페이지 요청
	@RequestMapping(value="/adminbooklist.it", method = RequestMethod.GET)
	public String adminbooklist(ModelMap model) {
		
		//데이터베이스 액션 처리
		
		/* View 정보를 반환하는 부분 */
		return "adminbooklist"; // "/WEB-INF/source/adminbooklist.jsp"
	}
	
	//로그인 성공시 접근할 페이지 요청
	@RequestMapping(value="/adminpicturelist.it", method = RequestMethod.GET)
	public String adminpicturelist(ModelMap model) {
		
		//데이터베이스 액션 처리
		
		/* View 정보를 반환하는 부분 */
		return "adminpicturelist"; // "/WEB-INF/source/adminpicturelist.jsp"
	}

	//로그인실패 페이지 요청
	@RequestMapping(value="/loginfail.it", method = RequestMethod.GET)
	public String loginfail() {
		
		/* View 정보를 반환하는 부분 */
		return "loginfail"; // "/WEB-INF/source/loginfail.jsp"
	}
	
	
	//로그아웃폼 페이지 요청
	@RequestMapping(value="/logoutform.it", method = RequestMethod.GET)
	public String logoutform() {
		
		/* View 정보를 반환하는 부분 */
		return "logoutform"; // "/WEB-INF/source/logoutform.jsp"
	}

}