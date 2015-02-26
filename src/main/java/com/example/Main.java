package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.model.Address;
import com.example.model.Customer;
import com.example.model.EyeColour;
import com.example.model.SortOrder;
import com.example.model.annotations.TaskResource;
import com.example.operations.DataOperations;
import com.example.services.CustomerServices;
import com.example.services.CustomerServicesImpl;
import com.example.services.ModelLoader;
import com.example.services.ModelLoaderImpl;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName()); 
    private static ExecutorService executorService = Executors.newFixedThreadPool(4);
    private static final String APP_PROPS_FILE = "app.properties";
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
        ModelLoader loader = ModelLoaderImpl.getInstance(inputData, outputData);
        Iterable<Customer> customers = loader.loadCustomers();

        //2 - Determine which eye colour is the most popular
        DataOperations ops = new DataOperations();
        Map<EyeColour, Long> eyeColourAggregation = ops.aggregateEyeColours(customers);
        EyeColour highestTrend = ops.getHighestTrend(eyeColourAggregation);
        System.out.println("The most popular eye colour is: " + highestTrend.getColour());

        //3 - Output all email addresses sorted alphabetically in ascending order
        CustomerServices customerServices = CustomerServicesImpl.getInstance(addressLookupURL);
        customerServices.getCustomersOrderedByEmail(customers, SortOrder.ASC)
                .forEach(customer -> System.out.println(
                        "First name: " + customer.getName().getFirst() + " | " +
                        "Last name: " + customer.getName().getLast() + " | " +
                        "Email Address: " + customer.getEmail()));



        //TODO - REMOVE
//        double lat = 49.1214842;
//        double lng = -55.3500208;
//        Address address = customerServices.lookupAddress(lat, lng);



        //4 - Using the above ExecutorService or other form of concurrency, populate the Address field of each customer.
        //    As this is a long running task, we expect some form of parallelism.
//        customers.spliterator().
//        executorService.submit(task)

        //5 - Using the above ExecutorService or other form of concurrency, determine which 2 Customers live closest to each other.

        //6 - Using the ModuleLoader, write the Customer list back to JSON, including the new Address information
        loader.writeCustomers(customers);
    }

    public static void loadConfig() throws Exception {
        Field fld = null;
        try (FileInputStream fis = new FileInputStream(ModelLoaderImpl.class.getClassLoader().getResource(APP_PROPS_FILE).getFile());) {
            props = new Properties();
            props.load(fis);
            for(Field field : Main.class.getDeclaredFields()) {
                setField(props, field);
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error reading properties from app properties file");
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
