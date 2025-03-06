package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.DepositRequestDTO;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.service.BankingOperationService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("deposit")
public class DepositController {
    private final BankingOperationService m_bankingOperationService;

    public DepositController(BankingOperationService bankingOperationService)
    {
        m_bankingOperationService = bankingOperationService;
    }

    @GetMapping
    public String showDepositPage(Model model)
    {
        model.addAttribute("depositRequestDTO", new DepositRequestDTO());
        return "bankingOperation/deposit";
    }

    @PostMapping
    public String deposit(@Valid @ModelAttribute DepositRequestDTO depositRequestDTO, BindingResult bindingResult, Model model, Authentication auth)
    {
        if (bindingResult.hasErrors())
            return "bankingOperation/deposit";

        try {
            var username = auth.getName();
            m_bankingOperationService.deposit(username, depositRequestDTO);
        }
        catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "bankingOperation/deposit";
        }

        model.addAttribute("message", "Deposit successful");
        return "bankingOperation/deposit";

    }
}
