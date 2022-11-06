package com.web.honbab.review.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.honbab.review.dto.ReviewDTO;
import com.web.honbab.review.dto.ReviewRepDTO;
import com.web.honbab.review.service.ReviewFileService;
import com.web.honbab.review.service.ReviewService;
import com.web.honbab.session.name.MemberSession;
import com.web.honbab.session.search.SearchSession;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("review")
public class ReviewController implements MemberSession, SearchSession{
	
	@Autowired
	private ReviewService rs;


	@RequestMapping(value = "reviewAllList")
	public String reviewAllList(Model model, 
								@RequestParam(value = "num", required = false, defaultValue = "1") int num,
								HttpSession session) {
		session.removeAttribute(SEARCHVALUE);
		session.removeAttribute(SEARCHOPTION);
		rs.reviewAllList(model, num);
		return "review/reviewAllList";
	}

	@RequestMapping(value = "upViews")
	public String upViews(@RequestParam int writeNo) {
		rs.upViews(writeNo);
		return "redirect:reviewContent?writeNo=" + writeNo;
	}
	
	@RequestMapping(value = "reviewContent")
	public String reviewContent(@RequestParam int writeNo, Model model) {
		rs.reviewContent(writeNo, model);
		return "review/reviewContent";
	}

	@RequestMapping(value = "reviewWriteForm")
	public String reviewWriteForm(Model model) {	
		return "review/reviewWriteForm";
	}

	@PostMapping("reviewWrite")
	public void reviewSave(MultipartHttpServletRequest mul, HttpServletRequest request, HttpServletResponse response) throws IOException{
		String message = rs.reviewSave(mul, request);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(message);
	}
	
	@GetMapping("download")
	public void download(@RequestParam String imageFileName, HttpServletResponse response) throws Exception{
		response.addHeader("Context-disposition", "attachment; fileName="+imageFileName);
		File file = new File(ReviewFileService.IMAGE_REVIEW+"\\"+imageFileName);
		FileInputStream in = new FileInputStream(file);
		FileCopyUtils.copy(in, response.getOutputStream());
		in.close();
	}
	
	@GetMapping("review_delete")
	public void review_delete(@RequestParam int writeNo, @RequestParam String imageFileName,
						HttpServletRequest request, HttpServletResponse response) throws IOException{
		String message = rs.reviewDelete(writeNo, imageFileName, request);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter()	;
		out.println(message);
	}
	
	@GetMapping("review_modify_form")
	public String reviewModifyForm(@RequestParam int writeNo, Model model) {
		rs.reviewContent(writeNo, model);
		return "review/reviewModifyForm";
	}
	
	@PostMapping("review_modify")
	public void reviewModify(MultipartHttpServletRequest mul,
							HttpServletResponse response, HttpServletRequest request) throws IOException{
		String message = rs.reviewModify(mul, request);
		response.setContentType("text/html; charset=utf-8" );
		PrintWriter out = response.getWriter();
		out.println(message);
	}
	
	@PostMapping(value="addReply", produces = "applacition/json; charset=utf-8")
	@ResponseBody //JSON{\"result\":true} 요거쓰려면 상단에 @RestController 작성하거나 아니면 해당메서드에 @ResponeseBody 요거작성해야함
	public String addReply(@RequestBody Map<String, Object> map, HttpSession session)  {
		ReviewRepDTO dto = new ReviewRepDTO();
		dto.setReNick("testID");
		dto.setReId((String)session.getAttribute(LOGIN));
		dto.setWriteGroup(Integer.parseInt((String)map.get("writeNo")));
		dto.setReComent((String)map.get("coment"));
		rs.addReply(dto);
		return "{\"result\":true}";
	}
	
	@GetMapping(value = "replyData/{writeNo}", produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<ReviewRepDTO> replyData(@PathVariable int writeNo){
		return rs.getRepList(writeNo);
	}
	
	@GetMapping(value ="reviewLike")
	public String reviewLike(@RequestParam int writeNo) {
		rs.reviewLike(writeNo);
		return "redirect:reviewContent?writeNo=" + writeNo;
	}
	
	@GetMapping(value= "search")
	public String searchList(Model model,
			@RequestParam(value = "num", required = false, defaultValue = "1") int num,
			HttpSession session) throws IOException{
		rs.searchReview(model, num);
		return "review/reviewAllList";
	}
	
	@PostMapping(value = "search") // 
	public String searchList(MultipartHttpServletRequest mul, Model model,
			@RequestParam(value = "num", required = false, defaultValue = "1") int num,
			HttpSession session) throws IOException{
		session.removeAttribute(SEARCHVALUE);
		session.removeAttribute(SEARCHVALUE);
		session.setAttribute(SEARCHVALUE, mul.getParameter("option"));
		session.setAttribute(SEARCHVALUE, mul.getParameter("text"));
		rs.searchReview(model, num);
		return "review/reviewAllList";
	}
}
