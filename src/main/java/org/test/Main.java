package org.test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Main {

    public static final int SIZE = 1_000_000;
    public static final String VALUE = "value";

    public static void main(String[] args) {
        // create
        List<String> arrayList = new ArrayList<>(SIZE);
        List<String> linkedList = new LinkedList<>();

        // populate
        fill(arrayList);
        fill(linkedList);

        // measure
        measureInsertInTheMiddle(arrayList);
        measureInsertInTheMiddle(linkedList);
    }

    private static void measureInsertInTheMiddle(List<String> list) {
        int insertAt = list.size() / 2;

        long start = currentTimeNanos();
        list.add(insertAt, VALUE);
        long end = currentTimeNanos();

        long elapsed = TimeUnit.NANOSECONDS.toMillis(end - start);
        System.out.println("Time elapsed for " + list.getClass().getName() + ": " + elapsed + " ms");
    }

    private static void fill(List<String> list) {
        System.out.print("Filling " + list.getClass().getName() + " with " + SIZE + " elements ... ");

        IntStream.range(0, SIZE).forEach(ignored -> list.add(UUID.randomUUID().toString()));

        System.out.println("done");
    }

    private static long currentTimeNanos() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported()
                ? bean.getCurrentThreadCpuTime()
                : 0L;
    }
}
