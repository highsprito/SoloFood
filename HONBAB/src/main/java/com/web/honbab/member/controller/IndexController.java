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
import com.web.honbab.member.dto.MemberDTO;
import com.web.honbab.member.service.MemberService;
import com.web.honbab.session.admin.AdminSession;
import com.web.honbab.session.name.MemberSession;

@Controller
@RequestMapping("main")
public class IndexController implements MemberSession{
	
	@Autowired
	private MemberService ms;

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
	public String successLogin(@RequestParam("id") String id, HttpSession session) {
		session.setAttribute(LOGIN, id);
		return "index";
	}
	
	
	@GetMapping("login")
	public String login() {
		return "main/login";
	}
	@GetMapping("idcheckNo")
	public String idcheckNo() {
		return "main/idcheckNo";
	}
	
	@GetMapping("idcheck")
	public String idcheck(HttpServletRequest request, RedirectAttributes rs) {
		int result = ms.user_check(request);
		if(result == 1) {
			rs.addAttribute("id", request.getParameter("id"));
			return "main/idcheck";
		} else {
		return "main/idcheckNo";
		}
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		if(session.getAttribute("loginUser") != null) {
			session.invalidate();
		}
		return "redirect:/index";
	}
	
	
	@RequestMapping("/register_form")
	public String register_form() {
		return "main/register";
	}
	
	@RequestMapping("/register")
	public String register(MemberDTO member) {
		int result = ms.register(member);
		if(result == 1) {
			return "redirect:login";
		}
		return "redirect:register_form";
	}
}
