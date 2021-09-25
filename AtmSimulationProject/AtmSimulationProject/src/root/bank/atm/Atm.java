package root.bank.atm;

import root.bank.BankSystem;
import root.bank.BankSystemErrorException;
import root.bank.HavingBalance;

public class Atm implements HavingBalance {

    private double balance; //in dollars ($)

    private AtmMenu menu = new AtmMenu(this);
    protected BankSystem bankSystem = new BankSystem();


    public Atm(double startBalance) {
        balance = startBalance;
    }

    public Atm(int startBalance) {
        balance = startBalance;
    }

    public void launchMenu() {
        menu.launch(bankSystem);
    }

    @Override
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance)
            throws BankSystemErrorException{
        if (balance < 0.0) {
            throw new BankSystemErrorException(
                    BankSystemErrorException.ErrorType.NEGATIVE_NUMBER_CANNOT_BE_ASSIGNED);
        }
        this.balance = balance;
    }

    public void setBalance(int balance)
            throws BankSystemErrorException{
        if (balance < 0) {
            throw new BankSystemErrorException(
                    BankSystemErrorException.ErrorType.NEGATIVE_NUMBER_CANNOT_BE_ASSIGNED);
        }
        this.balance = balance;
    }

    @Override
    public void addMoneyToBalance(double moneyAmount)
            throws BankSystemErrorException {
        if (moneyAmount < 0.0) {
            throw new BankSystemErrorException(
                    BankSystemErrorException.ErrorType.NEGATIVE_NUMBER_ADDED_TO_BALANCE);
        }
        balance += moneyAmount;
    }

    @Override
    public void removeMoneyFromBalance(double moneyAmount)
            throws BankSystemErrorException{
        if (moneyAmount < 0.0) {
            throw new BankSystemErrorException(
                    BankSystemErrorException.ErrorType.NEGATIVE_NUMBER_REMOVED_FROM_BALANCE);
        }
        if (balance - moneyAmount < 0.0) {
            throw new BankSystemErrorException(
                    BankSystemErrorException.ErrorType.BALANCE_MONEY_IS_NOT_ENOUGH_FOR_CHARGING);
        }
        balance -= moneyAmount;
    }
}
