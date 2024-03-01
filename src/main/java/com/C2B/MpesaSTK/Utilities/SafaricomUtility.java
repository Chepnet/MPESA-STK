package com.C2B.MpesaSTK.Utilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SafaricomUtility {
    @Value("${safaricom.daraja.consumer-key}")
    private String consumerKey;
    @Value("${safaricom.daraja.consumer-secret}")
    private String consumerSecret;

    @Value("${safaricom.daraja.shortcode}")
    private String shortCode;

    @Value("${safaricom.daraja.passkey}")
    private String passKey;

    @Value("${safaricom.daraja.timestamp}")
    private String timestamp;
    @Value("${safaricom.daraja.callback-url}")
    private String callbackUrl;

    @Value("${safaricom.daraja.transactionType}")
    private String transactionType;

    @Value("${safaricom.daraja.Authorization_url}")
    private String authorizationUrl;

    @Value("${safaricom.daraja.grant_type}")
    private String grantType;
    @Value("${safaricom.daraja.InitiateURL}")
    private String initiateUrl;
    @Value("${safaricom.daraja.password}")
    private String password;

    public String generatePassword()
    {
String passString=shortCode+passKey+timestamp;

return passString;
    }

}
