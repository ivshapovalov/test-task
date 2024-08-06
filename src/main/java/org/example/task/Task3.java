package org.example.task;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
Написать функцию, возвращающую прописное написание стоимости (до тысяч 99 999.99).
Входной параметр переменная типа bigDecimal
*/
public class Task3 {

    private static final String[] INTEGER_PART_UNITS = {
            "ноль",
            "один",
            "два",
            "три",
            "четыре",
            "пять",
            "шесть",
            "семь",
            "восемь",
            "девять",
            "десять",
            "одиннадцать",
            "двенадцать",
            "тринадцать",
            "четырнадцать",
            "пятнадцать",
            "шестнадцать",
            "семнадцать",
            "восемнадцать",
            "девятнадцать"};
    private static final String[] FRACTIONAL_PART_UNITS = {
            "ноль",
            "одна",
            "две",
            "три",
            "четыре",
            "пять",
            "шесть",
            "семь",
            "восемь",
            "девять",
            "десять",
            "одиннадцать",
            "двенадцать",
            "тринадцать",
            "четырнадцать",
            "пятнадцать",
            "шестнадцать",
            "семнадцать",
            "восемнадцать",
            "девятнадцать"};
    private static final String[] TENS = {
            "",
            "десять",
            "двадцать",
            "тридцать",
            "сорок",
            "пятьдесят",
            "шестьдесят",
            "семьдесят",
            "восемьдесят",
            "девяносто"
    };

    private static final String[] HUNDREDS = {
            "",
            "сто",
            "двести",
            "триста",
            "четыреста",
            "пятьсот",
            "шестьсот",
            "семьсот",
            "восемьсот",
            "девятьсот"
    };

    private static final String[] THOUSAND_PART_UNITS = {
            "",
            "одна",
            "две",
            "три",
            "четыре",
            "пять",
            "шесть",
            "семь",
            "восемь",
            "девять"
    };

    public static String convertNumberToWord(BigDecimal number) {
        int integerPart = number.intValue();
        int fractionalPart = number.remainder(BigDecimal.ONE).movePointRight(2).intValue();

        String integerPartWords = integerPart == 0 ? "Ноль рублей" : convertIntegerPart(integerPart);
        String fractionalPartWords = fractionalPart == 0 ? " ноль копеек" : convertFractionalPart(fractionalPart);

        return firstSymbolToUpperCase(integerPartWords) + fractionalPartWords;
    }

    private static String getIntegerPartCurrency(int number) {
        if (number == 1) {
            return "рубль";
        } else if (number <= 4) {
            return "рубля";
        } else {
            return "рублей";
        }
    }

    private static String getFractionalPartCurrency(int number) {
        if (number == 1) {
            return "копейка";
        } else if (number > 1 && number <= 4) {
            return "копейки";
        } else {
            return "копеек";
        }
    }

    public static String convertIntegerPart(int number) {
        if (number == 0) return "";
        if (number < 20) {
            return " " + INTEGER_PART_UNITS[number] + " " + getIntegerPartCurrency(number);
        }
        if (number < 100) {
            return " " + TENS[number / 10] + ((number % 10 != 0) ? "" : " " + getIntegerPartCurrency(number)) + convertIntegerPart(number % 10);
        }
        if (number < 1000) {
            return " " + HUNDREDS[number / 100] + ((number % 100 != 0) ? "" : getIntegerPartCurrency(number)) + convertIntegerPart(number % 100);
        }
        if (number < 10000) {
            return " " + THOUSAND_PART_UNITS[number / 1000] + " " + getIntegerPartThousandSuffix(number / 1000) + ((number % 1000 != 0) ? "" : " " + getIntegerPartCurrency(number)) + convertIntegerPart(number % 1000);
        }
        if (number < 20000) {
            return " " + INTEGER_PART_UNITS[number / 1000] + " " + getIntegerPartThousandSuffix(number / 1000) + ((number % 1000 != 0) ? "" : getIntegerPartCurrency(number)) + convertIntegerPart(number % 1000);
        }
        if (number < 100000) {
            return " " + TENS[number / 10000] + ((number % 10000 != 0) ? "" : getIntegerPartCurrency(number)) + convertIntegerPart(number % 10000);
        }
        if (number < 1000000) {
            return " " + HUNDREDS[number / 100000] + ((number % 100000 != 0) ? "" : getIntegerPartCurrency(number)) + convertIntegerPart(number % 100000);
        }
        throw new IllegalArgumentException("Целая часть числа должна быть менее миллиона");
    }

    private static String getIntegerPartThousandSuffix(int number) {
        if (number == 1) {
            return "тысяча";
        } else if (number > 1 && number <= 4) {
            return "тысячи";
        } else {
            return "тысяч";
        }
    }

