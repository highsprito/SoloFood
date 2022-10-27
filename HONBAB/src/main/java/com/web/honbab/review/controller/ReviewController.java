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

import com.web.honbab.review.dto.ReviewRepDTO;
import com.web.honbab.review.service.ReviewFileService;
import com.web.honbab.review.service.ReviewService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("review")
public class ReviewController {
	
	@Autowired
	private ReviewService rs;


	@RequestMapping(value = "reviewAllList")
	public String reviewAllList(Model model, 
								@RequestParam(value = "num", required = false, defaultValue = "1") int num) {
		rs.reviewAllList(model, num);
		return "review/reviewAllList";
	}

	@RequestMapping(value = "upViews")
	public String upViews(@RequestParam int uSeq) {
		rs.upViews(uSeq);
		return "redirect:reviewContent?uSeq=" + uSeq;
	}
	
	@RequestMapping(value = "reviewContent")
	public String reviewContent(@RequestParam int uSeq, Model model) {
		rs.reviewContent(uSeq, model);
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
	public void review_delete(@RequestParam int uSeq, @RequestParam String imageFileName,
						HttpServletRequest request, HttpServletResponse response) throws IOException{
		String message = rs.reviewDelete(uSeq, imageFileName, request);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter()	;
		out.println(message);
	}
	
	@GetMapping("review_modify_form")
	public String reviewModifyForm(@RequestParam int uSeq, Model model) {
		rs.reviewContent(uSeq, model);
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
	public String addReply(@RequestBody Map<String, Object> map, HttpSession session) {
		ReviewRepDTO dto = new ReviewRepDTO();
		//dto.setId((String)session.getAttribute(LOGIN)); //session 諛� 濡쒓렇�씤 濡쒖쭅 �셿�꽦�썑 �닔�젙
		dto.setuReNick("testID");
		dto.setuSeqGroup(Integer.parseInt((String)map.get("uSeq")));
		dto.setuReComent((String)map.get("content"));
		rs.addReply(dto);
		return "{\"result\":true}";
	}
	
	@GetMapping(value = "replyData/{uSeq}", produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<ReviewRepDTO> replyData(@PathVariable int uSeq){
		return rs.getRepList(uSeq);
	}
	
	@GetMapping(value ="reviewLike")
	public String reviewLike(@RequestParam int uSeq) {
		rs.reviewLike(uSeq);
		return "redirect:reviewContent?uSeq=" + uSeq;
	}
}
