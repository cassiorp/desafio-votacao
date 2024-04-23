package com.softdesign.exception;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ApiExceptionSchema {
    private final String message;
    private final Integer status;
    private final String error;
    private final ZonedDateTime timestamp;
}
