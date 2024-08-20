package com.example.BankingApp;/*
todo:
 -
 */

import java.util.ArrayList;
import java.time.LocalDate;
public class InvestmentAccount {

    private final int investmentAccountNum;
    private final int accountNum;
    private double investmentAccountBalance;
    private boolean isActive;
    private static double interestRate = 0.06;
    private final static ArrayList<InvestmentAccount> ActiveInvestmentAccountList = new ArrayList<>();
    private final static ArrayList<String> InvestmentAllTransactionHistory = new ArrayList<>();
    private final ArrayList<String> UserInvestmentTransactionHistory = new ArrayList<>();

    public InvestmentAccount(int accountNum, int checkingAccountNum, double startingBalance) {
        this.investmentAccountNum = checkingAccountNum;
        this.investmentAccountBalance = startingBalance;
        this.accountNum = accountNum;
        this.isActive = true;
        ActiveInvestmentAccountList.add(this);
        sayHelloSavings();
    }

    public void transaction(double amount){
        try{
            investmentAccountBalance = investmentAccountBalance + amount;
            LocalDate currentDate = LocalDate.now();
            UserInvestmentTransactionHistory.add("Transaction; Date: " + currentDate
                    + " Account Number: " + accountNum + " Amount: " + amount + "(" + this + ")");
            InvestmentAllTransactionHistory.add("Transaction; Date: " + currentDate
                    + " Account Number: " + accountNum + " Amount: " + amount + "(" + this + ")");
            System.out.println("Your Investment account balance is: " + investmentAccountBalance);
        }
        catch (Exception e){
            System.out.println("Error at Investment Account class, transaction: error in data passed to it");
        }
    }

    public void sayHelloSavings() {
        System.out.println("Investment account number: " + investmentAccountNum);
        System.out.println("Investment account balance: " + investmentAccountBalance);
    }

    public void sayGoodbye(){
        if (isActive){
            clearFunds();
            ActiveInvestmentAccountList.remove(this);
            UserInvestmentTransactionHistory.clear();
            isActive = false;
            investmentAccountBalance = 0;
        }
        else{
            System.out.println("Investment Account has already been closed or not found");
        }
    }

    public void clearFunds(){
        if(investmentAccountBalance > 0){
            BankManager.increaseBankCash(investmentAccountBalance);
            investmentAccountBalance = 0;
        }
    }

    protected static void applyMonthlyInterest(){
        for (InvestmentAccount investmentAccount : ActiveInvestmentAccountList){
            if (investmentAccount.isActive){
                double monthlyInterestRate = interestRate / 12;
                investmentAccount.investmentAccountBalance = investmentAccount.investmentAccountBalance * (1 + monthlyInterestRate);
                System.out.println("You have earned " + investmentAccount.investmentAccountBalance * monthlyInterestRate + " interest this month!");
            }
        }
    }

    public static InvestmentAccount getInvestmentAccount (int accountNum) {
        for (InvestmentAccount investmentAccount : ActiveInvestmentAccountList) {
            if (investmentAccount.accountNum == accountNum) {
                return investmentAccount;
            }

        }
        return null;
    }

    public double getInvestmentAccountBalance(){
        return investmentAccountBalance;
    }

    public static ArrayList<String> getInvestmentAllTransactionHistory(){
        return InvestmentAllTransactionHistory;
    }
    public ArrayList<String> getUserInvestmentTransactionHistory(){
        return UserInvestmentTransactionHistory;
    }











}
