package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.LoginRequestDTO;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.service.LoginService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final LoginService m_loginService;

    public LoginController(LoginService loginService)
    {
        m_loginService = loginService;
    }


    @GetMapping("/auth/login")
    public String showLoginPage(Model model) {

        model.addAttribute("loginRequestDTO", new LoginRequestDTO());

        return "login/my-login";
    }

    @PostMapping("/auth/login")
    public String login(@Valid @ModelAttribute("loginRequestDTO") LoginRequestDTO loginRequestDTO,
                        BindingResult bindingResult, Model model, HttpSession session) {

        if (bindingResult.hasErrors())
            return "login/my-login";


        try {
            Authentication authentication = m_loginService.login(loginRequestDTO);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return "/dashboard";
        } catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "login/my-login";
        }
    }

    @GetMapping("dashboard")
    public String showDashboard()
    {
        return "dashboard";
    }

}
