package com.example.biskit.errors;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PetNotFoundException.class)
    public String handlePetNotFound(PetNotFoundException ex, Model model) {

        model.addAttribute("mensaje", ex.getMessage());
        return "error";

    }

    @ExceptionHandler(ClientNotFoundException.class)
    public String handleClientNotFound(ClientNotFoundException ex, Model model) {

        model.addAttribute("mensaje", ex.getMessage());
        return "error";

    }

}