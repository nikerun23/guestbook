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

/* @Controller ������̼��� ���� Ŭ������ SpringWebMVC�� �����ϴ� ��Ʈ�ѷ��� ����� �� ��� */
@Controller
public class GuestBookController {
	
	@Autowired
	private GuestBookDAO dao;

	/* @RequestMapping ������̼ǿ��� method �Ӽ��� ��û ���(GET or POST)�� �м��� �� ��� */
	/* @RequestMapping ������̼ǰ� ����� �޼ҵ常 ���� ��û�� ���� ���� �޼ҵ尡 �� �� �ִ�. */
	/* ���� �޼ҵ��� �Ű� ���� ������ SpringWebMVC�� ���� �ڵ� �м��Ǳ� ������, �ʿ��� ��ü�� ��û�� �� ����Ѵ�. */
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

		/* ���� �׼��� ����� JSP ������(View)�� �����ϴ� ��� Model ��ü�� ����Ѵ�. */
		model.addAttribute("list", list);
		model.addAttribute("totalcount", totalcount);
		model.addAttribute("count", count);
		model.addAttribute("skey", skey);
		model.addAttribute("svalue", svalue);
		
		/* View ������ ��ȯ�ϴ� �κ� */
		return "guestbooklist"; // "/WEB-INF/source/guestbooklist.jsp"
	}
	
	
	//���� �۾��� �׼� �޼ҵ� �߰�
	@RequestMapping(value = "/guestbookinsert.it", method = RequestMethod.POST)
	public String guestbookinsert(GuestBook gb, HttpServletRequest request) {

		// �Է� ��û ������ ���� ó�� -> �������� �ڵ� ���� (�ڷ��� Ŭ���� �غ� or ��� ���� �غ�)

		//IP ȹ�� ���� �߰�
		String ipaddress = request.getRemoteAddr();
		gb.setIpaddress(ipaddress);

		dao.guestbookAdd(gb);
		
		/* View ������ ��ȯ�ϴ� �κ� */
		return "redirect:guestbooklist.it"; //���� ������ ��ȯ
	}
	
	@RequestMapping(value = "/guestbookdelete.it", method = RequestMethod.POST)
	public String guestbookdelete(GuestBook gb) {

		// ���� ��û ������ ���� ó�� -> �������� �ڵ� ���� (�ڷ��� Ŭ���� �غ� or ��� ���� �غ�)
	
		dao.guestbookRemove(gb);
		
		/* View ������ ��ȯ�ϴ� �κ� */
		return "redirect:guestbooklist.it"; //���� ������ ��ȯ
	}
	
	@RequestMapping(value = "/guestbookajax.it", method = RequestMethod.POST)
	public String guestbookajax(ModelMap model,GuestBook gb) {

		// ���� ��û ������ ���� ó�� -> �������� �ڵ� ���� (�ڷ��� Ŭ���� �غ� or ��� ���� �غ�)
		
		List<GuestBook> list = dao.guestBookAjax(gb);
		String result = list.get(0).getContent();
		
		model.addAttribute("result", result);
		
		/* View ������ ��ȯ�ϴ� �κ� */
		return "guestbookajax"; 
	}

}