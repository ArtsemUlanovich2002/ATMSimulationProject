package root.bank.atm;

import root.bank.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class FileManager {

    private Scanner scanner;
    private TextManager textManager = new TextManager();

    public void saveDataToFile(BankSystem bankSystem, Atm atm) {

        if (bankSystem.getBankAccounts().size() == 0) {
            return;
        }

        File file = new File("Data.txt");

        try {
            PrintWriter writer = new PrintWriter(file);

            writer.println(atm.getBalance());

            LinkedList<BankAccount> bankAccounts = bankSystem.getBankAccounts();

            for (int i = 0; i < bankAccounts.size(); i++) {
                writer.print(bankAccounts.get(i).getNumber());
                if (i == bankAccounts.size() - 1) {
                    writer.println();
                } else {
                    writer.print(" ");
                }
            }

            for (int i = 0; i < bankAccounts.size(); i++) {
                writer.print(BankCalculator.toStringFormat(bankAccounts.get(i).getBalance()));
                if (i == bankAccounts.size() - 1) {
                    writer.println();
                } else {
                    writer.print(" ");
                }
            }

            LinkedList<BankCard> bankCards = bankSystem.getBankCards();

            for (int i = 0; i < bankCards.size(); i++) {

                BankCard card = bankCards.get(i);

                writer.print(card.getNumber().getStringNumber());
                writer.print(" ");
                writer.print(card.getPinCode().getStringPinCode());
                writer.print(" ");
                writer.print(card.getBankAccount().getNumber());
                writer.print(" ");
                writer.print(card.isBlocked() ? "y " + card.getBlockingDate().getTime() : "n " + card.getIncorrectPinTimes());
                if (i != bankCards.size() - 1) {
                    writer.println();
                }
            }

            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("ERROR DURING SAVING THE DATA OF THE ATM");
        }
    }

    public void loadDataFromFile(BankSystem bankSystem, Atm atm) {

        File file = new File("Data.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("ERROR DURING CREATING A FILE");
        }

        try {
            scanner = new Scanner(file);

            if (!scanner.hasNextLine()) {
                return;
            }

            atm.setBalance(Double.parseDouble(scanner.nextLine()));

            String[] bankAccounts = scanner.nextLine().split(" ");
            String[] balances = scanner.nextLine().split(" ");
            for (int i = 0; i < bankAccounts.length; i++) {
                BankAccount account = new BankAccount();
                bankSystem.addBankAccount(account);
                account.addMoneyToBalance(BankCalculator.toDouble(balances[i]));
            }


            String[] bankCardData;
            BankAccount bankAccount;
            BankCardNumber cardNumber;
            BankCardPinCode cardPinCode;
            BankCard bankCard;

            while(scanner.hasNextLine()) {
                bankCardData = scanner.nextLine().split(" ");

                cardNumber = new BankCardNumber();
                cardNumber.setNumber(bankCardData[0]);
                cardPinCode = new BankCardPinCode();
                cardPinCode.setPinCode(bankCardData[1]);
                bankAccount =
                        bankSystem.getBankAccountByNumber(Integer.parseInt(bankCardData[2]));
                bankCard = new BankCard(bankAccount, cardNumber, cardPinCode);

                if (bankCardData[3].equals("n")) {
                    bankCard.setIncorrectPinTimes(Short.parseShort(bankCardData[4]));
                } else if (bankCardData[3].equals("y")){
                    bankCard.setIncorrectPinTimes(BankCard.MAX_ATTEMPTS_FOR_PIN);
                    bankCard.setBlockingDate(new Date(Long.parseLong(bankCardData[4])));
                }

                bankSystem.addBankCard(bankCard);
            }


        } catch (FileNotFoundException e) {
            System.out.println("ERROR DURING LOADING THE DATA OF THE ATM");
        } catch (BankSystemErrorException e) {
            System.out.println(textManager.getMessageByType(e.errorType));
        }
    }
}
