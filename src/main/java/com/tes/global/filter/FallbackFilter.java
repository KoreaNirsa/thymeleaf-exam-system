package com.tes.global.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import java.io.IOException;
import java.util.List;

/**
 * 존재하지 않는 URL 요청에 대해 404 상태 코드를 반환하는 전역 Fallback 필터입니다.
 * 
 * <p>
 * 이 필터는 DispatcherServlet 이전에 실행되며, 다음과 같은 요청을 필터링합니다.
 * </p>
 *
 * <ul>
 *   <li>정적 리소스 경로(`/css`, `/js` 등)는 그대로 통과</li>
 *   <li>매핑된 컨트롤러가 존재하는 경우에도 그대로 통과</li>
 *   <li>둘 다 해당하지 않으면 404 오류를 발생시켜 {@link org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver}로 위임</li>
 * </ul>
 *
 * <p>
 * 정적 자원 외에도 도메인별 prefix(`/student`, `/member` 등)를 명시적으로 제외하여  
 * 라우팅 충돌 없이 예외 처리만 전담하도록 구성합니다.
 * </p>
 *
 * @author 김재섭
 * @since 2025-06-04
 */
@Component
public class FallbackFilter implements Filter {

    private static final List<String> STATIC_PATH_PREFIXES = List.of(
            "/css", "/js", "/images", "/student", "/subject", "/member", "/evaluation", "/favicon.ico"
    );

    @Autowired
    private List<HandlerMapping> handlerMappings;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        // 1. 정적 경로는 필터링 없이 통과
        for (String prefix : STATIC_PATH_PREFIXES) {
            if (uri.startsWith(prefix)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // 2. 실제 컨트롤러가 존재하면 통과
        for (HandlerMapping mapping : handlerMappings) {
            try {
                HandlerExecutionChain handler = mapping.getHandler(req);
                if (handler != null) {
                    chain.doFilter(request, response);
                    return;
                }
            } catch (Exception e) {
            	// 매핑 실패는 무시하고 404로 처리
            }
        }

        //  3. 모든 조건에서 걸리지 않으면 404 반환
        res.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}