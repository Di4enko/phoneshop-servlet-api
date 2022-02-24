package com.es.phoneshop.security;

import com.es.phoneshop.security.impl.DosProtectionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Date;

public class DosProtectionServiceTest {
    private DosProtectionService dosProtectionService;
    private String ip;

    @Before
    public void setup() {
        dosProtectionService = DosProtectionServiceImpl.getInstance();
        ip = "ip";
    }

    @Test
    public void isAvailableFirstRequestTest() {
        boolean result = dosProtectionService.isAvailable(ip, new Date());
        Assertions.assertTrue(result);
    }

    @Test
    public void isAvailableTrueTest() {
        dosProtectionService.isAvailable(ip, new Date());
        boolean result = dosProtectionService.isAvailable(ip, new Date());
        Assertions.assertTrue(result);
    }

    @Test
    public void isAvailableFalseTest() {
        for(int i=0; i < 20; i++) {
            dosProtectionService.isAvailable(ip, new Date());
        }
        boolean result = dosProtectionService.isAvailable(ip, new Date());
        Assertions.assertFalse(result);
    }

    @Test
    public void isAvailableMinuteWaitTest() {
        for(int i=0; i < 20; i++) {
            dosProtectionService.isAvailable(ip, new Date());
        }
        Date testRequestTime = new Date();
        boolean blockResult = dosProtectionService.isAvailable(ip, testRequestTime);
        testRequestTime.setTime(testRequestTime.getTime() + 60001);
        boolean AvailableResult = dosProtectionService.isAvailable(ip, testRequestTime);
        Assertions.assertFalse(blockResult);
        Assertions.assertTrue(AvailableResult);
    }
}
