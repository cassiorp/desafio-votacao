package com.softdesign.exception;

import java.time.ZonedDateTime;
import lombok.Builder;

@Builder
public class ApiExceptionSchema {
    private final String message;
    private final Integer status;
    private final String error;
    private final ZonedDateTime timestamp;
}
