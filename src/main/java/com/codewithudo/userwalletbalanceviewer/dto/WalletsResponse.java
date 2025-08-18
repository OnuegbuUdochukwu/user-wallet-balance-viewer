package com.codewithudo.userwalletbalanceviewer.dto;

import lombok.Data;
import java.util.List;

@Data
public class WalletsResponse {
    private String status;
    private List<Wallet> data;
}
