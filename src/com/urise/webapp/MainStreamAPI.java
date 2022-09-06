package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainStreamAPI {
    public static void main(String[] args) {
        int[] ints = {1, 2, 3, 3, 2, 3};
        int[] ints1 = {7, 3, 7, 2, 2, 2, 2, 6};
        List<Integer> list = new ArrayList<>();
        Arrays.stream(ints1).forEach((x) -> list.add(x));
        System.out.println(minValue(ints));
        System.out.println(minValue(ints1));
        System.out.println(oddOrEven(list).toString());
    }

    private static int minValue(int[] values) {
        int[] temp = Arrays.stream(values).distinct().sorted().toArray();
        AtomicInteger integer = new AtomicInteger((int) Math.pow(10, temp.length));
        return Arrays.stream(temp).map((val) -> {
            integer.set(integer.get() / 10);
            return val * integer.get();
        }).sum();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce((o1, o2) -> o1 + o2).get();
        return integers.stream().filter((x) -> (x % 2) != (sum % 2)).collect(Collectors.toList());
    }
}
