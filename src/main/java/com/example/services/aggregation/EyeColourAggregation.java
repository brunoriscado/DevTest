package com.example.services.aggregation;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.model.Customer;
import com.example.model.EyeColour;

public class EyeColourAggregation {

    public Map<EyeColour, Long> aggregateEyeColours(Collection<Customer> customers) {
        Map<EyeColour, Long> aggregation = customers.stream()
                .collect(
                        Collectors.groupingBy(Customer::getEyeColor, Collectors.counting()))
                .entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> EyeColour.getEyeColourFromName(String.valueOf(entry.getKey())),
                                        Map.Entry::getValue));
        return aggregation;
    }

    public EyeColour getHighestTrend(Map<EyeColour, Integer> aggregation) {
        return null;
    }
}
