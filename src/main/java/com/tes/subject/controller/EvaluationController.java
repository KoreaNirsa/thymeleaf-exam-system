package com.tes.subject.controller;

import com.tes.subject.model.request.FeedbackEditReqDTO;
import com.tes.subject.service.EvaluationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * 피드백 관련 요청을 처리하는 컨트롤러입니다.
 *
 * @author KoreaNirsa
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class EvaluationController {
    private final EvaluationService evaluationService;

    /**
     * 피드백 정보를 수정합니다.
     * <p>
     * 전달받은 {@link FeedbackEditReqDTO} 객체를 바탕으로 피드백을 갱신하며, 수정 완료 후에는 해당 학생의 상세 페이지로 이동합니다.
     * </p>
     *
     * <ul>
     * <li>유효성 검증 실패 시 리다이렉트 경로는 /student입니다.</li>
     * <li>성공 시에는 /student/{memberId}?avg=...&rank=... 경로로 이동합니다.</li>
     * <li>피드백 수정 처리는 {@link EvaluationService#editFeedback(FeedbackEditReqDTO)}에 위임됩니다.</li>
     * </ul>
     *
     * @param feedbackEditReqDTO 피드백 수정 요청 DTO (검증 대상)
     * @param bindingResult DTO 검증 결과를 담고 있는 객체
     * @return 수정 결과에 따른 리다이렉트 경로
     *
     * @see com.tes.subject.model.request.FeedbackEditReqDTO
     * @see com.tes.subject.service.EvaluationService#editFeedback(FeedbackEditReqDTO)
     */
    @PatchMapping
    public String editFeedback(@Valid FeedbackEditReqDTO feedbackEditReqDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/student";
        }

        evaluationService.editFeedback(feedbackEditReqDTO);

        return "redirect:/student/" + feedbackEditReqDTO.getMemberId()
                + "?avg=" + feedbackEditReqDTO.getAvgScore()
                + "&rank=" + feedbackEditReqDTO.getRank();
    }
}

