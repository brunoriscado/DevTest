package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.model.Customer;
import com.example.model.EyeColour;
import com.example.model.SortOrder;
import com.example.model.annotations.TaskResource;
import com.example.model.workers.ClosestCustomersWorker;
import com.example.model.workers.ClosestCustomersWorker.CustomerPairDistance;
import com.example.model.workers.CustomerAddressWorker;
import com.example.operations.DataOperations;
import com.example.services.CustomerServices;
import com.example.services.CustomerServicesImpl;
import com.example.services.ModelLoader;
import com.example.services.ModelLoaderImpl;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName()); 
    private static final int numberOfThreads = 4;
    private static CustomerServices customerServices = null;
    private static ModelLoader loader = null;
    private static ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
    private static Properties props;

    @TaskResource
    private static String addressLookupURL;

    @TaskResource
    private static String inputData;

    @TaskResource
    private static String outputData;

    public static void main(String[] args) {
        try {
            loadConfig();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Unable to load configuration... shuting down!");
            System.exit(0);
        }
        //1 - Load the Data into an in memory collection using the ModelLoader.
        loader = ModelLoaderImpl.getInstance(inputData, outputData);
        Iterable<Customer> customers = loader.loadCustomers();

        //2 - Determine which eye colour is the most popular
        DataOperations ops = new DataOperations();
        Map<EyeColour, Long> eyeColourAggregation = ops.aggregateEyeColours(customers);
        EyeColour highestTrend = ops.getHighestTrend(eyeColourAggregation);
        System.out.println("**********************************\nThe most popular eye colour is: "
                + highestTrend.getColour());

        //3 - Output all email addresses sorted alphabetically in ascending order
        customerServices = CustomerServicesImpl.getInstance(addressLookupURL);
        System.out.println("\n**********************************\n");
        customerServices.getCustomersOrderedByEmail(customers, SortOrder.ASC)
                .forEach(customer -> System.out.println(
                        "First name: " + customer.getName().getFirst() + " | " +
                        "Last name: " + customer.getName().getLast() + " | " +
                        "Email Address: " + customer.getEmail()));
        System.out.println("\n**********************************\n");

        //4 - Using the above ExecutorService or other form of concurrency, populate the Address field of each customer.
        //    As this is a long running task, we expect some form of parallelism.
        Iterator<Customer> iterator = customers.iterator();
        List<Future<Customer>> futures = new ArrayList<Future<Customer>>();
        while (iterator.hasNext()) {
            futures.add(executorService.submit(new CustomerAddressWorker(customerServices, iterator.next())));
        }

        for (Future<Customer> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "Address worker Interrupted");
            } catch (ExecutionException e) {
                LOGGER.log(Level.WARNING, "Error fetching Address for user");
                e.printStackTrace();
            }
        }

        //5 - Using the above ExecutorService or other form of concurrency, determine which 2 Customers live closest to each other.
        iterator = customers.iterator();
        List<Future<CustomerPairDistance>> futuresCustomerPairs = new ArrayList<Future<CustomerPairDistance>>();
        List<CustomerPairDistance> closestCustomerDistances = new ArrayList<ClosestCustomersWorker.CustomerPairDistance>();
        while (iterator.hasNext()) {
            futuresCustomerPairs.add(executorService.submit(new ClosestCustomersWorker(customerServices, iterator.next(), customers)));
        }

        for (Future<CustomerPairDistance> future : futuresCustomerPairs) {
            try {
                closestCustomerDistances.add(future.get());
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "Closeness worker Interrupted");
            } catch (ExecutionException e) {
                LOGGER.log(Level.WARNING, "Error fetching Closest user");
                e.printStackTrace();
            }
        }
        executorService.shutdown();

        Collections.sort(closestCustomerDistances);
        System.out.println("**********************************\n" +
                "The closest customers are:\n" +
                "Customer ID: " + closestCustomerDistances.get(0).getCustomer1().get_id() + " (latitude: " + closestCustomerDistances.get(0).getCustomer1().getLatitude() +
                " and longitude: " + closestCustomerDistances.get(0).getCustomer1().getLongitude() + ")\n" +
                "Customer ID: " + closestCustomerDistances.get(0).getCustomer2().get_id() + " (latitude: " + closestCustomerDistances.get(0).getCustomer2().getLatitude() +
                " and longitude: " + closestCustomerDistances.get(0).getCustomer2().getLongitude() + ")");

        //6 - Using the ModuleLoader, write the Customer list back to JSON, including the new Address information
        loader.writeCustomers(customers);
        System.exit(0);
    }

    /**
     * Loads configuration from property files into Main class TaskResource annotated Fields
     * @throws Exception
     */
    public static void loadConfig() throws Exception {
        try (FileInputStream fis = new FileInputStream(System.getProperty("app.config"));) {
            props = new Properties();
            props.load(fis);
            for(Field field : Main.class.getDeclaredFields()) {
                setField(props, field);
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error reading properties file");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    private static void setField(Properties props, Field field) throws Exception {
        for (Annotation annotation : field.getAnnotations()) {
            if (annotation instanceof TaskResource) {
                String prop = props.getProperty(field.getName());
                if (prop == null) {
                    throw new Exception("Property not found: " + field.getName());
                }
                field.set(null, prop);
            }
        }
    }
}
