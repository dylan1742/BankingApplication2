package com.example.BankingApp;/*
todo:
 -
 */

import java.util.ArrayList;
import java.time.LocalDate;

public class SavingsAccount {
    private double savingsAccountBalance;
    private final int savingsAccountNum;
    private final int accountNum;
    private boolean isActive;
    private static double interestRate = .05;
    private final static ArrayList<SavingsAccount> ActiveSavingsAccountList = new ArrayList<>();
    private final static ArrayList<String> SavingsAllTransactionHistory = new ArrayList<>();
    private final ArrayList<String> UserSavingsTransactionHistory = new ArrayList<>();

    protected SavingsAccount(int accountNum, int checkingAccountNum, double startingBalance) {
        this.savingsAccountNum = checkingAccountNum;
        this.savingsAccountBalance = startingBalance;
        this.accountNum = accountNum;
        this.isActive = true;
        ActiveSavingsAccountList.add(this);
        sayHelloSavings();
    }

    protected void transaction( int amount){
        try{
            savingsAccountBalance = savingsAccountBalance + amount;
            LocalDate currentDate = LocalDate.now();
            UserSavingsTransactionHistory.add("Transaction; Date: " + currentDate
                    + " Account Number: " + accountNum + " Amount: " + amount + "(" + this + ")");
            SavingsAllTransactionHistory.add("Transaction; Date: " + currentDate
                    + " Account Number: " + accountNum + " Amount: " + amount + "(" + this + ")");
            System.out.println("Your savings account balance is: " + savingsAccountBalance);
        }
        catch (Exception e){
            System.out.println("Error at Savings Account class, transaction: error in data passed to it");
        }
    }

    protected void sayHelloSavings() {
        System.out.println("Savings account number: " + savingsAccountNum);
        System.out.println("Savings account balance: " + savingsAccountBalance);
    }

    protected void sayGoodbye(){
        if (isActive){
            clearFunds();
            ActiveSavingsAccountList.remove(this);
            UserSavingsTransactionHistory.clear();
            isActive = false;
            savingsAccountBalance = 0;
        }
        else{
            System.out.println("Savings Account has already been closed or not found");
        }
    }

    private void clearFunds(){
        if(savingsAccountBalance > 0){
            BankManager.increaseBankCash(savingsAccountBalance);
            savingsAccountBalance = 0;
        }
    }

    protected static void applyMonthlyInterest(){
        for (SavingsAccount savingsAccount : ActiveSavingsAccountList){
            if (savingsAccount.isActive){
                double monthlyInterestRate = interestRate / 12;
                savingsAccount.savingsAccountBalance = savingsAccount.savingsAccountBalance * (1 + monthlyInterestRate);
                System.out.println("You have earned " + savingsAccount.savingsAccountBalance * monthlyInterestRate + " interest this month!");
            }
        }
    }

    protected static SavingsAccount getSavings (int accountNum) {
        for (SavingsAccount savingsAccount : ActiveSavingsAccountList) {
            if (savingsAccount.accountNum == accountNum) {
                return savingsAccount;
            }
        }
        return null;
    }

    protected double getSavingsAccountBalance(){
        return savingsAccountBalance;
    }

    protected static ArrayList<String> getSavingsAllTransactionHistory(){
        return SavingsAllTransactionHistory;
    }

    protected ArrayList<String> getUserSavingsTransactionHistory(){
        return UserSavingsTransactionHistory;
    }






}
