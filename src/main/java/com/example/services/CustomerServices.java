package com.example.services;

import com.example.model.Address;
import com.example.model.Customer;
import com.example.model.EyeColour;
import com.example.model.SortOrder;

import java.util.SortedSet;

public interface CustomerServices {

    /**
     *
     * @param customers
     * @return
     */
    public Iterable<Customer> getCustomersByEyeColour(Iterable<Customer> customers, EyeColour eyeColour);

    /**
     *
     * @param customers
     * @return
     */
    public SortedSet<Customer> getCustomersOrderedByEmail(Iterable<Customer> customers, SortOrder sortOrder);

    /**
     *
     * @param latitude
     * @param longitude
     * @return
     */
    public Address lookupAddress(long latitude, long longitude);

    /**
     *
     * @param customer
     * @return
     */
    public Customer findClosestCustomer(Customer customer, Iterable<Customer> customers);

}
