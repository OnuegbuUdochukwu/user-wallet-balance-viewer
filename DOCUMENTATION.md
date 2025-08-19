# Project Documentation: User Wallet Balance Viewer

## Project Overview
The User Wallet Balance Viewer is a Spring Boot microservice that demonstrates how to make authenticated API requests. Unlike previous projects that used public data, this application fetches private, user-specific information—wallet balances—by providing a secret API key to the Quidax API.

It establishes a secure pattern for handling API secrets in a local development environment and introduces the Spring components required for building and sending authorized HTTP requests.

**GET** `/api/v1/users/{userId}/wallets`: Fetches all wallet balances for the user associated with the provided API key.

## Core Dependencies
- **spring-boot-starter-web**: Provides all necessary components for building REST APIs, including an embedded Tomcat server and the Jackson JSON library.
- **lombok**: A utility library used to reduce boilerplate code like getters and setters.

## Project Structure and Components
The project uses a standard layered architecture. The DTOs model the wrapped response from the Quidax API's authenticated wallets endpoint.

```
/dto/
 ├── Wallet.java          (Models a single wallet)
 └── WalletsResponse.java (Wrapper for the API's top-level response)
/service/
 └── WalletService.java   (Handles authenticated API calls)
/controller/
 └── WalletController.java (API Endpoint Layer)
```

## Detailed Class Explanations
### The DTO Layer (The Data Models)

#### 📄 Wallet.java

**Purpose**: A simple DTO that models a single wallet entry from the Quidax API.

**Code**:

```java
@Data
public class Wallet {
    private String id;
    private String currency;
    private String balance;
    private String locked;
}
```

#### 📄 WalletsResponse.java

**Purpose**: This DTO represents the top-level wrapper object that the Quidax API uses for this endpoint. It contains the status and the list of wallets.

**Code**:

```java
@Data
public class WalletsResponse {
    private String status;
    private List<Wallet> data;
}
```
### service/WalletService.java - The Business Logic

This class contains the core new logic for this project: securely injecting an API key and creating an authenticated HTTP request.

#### Key Annotations and Components:

- `@Value("${quidax.api.secret-key}")`: This annotation is used to inject values from configuration files. It tells Spring to find the property named `quidax.api.secret-key` in `application.properties` and assign its value to the `secretKey` string variable.

- `HttpHeaders`: A Spring class used to build the headers for an HTTP request. We use it to set the crucial Authorization header.

- `HttpEntity`: An object that wraps the request body and headers. Since this is a GET request, we only provide the headers.

- `restTemplate.exchange(...)`: A flexible RestTemplate method that allows us to send a request with custom components, including our `HttpEntity` which contains the authentication header.

#### Code:

```java
@Service
public class WalletService {

    private final RestTemplate restTemplate;

    @Value("${quidax.api.secret-key}")
    private String secretKey;

    public WalletService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Wallet> getUserWallets(String userId) {
        String url = "https://api.quidax.com/api/v1/users/" + userId + "/wallets";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + secretKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<WalletsResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        WalletsResponse body = response.getBody();

        if (body != null && "success".equals(body.getStatus())) {
            return body.getData();
        }

        return Collections.emptyList();
    }
}
```

### controller/WalletController.java - The API Layer

This class exposes our service's functionality to the web.

- `@PathVariable String userId`: This captures the user ID from the URL. For Quidax's API, the string `me` is used to refer to the user who owns the API key being used for authentication.

#### Code:

```java
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
```

---

Your Quidax chats aren’t used to improve our models. Gemini can make mistakes, so double-check it. Your privacy & Gemini.