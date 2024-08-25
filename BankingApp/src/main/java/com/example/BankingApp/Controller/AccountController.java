package com.example.BankingApp.Controller;

import com.example.BankingApp.Model.User;
import com.example.BankingApp.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public User createAccount(@RequestBody User user) {
        return accountService.createAccount(user.getFirstName(), user.getLastName(), user.getTotalBalance());
    }

    @DeleteMapping("/{id}")
    public void closeAccount(@PathVariable long id) {
        accountService.closeAccount(id);
    }

    @GetMapping
    public List<User> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}
