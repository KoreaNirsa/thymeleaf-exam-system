package com.tes.subject.service.impl;

import com.tes.subject.domain.entity.EvaluationStatus;
import com.tes.subject.domain.entity.StudentExamSubmission;
import com.tes.subject.domain.repository.EvaluationStatusRepository;
import com.tes.subject.domain.repository.StudentExamSubmissionRepository;
import com.tes.subject.model.request.FeedbackEditReqDTO;
import com.tes.subject.service.EvaluationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 평가 관련 요청을 처리하는 서비스 구현체입니다.
 *
 * @author KoreaNirsa
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EvaluationServiceImpl implements EvaluationService {
    private final StudentExamSubmissionRepository studentExamSubmissionRepository;
    private final EvaluationStatusRepository evaluationStatusRepository;

    /**
     * 피드백을 수정하고, 평가 상태를 "채점 진행중"으로 변경합니다.
     *
     * <p>
     * 다음과 같은 절차로 수행됩니다
     * </p>
     *
     * <ol>
     * <li>{@link StudentExamSubmission}을 ID로 조회 후 피드백 내용을 갱신합니다.</li>
     * <li>{@link EvaluationStatus}를 과목 ID와 회차 기준으로 조회한 후, 상태가 "점수 확인"이 아니면 변경합니다.</li>
     * </ol>
     *
     * @param feedbackEditReqDTO 피드백 수정 요청 DTO
     * @throws EntityNotFoundException 제출 정보나 평가 상태가 존재하지 않을 경우 발생
     *
     * @see com.tes.subject.model.request.FeedbackEditReqDTO
     * @see com.tes.subject.domain.entity.StudentExamSubmission
     * @see com.tes.subject.domain.entity.EvaluationStatus
     */
    @Override
    public void editFeedback(FeedbackEditReqDTO feedbackEditReqDTO) {
        StudentExamSubmission submission = studentExamSubmissionRepository.findById(feedbackEditReqDTO.getStudentExamSubmissionId())
                .orElseThrow(() -> new EntityNotFoundException("제출 정보를 찾을 수 없습니다."));

        submission.setFeedback(feedbackEditReqDTO.getFeedbackContent());

        EvaluationStatus status = evaluationStatusRepository.findBySubject_SubjectIdAndRound(feedbackEditReqDTO.getSubjectId(), submission.getRound())
                .orElseThrow(() -> new EntityNotFoundException("평가 상태를 찾을 수 없습니다."));

        if(!"점수 확인".equals(status.getStatus()) && !"채점 진행중".equals(status.getStatus())) {
            status.setStatus("채점 진행중");
        }
    }
}
