package com.example.vacationpaycalculator;

import com.example.vacationpaycalculator.model.Vacation;
import com.example.vacationpaycalculator.service.VacationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class VacationPayCalculatorApplicationTests {

    private final VacationService vacationService = new VacationService();

    @Test
    public void testCalculateVacation() {
        Vacation vacation = new Vacation();

        vacation.setAverageSalary(100000);
        vacation.setDaysNumber(28);
        vacation.setStartDate(LocalDate.of(2024, 10, 1));

        double vacationPay = vacationService.calculateVacationPay(vacation);
        assertEquals(83139.93, vacationPay, 0.01, "Сумма отпускных должна быть 83139,93 руб.");
    }

    @Test
    public void testCalculateVacationWithHolidays() {
        Vacation vacation = new Vacation();

        vacation.setAverageSalary(100000);
        vacation.setDaysNumber(14);
        vacation.setStartDate(LocalDate.of(2024, 5, 1));

        double vacationPay = vacationService.calculateVacationPay(vacation);
        assertEquals(35631.4, vacationPay, 0.01, "Сумма отпускных должна быть 35631,40 руб.");
    }

    @Test
    public void testInvalidAverageSalaryArgument() {
        Vacation vacation = new Vacation();

        vacation.setAverageSalary(0);
        vacation.setDaysNumber(28);
        vacation.setStartDate(LocalDate.of(2024, 10, 1));

        assertThrows(IllegalArgumentException.class, () -> vacationService.calculateVacationPay(vacation));
    }

    @Test
    public void testInvalidDaysNumberArgument() {
        Vacation vacation = new Vacation();
        vacation.setAverageSalary(100000);
        vacation.setDaysNumber(0);
        vacation.setStartDate(LocalDate.of(2024, 10, 1));

        assertThrows(IllegalArgumentException.class, () -> vacationService.calculateVacationPay(vacation));
    }

}
