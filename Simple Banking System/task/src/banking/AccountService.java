package banking;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

public class AccountService {
    private AccountRepository repo = new AccountRepository();
    private final Random r = new Random();

    public void createAccount() {
        Account acc = new Account(generateCardNumber(), generatePIN());
        repo.addAccount(acc);
        printCardCreated(acc.getCardNumber(), acc.getPIN());
    }

    private String generateCardNumber() {
        String rez = "400000" + (r.nextInt(999_999_999 - 100_000_000 + 1) + 100_000_000);
        return rez + getLastDigit(rez);
    }

    private String getLastDigit(String cardNumber15) {
        int[] digits = new int[15];
        for(int i = 0; i < 15; i++) {
            digits[i] = Character.getNumericValue(cardNumber15.charAt(i));
        }

        for(int i = 0; i < 15; i+=2) {
            digits[i] = digits[i] << 1;
            if (digits[i] > 9) digits[i] -= 9;
        }

        return String.valueOf((10 - (Arrays.stream(digits).sum() % 10)) % 10);
    }

    private String generatePIN() {
        return String.format("%04d", r.nextInt(10000));
    }

    private void printCardCreated(String cardNumber, String PIN) {
        System.out.println("\nYour card has been created\nYour card number:");
        System.out.println(cardNumber);
        System.out.println("Your card PIN:");
        System.out.println(PIN);
    }

    public boolean isValidCardNumber(String cardNumber) {
        return getLastDigit(cardNumber.substring(0, cardNumber.length() - 1))
                .equals(cardNumber.substring(cardNumber.length() - 1));

    }

    public Account getAccount(String cardNumber) {
        return repo.findByCardNumber(cardNumber);
    }

    public boolean isLogInSuccess(String cardNumber, String PIN) {
        return repo.exists(cardNumber) && repo.findByCardNumber(cardNumber).getPIN().equals(PIN);
    }

    public void addIncome(String cardNumber, BigDecimal income) {
        repo.addIncome(cardNumber, income);
    }

    public void deleteAccount(String cardNumber) {
        repo.deleteAccount(cardNumber);
    }

    public void doTransfer(String cardNumberFrom, String cardNumberTo, BigDecimal amount) {
        if (repo.findByCardNumber(cardNumberFrom).getBalance().compareTo(amount) < 0) {
            System.out.println("Not enough money!");
            return;
        }
        if (cardNumberFrom.equals(cardNumberTo)) {
            System.out.println("You can't transfer money to the same account!");
            return;
        }
        if (!isValidCardNumber(cardNumberTo)) {
            System.out.println("Probably you made mistake in the card number. Please try again!");
            return;
        }
        if (!repo.exists(cardNumberTo)) {
            System.out.println("Such a card does not exist.");
            return;
        }
        repo.transfer(cardNumberFrom, cardNumberTo, amount);
    }

}
