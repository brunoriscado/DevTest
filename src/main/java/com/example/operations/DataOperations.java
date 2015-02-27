package com.example.operations;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.example.model.Customer;
import com.example.model.EyeColour;
import com.example.utils.Util;

public class DataOperations {
    /**
     * This method performs a group by aggregation operation over an Iterable of Customer objects
     * it groups by the eye colour of a customer and counts the number of different eye colours existing within the sample data 
     * @param customers
     * @return Map - which has Eyecolour has a key and total count for that specific EyeColour
     */
    public Map<EyeColour, Long> aggregateEyeColours(Iterable<Customer> customers) {
        Map<EyeColour, Long> aggregation = Util.getStreamForIterable(customers)
                .collect(Collectors.groupingBy(Customer::getEyeColor, Collectors.counting()))
                .entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> EyeColour.getEyeColourFromName(String.valueOf(entry.getKey())),
                                        Map.Entry::getValue));
        return aggregation;
    }

    /**
     * This method uses the result from the EyeColour aggregation (aggregateEyeColours)
     * To obtain the EyeColour type that occurs the most
     * @param aggregation
     * @return EyeColour - the type that occured most times
     */
    public EyeColour getHighestTrend(Map<EyeColour, Long> aggregation) {
        Entry<EyeColour, Long> maxOccurances = aggregation.entrySet().stream()
                .max(Comparator.comparing(entry -> entry.getValue())).get();
        return maxOccurances != null ? maxOccurances.getKey() : null;
    }
}
