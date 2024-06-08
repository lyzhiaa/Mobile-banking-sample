package co.istad.mobilebanking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiException {
    //Dto validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<?, ?> HandlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = new ArrayList<>();

        e.getFieldErrors()
                .forEach(fieldError -> fieldErrors
                        .add(FieldError.builder()
                                .field(fieldError.getField())
                                .detail(fieldError.getDefaultMessage())
                                .build()));
        return Map.of("error", ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .reason(fieldErrors).build())
                ;
    }

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handlerResponseStatusException(ResponseStatusException e) {
        ErrorResponse<?> errorResponse = ErrorResponse.builder()
                .code(e.getStatusCode().value())
                .reason(e.getReason())
                .build();
        return ResponseEntity
                .status(e.getStatusCode())
                .body(Map.of("error", errorResponse));
    }
}
