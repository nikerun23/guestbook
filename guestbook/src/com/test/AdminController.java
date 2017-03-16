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
	
	// 로그인 성공시 접근할 페이지 요청
	/* 파일 업로드에 대한 수신 역할을 위해서 매개변수에 Picture 클래스 자료형의 변수 선언 */
	@RequestMapping(value = "/adminpictureinsert.it", method = { RequestMethod.GET, RequestMethod.POST })
	public String adminpictureinsert(Picture file, BindingResult result) throws IOException {
		System.out.println("adminpictureinsert 메소드 호출");

		if (result.hasErrors()) {
			System.out.println("validation errors");
			return "redirect:fileuploaderror.it";
		} else {
			
			//업로된 파일에 대한 객체 정보
			MultipartFile multipartFile = file.getFile();

			// Create a folder picture under WebContent sub-folder.
			// 주의) ServletContext 객체를 이용한 경로명 확보 필수
			String uploadPath = context.getRealPath("") + "picture" + File.separator;

			// 파일 중복 검사 과정 추가 or 임의의 파일명 동적 생성 -> 사용자 직접 작성
			String newFileName = FileRenamePolicy.rename(multipartFile.getOriginalFilename());

			// 파일 타입 확인
			String contentType = multipartFile.getContentType();
			// 파일 사이즈 확인
			long fileSize = multipartFile.getSize();

			// 사진 -> image/jpeg, image/png
			if ((contentType.equals("image/jpeg") || contentType.equals("image/png")) && fileSize <= 1024 * 500) {
				
				// 파일 업로드 액션
				FileCopyUtils.copy(multipartFile.getBytes(), new File(uploadPath + newFileName));

				// DB에 사진 정보 저장
				file.setGuestBookPicFileName(newFileName);
				file.setComment(file.getComment());
				dao.pictureAdd(file);
				
				return "redirect:adminpicturelist.it";// "WEB-INF/source/adminpicturelist.jsp"
				
			} else {
				return "redirect:fileuploaderror.it";
			}
		}
	}

	// 파일업로드 에러 페이지 요청
	@RequestMapping(value = "/fileuploaderror.it", method = RequestMethod.GET)
	public String fileuploaderror() {

		/* View 정보를 반환하는 부분 */
		return "fileuploaderror"; // "/WEB-INF/source/fileuploaderror.jsp"
	}

}