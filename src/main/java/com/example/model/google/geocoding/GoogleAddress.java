package com.example.model.google.geocoding;

import java.util.List;

public class GoogleAddress {
    private List<AddressComponents> address_components;
    private String formatted_address;
    private Geometry geometry;
    private String place_id;
    private List<String> type;

    public List<AddressComponents> getAddress_components() {
        return address_components;
    }

    public void setAddress_components(List<AddressComponents> address_components) {
        this.address_components = address_components;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public class AddressComponents {
        private String long_name;
        private String short_name;
        private List<String> types;

        public String getLong_name() {
            return long_name;
        }
        public void setLong_name(String long_name) {
            this.long_name = long_name;
        }
        public String getShort_name() {
            return short_name;
        }
        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }
        public List<String> getTypes() {
            return types;
        }
        public void setTypes(List<String> types) {
            this.types = types;
        }
    }

    private class Geometry {
        private Bounds bounds;
        private Location location;
        private ViewPort viewport;

        public Bounds getBounds() {
            return bounds;
        }

        public void setBounds(Bounds bounds) {
            this.bounds = bounds;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public ViewPort getViewport() {
            return viewport;
        }

        public void setViewport(ViewPort viewport) {
            this.viewport = viewport;
        }

        private class Bounds {
            private NorthEast notheast;
            private SouthWest southwest;

            public NorthEast getNotheast() {
                return notheast;
            }
            public void setNotheast(NorthEast notheast) {
                this.notheast = notheast;
            }
            public SouthWest getSouthwest() {
                return southwest;
            }
            public void setSouthwest(SouthWest southwest) {
                this.southwest = southwest;
            }
        }

        private class Location {
            private double lat;
            private double lng;

            public double getLat() {
                return lat;
            }
            public void setLat(double lat) {
                this.lat = lat;
            }
            public double getLng() {
                return lng;
            }
            public void setLng(double lng) {
                this.lng = lng;
            }
        }

        private class ViewPort {
            private NorthEast notheast;
            private SouthWest southwest;

            public NorthEast getNotheast() {
                return notheast;
            }
            public void setNotheast(NorthEast notheast) {
                this.notheast = notheast;
            }
            public SouthWest getSouthwest() {
                return southwest;
            }
            public void setSouthwest(SouthWest southwest) {
                this.southwest = southwest;
            }
        }

        private class NorthEast {
            private double lat;
            private double lng;

            public double getLat() {
                return lat;
            }
            public void setLat(double lat) {
                this.lat = lat;
            }
            public double getLng() {
                return lng;
            }
            public void setLng(double lng) {
                this.lng = lng;
            }
        }

        private class SouthWest {
            private double lat;
            private double lng;

            public double getLat() {
                return lat;
            }
            public void setLat(double lat) {
                this.lat = lat;
            }
            public double getLng() {
                return lng;
            }
            public void setLng(double lng) {
                this.lng = lng;
            }
        }
    }
}
