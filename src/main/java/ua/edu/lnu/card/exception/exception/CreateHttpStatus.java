package ua.edu.lnu.card.exception.exception;

import ua.edu.lnu.card.exception.exception.server.InternalServerError;
import ua.edu.lnu.card.exception.status.HttpStatus;

public interface CreateHttpStatus {
    CreateHttpStatus createHttpStatus(Integer code, String message) throws InternalServerError;
    CreateHttpStatus createHttpStatus(HttpStatus status, String message);
}
