package com.es.phoneshop.security;

import java.util.Date;

public interface DosProtectionService {
    boolean isAvailable(String ip, Date date);
}
