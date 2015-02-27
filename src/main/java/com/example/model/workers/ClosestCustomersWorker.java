package com.example.model.workers;

import java.util.concurrent.Callable;

import com.example.model.Customer;
import com.example.model.workers.ClosestCustomersWorker.CustomerPairDistance;
import com.example.services.CustomerServices;
import com.example.utils.Util;

public class ClosestCustomersWorker implements Callable<CustomerPairDistance>{
    private CustomerServices customerServices;
    private Customer customer;
    private Iterable<Customer> customers;

    public ClosestCustomersWorker(CustomerServices customerServices, Customer customer, Iterable<Customer> customers) {
        this.customerServices = customerServices;
        this.customer = customer;
        this.customers = customers;
    }

    public class CustomerPairDistance implements Comparable<CustomerPairDistance>{
        private Customer customer1;
        private Customer customer2;
        private double distance;

        public CustomerPairDistance(Customer customer1, Customer customer2) {
            this.customer1 = customer1;
            this.customer2 = customer2;
            this.distance = Util.getDistance(customer1.getLatitude(), customer1.getLongitude(),
                    customer2.getLatitude(), customer2.getLongitude());
        }

        public Customer getCustomer1() {
            return customer1;
        }

        public void setCustomer1(Customer customer1) {
            this.customer1 = customer1;
        }

        public Customer getCustomer2() {
            return customer2;
        }

        public void setCustomer2(Customer customer2) {
            this.customer2 = customer2;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        @Override
        public int compareTo(CustomerPairDistance o) {
            int result = 0;
            if (this.getDistance() > o.getDistance()) {
                result = 1;
            } else if (this.getDistance() < o.getDistance()) {
                result = -1;
            }
            return result;
        }
    }

    @Override
    public CustomerPairDistance call() throws Exception {
        Customer closestCustomer = customerServices.findClosestCustomer(customer, customers);
        return new CustomerPairDistance(customer, closestCustomer);
    }
}
