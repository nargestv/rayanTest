package com.rayan.demo.service;

import com.rayan.demo.config.security.JwtTokenUtil;
import com.rayan.demo.config.security.UserDetailsImpl;
import com.rayan.demo.log.LogTemplate;
import com.rayan.demo.log.LogType;
import com.rayan.demo.log.SingleVariableBody;
import com.rayan.demo.model.dto.user.LoginReqDto;
import com.rayan.demo.model.dto.user.LoginResDto;
import com.rayan.demo.model.repository.RefreshTokenRepository;
import com.rayan.demo.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final MessageService messageService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${reset.token-validity}")
    private Long resetTokenValidityTimeMinutes;

    @Transactional
    public LoginResDto userLogin(LoginReqDto reqDto) {
        LogTemplate<Object> logTemplate = LogTemplate.builder().logType(LogType.REQUEST).requestId(UUID.randomUUID().toString())
                .method("user-login").body(SingleVariableBody.of(reqDto.getUsername()))
                .statusCode(messageService.getSUCCESS_CODE())
                .statusMessage(messageService.getSUCCESS_MESSAGE())
                .build();
        logTemplate.setBody(reqDto.getPassword());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            reqDto.getUsername(),
                            reqDto.getPassword())
            );
        } catch (DisabledException e) {
            throw new DisabledException("User is disabled", e);
        }


        LoginResDto loginResDto = getLoginResDto(reqDto.getUsername());
        logTemplate.setLogType(LogType.RESPONSE);
        log.info(logTemplate.toString());
        return loginResDto;
    }

    private LoginResDto getLoginResDto(String username) {
        String uuid = UUID.randomUUID().toString();
        UserDetailsImpl userDetails = userService.loadUserByUsername(username);

        String generatedToken = jwtTokenUtil.generateToken(userDetails);
        Set<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        String refreshToken = jwtTokenUtil.generateRefreshToken(uuid);

        LoginResDto loginResDto = LoginResDto.builder()
                .authorities(authorities)
                .token(generatedToken)
                .refreshToken(refreshToken)
                .expireAt(jwtTokenUtil.getExpirationDateFromToken(generatedToken).toEpochMilli())
                .build();
        return loginResDto;
    }


}