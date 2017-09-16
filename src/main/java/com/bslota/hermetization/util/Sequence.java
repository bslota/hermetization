package com.bslota.hermetization.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by bslota on 20.08.17.
 */
public class Sequence {
    private static AtomicLong currentValue = new AtomicLong(0L);

    public static long nextValue() {
        return currentValue.incrementAndGet();
    }

    public static long currentValue() {
        return currentValue.get();
    }
}
