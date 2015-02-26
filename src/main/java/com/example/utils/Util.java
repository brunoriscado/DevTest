package com.example.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Util {

    public static <T> Collection<T> getCollectionFromIterable (Iterable<T> iterable) {
        Collection<T> list = new ArrayList<T>();
        for (T item : iterable) {
            list.add(item);
        }
        return list;
    }
    
    public static <T> Stream<T> getStreamForIterable(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
