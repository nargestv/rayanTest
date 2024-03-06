package com.rayan.demo.service;

import com.rayan.demo.DemoApplication;
import com.rayan.demo.model.domain.User;
import com.rayan.demo.model.dto.user.AddUserReqDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
//@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testAddUser() {

        AddUserReqDto newUser = new AddUserReqDto();
        newUser.setUsername("test_user");
        newUser.setEmail("test.user@example.com");
        List<User> allUserRegisters = userService.addUser(newUser);

        assertEquals(allUserRegisters.size(), 1);
        assertDoesNotThrow(() -> userService.addUser(newUser));
    }

    //or maybe another test
    @Test
    public void testLoginEndpoint() {
        String baseUrl = "http://localhost:" + 8081;
        String loginUrl = baseUrl + "/api/user/login";

        // Set up request headers and body
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String requestBody = "{\"username\": \"yourUsername\", \"password\": \"yourPassword\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make a POST request to the login endpoint
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                loginUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Verify the response
        assertEquals(200, responseEntity.getStatusCodeValue());

    }

    @Test
    public void testConcurrentAction() {
        String token = obtainAuthToken("user1", "password"); // Replace with your authentication logic

        // Simulate concurrent requests
        Runnable concurrentRequest = () -> {
            RestAssured.given()
                    .port(8081)
                    .header("Authorization", "Bearer " + token)
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/api/rayan/user/action1")
                    .then()
                    .statusCode(200);
        };

        // Start multiple threads for concurrent requests
        int numThreads = 5;
        for (int i = 0; i < numThreads; i++) {
            new Thread(concurrentRequest).start();
        }
    }

    private String obtainAuthToken(String username, String password) {
        // Implement your logic to obtain an authentication token
        // This can be done using RestAssured, Spring's TestRestTemplate, or any other method
        // For simplicity, you may use a test-only endpoint for token retrieval during testing
        // and replace this method with your actual implementation
        return RestAssured.given()
                .port(8081)
                .contentType(ContentType.JSON)
                .body("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}")
                .when()
                .post("/api/user/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }
}