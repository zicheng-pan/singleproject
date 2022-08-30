package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Integer[] array = new Integer[]{4, 3, 2, 1};
        List<Integer> a = Arrays.asList(array);
        List<Integer> b = a.stream().sorted(Integer::compareTo).collect(Collectors.toList());
        b.stream().forEach(it -> System.out.println(it));
        System.out.println("Hello World!");
    }
}
