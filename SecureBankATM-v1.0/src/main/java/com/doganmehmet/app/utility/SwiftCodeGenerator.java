package com.doganmehmet.app.utility;

import java.util.Random;
import java.util.stream.IntStream;

public class SwiftCodeGenerator {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String[] COUNTRY_CODES = {"TR", "DE", "US", "FR", "GB", "NL", "ES", "IT", "CA", "AU"};

    private static String randomString(String characters, int length, Random random)
    {
        var str = new StringBuilder();

        IntStream.range(0, length).forEach(index ->
                str.append(characters.charAt(random.nextInt(characters.length()))));

        return str.toString();
    }

    public static String generateSwiftCode()
    {
        var random = new Random();

        var bankCode = randomString(ALPHABET, 4, random);
        var countryCode = COUNTRY_CODES[random.nextInt(COUNTRY_CODES.length)];
        var locationCode = randomString(ALPHANUMERIC, 2, random);
        var branchCode = randomString(ALPHANUMERIC, 3, random);

        return bankCode + countryCode + locationCode + branchCode;
    }
}
