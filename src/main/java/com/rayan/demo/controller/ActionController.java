package com.rayan.demo.controller;

import com.rayan.demo.config.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/action")
public class ActionController {

   // private final AsyncService asyncService;
    private final JwtTokenUtil jwtTokenUtil;

//    @PreAuthorize("hasAuthority('user_action')") //it has a role for example
//    @PostMapping
//    public String performAction1() throws InterruptedException {
//
//        // Trigger Async Action (Action 2)
//        asyncService.performAsyncAction(jwtTokenUtil.getLoggedInUser());
//
//        return "Action 1 completed for user: " + jwtTokenUtil.getLoggedInUser();
//    }
}
