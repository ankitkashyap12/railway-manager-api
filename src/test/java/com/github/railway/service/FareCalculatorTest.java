package com.github.railway.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {
        "fixed_base_price=10.0",
        "price_per_miles=2.5"
})
class FareCalculatorTest {

    @Autowired
    private FareCalculator fareCalculator;

    @Test
    void calculateFare_ReturnsCorrectFare() {
        Double distance = 100.0;
        Double expectedFare = 10.0 + (100.0 * 2.5);

        Double calculatedFare = fareCalculator.calculateFare(distance);

        assertEquals(expectedFare, calculatedFare, "The calculated fare should match the expected fare.");
    }
}
