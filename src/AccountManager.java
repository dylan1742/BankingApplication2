/*
todo:
 -
 */

import java.util.ArrayList;
import java.lang.String;

public class AccountManager {
    protected static ArrayList<User> activeAccounts;
    protected static ArrayList<String> historyOfAccounts;
    private static int accountNumGiver = 1;
    protected static int totalNumOfActiveAccounts;

    protected AccountManager() {
        activeAccounts = new ArrayList<>();
        historyOfAccounts = new ArrayList<>();
    }

    protected static void createAccount(String firstName, String lastName, double startingBalance) {
        if (ComplianceManager.meetsMinDepositRequirements(startingBalance)){
            User newUser = new User(firstName, lastName, startingBalance, accountNumGiver++);
            newUser.sayHello();
            activeAccounts.add(newUser);
            historyOfAccounts.add(newUser + " Account number: " + newUser.getAccountNum());
            totalNumOfActiveAccounts++;
        }
        else {
            System.out.println("New User did not meet the required minim deposit");
        }

    }

    protected static void createAlternativeAccounts(char accountType, int accountNum, double depsoitAmount) {
        User user = findUser(accountNum);
            switch (accountType) {
                case 's':
                    if (ComplianceManager.userCheck(accountNum) && !user.hasSavingsAccount) {
                        user.createSavingsAccount(accountNum, depsoitAmount);
                        break;
                    }
                    else {
                        System.out.println("User already has this account");
                    }
                case 'i':
                    if (ComplianceManager.userCheck(accountNum) && !user.hasInvestmentAccount) {
                        user.createInvestmentAccount(accountNum, depsoitAmount);
                    }
                    else {
                        System.out.println("User already has this account");
                    }
            }
    }

    protected static void showAccountTransactionHistory(char accountType, int accountNum){
        switch (accountType){
            case 'c':
                ArrayList<String> checkingTransactions = findChecking(accountNum).getUserCheckingTransactionHistory();
                if (!checkingTransactions.isEmpty()){
                    System.out.println("Checking Account transactions:");
                    System.out.println(checkingTransactions);
                    break;
                }
            case 's':
                ArrayList<String> savingsTransactions = findSavings(accountNum).getUserSavingsTransactionHistory();
                if (!savingsTransactions.isEmpty()) {
                    System.out.println("Savings Account transactions:");
                    System.out.println(savingsTransactions);
                    break;
                }
            case 'i':
                ArrayList<String> investmentTransactions = findInvestment(accountNum).getUserInvestmentTransactionHistory();
                if (!investmentTransactions.isEmpty()) {
                    System.out.println("Investment Account transactions:");
                    System.out.println(investmentTransactions);
                    break;
                }
        }
    }

    protected static void closeAccount(int accountNumToClose) {
        User user = findUser(accountNumToClose);
        if (user != null) {
            user.sayGoodbye();
            activeAccounts.remove(user);
            totalNumOfActiveAccounts--;
        }
        else {
            System.out.println("Account not found.");
        }
    }

    protected static void showAccountInfo(int accountNum) {
        User user = findUser(accountNum);
        if (user != null && user.isActive) {
            user.sayHello();
        }
        else {
            System.out.println("Error at AccountManager class, showAccountInfo: account not found.");
        }
    }

    public static void depositFunds(int accountNum, double depositAmount){
        if(ComplianceManager.meetsMinDepositRequirements(depositAmount)){
            findUser(accountNum).transaction(depositAmount);
        }
        else {
            System.out.println("User did not meet the required minim deposit");
        }
    }

    public static void withdrawFunds(int accountNum, double withdrawAmount){
        if(ComplianceManager.meetsMaxTransfer(withdrawAmount)){
            findUser(accountNum).transaction(-withdrawAmount);
        }
        else {
            System.out.println("User exceeded the max withdrawal in a transaction");
        }
    }

    public static void transferBetweenAccounts(int accountNum, char accountTypeIn, char accountTypeOut, int amount) {
        if (ComplianceManager.transferBetweenAccountCheck(accountNum, accountTypeIn, accountTypeOut, amount)) {
            switch (accountTypeIn) {
                case 'c':
                    findChecking(accountNum).transaction(amount);
                    break;
                case 's':
                    findSavings(accountNum).transaction(amount);
                    break;
                case 'i':
                    findInvestment(accountNum).transaction(amount);
                    break;
            }
            switch (accountTypeOut) {
                case 'c':
                    findChecking(accountNum).transaction(-amount);
                    break;
                case 's':
                    findSavings(accountNum).transaction(-amount);
                    break;
                case 'i':
                    findInvestment(accountNum).transaction(-amount);
                    break;
            }
        }
        else{
            System.out.println("Error at AccountManager class, transferBetweenAccounts method: cannot complete transfer between accounts.");
        }
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
    private static User findUser(int accountNum){
        return User.getUser(accountNum);
    }
    protected static ArrayList<String> getHistoryOfAccounts(){
        return historyOfAccounts;
    }




}
