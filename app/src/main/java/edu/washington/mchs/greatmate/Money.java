package edu.washington.mchs.greatmate;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Comparator;
import java.util.IllegalFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by masq on 3/1/17.
 */

@IgnoreExtraProperties
public class Money implements Comparable<Money> {

    public final int dollars;
    public final int cents;

    public Money(double amt) {
        // TODO: make this better, need to ensure only two decimal points worth of cents.
        // TODO: determine if should round...
        this("" + ((int)(amt * 100.0)) / 100.0);
    }

    public Money(String amt) {
        if (amt == null) throw new IllegalArgumentException("Money cannot be null");
//        String[] components = amt.split("\\.", -1);
//        if (components.length != 2) throw new IllegalArgumentException("Money String is ill-formed: must be of the form '<dollars>.<cents>' e.g. '20.17'.");
        Pattern moneyRegex = Pattern.compile("^\\$?(-?\\d+)\\.(\\d{2})$");
        Matcher matcher = moneyRegex.matcher(amt);
        if (matcher.matches()) {
            try { this.dollars = Integer.parseInt(matcher.group(1), 10); }
            catch (Exception e) { throw new IllegalArgumentException("Money String is ill-formed; Could not parse dollars from String supplied"); }
            try { this.cents = (this.dollars >= 0 ? Integer.parseInt(matcher.group(2), 10) : (-Integer.parseInt(matcher.group(2), 10))); }
            catch (Exception e) { throw new IllegalArgumentException("Money String is ill-formed; Could not parse cents from String supplied"); }
        } else {
            throw new IllegalArgumentException("Money String is ill-formed; could not detect any money-like structures within String supplied");
        }
    }

    public Money(int dollars, int cents) {
        boolean posDollars = dollars >= 0;
        boolean posCents = cents >= 0;
        if (posDollars && !posCents) throw new IllegalArgumentException("Money object cannot be instantiated while Cents is negative and Dollars is positive.");
        if (cents >= 100) {
            dollars += cents / 100;
            cents %= 100;
        }
        if (!posDollars && posCents) {
            cents = (-cents);
        }
        this.cents = cents;
        this.dollars = dollars;
    }

    public double getAmount() {
        return (this.dollars + (this.cents / 100.0));
    }

    @Override
    public String toString() {
        return "[Money] amount: $" + this.getAmount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Money other = (Money) o; // cast object to Money class so that it can use Money methods
        return other.getAmount() == this.getAmount();
    }

    @Override
    public int compareTo(Money other) {
        return (int)(this.getAmount() - other.getAmount());
    }

    public static class MoneyComparator implements Comparator<Money> {
        @Override
        public int compare(Money m1, Money m2) {
            return (int)(m1.getAmount() - m2.getAmount());
        }
    }
}
