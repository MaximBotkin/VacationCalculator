package com.example.vacationpaycalculator.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Vacation {
    private int averageSalary;
    private int daysNumber;
    private LocalDate startDate;
}
