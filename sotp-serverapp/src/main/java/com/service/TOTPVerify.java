package com.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.entity.TOTPResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.aerogear.security.otp.api.Digits;
import org.jboss.aerogear.security.otp.api.Hash;
import org.jboss.aerogear.security.otp.api.Hmac;

public class TOTPVerify {
    private static final Logger logger = LogManager.getLogger(TOTPVerify.class);


    private final String secret;

    public TOTPVerify(String secret) {
        this.secret = secret;
    }

    public TOTPResult verify(String codeString, int pastIntervals, int futureIntervals) {

        boolean valid = false;
        long shift = 0;

        int code = Integer.parseInt(codeString);
        long currentInterval = System.currentTimeMillis() / 1000 / 30;

        int expectedResponse = generate(currentInterval);
        if (expectedResponse == code) {
            valid = true;
        }

        for (int i = 1; i <= pastIntervals; i++) {
            int pastResponse = generate(currentInterval - i);
            if (pastResponse == code) {
                valid = true;
                shift = -i;
            }
        }
        for (int i = 1; i <= futureIntervals; i++) {
            int futureResponse = generate(currentInterval + i);
            if (futureResponse == code) {
                valid = true;
                shift = i;
            }
        }

        return new TOTPResult(valid, shift);
    }

    private int generate(long interval) {
        return hash(interval);
    }

    private int hash(long interval) {

        byte[] hash = new byte[0];
        try {
            //hash = new Hmac(Hash.SHA1, Base32.decode(this.secret), interval).digest();
            hash = new Hmac(Hash.SHA1, this.secret.getBytes(), interval).digest();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return bytesToInt(hash);
    }

    private static int bytesToInt(byte[] hash) {
        // put selected bytes into result int
        int offset = hash[hash.length - 1] & 0xf;

        int binary = (hash[offset] & 0x7f) << 24 | (hash[offset + 1] & 0xff) << 16
                | (hash[offset + 2] & 0xff) << 8 | hash[offset + 3] & 0xff;

        return binary % Digits.SIX.getValue();
    }

}