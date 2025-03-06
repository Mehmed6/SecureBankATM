package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.WithdrawRequestDTO;
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
@RequestMapping("withdraw")
public class WithdrawController {
    private final BankingOperationService m_bankingOperationService;

    public WithdrawController(BankingOperationService bankingOperationService)
    {
        m_bankingOperationService = bankingOperationService;
    }

    @GetMapping
    public String showWithdrawPage(Model model)
    {
        model.addAttribute("withdrawRequestDTO", new WithdrawRequestDTO());
        return "bankingOperation/withdraw";
    }

    @PostMapping
    public String withdraw(@Valid @ModelAttribute WithdrawRequestDTO withdrawRequestDTO, BindingResult bindingResult, Model model, Authentication auth)
    {
        if (bindingResult.hasErrors())
            return "bankingOperation/withdraw";

        try {
            var username = auth.getName();
            m_bankingOperationService.withdraw(username, withdrawRequestDTO);
        }
        catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "bankingOperation/withdraw";
        }

        model.addAttribute("message", "Withdraw successful");
        return "bankingOperation/withdraw";
    }
}
