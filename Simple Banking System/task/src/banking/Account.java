package banking;

import java.math.BigDecimal;

public class Account {
    private String cardNumber;
    private String PIN = "";
    private BigDecimal balance = new BigDecimal(0);

    public Account(String cardNumber, String PIN) {
        this.cardNumber = cardNumber;
        this.PIN = PIN;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPIN() {
        return PIN;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
