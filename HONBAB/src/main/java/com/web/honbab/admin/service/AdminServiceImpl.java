package com.web.honbab.admin.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.honbab.admin.dto.AdminDTO;
import com.web.honbab.mybatis.admin.AdminMapper;


@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminMapper mapper;
	
//	@Override
//	public int admin_check(HttpServletRequest request) {
//		AdminDTO dto = mapper.admin_check(request.getParameter("adminId"));
//		if(dto != null) {
//			if(request.getParameter("adminPw").equals(dto.getAdminPw())) {
//				return 0;
//			}
//		}
//		return 1;
//	}
	
//	@Override
//	public String generalDelete(String id, HttpServletRequest request) {
//		int result = mapper.generalDelete(id);
//		String msg, url;
//		
//		if(result == 1) {
//			msg = "일반회원 삭제완료.";
//			url = "admin/ADMIndex";
//		} else {
//			msg ="오류 발생, 작업이 완료되지 않았습니다.";
//			url = "/member/info?id="+id;
//		}
//	return getMessage(request, msg, url);
//	}
//	
//	@Override
//	public String partnerDelete(String id, HttpServletRequest request) {
//		int result = mapper.partnerDelete(id);
//		String msg, url;
//		
//		if(result == 1) {
//			msg = "파트너 삭제완료.";
//			url = "admin/ADMIndex";
//		} else {
//			msg ="오류 발생, 작업이 완료되지 않았습니다.";
//			url = "/member/info?id="+id;
//		}
//	return getMessage(request, msg, url);
//	}
	
//	@Override
//	public String getMessage(HttpServletRequest request, String msg, String url) {
//
//		String message = null;
//		String path = request.getContextPath();
//		message = "<script>alert('" + msg + "');";
//		message += "location.href='" + path + url + "';</script>";
//		return message;
//		
//	}

	@Override
	public void ban(String id) {
		mapper.ban(id);
	}
	
	@Override
	public void partnerBan(String id) {
		mapper.partnerBan(id);
	}
	
	
	
}
