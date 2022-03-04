package com.controller;

import com.entity.TOTPResult;
import com.service.AuthenticationFlow;
import com.service.SmartOTPVerify;
import com.service.TOTPVerify;
import com.util.StringHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SOTPVerificationController {
    private static final Logger logger = LogManager.getLogger(SOTPVerificationController.class);

    @PostMapping("/verify-totp")
    public ResponseEntity<AuthenticationFlow> tOTPVerify(@RequestParam String code,
                                                         @RequestParam String secret,
                                                         @RequestParam(required = false, defaultValue = "60") Integer stepTime) {

        logger.info(String.format("code: %s, secret: %s, stepTime: %d", code, secret, stepTime));

        if (!StringHelper.isNullOrEmpty(secret) && !StringHelper.isNullOrEmpty(code)) {
            //TOTPVerify totp = new TOTPVerify(secret);
            //TOTPResult totpResult = totp.verify(code,1, 1);
            TOTPResult totpResult = SmartOTPVerify.getInstance().verify(code, secret, stepTime, 0, 0);
            logger.info(String.format("code: %s, secret: %s, valid: %s, shift: %d", code, secret, String.valueOf(totpResult.isValid()), totpResult.getShift()));

            if (totpResult.isValid()) {
                return ResponseEntity.ok().body(AuthenticationFlow.AUTHENTICATED);
            }

            return ResponseEntity.ok().body(AuthenticationFlow.NOT_AUTHENTICATED);
        }

        return ResponseEntity.ok().body(AuthenticationFlow.NOT_AUTHENTICATED);
    }

    @GetMapping("/gen-totp")
    public ResponseEntity<String> tOTPGenerate(@RequestParam String secret,
                                               @RequestParam(required = false, defaultValue = "60") Integer stepTime) {

        logger.info(String.format("secret: %s, stepTime: %d", secret, stepTime));

        if (!StringHelper.isNullOrEmpty(secret)) {

            String TOTPStr = SmartOTPVerify.getInstance().generateOTP(secret, new Date(), stepTime);
            logger.info(String.format("code: %s, secret: %s", TOTPStr, secret));

            return ResponseEntity.ok().body(TOTPStr);
        }

        return ResponseEntity.ok().body("NO TIME-OTP");
    }
}
