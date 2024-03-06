package com.rayan.demo.service;

import com.rayan.demo.config.security.UserDetailsImpl;
import com.rayan.demo.log.LogTemplate;
import com.rayan.demo.log.LogType;
import com.rayan.demo.log.SingleVariableBody;
import com.rayan.demo.model.domain.User;
import com.rayan.demo.model.dto.user.AddUserReqDto;
import com.rayan.demo.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
   // private final UserMapper userMapper;
    private final MessageService messageService;


    public void storeNewUser(User user) {
        userRepository.save(user);
    }
   // @Transactional(Transactional.TxType.REQUIRES_NEW)
    public List<User> addUser(AddUserReqDto addUserReqDto) {
        LogTemplate<Object> logTemplate = LogTemplate.builder().logType(LogType.REQUEST).requestId(UUID.randomUUID().toString())
                .method("add-user")
                .statusCode(messageService.getSUCCESS_CODE())
                .statusMessage(messageService.getSUCCESS_MESSAGE())
                .build();
        log.info(logTemplate.toString());

        User user = userRepository.findByEmail(addUserReqDto.getEmail())
                .orElse(new User());


        user = userRepository.save(user);

        List<User> userDtoList = getAllUserRegisters();

        logTemplate.setBody(userDtoList);
        logTemplate.setLogType(LogType.RESPONSE);
        log.info(logTemplate.toString());

        return userDtoList;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        LogTemplate<Object> logTemplate = LogTemplate.builder().logType(LogType.REQUEST).requestId(UUID.randomUUID().toString())
                .method("load-user-by-username").body(SingleVariableBody.of(username))
                .statusCode(messageService.getSUCCESS_CODE())
                .statusMessage(messageService.getSUCCESS_MESSAGE())
                .build();
        log.info(logTemplate.toString());

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            logTemplate.setLogType(LogType.ERROR);
            logTemplate.setBody(SingleVariableBody.of("bad credentials"));
            log.info(logTemplate.toString());
            throw new BadCredentialsException("bad credentials!");
        }
        logTemplate.setBody(user.get());
        logTemplate.setLogType(LogType.RESPONSE);
        log.info(logTemplate.toString());
        return new UserDetailsImpl(user.get());
    }

    public List<User> getAllUserRegisters() {
        LogTemplate<Object> logTemplate = LogTemplate.builder().logType(LogType.REQUEST).requestId(UUID.randomUUID().toString())
                .method("get-all-user-registers")
                .statusCode(messageService.getSUCCESS_CODE())
                .statusMessage(messageService.getSUCCESS_MESSAGE())
                .build();
        log.info(logTemplate.toString());

        List<User> userList = userRepository.findAll();

       // List<UserDto> userResDtoList = userList.stream().map(userMapper::toUserRegisterResDto).toList();

        logTemplate.setBody(SingleVariableBody.of(userList.size()));
        logTemplate.setLogType(LogType.RESPONSE);
        log.info(logTemplate.toString());

        return userList;
    }

}
