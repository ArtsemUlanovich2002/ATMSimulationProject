package root.bank;

public class BankSystemErrorException extends Exception {

    public enum ErrorType {
        NEGATIVE_NUMBER_ADDED_TO_BALANCE,
        NEGATIVE_NUMBER_REMOVED_FROM_BALANCE,
        BALANCE_MONEY_IS_NOT_ENOUGH_FOR_CHARGING,
        INVALID_BANK_CARD_NUMBER_ASSIGNED,
        INVALID_BANK_CARD_PIN_CODE_ASSIGNED,
        BANK_CARD_NUMBER_NOT_FOUND,
        BANK_CARD_PIN_CODE_NOT_FOUND,
        BANK_ACCOUNT_NOT_FOUND,
        TOO_HIGH_AMOUNT_OF_MONEY_TO_CHARGE,
        TOO_HIGH_AMOUNT_OF_MONEY_TO_TOP_UP_BY,
        BANK_CARD_NUMBER_ALREADY_EXISTS,
        NEGATIVE_NUMBER_CANNOT_BE_ASSIGNED
    }

    public ErrorType errorType;


    public BankSystemErrorException(ErrorType errorType) {
        this.errorType = errorType;
    }
}
