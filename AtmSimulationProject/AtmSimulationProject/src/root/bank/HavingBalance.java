package root.bank;

public interface HavingBalance {

    double getBalance();
    void addMoneyToBalance(double moneyAmount) throws BankSystemErrorException;
    void removeMoneyFromBalance(double moneyAmount) throws BankSystemErrorException;
}
