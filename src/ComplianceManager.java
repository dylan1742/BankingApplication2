/*
todo:
 -
 */

import java.time.LocalDate;
public class ComplianceManager {
    private final static int minBalance = 10;
    private final static int maxTransfer = 10000;
    private final static int minDeposit = 5;
    private final static int annualAccountFee = 100;
    private final static int monthlyAccountFee = annualAccountFee/12;

    protected static void postTransactionChecks(int accountNum){
        meetsMinBalanceRequirements(accountNum);
    }

    private static void meetsMinBalanceRequirements(int accountNum) {
        BankManager.updateAllAccountBalances();
        if (findChecking(accountNum).getCheckingBalance() < minBalance){
            applyMinBalanceFee(findChecking(accountNum));
        }
    }

    protected static void meetsMinBalanceAllBalanceRequirements(){
        BankManager.updateAllAccountBalances();
        for (CheckingAccount checkingAccount : CheckingAccount.getActiveCheckingAccountList()){
            if (checkingAccount.getCheckingBalance() < minBalance){
                applyMinBalanceFee(checkingAccount);
            }
        }
    }

    private static void applyMinBalanceFee(CheckingAccount checkingAccount) {
        checkingAccount.transaction(-minBalance);
        BankManager.updateAllAccountBalances();
    }

    protected static boolean meetsMinDepositRequirements(double amount) {
        return amount > minDeposit;
    }

    protected static boolean meetsMaxTransfer(double amount) {
        return amount < maxTransfer;
    }

    private static boolean transferBetweenAccountsFundsAvailable(int accountNum, char accountTypeOut, double amount) {
        CheckingAccount checkingAccount = findChecking(accountNum);
        SavingsAccount savingsAccount = findSavings(accountNum);
        InvestmentAccount investmentAccount = findInvestment(accountNum);
        amount += minBalance;
        return switch (accountTypeOut) {
            case 'c' -> checkingAccount.getCheckingBalance() >= amount;
            case 's' -> savingsAccount.getSavingsAccountBalance() >= amount;
            case 'i' -> investmentAccount.getInvestmentAccountBalance() >= amount;
            default -> false;
        };
    }

    protected static boolean userCheck(int accountNum){
        User user = findUser(accountNum);
        return user != null && user.isActive;
    }

    protected static boolean transferBetweenAccountCheck(int accountNum, char accountTypeIn, char accountTypeOut, int amount){
        return transferBetweenAccountsFundsAvailable(accountNum, accountTypeOut, amount)
                && userCheck(accountNum)
                && accountTypeIn != accountTypeOut;
    }

    protected static boolean transactionCheck(boolean debit, int accountNum, int amount){
        if (debit){
            return userCheck(accountNum)
                    && meetsMinDepositRequirements(amount);
        }
        else {
            return userCheck(accountNum)
                    && transactionFundsAvailable(accountNum, amount)
                    && meetsMaxTransfer(amount);
        }
    }

    protected static boolean transactionFundsAvailable(int account, int amount){
        CheckingAccount checkingAccount = findChecking(account);
        return checkingAccount.getCheckingBalance() >= amount;
    }

    protected static void applyMonthlyAccountFee() {
        System.out.println("First of the Month. Applying monthly account fees...");
        for (CheckingAccount checkingAccount : CheckingAccount.getActiveCheckingAccountList()){
            checkingAccount.transaction(-monthlyAccountFee);
        }
    }

    public static void NoTransaction(int accountIn, int accountOut, int amountRequested){
        System.out.println("System could not process transaction \nProvided inputs:");
        System.out.println("Account debited: " + accountIn + "; Active User: " + userCheck(accountIn));
        System.out.println("Account credited: " + accountOut + "; Active User: " + userCheck(accountOut));
        System.out.println("Account credited: " + accountOut + "; Funds Available: " + transactionFundsAvailable(accountOut, amountRequested));
        System.out.println("Payment Failed!!");
    }

    private static User findUser(int accountNum){
        return User.getUser(accountNum);
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





}

