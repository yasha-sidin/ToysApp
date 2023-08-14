package model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Probability {
    private final static double MAX_PROBABILITY = 100;
    private final static MathContext MATH_CONTEXT = new MathContext(3, RoundingMode.HALF_UP);

    private double value;

    public Probability() {

    }

    public Probability(double probability) {
        setProbability(probability);
    }

    public double getValue() {
        return value;
    }

    public void setProbability(double probability) {
        if (probability <= MAX_PROBABILITY) {
            this.value = Double.parseDouble(new BigDecimal(probability, MATH_CONTEXT).toString());
        } else {
            throw new RuntimeException("Probability can't be more than 100");
        }
    }
}
