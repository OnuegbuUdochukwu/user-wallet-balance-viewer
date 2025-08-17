package com.codewithudo.userwalletbalanceviewer.dto;

import lombok.Data;

@Data
public class Wallet {

    private String id;
    private String currency;
    private String balance;
    private String locked;

}