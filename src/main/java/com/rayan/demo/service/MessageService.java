package com.rayan.demo.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MessageService {

    @Value("${success.code}")
    private int SUCCESS_CODE;

    @Value("${success.message}")
    private String SUCCESS_MESSAGE;

    @Value("${failed.code}")
    private int FAILED_CODE;

    @Value("${failed.message}")
    private String FAILED_MESSAGE;

    @Value("${input-validation.code}")
    private int INPUT_VALIDATION_CODE;

    @Value("${input-validation.message}")
    private String INPUT_VALIDATION_MESSAGE;

    @Value("${internal-server-error.code}")
    private int INTERNAL_SERVER_ERROR_CODE;

    @Value("${internal-server-error.message}")
    private String INTERNAL_SERVER_ERROR_MESSAGE;

    @Value("${empty-body.code}")
    private int EMPTY_BODY_CODE;

    @Value("${empty-body.message}")
    private String EMPTY_BODY_MESSAGE;

    @Value("${too-many-requests.code}")
    private int TOO_MANY_REQUESTS_CODE;

    @Value("${too-many-requests.message}")
    private String TOO_MANY_REQUESTS_MESSAGE;
}
