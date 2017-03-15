package com.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;

/* @Controller 어노테이션은 현재 클래스를 SpringWebMVC가 관리하는 컨트롤러로 등록할 때 사용 */
@Controller
public class GuestBookController {
	
	@Autowired
	private GuestBookDAO dao;

	/* @RequestMapping 어노테이션에서 method 속성은 요청 방식(GET or POST)을 분석할 때 사용 */
	/* @RequestMapping 어노테이션과 연결된 메소드만 서블릿 요청에 대한 응답 메소드가 될 수 있다. */
	/* 응답 메소드의 매개 변수 지정은 SpringWebMVC에 의해 자동 분석되기 때문에, 필요한 객체를 요청할 때 사용한다. */
	@RequestMapping(value = "/guestbooklist.it", method = { RequestMethod.GET, RequestMethod.POST })
	public String guestbooklist(ModelMap model, String skey, String svalue) {

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

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("key", "user");

		list = dao.guestBookList(map1);
		totalcount = dao.totalCount(map2);
		count = list.size();

		/* 서블릿 액션의 결과를 JSP 페이지(View)에 전달하는 경우 Model 객체를 사용한다. */
		model.addAttribute("list", list);
		model.addAttribute("totalcount", totalcount);
		model.addAttribute("count", count);
		model.addAttribute("skey", skey);
		model.addAttribute("svalue", svalue);
		
		/* View 정보를 반환하는 부분 */
		return "guestbooklist"; // "/WEB-INF/source/guestbooklist.jsp"
	}
	
	
	//방명록 글쓰기 액션 메소드 추가
	@RequestMapping(value = "/guestbookinsert.it", method = RequestMethod.POST)
	public String guestbookinsert(GuestBook gb, HttpServletRequest request) {

		// 입력 요청 데이터 수신 처리 -> 스프링이 자동 수신 (자료형 클래스 준비 or 멤버 변수 준비)

		//IP 획득 과정 추가
		String ipaddress = request.getRemoteAddr();
		gb.setIpaddress(ipaddress);

		dao.guestbookAdd(gb);
		
		/* View 정보를 반환하는 부분 */
		return "redirect:guestbooklist.it"; //강제 페이지 전환
	}
	
	@RequestMapping(value = "/guestbookdelete.it", method = RequestMethod.POST)
	public String guestbookdelete(GuestBook gb) {

		// 삭제 요청 데이터 수신 처리 -> 스프링이 자동 수신 (자료형 클래스 준비 or 멤버 변수 준비)
	
		dao.guestbookRemove(gb);
		
		/* View 정보를 반환하는 부분 */
		return "redirect:guestbooklist.it"; //강제 페이지 전환
	}
	
	@RequestMapping(value = "/guestbookajax.it", method = RequestMethod.POST)
	public String guestbookajax(ModelMap model,GuestBook gb) {

		// 삭제 요청 데이터 수신 처리 -> 스프링이 자동 수신 (자료형 클래스 준비 or 멤버 변수 준비)
		
		List<GuestBook> list = dao.guestBookAjax(gb);
		String result = list.get(0).getContent();
		
		model.addAttribute("result", result);
		
		/* View 정보를 반환하는 부분 */
		return "guestbookajax"; 
	}

}