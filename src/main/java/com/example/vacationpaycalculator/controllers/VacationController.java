package com.example.vacationpaycalculator.controllers;


import com.example.vacationpaycalculator.model.Vacation;
import com.example.vacationpaycalculator.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/calculate")
public class VacationController {

    @Autowired
    private VacationService vacationService;

    @GetMapping
    public ResponseEntity<String> calculateVacation(@RequestParam int averageSalary,
                                                    @RequestParam int daysNumber,
                                                    @RequestParam(required = false)
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                        LocalDate startDate) {
        Vacation vacation = new Vacation();
        vacation.setAverageSalary(averageSalary);
        vacation.setDaysNumber(daysNumber);
        vacation.setStartDate(startDate);

        if (averageSalary <= 0) {
            return ResponseEntity.badRequest().body("Неправильно указана средняя зарплата, проверьте данные.");
        }

        if (daysNumber <= 0) {
            return ResponseEntity.badRequest().body("Неправильно указано количество дней отпуска, проверьте данные.");
        }

        double vacationPay = vacationService.calculateVacationPay(vacation);
        String result = String.format("%.2f", vacationPay);

        return ResponseEntity.ok("Сумма отпускных составит: " + result + " руб.");
    }
}
