package com.example.model.workers;

import java.util.concurrent.Callable;

import com.example.model.Address;
import com.example.model.Customer;
import com.example.services.CustomerServices;

public class CustomerAddressWorker implements Callable<Customer> {
    private Customer customer;
    private CustomerServices customerServices;

    public CustomerAddressWorker(CustomerServices customerServices, Customer customer) {
        this.customer = customer;
        this.customerServices = customerServices;
    }

    @Override
    public Customer call() throws Exception {
        Address address = customerServices.lookupAddress(customer.getLatitude(), customer.getLongitude());
        customer.setAddress(address);
        return customer;
    }

}
