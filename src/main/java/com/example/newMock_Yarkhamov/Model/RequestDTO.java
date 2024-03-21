package com.example.newMock_Yarkhamov.Model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestDTO {
    private String rqUID;
    private String clientId;
    private BigDecimal balance;
    private BigDecimal maxlimit;
    private String account;
    private String openDate;
    private String closeDate;
}
