package com.rayan.demo.controller;

import com.rayan.demo.model.dto.GeneralResponse;
import com.rayan.demo.model.dto.user.LoginReqDto;
import com.rayan.demo.model.dto.user.LoginResDto;
import com.rayan.demo.service.LoginService;
import com.rayan.demo.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/login")
public class LoginController {
    private final MessageService messageService;
    private final LoginService loginService;

    @PostMapping()
    public GeneralResponse<LoginResDto> loginByUsernameAndPassword(@Valid @RequestBody LoginReqDto loginReqDto) {
        LoginResDto loginResDto = loginService.userLogin(loginReqDto);
        return new GeneralResponse<>(messageService.getSUCCESS_CODE(), messageService.getSUCCESS_MESSAGE(), loginResDto);
    }
}
