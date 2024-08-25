package com.example.BankingApp.Service;

import com.example.BankingApp.Model.CheckingAccount;
import org.springframework.stereotype.Service;

@Service
public class ComplianceService {

    private final int minBalance = 10;
    private final int maxTransfer = 10000;

    public boolean meetsMinBalance(CheckingAccount account) {
        return account.getBalance() >= minBalance;
    }

    public boolean meetsMaxTransfer(double amount) {
        return amount <= maxTransfer;
    }

    // Other compliance methods
}
