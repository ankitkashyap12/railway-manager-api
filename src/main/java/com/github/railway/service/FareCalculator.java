package com.github.railway.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Ankit Kashyap on 08-03-2024
 */
@Service
public class FareCalculator {

    @Value("${fixed_base_price}")
    private Double fixedBasePrice;

    @Value("${price_per_miles}")
    private Double pricePerMiles;

    public Double calculateFare(Double distance){
        return fixedBasePrice+(distance*pricePerMiles);
    }
}
