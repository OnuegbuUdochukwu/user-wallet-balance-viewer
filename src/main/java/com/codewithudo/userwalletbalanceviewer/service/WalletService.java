package com.codewithudo.userwalletbalanceviewer.service;

import com.codewithudo.userwalletbalanceviewer.dto.Wallet;
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

    // Injects the secret key from application.properties
    @Value("${quidax.api.secret-key}")
    private String secretKey;

    public WalletService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Wallet> getUserWallets(String userId) {
        String url = "https://api.quidax.com/api/v1/users/" + userId + "/wallets";

        // 1. Create Headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + secretKey);

        // 2. Create an HTTP Entity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 3. Execute the authenticated request
        ResponseEntity<List<Wallet>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity, // Pass the entity with the headers
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }
}