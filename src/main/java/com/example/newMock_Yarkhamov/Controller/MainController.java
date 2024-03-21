package com.example.newMock_Yarkhamov.Controller;

import com.example.newMock_Yarkhamov.Model.RequestDTO;
import com.example.newMock_Yarkhamov.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@RestController
public class MainController {
 //для проверки в Postman http://localhost:8080/info/postBalances
    private Logger logger = LoggerFactory.getLogger(MainController.class);
    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;
            String rqUID = requestDTO.getRqUID();
            String currencyResponse = "";

            if (firstDigit == '8') {
                maxLimit = new BigDecimal(2000);
                currencyResponse = "US";
            } else if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000);
                currencyResponse = "EU";
            } else {
                maxLimit = new BigDecimal(10000);
                currencyResponse = "RUB";
            }

            Random random = new Random();
            BigDecimal bigDecimalBalance = BigDecimal.valueOf(random.nextDouble(maxLimit.doubleValue()));


            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setRqUID(rqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currencyResponse);
            responseDTO.setBalance(bigDecimalBalance.setScale(2, RoundingMode.DOWN));
            responseDTO.setMaxLimit(maxLimit);

            logger.error("******** Request ********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            logger.error("********* ResponseDTO ********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));
            return responseDTO;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }

}
