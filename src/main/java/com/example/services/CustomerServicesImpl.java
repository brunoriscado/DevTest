package com.example.services;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.example.model.Address;
import com.example.model.AddressDetails;
import com.example.model.AddressDetails.AddressType;
import com.example.model.Customer;
import com.example.model.EyeColour;
import com.example.model.SortOrder;
import com.example.model.google.geocoding.GeocodingAPIHandler;
import com.example.model.google.geocoding.GoogleReverseGeocodingResponse;
import com.example.utils.Util;

public class CustomerServicesImpl implements CustomerServices {

    private final Logger LOGGER = Logger.getLogger(CustomerServicesImpl.class.getName());
    public static CustomerServicesImpl instance = null;
    private static GeocodingAPIHandler handler;
    private CustomerServicesImpl() {
    }

    public static CustomerServices getInstance(String addressLookupURL) {
        if (instance == null) {
            instance = new CustomerServicesImpl();
            handler = new GeocodingAPIHandler(addressLookupURL);
        }
        return instance;
    }

    @Override
    public Iterable<Customer> getCustomersByEyeColour(
            Iterable<Customer> customers, EyeColour eyeColour) {
        customers = Util.getStreamForIterable(customers)
                .filter(customer -> customer.getEyeColor().equals(eyeColour.getColour()))
                .collect(Collectors.toList());
        return customers;
    }

    @Override
    public SortedSet<Customer> getCustomersOrderedByEmail(
            Iterable<Customer> customers, SortOrder sortOrder) {
        SortedSet<Customer> customersSortedByEmail = null;
        Comparator<Customer> comparator = null;
        switch(sortOrder) {
            case ASC:
                comparator = (e1, e2) -> e1.getEmail().compareTo(e2.getEmail());
                customersSortedByEmail = new TreeSet<Customer>(comparator);
                break;
            case DESC:
                comparator = (e1, e2) -> -(e1.getEmail().compareTo(e2.getEmail()));
                break;
            default:
                LOGGER.log(Level.INFO, "Not a sorting option");
                break;
        }
        customersSortedByEmail = new TreeSet<Customer>(comparator);
        customersSortedByEmail.addAll(Util.getStreamForIterable(customers).collect(Collectors.toSet()));
        return customersSortedByEmail;
    }

    @Override
    public Address lookupAddress(double latitude, double longitude) {
        GoogleReverseGeocodingResponse response = handler.getAddress(latitude, longitude);
        return mapGoogleAddressToAddress(response);
    }

    private Address mapGoogleAddressToAddress(GoogleReverseGeocodingResponse googleAddress) {
        Address address = new Address();
        address.setAddresses(googleAddress.getResults().stream().map(element -> new AddressDetails(
                (List<AddressType>)element.getTypes().stream()
                        .map(type -> AddressType.getAddressTypeFromType(type)).collect(Collectors.toList()),
                element.getFormatted_address()))
                .collect(Collectors.toList()));
        return address;
    }

    @Override
    public Customer findClosestCustomer(Customer customer,
            Iterable<Customer> customers) {
        Customer closestCustomer = null;
        Customer nextCustomer = null;
        double distance = 0.0;
        Iterator<Customer> iterator = customers.iterator();
        while (iterator.hasNext()) {
            nextCustomer = iterator.next();
            if (!nextCustomer.equals(customer)) {
                closestCustomer = nextCustomer;
                distance = Util.getDistance(customer.getLatitude(), customer.getLongitude(),
                        closestCustomer.getLatitude(), closestCustomer.getLongitude());
                break;
            }
        }
        while (iterator.hasNext()) {
            nextCustomer = iterator.next();
            double auxDistance = Util.getDistance(customer.getLatitude(), customer.getLongitude(),
                    nextCustomer.getLatitude(), nextCustomer.getLongitude()); 
            if (!nextCustomer.equals(customer) && auxDistance < distance) {
                distance = auxDistance;
                closestCustomer = nextCustomer;
            }
        }
        return closestCustomer;
    }
}
