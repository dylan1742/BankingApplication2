package com.example.BankingApp.Service;

import com.example.BankingApp.Model.User;
import com.example.BankingApp.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    private AccountRepository accountRepository;

    public void showBankInfo() {
        List<User> users = accountRepository.findAll();
        double totalBalance = users.stream().mapToDouble(User::getTotalBalance).sum();
        System.out.println("Total Assets Under Management: $" + totalBalance);
        System.out.println("Total Accounts: " + users.size());
    }

    // Other service methods
}
