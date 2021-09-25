package root;

import root.bank.atm.Atm;

public class Main {

    public static void main(String[] args) {
        Atm atm = new Atm(20000000);
        atm.launchMenu();
    }
}
