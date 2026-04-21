package com.example.biskit.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final String mensaje;
    private final String detalle;
    private final int status;
}