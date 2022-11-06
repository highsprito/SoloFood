package com.web.honbab.mybatis.admin;

import com.web.honbab.admin.dto.AdminDTO;

public interface AdminMapper {

//	AdminDTO admin_check(String adminId);

//	int generalDelete(String id);
//
//	int partnerDelete(String id);

	int ban(String id);

	int partnerBan(String id);

}
