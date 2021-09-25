package root.bank;

public class BankCardPinCode {

    private String stringPinCode;


    public String getStringPinCode() {
        return stringPinCode;
    }

    public boolean equalsTo(BankCardPinCode anotherPinCode) {
        return stringPinCode.equals(anotherPinCode.stringPinCode);
    }

    public void setPinCode(String stringPinCode)
            throws BankSystemErrorException{
        if (!isPinCodeAppropriate(stringPinCode)) {
            throw new BankSystemErrorException(
                    BankSystemErrorException.ErrorType.INVALID_BANK_CARD_PIN_CODE_ASSIGNED);
        }
        this.stringPinCode = stringPinCode;
    }

    public static boolean isPinCodeAppropriate(String stringPinCode) {
        if (stringPinCode.length() != 4) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            if (!Character.isDigit(stringPinCode.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
