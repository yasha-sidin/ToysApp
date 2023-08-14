package toysMachineApi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Money {
    private final static MathContext MATH_CONTEXT = new MathContext(4, RoundingMode.HALF_UP);
    private double value;
    private final char currencyChar;
    private final String currency;

    public Money(double value, char currencyChar, String currency) {
        if (value < 0.0) {
            throw new RuntimeException("Money can't be less than 0.");
        }
        this.value = Double.parseDouble(new BigDecimal(value, MATH_CONTEXT).toString());
        this.currencyChar = currencyChar;
        this.currency = currency;
    }

    public Money(double value) {
        this.value = Double.parseDouble(new BigDecimal(value, MATH_CONTEXT).toString());
        this.currencyChar = '$';
        this.currency = "Dollar";
    }

    public void setValue(double value) {
        if (value < 0.0) {
            throw new RuntimeException("Money can't be less than 0.");
        }
        this.value = Double.parseDouble(new BigDecimal(value, MATH_CONTEXT).toString());
    }

    public double getValue() {
        return this.value;
    }

    public char getCurrencyChar() {
        return currencyChar;
    }

    public String getCurrency() {
        return currency;
    }
}
