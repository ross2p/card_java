package ua.edu.lnu.card.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.edu.lnu.card.exception.exception.HttpResponse;
import ua.edu.lnu.card.exception.exception.client.NotFound;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpResponse.class)
    public ResponseEntity<Map<String, Object>> handleHttpResponseException(HttpResponse ex) {
        log.error("HttpError: ", ex);
        Map<String, Object> response = Map.of(
                "status", ex.getStatus(),
                "message", ex.getMessage(),
                "name", ex.getName()
        );
        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error("UsernameNotFoundException: ", ex);
        NotFound exception = new NotFound(ex.getMessage());
        return handleHttpResponseException(exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        HttpResponse exception = new HttpResponse(ex.getMessage());
        return handleHttpResponseException(exception);
    }


}