package com.example.BankingApp;/*
todo:
 -

 */

import java.util.ArrayList;
import java.time.LocalDate;

public class CheckingAccount {
    private double checkingBalance;
    private final int checkingAccountNum;
    private final int accountNum;
    private boolean isActive;
    private static final ArrayList<CheckingAccount> ActiveCheckingAccountList = new ArrayList<>();
    private final static ArrayList<String> CheckingAllTransactionHistory = new ArrayList<>();
    private final ArrayList<String> UserCheckingTransactionHistory = new ArrayList<>();

    protected CheckingAccount(int accountNum, int checkingAccountNum, double startingBalance) {
        this.checkingAccountNum = checkingAccountNum;
        this.checkingBalance = startingBalance;
        this.accountNum = accountNum;
        this.isActive = true;
        ActiveCheckingAccountList.add(this);
        sayHelloChecking();
    }

    protected void transaction(double amount){
        try{
            checkingBalance = checkingBalance + amount;
            LocalDate currentDate = LocalDate.now();
            UserCheckingTransactionHistory.add("Transaction; Date: " + currentDate
                    + " Account Number: " + accountNum + " Amount: " + amount + "(" + this + ")");
            CheckingAllTransactionHistory.add("Transaction; Date: " + currentDate
                    + " Account Number: " + accountNum + " Amount: " + amount + "(" + this + ")");
            System.out.println("Your checking account balance is: " + checkingBalance);
        }
        catch (Exception e){
        System.out.println("Error at Checking Account class, transaction: error in data passed to it");
        }
    }

    protected void sayHelloChecking() {
        System.out.println("Checking account number: " + checkingAccountNum);
        System.out.println("Checking account balance: " + checkingBalance);
    }

    protected void sayGoodbye(){
        if (isActive){
            clearFunds();
            ActiveCheckingAccountList.remove(this);
            UserCheckingTransactionHistory.clear();
            isActive = false;
            checkingBalance = 0;
        }
        else{
            System.out.println("Checking Account has already been closed or not found");
        }
    }

    private void clearFunds(){
        if(checkingBalance > 0){
            BankManager.increaseBankCash(checkingBalance);
            checkingBalance = 0;
        }
    }

    protected static CheckingAccount getChecking (int accountNum) {
        for (CheckingAccount checkingAccount : ActiveCheckingAccountList) {
            if (checkingAccount.accountNum == accountNum) {
                return checkingAccount;
            }
        }
        return null;
    }

    protected double getCheckingBalance(){
        return checkingBalance;
    }
    protected static ArrayList<String> getCheckingAllTransactionHistory(){
        return CheckingAllTransactionHistory;
    }
    protected ArrayList<String> getUserCheckingTransactionHistory(){
        return UserCheckingTransactionHistory;
    }
    protected static ArrayList<CheckingAccount> getActiveCheckingAccountList(){return  ActiveCheckingAccountList;}











}


