package root.bank;

public class BankAccount implements HavingBalance {

    private static int lastGeneratedNumber = 1000000;
    private int number;
    private double balance = 0.0; //in dollars ($)


    public BankAccount() {
        number = lastGeneratedNumber++;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void addMoneyToBalance(double moneyAmount)
            throws BankSystemErrorException{
        if (moneyAmount < 0.0) {
            throw new BankSystemErrorException(
                    BankSystemErrorException.ErrorType.NEGATIVE_NUMBER_ADDED_TO_BALANCE);
        }
        if (moneyAmount > 1000000.0) {
            throw  new BankSystemErrorException(
                    BankSystemErrorException.ErrorType.TOO_HIGH_AMOUNT_OF_MONEY_TO_TOP_UP_BY);
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
