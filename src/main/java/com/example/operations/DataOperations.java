package com.example.operations;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.example.model.Customer;
import com.example.model.EyeColour;
import com.example.utils.Util;

public class DataOperations {
    private static final Logger LOGGER = Logger.getLogger(DataOperations.class.getName());

    public Map<EyeColour, Long> aggregateEyeColours(Iterable<Customer> customers) {
        Map<EyeColour, Long> aggregation = Util.getStreamForIterable(customers)
                .collect(Collectors.groupingBy(Customer::getEyeColor, Collectors.counting()))
                .entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> EyeColour.getEyeColourFromName(String.valueOf(entry.getKey())),
                                        Map.Entry::getValue));
        return aggregation;
    }

    public EyeColour getHighestTrend(Map<EyeColour, Long> aggregation) {
        Entry<EyeColour, Long> maxOccurances = aggregation.entrySet().stream()
                .max(Comparator.comparing(entry -> entry.getValue())).get();
        return maxOccurances != null ? maxOccurances.getKey() : null;
    }
}
