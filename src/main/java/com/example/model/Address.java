package com.example.model;

import java.util.List;

/**
 * Created by will on 12/09/14.
 */
public class Address {
    List<AddressDetails> addresses;

    public List<AddressDetails> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDetails> addresses) {
        this.addresses = addresses;
    }
}
