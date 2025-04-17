package com.example.vacationpaycalculator.service;

import com.example.vacationpaycalculator.model.Vacation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

@Service
public class VacationService {

    public double calculateVacationPay(Vacation vacation) {
        int averageSalary = vacation.getAverageSalary();
        if (averageSalary <= 0) {
            throw new IllegalArgumentException("Запрлата не может равняться или быть меньше нуля.");
        }
        if (vacation.getDaysNumber() <= 0) {
            throw new IllegalArgumentException("Количество дней отпуска должно быть 1 и больше.");
        }
        int daysNumber = countVacationDays(vacation.getStartDate(), vacation.getDaysNumber());

        return ((averageSalary / 29.3) * daysNumber) * 0.87; // Сумма НДФЛ приведена примерная
    }

    public int countVacationDays(LocalDate startDate, int daysNumber) {
        List<MonthDay> holidays = new ArrayList<>();
        holidays.add(MonthDay.of(1, 1));   // 1 января
        holidays.add(MonthDay.of(1, 2));   // 2 января
        holidays.add(MonthDay.of(1, 3));   // 3 января
        holidays.add(MonthDay.of(1, 4));   // 4 января
        holidays.add(MonthDay.of(1, 5));   // 5 января
        holidays.add(MonthDay.of(1, 6));   // 6 января
        holidays.add(MonthDay.of(1, 8));   // 8 января
        holidays.add(MonthDay.of(1, 7));   // Рождество (7 января)
        holidays.add(MonthDay.of(2, 23));  // День защитника Отечества (23 февраля)
        holidays.add(MonthDay.of(3, 8));   // Международный женский день (8 марта)
        holidays.add(MonthDay.of(5, 1));   // Праздник весны и труда (1 мая)
        holidays.add(MonthDay.of(5, 9));   // День Победы (9 мая)
        holidays.add(MonthDay.of(6, 12));  // День России (12 июня)
        holidays.add(MonthDay.of(11, 4));  // День народного единства (4 ноября)

        LocalDate endDate = startDate.plusDays(daysNumber);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            for (MonthDay holiday : holidays) {
                if (holiday.equals(MonthDay.from(date))) {
                    daysNumber--;
                }
            }
        }

        return daysNumber;
    }
}
