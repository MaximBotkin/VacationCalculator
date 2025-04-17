package com.example.vacationpaycalculator;

import com.example.vacationpaycalculator.controllers.VacationController;
import com.example.vacationpaycalculator.service.VacationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VacationController.class)
public class VacationCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VacationService vacationService;

    @Test
    public void testCalculateVacation() throws Exception {
        when(vacationService.calculateVacationPay(any())).thenReturn(41569.97);

        String url = "/calculate?averageSalary=100000&daysNumber=14&startDate=2024-10-01";

        mockMvc.perform(get(url)).andExpect(status().isOk()).andExpect(content()
                .string("Сумма отпускных составит: 41569,97 руб."));
    }

    @Test
    public void testInvalidAverageSalary() throws Exception {
        String url = "/calculate?averageSalary=-100000&daysNumber=14&startDate=2024-10-01";

        mockMvc.perform(get(url)).andExpect(status().isBadRequest()).andExpect(content()
                .string("Неправильно указана средняя зарплата, проверьте данные."));
    }

    @Test
    public void testInvalidDaysNumber() throws Exception {
        String url = "/calculate?averageSalary=100000&daysNumber=0&startDate=2024-10-01";

        mockMvc.perform(get(url)).andExpect(status().isBadRequest()).andExpect(content()
                .string("Неправильно указано количество дней отпуска, проверьте данные."));
    }
}
