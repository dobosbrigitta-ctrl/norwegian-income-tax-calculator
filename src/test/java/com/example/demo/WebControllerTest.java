package com.example.demo;

import com.example.demo.controller.WebController;
import com.example.demo.util.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class WebControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExchangeRate exchangeRate;

    @InjectMocks
    private WebController webController;

    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(webController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void processTaxForm_WithTaxCard_ShouldReturnCalculateWithCardView() throws Exception {

        when(exchangeRate.convertCurrencyToNok(50000, "EUR")).thenReturn(580000.0);


        mockMvc.perform(post("/calculate")
                        .param("grossValue", "50000")
                        .param("taxPercentage", "30")
                        .param("currency", "EUR")
                        .param("haveTaxCard", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("calculate_withcard"))
                .andExpect(model().attribute("textValue", "Your net: 174000"));
    }

    @Test
    void processTaxForm_WhenGrossValueIsNull_ShouldReturnCalculateWithError() throws Exception {

        mockMvc.perform(post("/calculate")
                        .param("taxPercentage", "25")
                        .param("currency", "NOK")
                        .param("haveTaxCard", "false"))
                .andExpect(status().isOk())
                .andExpect(view().name("calculate"))
                .andExpect(model().attribute("textValue", "Error: Gross value cannot be empty!"))
                .andExpect(model().attribute("BasicTax", "0"));
    }
}
