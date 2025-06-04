package com.tes.global.error;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Spring Boot의 {@link org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver}를 구현하여
 * 오류 상태 코드에 따른 사용자 정의 HTML View를 반환하는 컴포넌트입니다.
 *
 * <p>
 * 현재 구현은 404 오류에 대해서만 {@code pages/error/404.html} 뷰를 반환하며,
 * 해당 뷰에 다음 속성들이 포함됩니다:
 * </p>
 *
 * <ul>
 *     <li>{@code hideHeader} : 헤더 숨김 여부 (true)</li>
 * </ul>
 *
 * <p>
 * 다른 상태 코드(500, 403 등)에 대해서는 향후 확장성을 고려하여 기본 뷰로 리다이렉트하거나
 * 공통 에러 페이지를 지정할 수 있습니다.
 * </p>
 *
 * @author 김재섭
 * @since 2025-06-04
 */
@Component
public class CustomErrorViewResolver implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        ModelAndView mav = new ModelAndView("pages/error/404");
        
        mav.addObject("hideHeader", true);
        mav.addAllObjects(model);
        
        return mav;
    }
}