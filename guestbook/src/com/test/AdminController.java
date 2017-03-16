package com.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	@RequestMapping(value="/adminbooklist.it", method = {RequestMethod.GET, RequestMethod.POST})
	public String adminbooklist(ModelMap model, String skey, String svalue) {
		
		//로그인 여부 확인 과정 불필요 -> Spring Security로 대체
		
		//데이터베이스 액션 처리
		
		List<GuestBook> list = null;
		int totalcount = 0;
		int count = 0;

		if (skey == null) {
			skey = "all";
			svalue = "";
		}
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("key", skey);
		map1.put("value", svalue);
		
		// totalCount
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("key", "admin");
		
		list = dao.adminBookList(map1);
		totalcount = dao.totalCount(map2);
		count = list.size();

		/* 서블릿 액션의 결과를 JSP 페이지(View)에 전달하는 경우 Model 객체를 사용한다. */
		model.addAttribute("list", list);
		model.addAttribute("totalcount", totalcount);
		model.addAttribute("count", count);
		model.addAttribute("skey", skey);
		model.addAttribute("svalue", svalue);
		
		/* View 정보를 반환하는 부분 */
		return "adminbooklist"; // "/WEB-INF/source/adminbooklist.jsp"
	}
	
	//로그인 성공시 접근할 페이지 요청
	@RequestMapping(value="/adminpicturelist.it", method = RequestMethod.GET)
	public String adminpicturelist(ModelMap model) {
		
		//데이터베이스 액션 처리
		List<Picture> pList = null;
		
		pList = dao.pictureList();//인터페이스(Impl)pictureList 실행
		
		/* 서블릿 액션의 결과를 JSP 페이지(View)에 전달하는 경우 Model 객체를 사용한다. */
		model.addAttribute("plist", pList);
		
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
	
	@RequestMapping(value="/adminblind.it", method = RequestMethod.GET)
	public String adminbilnd(ModelMap map, GuestBook gb){
		
		//gb에 get으로 전송된 gid와 blind값을 전달 받는다.
		//블라인드 요청 처리 과정 추가
		//gid, blind(0 or 1)
		
		dao.adminBlind(gb);
		
		return "redirect:adminbooklist.it";// 강제 페이지 전환
	}
	
	

}