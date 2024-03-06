package com.rayan.demo.controller;

import com.rayan.demo.config.security.JwtTokenUtil;
import com.rayan.demo.service.AsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CountDownLatch;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/action")
public class ActionController {

    private final AsyncService asyncService;
    private final JwtTokenUtil jwtTokenUtil;

    @PreAuthorize("hasAuthority('user_action')") //it has a permission for example
    @PostMapping("perform-async-action")
    public String performAction1() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        // Trigger Async Action
        asyncService.performAsyncAction(jwtTokenUtil.getLoggedInUser(), latch);

        return "Action 1 completed for user: " + jwtTokenUtil.getLoggedInUser();
    }
}
