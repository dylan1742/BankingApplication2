package com.example.BankingApp.Service;

import com.example.BankingApp.Model.CheckingAccount;
import com.example.BankingApp.Model.User;
import com.example.BankingApp.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ComplianceService complianceService;

    public void transfer(long fromAccountId, long toAccountId, double amount) {
        User fromUser = accountRepository.findById(fromAccountId);
        User toUser = accountRepository.findById(toAccountId);

        if (fromUser != null && toUser != null) {
            CheckingAccount fromAccount = fromUser.getCheckingAccounts().get(0);  // Assume one checking account per user
            CheckingAccount toAccount = toUser.getCheckingAccounts().get(0);

            if (complianceService.meetsMinBalance(fromAccount) && complianceService.meetsMaxTransfer(amount)) {
                fromAccount.transaction(-amount);
                toAccount.transaction(amount);
                accountRepository.save(fromUser);
                accountRepository.save(toUser);
            }
        }
    }

    // Other transaction methods
}
