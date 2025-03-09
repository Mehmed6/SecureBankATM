package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.UserDTO;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.service.BankAdminService;
import com.doganmehmet.app.service.RegisterService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {
    private final RegisterService m_registerService;
    private final BankAdminService m_bankAdminService;

    public RegisterController(RegisterService registerService, BankAdminService bankAdminService)
    {
        m_registerService = registerService;
        m_bankAdminService = bankAdminService;
    }

    @GetMapping("register")
    public String showRegisterPage(Model model)
    {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("banks", m_bankAdminService.findAll());
        return "register/my-register";
    }

    @PostMapping("register")
    public String register(@Valid @ModelAttribute UserDTO userDTO,
                           BindingResult bindingResult,
                           @RequestParam("bankId") Long bankId,
                           RedirectAttributes redirectAttributes,
                           Model model)
    {

        if (bindingResult.hasErrors()) {
            model.addAttribute("banks", m_bankAdminService.findAll());
            return "register/my-register";
        }

        try {
            m_registerService.register(userDTO, bankId);
        }
        catch (ApiException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMyError().getErrorMessage());
            return "redirect:register";
        }

       redirectAttributes.addFlashAttribute("message", "Registration successful! You can log in now.");
        return "redirect:auth/login";
    }
}
