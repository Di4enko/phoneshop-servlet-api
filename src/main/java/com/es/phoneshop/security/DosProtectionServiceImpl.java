package com.es.phoneshop.security;

import com.es.phoneshop.service.cartService.CartServiceImp.CartServiceImpl;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DosProtectionServiceImpl implements DosProtectionService {
    private static DosProtectionService instance;
    private static final int THRESHOLD = 20;
    private static final long MINUTE = 60000;
    private Map<String, Long> countMap = new ConcurrentHashMap();
    private Map<String, Long> requestTime = new ConcurrentHashMap<>();
    private DosProtectionServiceImpl() {}

    public static DosProtectionService getInstance() {
        if (instance == null) {
            synchronized (CartServiceImpl.class) {
                if (instance == null) {
                    instance = new DosProtectionServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean isAvailable(String ip, Date date) {
        Long count = countMap.get(ip);
        Long time = requestTime.get(ip);
        if (count == null && time == null) {
            count = 1L;
            time = date.getTime();
            requestTime.put(ip, time);
        } else {
            if (date.getTime() - time > MINUTE) {
                requestTime.put(ip, date.getTime());
                countMap.put(ip, 1L);
                return true;
            }
            ++count;
            if (count > THRESHOLD) {
                requestTime.put(ip, date.getTime());
                return false;
            }
        }
        countMap.put(ip, count);
        return true;
    }
}
