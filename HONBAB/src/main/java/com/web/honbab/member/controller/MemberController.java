package com.web.honbab.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.honbab.admin.service.OperService;
import com.web.honbab.challenge.service.ChallengeService;
import com.web.honbab.find.service.FindService;
import com.web.honbab.member.dto.MemberDTO;
import com.web.honbab.member.service.MemberService;
import com.web.honbab.review.service.ReviewService;
import com.web.honbab.session.admin.AdminSession;
import com.web.honbab.session.name.MemberSession;

@Controller
@RequestMapping("member")
public class MemberController implements MemberSession, AdminSession{
	
	@Autowired
	private MemberService ms;
	
	@Autowired
	private OperService os;

	
	@Autowired
	private ReviewService rs;
	   
	   @Autowired
	   private FindService fs;
	   
	   @Autowired
	   private ChallengeService cs;
	   
	@PostMapping("user_check")
	public String userCheck(HttpServletRequest request, RedirectAttributes rs) {
		int result = ms.user_check(request);
		if(result == 0) {
			rs.addAttribute("id", request.getParameter("id"));
			return "redirect:successLogin";
		}
		return "redirect:login";
	}
	@PostMapping("bizuser_check")
	public String bizuserCheck(HttpServletRequest request, RedirectAttributes rs) {
		int result = ms.bizuser_check(request);
		if(result == 0) {
			rs.addAttribute("id", request.getParameter("id"));
			return "redirect:successLogin";
		}
		return "redirect:login";
	}
	
	@RequestMapping("successLogin")
	public String successLogin(@RequestParam("id") String id, HttpSession session, Model model) {
		session.setAttribute(LOGIN, id);
	      rs.reviewBestList(model);
	      fs.findBestList(model);
	      cs.challengeBestList(model);
		return "index";
	}
	
	@RequestMapping("adminUserCheck")
	public String adminUserCheck(HttpServletRequest request, RedirectAttributes rs) {
		int result = os.adminUserCheck(request);
		if(result == 0) {
			rs.addAttribute("id", request.getParameter("id"));
			return "redirect:successADMLogin";
		}
		return "redirect:login";
	}
	
	@RequestMapping("successADMLogin")
	public String successADMLogin(@RequestParam("id") String id, HttpSession session) {
		session.setAttribute(ADMIN, id);
		return "admin/ADMIndex";
	}
	
	@GetMapping("login")
	public String login() {
		return "member/login";
	}

	@GetMapping("idcheckNo")
	public String idcheckNo() {
		return "main/idcheckNo";
	}
	
	@GetMapping("idcheck")
	public String idcheck(HttpServletRequest request, RedirectAttributes rs) {
		Integer result = ms.user_check(request);
		if(result != null) {
			rs.addAttribute("id", request.getParameter("id"));
			return "main/idcheck";
		} else {
		return "redirect:/member/idcheckNo";
		}
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		if(session.getAttribute("loginUser") != null) {
			session.invalidate();
		}
		return "redirect:/index";
	}
	
	@RequestMapping("/info")
	public String info(@RequestParam("id") String id, Model model) {
		ms.info(id, model);
		return "member/info";
	}
	
	
	@RequestMapping("/register_form")
	public String register_form() {
		return "member/register";
	}
	
	@RequestMapping("/register")
	public String register(MemberDTO member) {
		int result = ms.register(member);
		if(result == 1) {
			return "redirect:login";
		}
		return "redirect:register_form";
	}
	
	@GetMapping("delete")
	public void delete(@RequestParam("id") String id,HttpServletResponse response, HttpServletRequest request) throws IOException{
		String message = ms.memberDelete(id, request);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(message);
	}
	
	@RequestMapping("/callback")
	public String callback() {
		return "redirect:register_form";
	}


	@RequestMapping("modifyForm")
	public String modifyForm(@RequestParam("id") String id, Model model) {
		ms.info(id, model);
		return "member/modifyForm";
	}
	
	@PostMapping("modifySave")
	public void modifySave(MultipartHttpServletRequest mul, HttpServletRequest request, HttpServletResponse response) throws IOException{
		String message = ms.modifySave(mul, request);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(message);
	}
}
