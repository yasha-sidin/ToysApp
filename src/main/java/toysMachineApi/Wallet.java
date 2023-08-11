package toysMachineApi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Wallet {
    private final static MathContext MATH_CONTEXT = new MathContext(4, RoundingMode.HALF_UP);
    private Double money = 0.0;

    public void addMoney(Double money) {
        this.money = this.money + Double.parseDouble(new BigDecimal(money, MATH_CONTEXT).toString());
    }

    public Double getMoney() {
        return this.money;
    }

    public void spendMoney(Double money) {
        money = Double.parseDouble(new BigDecimal(money, MATH_CONTEXT).toString());
        if (this.money - money < 0.0) {
            throw new RuntimeException("You don't have enough money in your wallet.");
        }
        this.money = this.money - money;
    }
}
