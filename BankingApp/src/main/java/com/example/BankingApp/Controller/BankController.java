package com.example.BankingApp.Controller;

import com.example.BankingApp.Service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @GetMapping("/info")
    public void showBankInfo() {
        bankService.showBankInfo();
    }
}