    public static String convertFractionalPart(int number) {
        if (number == 0) return "";

        if (number < 20) {
            return " " + FRACTIONAL_PART_UNITS[number] + " " + getFractionalPartCurrency(number);
        }
        if (number < 100) {
            return " " + TENS[number / 10] + ((number % 10 != 0) ? "" : " " + getFractionalPartCurrency(number)) + convertFractionalPart(number % 10);
        }
        throw new IllegalArgumentException("Дробная часть цисла должна быть менее 99");
    }

    private static String firstSymbolToUpperCase(String text) {
        return text.trim().substring(0, 1).toUpperCase() + text.trim().substring(1);
    }

    @Test
    public void test_0_0() {
        BigDecimal number = BigDecimal.ZERO;
        String actual = convertNumberToWord(number);
        String expected = "Ноль рублей ноль копеек";
        assertEquals(expected, actual);
    }

    @Test
    public void test_1_0() {
        BigDecimal number = BigDecimal.ONE;
        String actual = convertNumberToWord(number);
        String expected = "Один рубль ноль копеек";
        assertEquals(expected, actual);
    }

    @Test
    public void test_3_25() {
        BigDecimal number = BigDecimal.valueOf(3.25);
        String actual = convertNumberToWord(number);
        String expected = "Три рубля двадцать пять копеек";
        assertEquals(expected, actual);
    }

    @Test
    public void test_9_01() {
        BigDecimal number = BigDecimal.valueOf(9.01);
        String actual = convertNumberToWord(number);
        String expected = "Девять рублей одна копейка";
        assertEquals(expected, actual);
    }

    @Test
    public void test_10_02() {
        BigDecimal number = BigDecimal.valueOf(10.02);
        String actual = convertNumberToWord(number);
        String expected = "Десять рублей две копейки";
        assertEquals(expected, actual);
    }

    @Test
    public void test_11_09() {
        BigDecimal number = BigDecimal.valueOf(11.09);
        String actual = convertNumberToWord(number);
        String expected = "Одиннадцать рублей девять копеек";
        assertEquals(expected, actual);
    }

    @Test
    public void test_20_99() {
        BigDecimal number = BigDecimal.valueOf(20.99);
        String actual = convertNumberToWord(number);
        String expected = "Двадцать рублей девяносто девять копеек";
        assertEquals(expected, actual);
    }

    @Test
    public void test_21_40() {
        BigDecimal number = BigDecimal.valueOf(21.40);
        String actual = convertNumberToWord(number);
        String expected = "Двадцать один рубль сорок копеек";
        assertEquals(expected, actual);
    }

    @Test
    public void test_99() {
        BigDecimal number = BigDecimal.valueOf(99);
        String actual = convertNumberToWord(number);
        String expected = "Девяносто девять рублей ноль копеек";
        assertEquals(expected, actual);
    }

    @Test
    public void test_143_28() {
        BigDecimal number = BigDecimal.valueOf(143.28);
        String actual = convertNumberToWord(number);
        String expected = "Сто сорок три рубля двадцать восемь копеек";
        assertEquals(expected, actual);
    }

    @Test
    public void test_1000_00() {
        BigDecimal number = BigDecimal.valueOf(1000);
        String actual = convertNumberToWord(number);
        String expected = "Одна тысяча рублей ноль копеек";
        assertEquals(expected, actual);
    }

    @Test
    public void test_1234_20() {
        BigDecimal number = BigDecimal.valueOf(1234.20);
        String actual = convertNumberToWord(number);
        String expected = "Одна тысяча двести тридцать четыре рубля двадцать копеек";
        assertEquals(expected, actual);
    }

    @Test
    public void test_12345_21() {
        BigDecimal number = BigDecimal.valueOf(12345.21);
        String actual = convertNumberToWord(number);
        String expected = "Двенадцать тысяч триста сорок пять рублей двадцать одна копейка";
        assertEquals(expected, actual);
    }

    @Test
    public void test_42345_21() {
        BigDecimal number = BigDecimal.valueOf(42345.21);
        String actual = convertNumberToWord(number);
        String expected = "Сорок две тысячи триста сорок пять рублей двадцать одна копейка";
        assertEquals(expected, actual);
    }

    @Test
    public void test_123456_03() {
        BigDecimal number = BigDecimal.valueOf(123456.03);
        String actual = convertNumberToWord(number);
        String expected = "Сто двадцать три тысячи четыреста пятьдесят шесть рублей три копейки";
        assertEquals(expected, actual);
    }

    @Test
    public void test_999999_00() {
        BigDecimal number = BigDecimal.valueOf(999999.00);
        String actual = convertNumberToWord(number);
        String expected = "Девятьсот девяносто девять тысяч девятьсот девяносто девять рублей ноль копеек";
        assertEquals(expected, actual);
    }

    @Test
    public void test_1000000_00() {
        BigDecimal number = BigDecimal.valueOf(1000000);
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class, () ->
                        convertNumberToWord(number));
        assertEquals("Целая часть числа должна быть менее миллиона", thrown.getMessage());
    }
}
