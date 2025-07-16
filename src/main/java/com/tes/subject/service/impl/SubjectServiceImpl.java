package com.tes.subject.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.tes.subject.service.SubjectService;
import org.springframework.stereotype.Service;

import com.tes.subject.domain.entity.EvaluationStatus;
import com.tes.subject.domain.entity.StudentExamSubmission;
import com.tes.subject.domain.entity.Subject;
import com.tes.subject.domain.repository.StudentExamSubmissionRepository;
import com.tes.subject.domain.repository.SubjectRepository;
import com.tes.subject.model.response.DashboardResDTO;

import lombok.RequiredArgsConstructor;

/**
 * 과목 관련 요청을 처리하는 서비스 구현체입니다.
 *
 * @author KoreaNirsa
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
	private final SubjectRepository dashboardRepository;
	private final StudentExamSubmissionRepository submissionRepository;

    /**
     * 로그인한 사용자 ID를 기반으로 대시보드에 표시할 과목별 정보 목록을 반환합니다.
     *
     * <p>
     * 각 과목에 대해:
     * <ul>
     *     <li>1차 평가 상태({@link EvaluationStatus})</li>
     *     <li>학생 제출 정보({@link StudentExamSubmission})</li>
     * </ul>
     * 를 조회하여 {@link DashboardResDTO}로 매핑합니다.
     * </p>
     *
     * @param memberId 로그인한 사용자의 ID
     * @return 대시보드 출력용 응답 DTO 리스트
     */
	public List<DashboardResDTO> getDashboardData(Long memberId) {
		List<Subject> subjects = dashboardRepository.findAllSubjectsWithFirstExamStatus();

		return subjects.stream()
				.map(subject -> {
					// 해당 과목의 평가 상태 리스트에서 round가 1(1차 평가)인 상태만 필터링하여 가져옴
		            // 없을 경우 null 반환
					EvaluationStatus firstRound = subject.getEvaluationStatuses().stream()
						.filter(es -> es.getRound() == 1)
						.findFirst()
						.orElse(null);
					
		            // 해당 과목에 대해 현재 로그인한 사용자의 1차 제출 정보를 DB에서 조회
		            // 제출 정보가 없으면 null 반환
					StudentExamSubmission submission = submissionRepository
						.findByMember_MemberIdAndSubject_SubjectIdAndRound(memberId, subject.getSubjectId(), 1)
						.orElse(null);
					
					return DashboardResDTO.from(subject, firstRound, submission);
				})
				.collect(Collectors.toList());
	}	
}