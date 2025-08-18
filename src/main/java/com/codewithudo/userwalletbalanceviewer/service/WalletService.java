package com.codewithudo.userwalletbalanceviewer.service;

import com.codewithudo.userwalletbalanceviewer.dto.Wallet;
import com.codewithudo.userwalletbalanceviewer.dto.WalletsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class WalletService {

    private final RestTemplate restTemplate;

    @Value("${quidax.api.secret-key}")
    private String secretKey;

    public WalletService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Wallet> getUserWallets(String userId) {
        String url = "https://app.quidax.io/api/v1/users/" + userId + "/wallets";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + secretKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 1. Change the expected response type to our new wrapper
        ResponseEntity<WalletsResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        WalletsResponse body = response.getBody();

        // 2. Unwrap the list of wallets from the 'data' field
        if (body != null && "success".equals(body.getStatus())) {
            return body.getData();
        }

        return Collections.emptyList();
    }
}