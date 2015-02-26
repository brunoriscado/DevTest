package com.example;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.model.Customer;
import com.example.model.EyeColour;
import com.example.model.SortOrder;
import com.example.operations.DataOperations;
import com.example.services.CustomerServices;
import com.example.services.CustomerServicesImpl;
import com.example.services.ModelLoader;
import com.example.services.ModelLoaderImpl;
import com.example.utils.Util;

public class Main {

    ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        //1 - Load the Data into an in memory collection using the ModelLoader.
        ModelLoader loader = ModelLoaderImpl.getInstance("data.json", "/tmp/output.json");
        Iterable<Customer> customers = loader.loadCustomers();

        //2 - Determine which eye colour is the most popular
        //TODO - Stop using the get collection from Iterable, use Stream from Iterable instead
        DataOperations ops = new DataOperations();
        Map<EyeColour, Long> eyeColourAggregation = ops.aggregateEyeColours(Util.getCollectionFromIterable(customers));
        EyeColour highestTrend = ops.getHighestTrend(eyeColourAggregation);

        //3 - Output all email addresses sorted alphabetically in ascending order
        CustomerServices customerServices = CustomerServicesImpl.getInstance();
        customerServices.getCustomersOrderedByEmail(customers, SortOrder.ASC);

        //4 - Using the above ExecutorService or other form of concurrency, populate the Address field of each customer.
        //    As this is a long running task, we expect some form of parallelism.

        //5 - Using the above ExecutorService or other form of concurrency, determine which 2 Customers live closest to each other.

        //6 - Using the ModuleLoader, write the Customer list back to JSON, including the new Address information
        loader.writeCustomers(customers);
    }
}
