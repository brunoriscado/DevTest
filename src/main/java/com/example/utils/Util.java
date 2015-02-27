package com.example.utils;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Util {

    public static <T> Stream<T> getStreamForIterable(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double absoluteDistance1 = Math.sqrt(Math.pow(lat1, 2.0) + Math.pow(lng1, 2.0));
        double absoluteDistance2 = Math.sqrt(Math.pow(lat2, 2.0) + Math.pow(lng2, 2.0));
        return Math.sqrt(Math.pow(absoluteDistance2 - absoluteDistance1, 2.0));
    }
}
