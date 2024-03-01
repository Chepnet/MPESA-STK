package com.C2B.MpesaSTK.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StkPayload {

    private int amount;
    private  String phoneNumber;
    private  String accountReference;
    private  String  transactionDesc;
    private String transactionRef;
}