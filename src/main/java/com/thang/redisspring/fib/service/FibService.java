package com.thang.redisspring.fib.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FibService {

    // have a strategy for cache evict
    @Cacheable(value = "math:fib", key = "#index") // create 1 hash in redis
    public int getFib(int index) {
        System.out.println("calculating fib for " + index);
        return fib(index);
    }

    // PUT / POST / PATCH / DELETE
    @CacheEvict(value = "math:fib", key = "#index")
    public void clearCache(int index) {
        System.out.println("clearing hash key");
    }

    @Scheduled(fixedRate = 10_000) //clear every 10s
    @CacheEvict(value = "math:fib", allEntries = true)
    public void clearCache() {
        System.out.println("clearing all fib keys");
    }

    private int fib(int index) {
        if (index <= 1) return index;
        return fib(index - 1) + fib(index - 2);
    }
}
