package com.codewithudo.userwalletbalanceviewer.controller;

import com.codewithudo.userwalletbalanceviewer.dto.Wallet;
import com.codewithudo.userwalletbalanceviewer.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{userId}/wallets")
    public List<Wallet> getUserWallets(@PathVariable String userId) {
        return walletService.getUserWallets(userId);
    }
}