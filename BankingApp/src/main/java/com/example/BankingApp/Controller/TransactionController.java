package com.example.BankingApp.Controller;

import com.example.BankingApp.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public void transfer(@RequestParam long fromAccountId, @RequestParam long toAccountId, @RequestParam double amount) {
        transactionService.transfer(fromAccountId, toAccountId, amount);
    }
}
