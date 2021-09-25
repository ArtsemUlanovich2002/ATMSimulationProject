package root.bank;

public class BankCardNumber {

    private String stringNumber;


    public String getStringNumber() {
        return stringNumber;
    }

    public boolean equalsTo(BankCardNumber anotherNumber) {
        return stringNumber.equals(anotherNumber.stringNumber);
    }

    public void setNumber(String stringNumber)
            throws BankSystemErrorException{
        if (!isNumberAppropriate(stringNumber)) {
            throw new BankSystemErrorException(
                    BankSystemErrorException.ErrorType.INVALID_BANK_CARD_NUMBER_ASSIGNED);
        }
        this.stringNumber = stringNumber;
    }

    public static boolean isNumberAppropriate(String stringNumber) {
        if (stringNumber.length() != 19) {
            return false;
        }

        for (int i = 0; i < 19; i++) {
            if ((i + 1) % 5 == 0) {
                if (stringNumber.charAt(i) != '-') {
                    return false;
                }
            } else {
                if (!Character.isDigit(stringNumber.charAt(i))) {
                    return false;
                }
            }
        }

        return true;
    }
}
