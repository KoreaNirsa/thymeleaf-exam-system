package com.tes.subject.service;

import java.util.List;

import com.tes.member.model.request.LoginReqDTO;
import com.tes.member.model.response.LoginResDTO;
import com.tes.subject.model.response.DashboardResDTO;

public interface SubjectService {
	public List<DashboardResDTO> getDashboardData(Long memberId);
}
