package root.bank.atm;

import root.bank.*;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class AtmMenu {

    private Atm parentAtm;

    private final String lineSeparator =
            "===============================";
    private final String backConstant = "back";
    private final String exitConstant = "exit";
    private final String chooseNumberText =
            "Choose any number to go to the appropriate section";
    private final String goBackHint =
            "Type \"" + backConstant + "\" to return back";
    private final String exitHint =
            "Type \"" + exitConstant + "\" to exit the ATM";

    private Scanner scanner = new Scanner(System.in);
    private TextManager textManager = new TextManager();
    private FileManager fileManager = new FileManager();
    private String input = "";


    protected AtmMenu(Atm atm) {
        parentAtm = atm;
    }

    protected void launch(BankSystem bankSystem) {

        fileManager.loadDataFromFile(bankSystem, parentAtm);

        while (true) {
            printLineSeparator();
            println(bankSystem.getName());
            printLineSeparator();
            println("1. Bank Service Side");
            println("2. Bank Customer Side");
            println();
            printChooseNumberText();
            printExitHint();
            printLineSeparator();

            input = scanner.nextLine();
            if (input.equals("1")) {
                launchBankServiceSide(bankSystem);
            } else if (input.equals("2")){
                launchCustomerSide(bankSystem);
            } else if (input.equals(exitConstant)) {
                fileManager.saveDataToFile(bankSystem, parentAtm);
                return;
            } else {
                println(textManager.getMessageByType(TextManager.MessageTypes.INVALID_INPUT));
            }
        }
    }

    private void launchBankServiceSide(BankSystem bankSystem) {

        while (true) {
            printLineSeparator();
            println("1. Add bank account");
            println("2. Show existing bank accounts");
            println("3. Add bank card");
            println();
            printChooseNumberText();
            printGoBackHint();
            printLineSeparator();

            input = scanner.nextLine();
            if (input.equals("1")) {
                launchAddBankAccountMenu(bankSystem);
            } else if (input.equals("2")) {
                launchShowAvailableBankAccountsMenu(bankSystem);
            } else if (input.equals("3")) {
                launchAddBankCardMenu(bankSystem);
            } else if (input.equals(backConstant)) {
                break;
            } else {
                println(textManager.getMessageByType(TextManager.MessageTypes.INVALID_INPUT));
            }
        }
    }

    private void launchCustomerSide(BankSystem bankSystem) {

        BankCardNumber bankCardNumber = new BankCardNumber();
        BankCardPinCode bankCardPinCode = new BankCardPinCode();
        BankCard bankCard;

        while (true) {
            printLineSeparator();
            println("In order to do any operations with your bank account,"
                    + " please, go through the authorization process");
            println();

            println("Please, enter the number of your card in the format"
                    + " XXXX-XXXX-XXXX-XXXX: ");
            printGoBackHint();

            while (true) {
                input = scanner.nextLine();

                if (input.equals(backConstant)) {
                    return;
                }

                try {
                    bankCard = bankSystem.getBankCardByStringNumber(input);
                    break;
                } catch (BankSystemErrorException e) {
                    println(textManager.getMessageByType(e.errorType));
                }
            }

            BankCalculator.unlockCardIfNecessary(bankCard);

            if (bankCard.isBlocked()) {
                println(textManager.getMessageByType(TextManager.MessageTypes.CARD_IS_BLOCKED));
                return;
            }

            println();
            println("Please, enter the 4-digit PIN code of your card: ");
            printGoBackHint();

            while (true) {
                input = scanner.nextLine();

                if (input.equals(backConstant)) {
                    return;
                }

                if (bankCard.getPinCode().getStringPinCode().equals(input)) {
                    launchBankAccountOperationsMenu(bankSystem, bankCard);
                    bankCard.discardIncorrectPinTimes();
                    break;
                } else {
                    println(textManager.getMessageByType(TextManager.MessageTypes.WRONG_PIN_CODE));
                    bankCard.incrementIncorrectPinTimes();
                    if (bankCard.isBlocked()) {
                        println("The card has been blocked for 1 day"
                                + " due to entering the wrong PIN code 3 times");
                        return;
                    }
                }
            }
        }
    }

    private void launchAddBankAccountMenu(BankSystem bankSystem) {

        while (true) {
            BankAccount newAccount = new BankAccount();
            bankSystem.addBankAccount(newAccount);
            printLineSeparator();
            println("A bank account with the number " + newAccount.getNumber() +
                    " has been added to the system");
            println();
            printGoBackHint();
            printLineSeparator();

            input = scanner.nextLine();
            if (input.equals(backConstant)) {
                break;
            } else {
                println(textManager.getMessageByType(TextManager.MessageTypes.INVALID_INPUT));
            }
        }
    }

    private void launchShowAvailableBankAccountsMenu(BankSystem bankSystem) {

        while (true) {
            printLineSeparator();
            println("All existing bank accounts");
            printLineSeparator();

            LinkedList<BankAccount> bankAccounts = bankSystem.getBankAccounts();

            if (bankAccounts.size() == 0) {
                println("There are no existing bank accounts");
            } else {
                for (int i = 0; i < bankAccounts.size(); i++) {
                    print((i + 1) + " - ");
                    println(bankAccounts.get(i).getNumber());
                }
            }

            println();
            printGoBackHint();
            printLineSeparator();

            input = scanner.nextLine();
            if (input.equals(backConstant)) {
                break;
            } else {
                println(textManager.getMessageByType(TextManager.MessageTypes.INVALID_INPUT));
            }
        }
    }

    private void launchAddBankCardMenu(BankSystem bankSystem) {

        BankAccount bankAccount;
        BankCardNumber bankCardNumber = new BankCardNumber();
        BankCardPinCode bankCardPinCode = new BankCardPinCode();
        BankCard bankCard;
        boolean correctDataEntered = false;

        while (!correctDataEntered) {
            printLineSeparator();
            println("Please, choose which bank account you want"
                    + " to assign the bank card to: ");

            while (true) {
                input = scanner.nextLine();
                try {
                    bankAccount = bankSystem.getBankAccountByNumber(Integer.parseInt(input));
                    break;
                } catch (BankSystemErrorException e) {
                    println(textManager.getMessageByType(e.errorType));
                } catch (NumberFormatException e) {
                    println(textManager.getMessageByType(TextManager.MessageTypes.INVALID_INPUT));
                }
            }

            println();
            println("Please, create a number for the card"
                    + " in the format XXXX-XXXX-XXXX-XXXX: ");

            while (true) {
                input = scanner.nextLine();
                try {
                    bankCardNumber.setNumber(input);
                    if (bankSystem.bankCardAlreadyExists(bankCardNumber)) {
                        println(textManager.getMessageByType(
                                BankSystemErrorException.ErrorType.BANK_CARD_NUMBER_ALREADY_EXISTS));
                        continue;
                    }
                    break;
                } catch (BankSystemErrorException e) {
                    println(textManager.getMessageByType(e.errorType));
                }
            }

            println();
            println("Please, create a 4-digit  PIN code for the card: ");

            while (true) {
                input = scanner.nextLine();
                try {
                    bankCardPinCode.setPinCode(input);
                    break;
                } catch (BankSystemErrorException e) {
                    println(textManager.getMessageByType(e.errorType));
                }
            }

            bankCard = new BankCard(bankAccount, bankCardNumber, bankCardPinCode);

            try {
                bankSystem.addBankCard(bankCard);
                correctDataEntered = true;
            } catch (BankSystemErrorException e) {
                println(textManager.getMessageByType(e.errorType));
                continue;
            }

            println();
            println("The bank card has been successfully created."
                    + " Please, remember the card\'s data:");
            println("Number: " + bankCard.getNumber().getStringNumber());
            println("PIN code: " + bankCard.getPinCode().getStringPinCode());
            println();
            printGoBackHint();
            printLineSeparator();

            while (true) {
                input = scanner.nextLine();
                if (input.equals(backConstant)) {
                    break;
                } else {
                    println(textManager.getMessageByType(TextManager.MessageTypes.INVALID_INPUT));
                }
            }
        }
    }

    private void launchBankAccountOperationsMenu(BankSystem bankSystem,
                                                 BankCard bankCard) {
        while (true) {
            printLineSeparator();
            println("1. Check the balance");
            println("2. Top up the balance");
            println("3. Charge money from the balance");
            println();
            printChooseNumberText();
            printGoBackHint();
            printLineSeparator();

            input = scanner.nextLine();
            if (input.equals("1")) {
                launchCheckBalance(bankCard);
            } else if (input.equals("2")) {
                launchTopUpBalance(bankCard);
            } else if (input.equals("3")) {
                launchChargeFromBalance(bankCard);
            } else if (input.equals(backConstant)) {
                break;
            } else {
                println(textManager.getMessageByType(TextManager.MessageTypes.INVALID_INPUT));
            }
        }
    }

    private void launchCheckBalance(BankCard bankCard) {

        while (true) {
            printLineSeparator();
            println("The balance of your bank account is "
                    + BankCalculator.getDollarsAndCents(bankCard.getBankAccount().getBalance()));
            println();
            printGoBackHint();
            printLineSeparator();

            input = scanner.nextLine();
            if (input.equals(backConstant)) {
                break;
            } else {
                println(textManager.getMessageByType(TextManager.MessageTypes.INVALID_INPUT));
            }
        }

    }

    private void launchTopUpBalance(BankCard bankCard) {
        while (true) {
            printLineSeparator();
            println("Please, enter the amount of money by which you want"
                    + " to top up your balance in the format dollars.cents or dollars: ");

            try {
                input = scanner.nextLine();
                bankCard.getBankAccount().addMoneyToBalance(BankCalculator.toDouble(input));
                parentAtm.addMoneyToBalance(BankCalculator.toDouble(input));
                println("Your bank account balance has been increased by "
                        + BankCalculator.getDollarsAndCents(BankCalculator.toDouble(input)));
            } catch (BankSystemErrorException e) {
                println(textManager.getMessageByType(e.errorType));
            } catch (NumberFormatException e) {
                println(textManager.getMessageByType(TextManager.MessageTypes.INVALID_INPUT));
            }

            println();
            printGoBackHint();

            while (true) {
                input = scanner.nextLine();
                if (input.equals(backConstant)) {
                    printLineSeparator();
                    return;
                } else {
                    println(textManager.getMessageByType(TextManager.MessageTypes.INVALID_INPUT));
                }
            }
        }
    }

    private void launchChargeFromBalance(BankCard bankCard) {
        while (true) {
            printLineSeparator();
            println("Please, enter the amount of money which you want"
                    + " to charge from your balance in the format dollars.cents or dollars: ");

            try {
                input = scanner.nextLine();
                parentAtm.removeMoneyFromBalance(BankCalculator.toDouble(input));
                bankCard.getBankAccount().removeMoneyFromBalance(BankCalculator.toDouble(input));
                println("You have charged "
                        + BankCalculator.getDollarsAndCents(BankCalculator.toDouble(input))
                        + " from your bank account");
            } catch (BankSystemErrorException e) {
                println(textManager.getMessageByType(e.errorType));
            } catch (NumberFormatException e) {
                println(textManager.getMessageByType(TextManager.MessageTypes.INVALID_INPUT));
            }

            println();
            printGoBackHint();

            while (true) {
                input = scanner.nextLine();
                if (input.equals(backConstant)) {
                    printLineSeparator();
                    return;
                } else {
                    println(textManager.getMessageByType(TextManager.MessageTypes.INVALID_INPUT));
                }
            }
        }
    }


    private void print(String text) {
        System.out.print(text);
    }

    private void println(String text) {
        System.out.println(text);
    }

    private void println(int number) {
        System.out.println(number);
    }

    private void println() {
        System.out.println();
    }

    private void printLineSeparator() {
        System.out.println(lineSeparator);
    }

    private void printChooseNumberText() {
        System.out.println(chooseNumberText);
    }

    private void printGoBackHint() {
        System.out.println(goBackHint);
    }

    private void printExitHint() {
        System.out.println(exitHint);
    }
}
