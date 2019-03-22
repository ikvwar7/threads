package threads.utils;

import org.junit.Before;
import org.junit.Test;
import threads.exception.NoNumberException;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

public class NumberConverterTest {

    private NumberConverter numberConverter;

    @Before
    public void init() {
        numberConverter = new NumberConverter();
    }

    @Test
    public void whenCorrectNumberThenOk() {
        numberConverter.convert("twenty");
        numberConverter.convert("forty five");
        numberConverter.convert("three hundred seventy");
        numberConverter.convert("three hundred five");
        numberConverter.convert("three hundred seventy five");
        numberConverter.convert("three thousand seventy five");
        numberConverter.convert("two thousand nine hundred five");
        numberConverter.convert("two thousand nine hundred fifty");
        numberConverter.convert("two thousand nine hundred ninety nine");

        assertThat(Values.list, contains(20, 45, 305, 370, 375, 2905, 2950, 2999, 3075));
    }

    @Test(expected = NoNumberException.class)
    public void whenNotCorrectOneDigitNumberThenThrowException() {
        numberConverter.convert("twentyf");
    }

    @Test(expected = NoNumberException.class)
    public void whenNotCorrectTwoDigitNumberThenThrowException() {
        numberConverter.convert("twenty twof");
    }

    @Test(expected = NoNumberException.class)
    public void whenNotCorrectThreeDigitNumberThenThrowException() {
        numberConverter.convert("one two hundred");
    }

    @Test(expected = NoNumberException.class)
    public void whenNotCorrectFourDigitNumberThenThrowException() {
        numberConverter.convert("three seventy five hundred");
    }

    @Test(expected = NoNumberException.class)
    public void whenNotCorrectFiveDigitNumberThenThrowException() {
        numberConverter.convert("two thousand forty hundred fifty");
    }

    @Test(expected = NoNumberException.class)
    public void whenNotCorrectSixDigitNumberThenThrowException() {
        numberConverter.convert("two thousand five hundred fifty ten");
    }
}