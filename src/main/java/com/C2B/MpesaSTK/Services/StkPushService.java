package com.C2B.MpesaSTK.Services;

import com.C2B.MpesaSTK.Model.*;
import com.C2B.MpesaSTK.Repository.LogStkRequestRepository;
import com.oracle.svm.core.annotate.Inject;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;
import java.util.Date;


@Service

public class StkPushService {
    private final LogStkRequestRepository   logStkRequestRepository;
    private final  WebClient webClient ;
    public StkPushService(LogStkRequestRepository logStkRequestRepository, WebClient webClient) {
        this.logStkRequestRepository = logStkRequestRepository;
        this.webClient = webClient;
    }
    private TokenResult tokenResult=new TokenResult();
    private StkRequestDTO  stkRequestDTO=new StkRequestDTO();
    private StkPushResponseDTO stkPushResponseDTO=new StkPushResponseDTO();
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


    public String GenerateToken()
{
    String accessToken="";
    // Encode consumer key and consumer secret
    String auth = consumerKey + ":" + consumerSecret;
    byte[] authBytes = auth.getBytes();
    String base64Creds = Base64.getEncoder().encodeToString(authBytes);
    // Set headers
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", "Basic " + base64Creds);

    HttpEntity<String> request = new HttpEntity<>(headers);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<TokenResult> response = restTemplate.exchange(authorizationUrl, HttpMethod.GET, request, TokenResult.class);
    TokenResult tokenResult1=response.getBody();
    accessToken=tokenResult1.getToken();
    return  accessToken;
}
public ResponseEntity<Object> InitiateRequest(StkPayload stkPayload)
{
    String token=GenerateToken();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", "Bearer  " + token);

    //Logging request to the database
    SavingDataToDB(stkPayload);

    stkRequestDTO.setTransactionType(transactionType);
    stkRequestDTO.setTimestamp(timestamp);
    stkRequestDTO.setCallBackURL(callbackUrl);
    stkRequestDTO.setPhoneNumber(stkPayload.getPhoneNumber());
    stkRequestDTO.setAmount(stkPayload.getAmount());
    stkRequestDTO.setTransactionDesc(stkPayload.getTransactionDesc());
    stkRequestDTO.setPassword(password);
    stkRequestDTO.setPartyB(shortCode);
    stkRequestDTO.setPartyA(stkPayload.getPhoneNumber());
    stkRequestDTO.setBusinessShortCode(shortCode);
    stkRequestDTO.setAccountReference(stkPayload.getAccountReference());
    System.out.println(stkRequestDTO);
try{
    webClient.post()
            .uri(initiateUrl)
            .header("Authorization","Bearer "+token)
            .bodyValue(stkRequestDTO)
            .retrieve()
            .bodyToMono(StkPushResponseDTO.class)
            .subscribe(response->{
                stkPushResponseDTO=response;
                System.out.println("Response is :"+ response);
                        });}
catch (Exception e)
{
    System.out.println("Safaricom service is unreachable");
}
if(stkPushResponseDTO!=null)
{
    LogStkRequest logStkRequest=updateData(stkPayload,stkPushResponseDTO);
    return ResponseEntity.ok().headers(headers).body(logStkRequest);
}
return  ResponseEntity.ok("Safaricom failed");



}

    private void SavingDataToDB(StkPayload stkPayload) {
        LogStkRequest logStkRequest=new LogStkRequest();
        logStkRequest.setAmount(stkPayload.getAmount());
        logStkRequest.setAccountReference(stkPayload.getAccountReference());
        logStkRequest.setPhoneNumber(stkPayload.getPhoneNumber());
        logStkRequest.setTransactionType(transactionType);
        logStkRequest.setBusinessShortCode(shortCode);
        logStkRequest.setPartyB(shortCode);
        logStkRequest.setPartyA(stkPayload.getPhoneNumber());
        logStkRequest.setCallbackUrl(callbackUrl);
        logStkRequest.setDateCreated(new Date());
        logStkRequest.setDateModified(new Date());
        logStkRequest.setTransactionRef(stkPayload.getTransactionRef());
        logStkRequest.setTransactionDescription(stkPayload.getTransactionDesc());
        saveData(logStkRequest);
    }

    private void saveData(LogStkRequest logStkRequest)
{
    logStkRequestRepository.save(logStkRequest);
}
private LogStkRequest updateData(StkPayload stkPayload,StkPushResponseDTO stkPushResponseDTO )
{

    //retrieve the entity you want to update
   LogStkRequest logStkRequest=logStkRequestRepository.findByTransactionRef(stkPayload.getTransactionRef()).orElse(null);

   //check if entity exists
    if(logStkRequest==null)
    {
        //Handle not found exception
        return null;
    }
    //modify the entity's attribute

    logStkRequest.setMerchantRequestId(stkPushResponseDTO.getMerchantRequestID());
    logStkRequest.setCheckoutRequestId(stkPushResponseDTO.getCheckoutRequestID());
    logStkRequest.setResponseCode(stkPushResponseDTO.getResponseCode());
    logStkRequest.setCustomerMessage(stkPushResponseDTO.getCustomerMessage());
    logStkRequest.setResponseDescription(stkPushResponseDTO.getResponseDescription());
    return logStkRequestRepository.save(logStkRequest);
}




}


