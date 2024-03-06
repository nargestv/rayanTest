package com.rayan.demo.service;

import com.rayan.demo.DemoApplication;
import com.rayan.demo.model.domain.User;
import com.rayan.demo.model.dto.user.LoginReqDto;
import com.rayan.demo.model.dto.user.LoginResDto;
import com.rayan.demo.model.repository.UserRepository;
import com.rayan.demo.utils.EncryptDecryptUtil;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class LoginServiceTest {
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRegisterRepository;
    private LoginReqDto loggedInUser;
    private User userRegisterEntity;


    @Before
    public void before() {

        loggedInUser = new LoginReqDto();
        loggedInUser.setEmail("test.user@example.com");
        loggedInUser.setUsername("test_user");
        loggedInUser.setPassword("123456");

        userRegisterEntity = new User();
        userRegisterEntity.setUsername(loggedInUser.getUsername());
        userRegisterEntity.setEmail(loggedInUser.getEmail());
        userRegisterEntity.setPassword(EncryptDecryptUtil.encrypt(loggedInUser.getPassword()));
        userRegisterEntity.setEnabled(true);

        userRegisterEntity = userRegisterRepository.save(userRegisterEntity);
    }

    @Test
    public void testLoginUser() {
        LoginResDto loginResDto = loginService.userLogin(loggedInUser);
        assertDoesNotThrow(() -> loginService.userLogin(loggedInUser));
    }
}