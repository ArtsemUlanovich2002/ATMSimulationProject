package root.bank;

import java.util.Calendar;
import java.util.Date;

public class BankCalculator {

    private BankCalculator() {

    }

    public static int getDecimalPart(double value) {
        return (int) value;
    }

    public static int getFloatingPart(double value) {
        return (int)((int)(value * 100.0) - ((int)value) * 100.0);
    }

    public static double toDouble(String str) {
        String decimalPart = "";
        String floatingPart = "";



        int i = 0;

        while (true) {
            if (str.charAt(i) != '.') {
                decimalPart += str.charAt(i);
                i++;
                if (i == str.length()) {
                    return Integer.parseInt(str);
                }
            } else {
                i++;
                break;
            }
        }

        for (int j = i; j < str.length(); j++) {
            floatingPart += str.charAt(j);
        }

        double multiplier = 1.0;
        for (int j = 0; j < floatingPart.length(); j++) {
            multiplier *= 10.0;
        }

        double finalValue = Integer.parseInt(decimalPart) + Integer.parseInt(floatingPart) / multiplier;

        return finalValue;
    }

    public static String toStringFormat(double value) {
        return Double.toString((double)((int)(value * 100.0)) / 100.0);
    }

    public static String getDollarsAndCents(double moneyAmount) {
        return (getDecimalPart(moneyAmount) +
                (getLastDigit(getDecimalPart(moneyAmount)) != 1 ? " dollars " : " dollar ")
                + getFloatingPart(moneyAmount)
                + (getLastDigit(getFloatingPart(moneyAmount)) != 1 ? " cents" : " cent"));
    }

    public static byte getLastDigit(int value) {
        String stringValue = Integer.toString(value);
        return Byte.parseByte(Character.toString(stringValue.charAt(stringValue.length() - 1)));
    }

    public static void unlockCardIfNecessary(BankCard bankCard) {
        if (!bankCard.isBlocked()) {
            return;
        }
        Date todaysDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bankCard.getBlockingDate());
        calendar.add(Calendar.DATE, BankCard.DAYS_TO_WAIT_FOR_UNLOCKING);
        Date dateForUnlocking = calendar.getTime();
        if (dateForUnlocking.compareTo(todaysDate) <= 0) {
            bankCard.discardIncorrectPinTimes();
        }
    }
}
