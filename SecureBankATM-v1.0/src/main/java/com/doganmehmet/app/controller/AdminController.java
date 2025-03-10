package com.doganmehmet.app.controller;

import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.service.BankAdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    private final BankAdminService m_bankAdminService;

    public AdminController(BankAdminService bankAdminService)
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

        return "bank/saveBank";
    }

    @GetMapping("bank/list")
    public String showBankList(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int pageSize,
                               Model model)
    {
        var pageable = PageRequest.of(page, pageSize, Sort.by("bankId").ascending());
        model.addAttribute("banks", m_bankAdminService.findAllPageable(pageable));
        return "bank/bankList";
    }

    @GetMapping("/bank/balances/{bankName}")
    public String showUsersBalancesByBankName(@PathVariable String bankName,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int pageSize,
                                              Model model)
    {
        try {
            var pageable = PageRequest.of(page, pageSize, Sort.by("username").ascending());
            model.addAttribute("userBalancesWithBank", m_bankAdminService
                    .findAllUsersBalancesByBankNamePageable(bankName,  pageable));
            model.addAttribute("bankName", bankName);
        }
        catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("userBalancesWithBank", Page.empty());
            return "bank/usersBalancesWithBank";
        }

        return "bank/usersBalancesWithBank";
    }

    @GetMapping("/bank/all/balances")
    public String showAllBanksUsersBalances(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int pageSize,
                                            Model model)
    {
        var pageable = PageRequest.of(page, pageSize, Sort.by("username").ascending());
        model.addAttribute("userBalances", m_bankAdminService.findAllUsersBalancesPageable(pageable));
        return "bank/banksUsersBalances";
    }

    @GetMapping("bank/audit/history/{username}")
    public String showUserAuditHistory(@PathVariable String username,
                                       @RequestParam(defaultValue = "0")int page,
                                       @RequestParam(defaultValue = "5") int pageSize,
                                       Model model)
    {
        try {
            var pageable = PageRequest.of(page, pageSize, Sort.by("actionDate").descending());

            model.addAttribute("username", username);
            model.addAttribute("auditLogs", m_bankAdminService.findAuditByUsernamePageable(username, pageable));
        }
        catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("auditLogs", Page.empty());
            return "audit/auditHistoryByUsername";
        }

        return "audit/auditHistoryByUsername";
    }

    @GetMapping("bank/audit/all/history")
    public String showAllAuditHistory(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int pageSize,
                                      Model model)
    {
        var pageable = PageRequest.of(page, pageSize, Sort.by("actionDate").descending());
        model.addAttribute("auditLogs", m_bankAdminService.findAllAuditPageable(pageable));
        return "audit/auditHistory";
    }

    @GetMapping("bank/transaction/history/{username}")
    public String showUserTransactionHistory(@PathVariable String username,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int pageSize,
                                             Model model)
    {
        try {
            var pageable = PageRequest.of(page, pageSize, Sort.by("transactionDate").descending());
            model.addAttribute("username", username);
            model.addAttribute("transactionLogs", m_bankAdminService.findTransactionByUsernamePageable(username, pageable));
        }
        catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("transactionLogs", Page.empty());
            return "transaction/transactionHistoryByUsername";
        }
        return "transaction/transactionHistoryByUsername";
    }

    @GetMapping("bank/transaction/all/history")
    public String showAllTransactionHistory(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int pageSize,
                                            Model model)
    {
        var pageable = PageRequest.of(page, pageSize, Sort.by("transactionDate").descending());
        model.addAttribute("transactionLogs", m_bankAdminService.findAllTransactionPageable(pageable));
        return "transaction/transactionHistory";
    }

    @GetMapping("bank/users/{bankName}")
    public String showAllUsersByBankName(@PathVariable String bankName,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int pageSize,
                                         Model model)
    {
        try {
            var pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
            model.addAttribute("bankName", bankName);
            model.addAttribute("users", m_bankAdminService.findAllUserByBankNamePageable(bankName, pageable));
        }
        catch (ApiException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("users", Page.empty());
            return "user/bankUsersByBankName";
        }
        return "user/bankUsersByBankName";
    }

    @GetMapping("bank/all/users")
    public String showAllUsers( @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int pageSize,
                                Model model)
    {
        var pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        model.addAttribute("users", m_bankAdminService.findAllUsersPageable(pageable));

        return "user/banksUsers";
    }

}
