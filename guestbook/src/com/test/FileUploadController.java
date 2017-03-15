package com.test;

import java.io.File;
import java.io.IOException;

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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {
	
   @Autowired
   ServletContext context; 

   /* 최초 요청시 실행할 메소드 */
   @RequestMapping(value = "/fileUploadPage.it", method = RequestMethod.GET)
   public ModelAndView fileUploadPage() {
      FileModel file = new FileModel();
      ModelAndView modelAndView = new ModelAndView("fileUpload", "command", file);
      return modelAndView; //"WEB-INF/source/fileUpload.jsp"
   }

   /* 서브밋 액션 요청시 실행할 메소드 */
   @RequestMapping(value="/fileUploadPage.it", method = RequestMethod.POST)
   public String fileUpload(@Validated FileModel file, BindingResult result, ModelMap model) throws IOException {
      if (result.hasErrors()) {
         System.out.println("validation errors");
         return "fileUploadPage.it";
      } else {            
         System.out.println("Fetching file");
         MultipartFile multipartFile = file.getFile();
         //Create a folder temp under WebContent sub-folder.
         String uploadPath = context.getRealPath("") + File.separator + "temp" + File.separator;
         System.out.println(uploadPath);

         //파일 중복 검사 과정 추가 or 임의의 파일명 동적 생성 -> 사용자 직접 작성
         
         FileCopyUtils.copy(file.getFile().getBytes(), new File(uploadPath+file.getFile().getOriginalFilename()));
         String fileName = multipartFile.getOriginalFilename();
         long fileSize = multipartFile.getSize();
         
         model.addAttribute("fileName", fileName);
         model.addAttribute("fileSize", fileSize);
         return "success"; //"WEB-INF/source/success.jsp"
      }
   }
}
