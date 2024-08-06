package org.example.task;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Написать функцию, определяющую ближайшую дату отправки списка в страховую с условием,
что отправка осуществляется 1, 10 и 20 числа каждого месяца в 18:00. Если дата отправки попадает
на рабочий/праздничный день - то отправка осуществляется в предыдущий рабочий день.
дата запроса == текущему времени.
Можно использовать функцию:
public getVacCheck(java.sql.Date modDate); - проверяет дату, является ли она рабочей. если выходной -
возвращает ближайший рабочий день следующий за выходными. Возвращает переменную типа java.sql.Date
*/
public class Task2 {

    private static LocalDateTime getTransferDateTime(Date modDate) {
        int currentDay = modDate.getDate();
        LocalDate transferDate;
        if (currentDay == 1) {
            transferDate = changeTransferDateIfVacancy(modDate.toLocalDate());
        } else if (currentDay <= 10) {
            transferDate = changeTransferDateIfVacancy(modDate.toLocalDate().plusDays(10 - currentDay));
        } else if (currentDay <= 20) {
            transferDate = changeTransferDateIfVacancy(modDate.toLocalDate().plusDays(20 - currentDay));
        } else {
            LocalDate modDatePlusMonth = modDate.toLocalDate().plusMonths(1);
            transferDate = changeTransferDateIfVacancy(
                    modDatePlusMonth.minusDays(modDatePlusMonth.getDayOfMonth() - 1));
        }
        return LocalDateTime.of(transferDate, LocalTime.of(18, 0, 0));
    }

    public static LocalDate changeTransferDateIfVacancy(LocalDate localDate) {
        int dayOfWeek = localDate.getDayOfWeek().getValue();
        if (dayOfWeek > 5) {
            return localDate.minusDays(Math.abs(5 - dayOfWeek));
        } else {
            return localDate;
        }
    }

    @Test
    public void test_001() {
        Date date = Date.valueOf("2024-08-01");
        LocalDateTime actual = getTransferDateTime(date);
        LocalDateTime expected = LocalDateTime.of(2024, 8, 1, 18, 0, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_002() {
        Date date = Date.valueOf("2024-08-02");
        LocalDateTime actual = getTransferDateTime(date);
        LocalDateTime expected = LocalDateTime.of(2024, 8, 9, 18, 0, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_003() {
        Date date = Date.valueOf("2024-08-09");
        LocalDateTime actual = getTransferDateTime(date);
        LocalDateTime expected = LocalDateTime.of(2024, 8, 9, 18, 0, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_004() {
        Date date = Date.valueOf("2024-08-10");
        LocalDateTime actual = getTransferDateTime(date);
        LocalDateTime expected = LocalDateTime.of(2024, 8, 9, 18, 0, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_005() {
        Date date = Date.valueOf("2024-08-11");
        LocalDateTime actual = getTransferDateTime(date);
        LocalDateTime expected = LocalDateTime.of(2024, 8, 20, 18, 0, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_006() {
        Date date = Date.valueOf("2024-08-12");
        LocalDateTime actual = getTransferDateTime(date);
        LocalDateTime expected = LocalDateTime.of(2024, 8, 20, 18, 0, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_007() {
        Date date = Date.valueOf("2024-08-20");
        LocalDateTime actual = getTransferDateTime(date);
        LocalDateTime expected = LocalDateTime.of(2024, 8, 20, 18, 0, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_008() {
        Date date = Date.valueOf("2024-08-21");
        LocalDateTime actual = getTransferDateTime(date);
        LocalDateTime expected = LocalDateTime.of(2024, 8, 30, 18, 0, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_009() {
        Date date = Date.valueOf("2024-08-31");
        LocalDateTime actual = getTransferDateTime(date);
        LocalDateTime expected = LocalDateTime.of(2024, 8, 30, 18, 0, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_010() {
        Date date = Date.valueOf("2024-09-01");
        LocalDateTime actual = getTransferDateTime(date);
        LocalDateTime expected = LocalDateTime.of(2024, 8, 30, 18, 0, 0);
        assertEquals(expected, actual);
    }

}
