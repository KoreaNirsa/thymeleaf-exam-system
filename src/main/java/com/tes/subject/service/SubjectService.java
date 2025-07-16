package com.tes.subject.service;

import java.util.List;

import com.tes.auth.model.request.LoginReqDTO;
import com.tes.auth.model.response.LoginResDTO;
import com.tes.subject.model.response.DashboardResDTO;

/**
 * 과목 관련 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 * 
 * @author 
 * @since 1.0
 */
public interface SubjectService {
	
    /**
     * 로그인한 사용자의 ID를 기반으로 대시보드에 출력할 과목별 평가 정보를 조회합니다.
     * <p>
     * 조회 결과에는 과목명, 평가일, 평가 상태, 점수, 피드백, 버튼 상태 등이 포함됩니다.
     * </p>
     *
     * @param memberId 로그인한 사용자의 고유 ID
     * @return 과목별 대시보드 출력용 응답 DTO 리스트
     */
	public List<DashboardResDTO> getDashboardData(Long memberId);
}
