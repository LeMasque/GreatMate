package edu.washington.mchs.greatmate;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by masq on 3/2/17.
 */

public class MoneyTest {

    @Test(expected = IllegalArgumentException.class)
    public void stringConNull() {
        Money m = new Money(null);
    }

    @Test
    public void inputConOut() {
        Money mi = new Money(283, 56);
        assertEquals(283.56, mi.getAmount(), 0.0);

        Money ms = new Money("283.56");
        assertEquals(283.56, ms.getAmount(), 0.0);

        Money md = new Money(283.56);
        assertEquals(283.56, md.getAmount(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void stringConNegCents() {
        Money m = new Money("-234.-76");
    }

    @Test(expected = IllegalArgumentException.class)
    public void stringConBigCents() {
        Money m = new Money("234.1928374109");
    }

    @Test
    public void doubleConBigCents() {
        Money m = new Money(253.8192840);
        assertEquals(253.82, m.getAmount(), 0.0);
    }

    @Test
    public void doubleConNegative() {
        Money m = new Money(-12.83);
        assertEquals(-12.83, m.getAmount(), 0.0);
    }

    @Test
    public void stringConLeadingZeroCents() {
        Money m = new Money("234.06");
        assertEquals(234.06, m.getAmount(), 0.0);
    }

    @Test
    public void stringConTrailingZeroCents() {
        Money m = new Money("234.60");
        assertEquals(234.6, m.getAmount(), 0.0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void stringConMissingTrailingZeroCents() {
        Money m = new Money("234.6");
        assertEquals(234.6, m.getAmount(), 0.0);
    }

    @Test
    public void stringConLeadingDollarSign() {
        Money m = new Money("$537.83");
        assertEquals(537.83, m.getAmount(), 0.0);
    }

    @Test
    public void intConNegDollarNegCents() {
        Money m = new Money(-254, -83);
        assertEquals(-254.83, m.getAmount(), 0.0);
    }

    @Test
    public void intConNegDollarPosCents() {
        Money m = new Money(-254, 83);
        assertEquals(-254.83, m.getAmount(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void intConPosDollarNegCents() {
        Money m = new Money(254, -83);
    }
}
