package com.ishant.csfle.util;

import java.util.concurrent.atomic.AtomicLong;


public class IDUtil {

    private static final AtomicLong apiRequestId = new AtomicLong(0);

    public static long generateApiId() {
        return apiRequestId.getAndIncrement();
    }
}
