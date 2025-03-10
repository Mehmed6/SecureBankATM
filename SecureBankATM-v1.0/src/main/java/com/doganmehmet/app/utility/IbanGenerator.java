package com.doganmehmet.app.utility;

import java.util.Random;

public class IbanGenerator {

    private static final String[] COUNTRY_CODES = {"TR", "DE", "US", "FR", "GB", "NL", "ES", "IT", "CA", "AU"};
    private static final Random RANDOM = new Random();
    public static String generateRandomIban()
    {
        var countryCode = COUNTRY_CODES[RANDOM.nextInt(COUNTRY_CODES.length)];
        var random = new Random();
        var control = String.format("%02d",random.nextInt(100));
        var bankCode = String.format("%04d",random.nextInt(10000));
        var accountNumber = String.format("%016d", random.nextLong() & Long.MAX_VALUE);

        return countryCode + control + bankCode + accountNumber;
    }
}
