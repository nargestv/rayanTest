package com.rayan.demo.controller;

import com.rayan.demo.config.security.JwtTokenUtil;
import com.rayan.demo.model.domain.User;
import com.rayan.demo.model.dto.GeneralResponse;
import com.rayan.demo.model.dto.user.AddUserReqDto;
import com.rayan.demo.service.MessageService;
import com.rayan.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/rayan/user")
public class UserController {

    private final UserService userService;
    private final MessageService messageService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/user/add")
    public GeneralResponse<List<User>> addUser(@RequestBody AddUserReqDto addUserReqDto) {
        return new GeneralResponse<>(messageService.getSUCCESS_CODE(), messageService.getSUCCESS_MESSAGE(),
                userService.addUser(addUserReqDto));
    }
}
