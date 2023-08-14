package toysMachineApi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Wallet {

    private Money balance;

    public void addMoney(Money money) {
        this.balance.setValue(money.getValue() + this.balance.getValue());
    }

    public Money getMoney() {
        return this.balance;
    }

    public void spendMoney(Money money) {
        if (this.balance.getValue() - money.getValue() < 0.0) {
            throw new RuntimeException("You don't have enough money in your wallet.");
        }
        this.balance.setValue(this.balance.getValue() - money.getValue());
    }
}
