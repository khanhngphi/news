package com.lyonguyen.news.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Random {

    public int range(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
