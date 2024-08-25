package com.example.BankingApp.Model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CheckingAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection
    private List<String> transactionHistory = new ArrayList<>();

    public void transaction(double amount) {
        balance += amount;
        transactionHistory.add("Transaction on " + LocalDate.now() + ": " + amount);
    }

    // Constructors, Getters, and Setters
}
