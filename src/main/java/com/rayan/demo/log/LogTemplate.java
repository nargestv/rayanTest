package com.rayan.demo.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rayan.demo.config.ObjectMapperConfig;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogTemplate<T> {
    /**
     * service is used for creating index (must be in correct format for example don't use space char)
     */
    private final String service = "demo-service";
    private String method;
    private String requestId;
    private String loggedInUser;
    private LogType logType;
    private Integer statusCode;
    private String statusMessage;
    private T body;

    /**
     * @return string template of our log and its should be sync with logstash configuration
     */
    @Override
    public String toString() {
        try {
            return "service:" + service +
                    ", method:" + method +
                    ", requestId:" + requestId +
                    ", loggedInUser:" + loggedInUser +
                    ", type:" + logType +
                    ", statusCode:" + statusCode +
                    ", statusMessage:" + statusMessage +
                    ", body:" + ObjectMapperConfig.createObjectMapper().writeValueAsString(body);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
