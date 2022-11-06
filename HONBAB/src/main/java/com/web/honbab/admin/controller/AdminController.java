package com.web.honbab.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.honbab.admin.service.AdminService;
import com.web.honbab.member.service.MemberService;
import com.web.honbab.session.admin.AdminSession;


@Controller
@RequestMapping("ban")
public class AdminController implements AdminSession{
	
	@Autowired
	private AdminService as;
	
	@Autowired
	private MemberService ms;

//	@PostMapping("admin_check") //관리자 확인
//	public String adminCheck(HttpServletRequest request, RedirectAttributes rs) {
//		int result = as.admin_check(request);
//		if(result == 0) {
//			rs.addAttribute("adminId", request.getParameter("adminId"));
//			return "redirect:successLogin";
//		}
//		return "redirect:adminlogin";
//	}	
//	
//	@PostMapping("successLogin") //로그인 성공
//	public String successLogin(@RequestParam("adminId") String adminId, HttpSession session) {
//		session.setAttribute(LOGIN, adminId);
//		return "admin/successLogin";
//	}
//	
//	@PostMapping("adminLogin")
//	public String adminLogin() {
//		return "admin/adminlogin";
//	}
//	
//	@GetMapping("adminLogout")
//	public String logout(HttpSession session) {
//		if(session.getAttribute("loginUser") != null) {
//			session.invalidate();
//		}
//		return "redirect:/index";
//	}
	
	@RequestMapping("userManagement")
	public String adminBanIndex() { 
		return "admin/adminBan";
	};
	
	@GetMapping("adminBan") //회원탈퇴권한 페이지
	public String adminBan(@RequestParam("id") String id) {
		System.out.println("id : " + id);
		as.ban(id);
		return "admin/adminBan";
	}	
	
	@GetMapping("adminPartnerBan") //회원탈퇴권한 페이지
	public String adminPartnerBan(@RequestParam("id") String id) {
		System.out.println("id : " + id);
		as.partnerBan(id);
		return "admin/adminPartnerBan";
	}	
	
	
	
//	@RequestMapping("generalDelete") //일반회원 삭제
//	public void generalDelete(@RequestParam("id") String id,HttpServletResponse response, HttpServletRequest request) throws IOException{
//		String message = as.generalDelete(id, request);
//		response.setContentType("text/html; charset=utf-8");
//		PrintWriter out = response.getWriter();		
//		out.println(message);
//	}
//	
//	@PostMapping("partnerDelete") //파트너 삭제
//	public void partnerDelete(@RequestParam("id") String id,HttpServletResponse response, HttpServletRequest request) throws IOException{
//		String message = as.partnerDelete(id, request);
//		response.setContentType("text/html; charset=utf-8");
//		PrintWriter out = response.getWriter();
//		out.println(message);
//	}
	
	@RequestMapping("member/info")
	public String info(@RequestParam("id") String id, Model model) {
		ms.info(id, model);
		return "member/info";
	}
	
	
	
	
	

}
