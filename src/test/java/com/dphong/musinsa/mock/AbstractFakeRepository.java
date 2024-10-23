package com.dphong.musinsa.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractFakeRepository<T> {

    AtomicLong autoIncrementId = new AtomicLong(0);
    List<T> data = new ArrayList<>();
}
