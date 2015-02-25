package com.example.services;

import com.example.model.Customer;

/**
 * Implement this interface to allow reading and writing of the JSON file.
 */
public interface ModelLoader {

    /**
     *
     * @return
     */
    public Iterable<Customer> loadCustomers();

    /**
     *
     */
    public int writeCustomers(Iterable<Customer> customers);

}
