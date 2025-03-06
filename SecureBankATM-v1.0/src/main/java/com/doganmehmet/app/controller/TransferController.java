package com.doganmehmet.app.controller;


import com.doganmehmet.app.dto.TransferRequestDTO;
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
@RequestMapping("transfer")
public class TransferController {

    private final BankingOperationService m_bankingOperationService;

    public TransferController(BankingOperationService bankingOperationService)
    {
        m_bankingOperationService = bankingOperationService;
    }

    @GetMapping
    public String showTransferPage(Model model)
    {
        model.addAttribute("transferRequestDTO", new TransferRequestDTO());
        return "bankingOperation/transfer";
    }

    @PostMapping
    public String transfer(@Valid @ModelAttribute TransferRequestDTO transferRequestDTO, BindingResult bindingResult, Model model, Authentication auth)
    {
        if (bindingResult.hasErrors())
            return "bankingOperation/transfer";

        try {
            var username = auth.getName();
            m_bankingOperationService.transfer(username, transferRequestDTO);
        }
        catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "bankingOperation/transfer";
        }

        model.addAttribute("message","Transfer Successfully!");
        return "bankingOperation/transfer";
    }
}
