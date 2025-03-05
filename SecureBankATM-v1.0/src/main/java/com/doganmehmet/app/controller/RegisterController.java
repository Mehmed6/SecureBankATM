package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.UserDTO;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.service.RegisterService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    private final RegisterService m_registerService;

    public RegisterController(RegisterService registerService)
    {
        m_registerService = registerService;
    }

    @GetMapping("register")
    public String showRegisterForm(Model model)
    {
        model.addAttribute("userDTO", new UserDTO());
        return "register/my-register";
    }

    @PostMapping("register")
    public String register(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
            return "register/my-register";

        try {
            m_registerService.register(userDTO);
        }
        catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMyError().getErrorMessage());
            return "register/my-register";
        }

        model.addAttribute("message", "Registration successful! You can log in now.");
        return "login/my-login";
    }
}
