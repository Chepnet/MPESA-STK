package com.C2B.MpesaSTK.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StkPushResponseDTO {
    @JsonProperty("MerchantRequestID")
    private  String merchantRequestID;
    @JsonProperty("CheckoutRequestID")
    private  String checkoutRequestID;
    @JsonProperty("ResponseCode")
    private  String responseCode;
    @JsonProperty("ResponseDescription")
    private  String responseDescription;
    @JsonProperty("CustomerMessage")
    private  String customerMessage;
}
