package com.C2B.MpesaSTK.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StkRequestDTO {
    @JsonProperty("TransactionType")
    private String transactionType;
    @JsonProperty("BusinessShortCode")
    private   String businessShortCode;
    @JsonProperty("Password")
    private  String  password;
    @JsonProperty("Timestamp")
    private  String timestamp;
    @JsonProperty("Amount")
    private int amount;
    @JsonProperty("PartyA")
    private  String partyA;
    @JsonProperty("PartyB")
    private  String partyB;
    @JsonProperty("PhoneNumber")
    private  String phoneNumber;
    @JsonProperty("CallBackURL")
    private String callBackURL;
    @JsonProperty("AccountReference")
    private  String accountReference;
    @JsonProperty("TransactionDesc")
    private  String  transactionDesc;
}
