package com.example.services;

import java.util.SortedSet;

import com.example.model.Address;
import com.example.model.Customer;
import com.example.model.EyeColour;
import com.example.model.SortOrder;

public class CustomerServicesImpl implements CustomerServices {

    @Override
    public Iterable<Customer> getCustomersByEyeColour(
            Iterable<Customer> customers, EyeColour eyeColour) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SortedSet<Customer> getCustomersOrderedByEmail(
            Iterable<Customer> customers, SortOrder sortOrder) {
        switch(sortOrder) {
            case ASC:
                break;
            case DESC:
                break;
            default:
        }
        return null;
    }

    @Override
    public Address lookupAddress(long latitude, long longitude) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Customer findClosestCustomer(Customer customer,
            Iterable<Customer> customers) {
        // TODO Auto-generated method stub
        return null;
    }

}
