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

	//�α��� ������ ������ ������ ��û
	@RequestMapping(value="/adminbooklist.it", method = RequestMethod.GET)
	public String adminbooklist(ModelMap model) {
		
		//�����ͺ��̽� �׼� ó��
		
		/* View ������ ��ȯ�ϴ� �κ� */
		return "adminbooklist"; // "/WEB-INF/source/adminbooklist.jsp"
	}
	
	//�α��� ������ ������ ������ ��û
	@RequestMapping(value="/adminpicturelist.it", method = RequestMethod.GET)
	public String adminpicturelist(ModelMap model) {
		
		//�����ͺ��̽� �׼� ó��
		
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

}