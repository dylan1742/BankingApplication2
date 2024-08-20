package com.example.BankingApp;/*
todo:
 - close individual accounts (just investment and savings not checking)
 - open individual accounts
 */

import java.util.ArrayList;
import java.lang.String;
import java.time.LocalDate;

public class User {
    protected String firstName;
    protected String lastName;
    private final int accountNum;
    public boolean isActive;
    private final LocalDate dateOpened;
    private double totalBalance;
    private String currency;
    private final boolean hasCheckingAccount;
    public boolean hasSavingsAccount;
    public boolean hasInvestmentAccount;
    private static final ArrayList<User> ActiveAccountList = new ArrayList<>();

    protected User(String firstName, String lastName, double startingBalance, int accountNum) {
        this.firstName = firstName;
        this.totalBalance = startingBalance;
        this.accountNum = accountNum;
        this.lastName = lastName;
        dateOpened = LocalDate.now();
        currency = "USD";
        isActive = true;
        ActiveAccountList.add(this);
        final int checkingAccountNum = createAccountNums('c', accountNum);
        CheckingAccount checkingAccount = new CheckingAccount(accountNum, checkingAccountNum, totalBalance);
        hasCheckingAccount = true;
    }

    protected void createSavingsAccount(int accountNum, double startingBalance){
        if (hasCheckingAccount && !hasSavingsAccount){
            final int savingsAccountNum = createAccountNums('s', accountNum);
            SavingsAccount savingsAccount = new SavingsAccount(accountNum, savingsAccountNum, startingBalance);
            hasSavingsAccount = true;
        }
        else {
            System.out.println("Error at User class, createSavingsAccount: Savings Account already exists");
        }
    }

    protected void createInvestmentAccount(int accountNum, double startingBalance){
        if (hasCheckingAccount && !hasInvestmentAccount){
            final int investmentAccountNum = createAccountNums('i', accountNum);
            InvestmentAccount investmentAccount = new InvestmentAccount(accountNum, investmentAccountNum, startingBalance);
            hasInvestmentAccount = true;
        }
        else {
            System.out.println("Error at User class, createInvestmentAccount: Investment Account already exists");
        }
    }

    protected void sayGoodbye() {
        if (isActive && hasCheckingAccount) {
            isActive = false;
            totalBalance = 0;
            ActiveAccountList.remove(this);
            alternativeAccountsSayGoodbye();
            findChecking(accountNum).sayGoodbye();
            System.out.println("Goodbye " + firstName + " " + lastName + ". All accounts have been closed");
        }
        else{
            System.out.println("Error at User class, sayGoodbye");
        }
    }

    private void alternativeAccountsSayGoodbye(){
        if (hasSavingsAccount){
            findSavings(accountNum).sayGoodbye();
        }
        if (hasInvestmentAccount){
            findInvestment(accountNum).sayGoodbye();
        }
    }

    protected void sayHello() {
        try{
            updateTotalBalance();
            System.out.println("Hello " + firstName + " " + lastName + ", you have $" + totalBalance + " in all your accounts");
            System.out.println("account number: " + accountNum);
        } catch (Exception e) {
            System.out.println("Error at User class, sayHello");
            throw new RuntimeException(e);
        }
    }

    protected void updateTotalBalance(){
        if (isActive && hasCheckingAccount && hasSavingsAccount && hasInvestmentAccount){
            totalBalance = findChecking(accountNum).getCheckingBalance() +
                    findSavings(accountNum).getSavingsAccountBalance() +
                    findInvestment(accountNum).getInvestmentAccountBalance();
        }
        else if (isActive && hasCheckingAccount && hasSavingsAccount && !hasInvestmentAccount) {
            totalBalance = findChecking(accountNum).getCheckingBalance() +
                    findSavings(accountNum).getSavingsAccountBalance();
        }
        else if (isActive && hasCheckingAccount && !hasSavingsAccount && !hasInvestmentAccount) {
            totalBalance = findChecking(accountNum).getCheckingBalance();
        }
        else {
            System.out.println("Error at User class, updateTotalBalance: not accounts found");
        }
    }

    protected void transaction(double amount) {
        totalBalance = totalBalance + amount;
        findChecking(accountNum).transaction(amount);
        ComplianceManager.postTransactionChecks(accountNum);
        sayHello();
    }

    private int createAccountNums(char accountType, int accountNum) {
        return switch (accountType) {
            case 'c' -> accountNum + 10;
            case 's' -> accountNum + 1000;
            case 'i' -> accountNum + 1000000;
            default -> 0;
        };
    }

    protected static User getUser(int accountNum) {
        for (User user : ActiveAccountList) {
            if (accountNum == user.accountNum) {
                return user;
            }
        }
        return null;
    }

    private static CheckingAccount findChecking(int accountNum){
            return CheckingAccount.getChecking(accountNum);
    }
    private static SavingsAccount findSavings(int accountNum){
        return SavingsAccount.getSavings(accountNum);
    }
    private static InvestmentAccount findInvestment(int accountNum){
        return InvestmentAccount.getInvestmentAccount(accountNum);
    }
    protected double getTotalBalance(){
        return totalBalance;
    }
    protected static ArrayList<User> getActiveAccountList(){
        return ActiveAccountList;
    }
    protected int getAccountNum (){
        return accountNum;
    }


}
