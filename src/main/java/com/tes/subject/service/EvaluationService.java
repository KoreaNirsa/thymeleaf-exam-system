package com.tes.subject.service;

import com.tes.subject.model.request.FeedbackEditReqDTO;

/**
 * 평가 관련 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 *
 * @author
 * @since 1.0
 */
public interface EvaluationService {
    public void editFeedback(FeedbackEditReqDTO feedbackEditReqDTO);
}
