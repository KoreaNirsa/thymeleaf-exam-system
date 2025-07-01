package com.tes.member.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tes.member.model.request.StudentAddReqDTO;
import com.tes.member.model.response.StudentDetailInfoResDTO;
import com.tes.member.model.response.StudentListResDTO;

/**
 * 학생 관련 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 * <p>
 * 현재는 학생 목록을 평균 점수 기준으로 페이징 조회하는 기능만 제공되며,
 * 향후 학생 성적 통계, 평가 이력 조회, 과목별 성취도 분석 등으로 확장될 수 있습니다.
 * </p>
 *
 * <p>
 * 해당 인터페이스는 {@code StudentServiceImpl} 클래스에서 구현됩니다.
 * </p>
 *
 * @author 
 * @since 1.0
 */
public interface StudentService {
	public Page<StudentListResDTO> getStudentPage(Pageable pageable);

	public void addStudent(StudentAddReqDTO studentAddReqDTO);
	public StudentDetailInfoResDTO getStudentDetail(long memberId, double avg, int rank);

	public void changePassword(long memberId, String newPassword, String confirmPassword, long sessionMemberId);
}
