/*
todo:
 -
 */

import java.util.ArrayList;

public class TransactionManager {
    private static final ArrayList<String> transactionHistory = new ArrayList<>();

    // traffic director
    protected static void transfer(int accountIn, int accountOut, int amountRequested, boolean inNetwork, boolean outOfNetwork) {
        if (inNetwork) {
            inNetwork(accountIn, accountOut, amountRequested);
        } else if (outOfNetwork) {
            outOfNetwork(accountIn, accountOut, amountRequested);
        }
        else{
            System.out.println("Error at Transaction Manager class, transfer method");
        }
    }

    private static void outOfNetwork(int accountIn, int accountOut, int amountRequested) {
        try{
            if(ComplianceManager.transactionCheck(false, accountIn, amountRequested)) {
                findUser(accountIn).transaction(amountRequested);
                transactionHistory(accountIn, amountRequested);
            }
            else if (ComplianceManager.transactionCheck(true, accountOut, amountRequested)) {
                findUser(accountOut).transaction(-amountRequested);
                transactionHistory(accountOut, -amountRequested);
            }
            else {
                System.out.println("In Network Transaction:");
                ComplianceManager.NoTransaction(accountIn, accountOut, amountRequested);
            }
        }
        catch (Exception e){
            System.out.println("Error at Transaction Manager class, outOfNetwork method");
        }
    }

    private static void inNetwork(int accountIn, int accountOut, int amountRequested) {
        try{
            if (ComplianceManager.transactionCheck(true, accountIn, amountRequested)
                    && ComplianceManager.transactionCheck(false, accountOut, amountRequested)) {
                transactionHistory(accountIn, amountRequested);
                findUser(accountIn).transaction(amountRequested);
                findUser(accountOut).transaction(-amountRequested);
                transactionHistory(accountOut, -amountRequested);
            }
            else {
                System.out.println("In Network Transaction:");
                ComplianceManager.NoTransaction(accountIn, accountOut, amountRequested);
            }
        }
        catch (Exception e){
            System.out.println("Error at Transaction Manager class, inNetwork method");
        }
    }

    private static void transactionHistory(int accountNum, int amount){
        String TransactionData = "Account number: " + accountNum + " = Account change (" + amount + ")";
        transactionHistory.add(TransactionData);
    }

    private static User findUser(int accountNum){
        return User.getUser(accountNum);
    }
    protected static ArrayList<String> getTransactionHistory(){
        return transactionHistory;
    }




}
