package root.bank;

import java.util.Date;

public class BankCard {

    private BankCardNumber number;
    private BankCardPinCode pinCode;

    private BankAccount bankAccount;

    private boolean blocked = false;
    private short incorrectPinTimes = 0;
    private Date blockingDate = null;
    public static final short MAX_ATTEMPTS_FOR_PIN = 3;
    public static final short DAYS_TO_WAIT_FOR_UNLOCKING = 1;

    public BankCard(BankAccount bankAccount,
                    BankCardNumber number, BankCardPinCode pinCode) {
        this.bankAccount = bankAccount;
        this.number = number;
        this.pinCode = pinCode;
    }

    public BankCardNumber getNumber() {
        return number;
    }

    public BankCardPinCode getPinCode() {
        return pinCode;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount newBankAccount) {
        bankAccount = newBankAccount;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public short getIncorrectPinTimes() {
        return incorrectPinTimes;
    }

    public void setIncorrectPinTimes(short incorrectPinTimes)
            throws BankSystemErrorException {
        if (incorrectPinTimes < 0) {
            throw new BankSystemErrorException(BankSystemErrorException.ErrorType.NEGATIVE_NUMBER_CANNOT_BE_ASSIGNED);
        }
        this.incorrectPinTimes = incorrectPinTimes;
        if (incorrectPinTimes >= MAX_ATTEMPTS_FOR_PIN) {
            blocked = true;
            blockingDate = new Date();
        }
    }

    public void incrementIncorrectPinTimes() {
        incorrectPinTimes++;
        if (incorrectPinTimes >= MAX_ATTEMPTS_FOR_PIN) {
            blockingDate = new Date();
            blocked = true;
        }
    }

    public void discardIncorrectPinTimes() {
        incorrectPinTimes = 0;
        blocked = false;
        blockingDate = null;
    }

    public Date getBlockingDate() {
        return blockingDate;
    }

    public void setBlockingDate(Date date) {
        blockingDate = date;
    }
}
