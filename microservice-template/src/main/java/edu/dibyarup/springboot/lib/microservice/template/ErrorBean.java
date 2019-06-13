package edu.dibyarup.springboot.lib.microservice.template;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorBean {
    private boolean success;
    private String errorCode;
    private String errorMessage;
}
