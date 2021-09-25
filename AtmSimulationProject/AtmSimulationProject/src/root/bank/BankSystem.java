package root.bank;

import java.util.HashSet;
import java.util.LinkedList;

public class BankSystem {

    private final String name = "GOLD COIN BANK";

    private LinkedList<BankAccount> bankAccounts = new LinkedList<>();
    private LinkedList<BankCard> bankCards = new LinkedList<>();

    public String getName() {
        return name;
    }

    public LinkedList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public LinkedList<BankCard> getBankCards() {
        return bankCards;
    }

    public void addBankAccount(BankAccount bankAccount) {
        bankAccounts.add(bankAccount);
    }

    public void addBankCard(BankCard bankCard)
            throws BankSystemErrorException {
        if (bankCardAlreadyExists(bankCard.getNumber())) {
            throw new BankSystemErrorException(
                    BankSystemErrorException.ErrorType.BANK_CARD_NUMBER_ALREADY_EXISTS);
        }
        bankCards.add(bankCard);
    }

    public boolean bankCardAlreadyExists(BankCardNumber number) {
        for (BankCard bankCard : bankCards) {
            if (bankCard.getNumber().equalsTo(number))
                return true;
        }

        return false;
    }

    public BankCard getBankCardByNumber(BankCardNumber number)
            throws BankSystemErrorException {
        for (BankCard bankCard : bankCards) {
            if (bankCard.getNumber().equalsTo(number)) {
                return bankCard;
            }
        }

        throw new BankSystemErrorException(
                BankSystemErrorException.ErrorType.BANK_CARD_NUMBER_NOT_FOUND);
    }

    public BankCard getBankCardByStringNumber(String stringNumber)
            throws BankSystemErrorException {
        for (BankCard bankCard : bankCards) {
            if (bankCard.getNumber().getStringNumber().equals(stringNumber)) {
                return bankCard;
            }
        }

        throw new BankSystemErrorException(
                BankSystemErrorException.ErrorType.BANK_CARD_NUMBER_NOT_FOUND);
    }

    public BankAccount getBankAccountByNumber(int number)
            throws BankSystemErrorException {
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getNumber() == number) {
                return bankAccount;
            }
        }

        throw new BankSystemErrorException(
                BankSystemErrorException.ErrorType.BANK_ACCOUNT_NOT_FOUND);
    }
}
