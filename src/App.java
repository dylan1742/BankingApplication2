/*
todo (harder):




1. create visualization of how it works / system design


 - encapsulation - make methods and variables private as needed and use getters and setters to access things
 - error handling for all classes and methods
 - handle wrong unit errors
 - connect to a database
 - incorporate date into savings account, investment account, and user for when they created or deleted an account
 - add a foreign currency exchange based on an api
 - add interest to the savings accounts by using date (see if you can get an api for the fed's rate to pass to users
   but research if banks add a few more points to it)
 - create a front end??


todo (easier):
 - test new methods in compliance manager
 - hava a loop for check balances by using time (checks every 30 minutes)
*/

import java.lang.String;
import java.util.Arrays;


public class App {
    public static void Spacer(String title) {
        for (int i = 0; i < 10; i++) {
            System.out.println("-");
        }
        System.out.println("*******" + title + "******");
    }


    public static void main(String[] args) {
        // create managers
        AccountManager accountManager = new AccountManager();
        TransactionManager transactionManager = new TransactionManager();


        // creates accounts
        AccountManager.createAccount("Bill","Account", 10);
        AccountManager.createAccount("Amy","Edgar",20);
        AccountManager.createAccount("Jane","Yolo",30);
        AccountManager.createAccount("Ryan","Wap",40);
        AccountManager.createAlternativeAccounts('s', 1, 0);
        AccountManager.createAlternativeAccounts('i', 1, 0);
        AccountManager.createAlternativeAccounts('s', 2, 0);
        AccountManager.createAlternativeAccounts('i', 2, 0);
        AccountManager.createAlternativeAccounts('s', 3, 0);
        AccountManager.createAlternativeAccounts('i', 3, 0);
        AccountManager.createAlternativeAccounts('s', 4, 0);
        AccountManager.createAlternativeAccounts('i', 4, 0);


        // update total balances
        BankManager.updateAllAccountBalances();


        // close account
        Spacer("close account #3 (should close all three accounts and user account");
        System.out.println("print out active account list");
        System.out.println(AccountManager.activeAccounts);
        System.out.println("closing account #3:");
        AccountManager.closeAccount(3);
        System.out.println("print out active account list (should be one less, #3 gone)");
        System.out.println(AccountManager.activeAccounts);


        // transfer between accounts (same user)
        Spacer("transfer between accounts (same user)");
        System.out.println("*** before balances: ");
        AccountManager.showAccountInfo(2);
        System.out.println("*** transfer $5 from checking to savings (Account #2, started with $20)");
        AccountManager.transferBetweenAccounts(2, 's', 'c', 5);
        System.out.println("*** after balances: ");
        AccountManager.showAccountInfo(2);


        // in network transfer
        Spacer("transfer between 2 different users (in network transfer)");
        System.out.println("*** before balances: ");
        AccountManager.showAccountInfo(1);
        AccountManager.showAccountInfo(4);
        System.out.println("transferring!!!!!!");
        System.out.println("*** after balances: (should be Bill $15 and Ryan $35) ");
        TransactionManager.transfer(1,4,5, true, false);


        // out of network transferring
        // out of network transaction (OUT)
        Spacer("out of network transaction (OUT)");
        System.out.println("before balance of Bill (account #1)");
        AccountManager.showAccountInfo(1);
        System.out.println("transferring out $15");
        System.out.println("After balances");
        TransactionManager.transfer(5869, 1, 15, false, true);
        // out of network transaction (IN)
        Spacer("out of network transaction (IN)");
        System.out.println("before balance of Bill (account #1)");
        AccountManager.showAccountInfo(1);
        System.out.println("transferring in $500");
        System.out.println("After balances");
        TransactionManager.transfer(1, 3859375, 500, false, true);


        // display app information
        Spacer("Showing bank information:");
        BankManager.showBankInfo();


        // deposit and withdrawals
        Spacer("Deposit and Withdrawal");
        System.out.println("Deposit of Bill (account #1), before $15 increased by $1000 to $1015:");
        AccountManager.depositFunds(1, 1000);
        System.out.println("Withdrawal of Bill (account #1), before $1015 increased by $500 to $515:");
        AccountManager.withdrawFunds(1,500);


        // show all transactions for each account
        Spacer("All transaction history for account #1");
        AccountManager.showAccountTransactionHistory('c', 1);
        AccountManager.showAccountTransactionHistory('s', 1);
        AccountManager.showAccountTransactionHistory('i', 1);

        // monthly to dos (needs to be in a loop checking every 30 minutes)
        Spacer("Checking if it is the first of the month");
        BankManager.thirtyMinChecks();

        // try to transfer between account too much
        Spacer("Transfer between accounts too much");
        AccountManager.transferBetweenAccounts(1,'c','i',10);


        // transfer to another user, so it is less than the account min
        Spacer("Transfer to another user to get a balance below the min balance");

        // transfer more than what the user has in the account to another user
        Spacer("Transferring over the account balance to another user");

        











    }
}