package com.example.vacationpaycalculator.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


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
@Tag(name = "Калькулятор отпускных", description = "API для расчета суммы отпускных")
public class VacationController {

    @Autowired
    private VacationService vacationService;

    @Operation(
            summary = "Рассчитать отпускные",
            description = "Возвращает сумму отпускных на основе средней зарплаты и количества дней",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный расчет"),
                    @ApiResponse(responseCode = "400", description = "Неверные параметры запроса")
            }
    )

    @GetMapping
    public ResponseEntity<String> calculateVacation(
            @Parameter(description = "Средняя зарплата (руб)", example = "100000")
            @RequestParam int averageSalary,
            @Parameter(description = "Количество дней отпуска", example = "14")
            @RequestParam int daysNumber,
            @Parameter(description = "Дата начала отпуска (опционально)", example = "2024-10-01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
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
