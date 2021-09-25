package root.bank.atm;

import org.w3c.dom.Text;
import root.bank.BankSystemErrorException;

import java.util.HashMap;

public class TextManager {

    public enum MessageTypes {
        INVALID_INPUT,
        WRONG_PIN_CODE,
        CARD_IS_BLOCKED
    }

    private HashMap<MessageTypes,
            String> messagesByType = new HashMap<>();
    private HashMap<BankSystemErrorException.ErrorType,
            String> messagesByErrorType = new HashMap<>();


    public TextManager() {
        messagesByType.put(MessageTypes.INVALID_INPUT,
                "Invalid input");
        messagesByType.put(MessageTypes.WRONG_PIN_CODE,
                "Wrong PIN code");
        messagesByType.put(MessageTypes.CARD_IS_BLOCKED,
                "The card is blocked");


        messagesByErrorType.put(
                BankSystemErrorException.ErrorType.NEGATIVE_NUMBER_ADDED_TO_BALANCE,
                "You cannot add negative amount of money to the balance");
        messagesByErrorType.put(
                BankSystemErrorException.ErrorType.NEGATIVE_NUMBER_REMOVED_FROM_BALANCE,
                "You cannot remove negative amount of money from the balance");
        messagesByErrorType.put(
                BankSystemErrorException.ErrorType.BALANCE_MONEY_IS_NOT_ENOUGH_FOR_CHARGING,
                "You cannot remove the amount of money, which the balance does not possesses");
        messagesByErrorType.put(
                BankSystemErrorException.ErrorType.INVALID_BANK_CARD_NUMBER_ASSIGNED,
                "You are trying to assign an invalid number for the card");
        messagesByErrorType.put(
                BankSystemErrorException.ErrorType.INVALID_BANK_CARD_PIN_CODE_ASSIGNED,
                "You are trying to assign an invalid PIN code for the card");
        messagesByErrorType.put(
                BankSystemErrorException.ErrorType.BANK_CARD_NUMBER_NOT_FOUND,
                "A bank card with such a number does not exist");
        messagesByErrorType.put(
                BankSystemErrorException.ErrorType.BANK_CARD_PIN_CODE_NOT_FOUND,
                "An incorrect PIN code has been entered");
        messagesByErrorType.put(
                BankSystemErrorException.ErrorType.BANK_ACCOUNT_NOT_FOUND,
                "The bank account with such a number has not been found");
        messagesByErrorType.put(
                BankSystemErrorException.ErrorType.TOO_HIGH_AMOUNT_OF_MONEY_TO_CHARGE,
                "You are trying to charge money more than the limit allows");
        messagesByErrorType.put(
                BankSystemErrorException.ErrorType.TOO_HIGH_AMOUNT_OF_MONEY_TO_TOP_UP_BY,
                "You are trying to top up the balance by more than 1 000 000 dollars,"
                        + " which is impossible");
        messagesByErrorType.put(
                BankSystemErrorException.ErrorType.BANK_CARD_NUMBER_ALREADY_EXISTS,
                "A card with such a number already exists in the system");
        messagesByErrorType.put(
                BankSystemErrorException.ErrorType.NEGATIVE_NUMBER_CANNOT_BE_ASSIGNED,
                "You are trying to assign a negative number to what it cannot be assigned");
    }

    public String getMessageByType(MessageTypes messageType) {
        return messagesByType.get(messageType);
    }

    public String getMessageByType(BankSystemErrorException.ErrorType errorType) {
        return messagesByErrorType.get(errorType);
    }
}
