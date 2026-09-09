package com.ecoevent.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("title", "Not Found");
        return "error/404";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model, HttpServletRequest request) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("title", "Error");
        model.addAttribute("path", request.getRequestURI());
        return "error/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("error", "An unexpected error occurred: " + ex.getMessage());
        model.addAttribute("title", "Server Error");
        return "error/error";
    }
}
