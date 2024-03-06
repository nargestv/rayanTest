package com.rayan.demo.service;

// AsyncService.java
import com.rayan.demo.config.security.LoggedInUser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class AsyncService {

    public static int staticSum;


    @Async
    public void performAsyncAction(LoggedInUser loggedInUser, CountDownLatch latch) throws InterruptedException {
        // Simulating a 10-second action
        Thread.sleep(10000);
        System.out.println("Async action completed for user: " + loggedInUser.getUserName());

        // Release the latch to signal the completion of the asynchronous action
        latch.countDown();
    }

}
