package com.example.model;

import java.util.List;

public class AddressDetails {
    private List<AddressType> addressTypes;
    private String address;

//    public AddressDetails() {
//    }

    public AddressDetails(List<AddressType> addressTypes, String address) {
        this.addressTypes = addressTypes;
        this.address = address;
    }

    public List<AddressType> getAddressType() {
        return addressTypes;
    }

    public void setAddressType(List<AddressType> addressTypes) {
        this.addressTypes = addressTypes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public enum AddressType {
        STREET_ADDRESS("street_address"),
        ROUTE("route"),
        INTERSECTION("intersection"),
        POLITICAL("political"),
        COUNTRY("country"),
        ADMINISTRATIVE_AREA_LEVEL_1("administrative_area_level_1"),
        ADMINISTRATIVE_AREA_LEVEL_2("administrative_area_level_2"),
        ADMINISTRATIVE_AREA_LEVEL_3("administrative_area_level_3"),
        ADMINISTRATIVE_AREA_LEVEL_4("administrative_area_level_4"),
        ADMINISTRATIVE_AREA_LEVEL_5("administrative_area_level_5"),
        COLLOQUIAL("colloquial"),
        LOCALITY("locality"),
        WARD("ward"),
        SUBLOCALITY("sublocality"),
        NEIGHBORHOOD("neighborhood"),
        PREMISE("premise"),
        SUBPREMISE("subpremise"),
        POSTAL_CODE("postal_code"),
        NATURAL_FEATURE("natural_feature"),
        AIRPORT("airport"),
        PARK("park"),
        POINT_OF_INTEREST("point_of_interest");

        private String type;

        private AddressType(String type) {
            this.setAddressType(type);
        }

        public static AddressType getAddressTypeFromType(String type) {
            AddressType result = null;
            for (AddressType addressType : AddressType.values()) {
                if (addressType.getAddressType().equalsIgnoreCase(type)) {
                    result = addressType;
                }
            }
            return result;
        }

        public String getAddressType() {
            return type;
        }

        public void setAddressType(String type) {
            this.type = type;
        }
    }
}
