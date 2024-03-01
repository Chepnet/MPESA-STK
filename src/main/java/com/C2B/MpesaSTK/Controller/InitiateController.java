package com.C2B.MpesaSTK.Controller;


import com.C2B.MpesaSTK.Model.StkPayload;
import com.C2B.MpesaSTK.Services.StkPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class InitiateController {
   private final StkPushService stkPushService;

    public InitiateController(StkPushService stkPushService) {
        this.stkPushService = stkPushService;
    }

    @PostMapping("stkpush/initiate")
    public ResponseEntity<Object> stkRequest(@RequestBody StkPayload stkPayload)
    {
        return stkPushService.InitiateRequest(stkPayload);
    }
}
