package com.tes.global.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tes.auth.model.response.LoginResDTO;
import com.tes.global.enums.WhitelistPath;
import com.tes.member.domain.entity.Member;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthFilter implements Filter {

	private static final List<String> WHITELIST = Arrays.stream(WhitelistPath.values())
	        .map(WhitelistPath::getPath)
	        .toList();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        String uri = req.getRequestURI();
        boolean isWhitelisted = WHITELIST.stream().anyMatch(uri::startsWith);
        
        HttpSession session = req.getSession(false);
        LoginResDTO member = (session != null) ? (LoginResDTO) session.getAttribute("member") : null;
        
        if (uri.equals("/") || isWhitelisted || (member != null && member.getRole() != null)) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect("/login");
        }
    }
}