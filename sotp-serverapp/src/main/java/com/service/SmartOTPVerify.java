package com.service;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import com.entity.TOTPResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SmartOTPVerify {
    private static final Logger logger = LogManager.getLogger(SmartOTPVerify.class);
    private static SmartOTPVerify sOTPInstance = null;

    private SmartOTPVerify() {
    }

    public static SmartOTPVerify getInstance() {
        if (SmartOTPVerify.sOTPInstance == null) {
            sOTPInstance = new SmartOTPVerify();
            if (sOTPInstance == null) return null;
        }
        return SmartOTPVerify.sOTPInstance;
    }

    public TOTPResult verify(String codeString, String keyString, int stepTime, int pastIntervals, int futureIntervals) {

        boolean valid = false;
        long shift = 0;

        long currentSec = System.currentTimeMillis() / 1000;

        String expectedResponse = generateOTP(keyString, new Date(1000 * currentSec), stepTime);
        if (codeString.equals(expectedResponse)) {
            valid = true;
        }

        for (int i = 1; i <= pastIntervals; i++) {
            expectedResponse = generateOTP(keyString, new Date(1000 * (currentSec - i)), stepTime);
            if (codeString.equals(expectedResponse)) {
                valid = true;
                shift = -i;
                break;
            }
            shift = -i;
        }
        for (int i = 1; i <= futureIntervals; i++) {
            expectedResponse = generateOTP(keyString, new Date(1000 * (currentSec + i)), stepTime);
            if (codeString.equals(expectedResponse)) {
                valid = true;
                shift = i;
                break;
            }
            shift = i;
        }

        return new TOTPResult(valid, shift);
    }

    public String generateOTP(String keyStr, Date date, int stepTime) {
        try {
            TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator(stepTime, TimeUnit.SECONDS);

            Key key = new SecretKeySpec(keyStr.getBytes(), totp.getAlgorithm());

            long valueOTP = totp.generateOneTimePassword(key, date);
            String strOTP = String.format("%06d", valueOTP);

            return strOTP;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
