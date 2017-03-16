package com.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminController {
	
	@Autowired
	private GuestBookDAO dao;
	
	@Autowired
	private ServletContext context;

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
	
	// �α��� ������ ������ ������ ��û
	/* ���� ���ε忡 ���� ���� ������ ���ؼ� �Ű������� Picture Ŭ���� �ڷ����� ���� ���� */
	@RequestMapping(value = "/adminpictureinsert.it", method = { RequestMethod.GET, RequestMethod.POST })
	public String adminpictureinsert(Picture file, BindingResult result) throws IOException {
		System.out.println("adminpictureinsert �޼ҵ� ȣ��");

		if (result.hasErrors()) {
			System.out.println("validation errors");
			return "redirect:fileuploaderror.it";
		} else {
			
			//���ε� ���Ͽ� ���� ��ü ����
			MultipartFile multipartFile = file.getFile();

			// Create a folder picture under WebContent sub-folder.
			// ����) ServletContext ��ü�� �̿��� ��θ� Ȯ�� �ʼ�
			String uploadPath = context.getRealPath("") + "picture" + File.separator;

			// ���� �ߺ� �˻� ���� �߰� or ������ ���ϸ� ���� ���� -> ����� ���� �ۼ�
			String newFileName = FileRenamePolicy.rename(multipartFile.getOriginalFilename());

			// ���� Ÿ�� Ȯ��
			String contentType = multipartFile.getContentType();
			// ���� ������ Ȯ��
			long fileSize = multipartFile.getSize();

			// ���� -> image/jpeg, image/png
			if ((contentType.equals("image/jpeg") || contentType.equals("image/png")) && fileSize <= 1024 * 500) {
				
				// ���� ���ε� �׼�
				FileCopyUtils.copy(multipartFile.getBytes(), new File(uploadPath + newFileName));

				// DB�� ���� ���� ����
				file.setGuestBookPicFileName(newFileName);
				file.setComment(file.getComment());
				dao.pictureAdd(file);
				
				return "redirect:adminpicturelist.it";// "WEB-INF/source/adminpicturelist.jsp"
				
			} else {
				return "redirect:fileuploaderror.it";
			}
		}
	}

	// ���Ͼ��ε� ���� ������ ��û
	@RequestMapping(value = "/fileuploaderror.it", method = RequestMethod.GET)
	public String fileuploaderror() {

		/* View ������ ��ȯ�ϴ� �κ� */
		return "fileuploaderror"; // "/WEB-INF/source/fileuploaderror.jsp"
	}

}