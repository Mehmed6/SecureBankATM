package com.doganmehmet.app.controller;

import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.service.BankAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/")
public class BankAdminController {

    private final BankAdminService m_bankAdminService;

    public BankAdminController(BankAdminService bankAdminService)
    {
        m_bankAdminService = bankAdminService;
    }

    @GetMapping("save/bank")
    public String showBankPage()
    {
        return "bank/saveBank";
    }

    @PostMapping("save/bank")
    public String saveBank(@RequestParam String bankName, Model model)
    {
        try {
            m_bankAdminService.saveBank(bankName);
        }
        catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "bank/saveBank";
        }

        model.addAttribute("message", "Bank saved successfully");
        model.addAttribute("banks", m_bankAdminService.findAll());

        return "bank/bankList";
    }

    @GetMapping("bank/list")
    public String showBankList(Model model)
    {
        model.addAttribute("banks", m_bankAdminService.findAll());
        return "bank/bankList";
    }

    @GetMapping("/bank/balances/{bankName}")
    public String showUsersBalancesByBankName(@PathVariable String bankName, Model model)
    {
        try {
            model.addAttribute("userBalancesWithBank", m_bankAdminService.findAllUsersBalancesByBankName(bankName));
        }
        catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "bank/usersBalancesWithBank";
        }

        return "bank/usersBalancesWithBank";
    }

    @GetMapping("/bank/all/balances")
    public String showAllBanksUsersBalances( Model model)
    {
        model.addAttribute("userBalances", m_bankAdminService.findAllUsersBalances());
        return "bank/banksUsersBalances";
    }

    @GetMapping("bank/audit/history/{username}")
    public String showUserAuditHistory(@PathVariable String username, Model model)
    {
        try {
            model.addAttribute("auditLogs", m_bankAdminService.findAuditByUsername(username));
        }
        catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "audit/auditHistory";
        }

        return "audit/auditHistory";
    }

    @GetMapping("bank/audit/all/history")
    public String showAllAuditHistory( Model model)
    {
        model.addAttribute("auditLogs", m_bankAdminService.findAllAudit());
        return "audit/auditHistory";
    }

    @GetMapping("bank/transaction/history/{username}")
    public String showUserTransactionHistory(@PathVariable String username, Model model)
    {
        try {
            model.addAttribute("transactionLogs", m_bankAdminService.findTransactionByUsername(username));
        }
        catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "transaction/transactionHistory";
        }
        return "transaction/transactionHistory";
    }

    @GetMapping("bank/transaction/all/history")
    public String showAllTransactionHistory(Model model)
    {
        model.addAttribute("transactionLogs", m_bankAdminService.findAllTransaction());
        return "transaction/transactionHistory";
    }

    @GetMapping("bank/all/users/{bankName}")
    public String showAllUsersByBankName(@PathVariable String bankName, Model model)
    {
        try {
            model.addAttribute("users", m_bankAdminService.findAllUserByBankName(bankName));
        }
        catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "user/bankUsersByBankName";
        }
        return "user/bankUsersByBankName";
    }

    @GetMapping("bank/all/users")
    public String showAllUsers( Model model)
    {
        try {
            model.addAttribute("users", m_bankAdminService.findAllUsers());
        }
        catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "user/banksUsers";
        }
        return "user/banksUsers";
    }
}
