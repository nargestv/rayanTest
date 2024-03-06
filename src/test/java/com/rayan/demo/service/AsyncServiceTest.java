package com.rayan.demo.service;

import com.rayan.demo.DemoApplication;
import com.rayan.demo.config.security.LoggedInUser;
import com.rayan.demo.model.domain.User;
import com.rayan.demo.model.dto.user.AddUserReqDto;
import com.rayan.demo.model.dto.user.LoginReqDto;
import com.rayan.demo.utils.EncryptDecryptUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
//@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class AsyncServiceTest {

    @Autowired
    private AsyncService asyncActionService;
    private LoggedInUser loggedInUser;
    private User userRegisterEntity;


    @Before
    public void before() {
        loggedInUser.setUserName("test_user");

        userRegisterEntity = new User();
        userRegisterEntity.setUsername(loggedInUser.getUserName());
        userRegisterEntity.setEnabled(true);
    }

    @Test
    public void testPerformAsyncAction() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        // Trigger the asynchronous action
        asyncActionService.performAsyncAction(loggedInUser, latch);

        // Wait for the asynchronous action to complete (up to 10 seconds)
        assertTrue(latch.await(1000, TimeUnit.SECONDS));
    }
}