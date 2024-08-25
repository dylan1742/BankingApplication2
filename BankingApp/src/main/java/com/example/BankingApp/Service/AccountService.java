package com.example.BankingApp.Service;

import com.example.BankingApp.Model.User;
import com.example.BankingApp.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public User createAccount(String firstName, String lastName, double startingBalance) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setTotalBalance(startingBalance);
        user.setActive(true);
        user.setDateOpened(LocalDate.now());
        return accountRepository.save(user);
    }

    public void closeAccount(long id) {
        User user = accountRepository.findById(id);
        if (user != null && user.isActive()) {
            user.setActive(false);
            accountRepository.save(user);
        }
    }

    public List<User> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Other service methods
}
