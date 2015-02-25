package com.example.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.model.Customer;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

public class ModelLoaderImpl implements ModelLoader {

    private final Logger LOGGER = Logger.getLogger(ModelLoaderImpl.class.getName());
    public static String dataFile;
    public static String outputFile;
    public static ModelLoader instance = null;
    private static Gson gsonParser = null;

    private ModelLoaderImpl() {
    }

    public static ModelLoader getInstance(String input, String output) {
        if (instance == null) {
            instance = new ModelLoaderImpl();
            gsonParser = new Gson();
        }
        dataFile = input;
        outputFile = output;
        return instance;
    }

    @Override
    public Iterable<Customer> loadCustomers() {
        Iterable<Customer> customers = null;
        try (InputStreamReader isr = new InputStreamReader(ModelLoaderImpl.class.getClassLoader().getResourceAsStream(dataFile));
                    BufferedReader br = new BufferedReader(isr);) {
            customers = gsonParser.fromJson(br, new TypeToken<ArrayList<Customer>>() {}.getType());
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, "Data file: {0} could not be found", dataFile);
        } catch ( JsonIOException | JsonSyntaxException e){
            LOGGER.log(Level.WARNING, "Error parsing contents of data file: {0}", dataFile);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error reading contents of data file: {0}", dataFile);
        }
        return customers;
    }

    @Override
    public int writeCustomers(Iterable<Customer> customers) {
        File output = new File(outputFile);
        output.setWritable(true);
        int result = 1;
        try (FileWriter fw = new FileWriter(output); BufferedWriter bw = new BufferedWriter(fw);
                JsonWriter jw = new JsonWriter(bw);) {
            gsonParser.toJson(customers, new TypeToken<ArrayList<Customer>>() {}.getType(), jw);
        } catch (FileNotFoundException e) {
            result = 0;
            LOGGER.log(Level.WARNING, "Output file: {0} could not be found", outputFile);
        } catch ( JsonIOException | JsonSyntaxException e) {
            result = 0;
            LOGGER.log(Level.WARNING, "Error Serializing Json Object to output file");
        } catch (IOException e) {
            result = 0;
            LOGGER.log(Level.WARNING, "Error Serializing Json Object to output file");
        }
        return result;
    }
}
