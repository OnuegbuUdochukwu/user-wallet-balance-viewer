# User Wallet Balance Viewer API

A Spring Boot REST API that demonstrates how to make authenticated requests to the Quidax API to fetch private user data. This service securely uses an API key to retrieve all wallet balances for a specific user.

## Features

- Makes authenticated GET requests to a protected API endpoint.
- Securely injects an API secret key from `application.properties` using `@Value`.
- Builds custom HTTP requests with an Authorization header.
- Models and parses a wrapped JSON response containing a list of user wallets.

## Technologies Used

- Java 17
- Spring Boot 3
- Spring MVC
- Maven
- Lombok

## Setup and Configuration

This application requires a personal Quidax API secret key to function.

### Add Your Key

Open the `src/main/resources/application.properties` file and add your secret key:

```properties
quidax.api.secret-key=YOUR_SECRET_KEY_GOES_HERE
```

### Secure Your Key

To prevent your key from being uploaded to GitHub, open the `.gitignore` file in the project's root directory and add the following line:

```
application.properties
```

## API Endpoint

| Method | Endpoint                        | Description                                           |
|--------|---------------------------------|-------------------------------------------------------|
| GET    | `/api/v1/users/{userId}/wallets` | Get all wallet balances for the authenticated user.  |

## Export to Sheets

### Example Usage:

To use this endpoint, replace `{userId}` with the string `me`. The Quidax API understands `me` as the owner of the API key being used.

```
http://localhost:8080/api/v1/users/me/wallets
```

## Getting Started

To get a local copy up and running, follow these steps.

### Prerequisites

- JDK (Java Development Kit) 17 or later
- Maven
- A valid Quidax API Secret Key

### Installation & Running the App

Clone the repository:

```bash
git clone https://github.com/your-username/user-wallet-balance-viewer.git
```

Navigate to the project directory:

```bash
cd user-wallet-balance-viewer
```

Configure your secret key as described in the "Setup and Configuration" section.

Run the application using the Maven wrapper:

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will start on `http://localhost:8080`.