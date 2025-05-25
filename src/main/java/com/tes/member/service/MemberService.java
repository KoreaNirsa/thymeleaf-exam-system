package com.tes.member.service;

import com.tes.member.model.request.LoginReqDTO;
import com.tes.member.model.response.LoginResDTO;

public interface MemberService {
	public LoginResDTO login(LoginReqDTO loginReqDTO);
}
