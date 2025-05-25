package com.tes.subject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tes.subject.domain.entity.EvaluationStatus;
import com.tes.subject.domain.entity.StudentExamSubmission;
import com.tes.subject.domain.entity.Subject;
import com.tes.subject.domain.repository.StudentExamSubmissionRepository;
import com.tes.subject.domain.repository.SubjectRepository;
import com.tes.subject.model.response.DashboardResDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
	private final SubjectRepository dashboardRepository;
	private final StudentExamSubmissionRepository submissionRepository;

	public List<DashboardResDTO> getDashboardData(Long memberId) {
		List<Subject> subjects = dashboardRepository.findAllSubjectsWithFirstExamStatus();

		return subjects.stream()
				.map(subject -> {
					EvaluationStatus firstRound = subject.getEvaluationStatuses().stream()
						.filter(es -> es.getRound() == 1)
						.findFirst()
						.orElse(null);
					
					StudentExamSubmission submission = submissionRepository
						.findByMember_MemberIdAndSubject_SubjectIdAndRound(memberId, subject.getSubjectId(), 1)
						.orElse(null);
					return DashboardResDTO.from(subject, firstRound, submission);
				})
				.collect(Collectors.toList());
	}	
}