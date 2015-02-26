package com.example.services;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.example.model.Address;
import com.example.model.Customer;
import com.example.model.EyeColour;
import com.example.model.SortOrder;
import com.example.utils.Util;

public class CustomerServicesImpl implements CustomerServices {

    private final Logger LOGGER = Logger.getLogger(CustomerServicesImpl.class.getName());
    public static CustomerServicesImpl instance = null;

    private CustomerServicesImpl() {
    }

    public static CustomerServices getInstance() {
        if (instance == null) {
            instance = new CustomerServicesImpl();
        }
        return instance;
    }

    @Override
    public Iterable<Customer> getCustomersByEyeColour(
            Iterable<Customer> customers, EyeColour eyeColour) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SortedSet<Customer> getCustomersOrderedByEmail(
            Iterable<Customer> customers, SortOrder sortOrder) {
        SortedSet<Customer> customersSortedByEmail = null;
        switch(sortOrder) {
            case ASC:
                Comparator<Customer> ascEmailComparator = (e1, e2) -> e1.getEmail().compareTo(e2.getEmail());
                customersSortedByEmail = new TreeSet<Customer>(ascEmailComparator);
                customersSortedByEmail.addAll(Util.getStreamForIterable(customers).collect(Collectors.toSet()));
                //customersSortedByEmail.addAll(Util.getStreamForIterable(customers).sorted(ascEmailComparator).collect(Collectors.toSet()));
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
