package com.example.utils;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Util {

    public static <T> Stream<T> getStreamForIterable(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
