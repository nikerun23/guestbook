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

	//�α��� ������ ������ ������ ��û
	@RequestMapping(value="/adminbooklist.it", method = {RequestMethod.GET, RequestMethod.POST})
	public String adminbooklist(ModelMap model, String skey, String svalue) {
		
		//�α��� ���� Ȯ�� ���� ���ʿ� -> Spring Security�� ��ü
		
		//�����ͺ��̽� �׼� ó��
		
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

		/* ���� �׼��� ����� JSP ������(View)�� �����ϴ� ��� Model ��ü�� ����Ѵ�. */
		model.addAttribute("list", list);
		model.addAttribute("totalcount", totalcount);
		model.addAttribute("count", count);
		model.addAttribute("skey", skey);
		model.addAttribute("svalue", svalue);
		
		/* View ������ ��ȯ�ϴ� �κ� */
		return "adminbooklist"; // "/WEB-INF/source/adminbooklist.jsp"
	}
	
	//�α��� ������ ������ ������ ��û
	@RequestMapping(value="/adminpicturelist.it", method = RequestMethod.GET)
	public String adminpicturelist(ModelMap model) {
		
		//�����ͺ��̽� �׼� ó��
		List<Picture> pList = null;
		
		pList = dao.pictureList();//�������̽�(Impl)pictureList ����
		
		/* ���� �׼��� ����� JSP ������(View)�� �����ϴ� ��� Model ��ü�� ����Ѵ�. */
		model.addAttribute("plist", pList);
		
		/* View ������ ��ȯ�ϴ� �κ� */
		return "adminpicturelist"; // "/WEB-INF/source/adminpicturelist.jsp"
	}

	//�α��ν��� ������ ��û
	@RequestMapping(value="/loginfail.it", method = RequestMethod.GET)
	public String loginfail() {
		
		/* View ������ ��ȯ�ϴ� �κ� */
		return "loginfail"; // "/WEB-INF/source/loginfail.jsp"
	}
	
	//�α׾ƿ��� ������ ��û
	@RequestMapping(value="/logoutform.it", method = RequestMethod.GET)
	public String logoutform() {
		
		/* View ������ ��ȯ�ϴ� �κ� */
		return "logoutform"; // "/WEB-INF/source/logoutform.jsp"
	}
	
	@RequestMapping(value="/adminblind.it", method = RequestMethod.GET)
	public String adminbilnd(ModelMap map, GuestBook gb){
		
		//gb�� get���� ���۵� gid�� blind���� ���� �޴´�.
		//����ε� ��û ó�� ���� �߰�
		//gid, blind(0 or 1)
		
		dao.adminBlind(gb);
		
		return "redirect:adminbooklist.it";// ���� ������ ��ȯ
	}
	
	

}