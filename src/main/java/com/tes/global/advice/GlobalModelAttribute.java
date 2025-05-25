package com.tes.global.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalModelAttribute {
    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpSession session) {
        Object member = session.getAttribute("member");
        if (member != null) {
            model.addAttribute("member", member);
        }
    }
}
