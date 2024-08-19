/*
todo:
 -
 */

import java.time.LocalDate;

public class BankManager {
    private static double bankCash = 0;
    // use current date for security enhancements
    LocalDate currentDate = LocalDate.now();
    protected static void showBankInfo(){
        double AUM = 0;
        for(User user : User.getActiveAccountList()) {
            user.updateTotalBalance();
            AUM += user.getTotalBalance();
        }
        System.out.println("***************- System Information -************************");
        System.out.println("Total Assets Under Management across the bank: $" + AUM);
        System.out.println("Total Bank cash: $" + bankCash);
        System.out.println("Number of active accounts: " + AccountManager.totalNumOfActiveAccounts);
        System.out.println("All history of accounts (open or closed):");
        System.out.println(AccountManager.getHistoryOfAccounts());
        System.out.println("All transaction history (open or closed):");
        System.out.println(TransactionManager.getTransactionHistory());
        System.out.println("All checking transactions (open or closed):");
        System.out.println(CheckingAccount.getCheckingAllTransactionHistory());
        System.out.println("All savings transactions (open or closed):");
        System.out.println(SavingsAccount.getSavingsAllTransactionHistory());
        System.out.println("All investment transactions (open or closed):");
        System.out.println(InvestmentAccount.getInvestmentAllTransactionHistory());
        System.out.println("*******************- END -********************");
    }

    protected static void updateAllAccountBalances(){
        for(User user : User.getActiveAccountList()){
            user.updateTotalBalance();
        }
        System.out.print("All accounts updated");
    }

    protected static void increaseBankCash(double amount){
        bankCash += amount;
    }

    protected void thirtyMinChecks() {
        ComplianceManager.meetsMinBalanceAllBalanceRequirements();
        if (currentDate.getDayOfMonth() == 1) {
            System.out.println("It is the first of the month... Applying changes...");
            ComplianceManager.applyMonthlyAccountFee();
            SavingsAccount.applyMonthlyInterest();
            InvestmentAccount.applyMonthlyInterest();
            updateAllAccountBalances();
        }
        else{
            System.out.println("Not the first of the month");
        }
    }





}
