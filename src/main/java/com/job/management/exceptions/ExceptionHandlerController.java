package com.job.management.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

    private final MessageSource messageSource;

    public ExceptionHandlerController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorMessageDTO> errors = createErrorMessages(e);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CompanyAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDTO> handleCompanyAlreadyExistsException(CompanyAlreadyExistsException e) {
        ErrorMessageDTO error = new ErrorMessageDTO(e.getMessage(), "email");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDTO> handleGenericException(Exception e) {
        ErrorMessageDTO error = new ErrorMessageDTO("An unexpected error occurred.", "error");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<ErrorMessageDTO> createErrorMessages(MethodArgumentNotValidException e) {
        List<ErrorMessageDTO> errorMessages = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(err -> {
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            ErrorMessageDTO error = new ErrorMessageDTO(message, err.getField());
            errorMessages.add(error);
        });

        return errorMessages;
    }
}
