package com.example.utils;

import java.util.ArrayList;
import java.util.Collection;

public class Util {

    public static <T> Collection<T> getCollectionFromIterable (Iterable<T> iterable) {
        Collection<T> list = new ArrayList<T>();
        for (T item : iterable) {
            list.add(item);
        }
        return list;
    }
}
