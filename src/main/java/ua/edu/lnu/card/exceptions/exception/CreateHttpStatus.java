package ua.edu.lnu.card.exceptions.exception;

import ua.edu.lnu.card.exceptions.exception.server.InternalServerError;
import ua.edu.lnu.card.exceptions.status.HttpStatus;

public interface CreateHttpStatus {
    CreateHttpStatus createHttpStatus(Integer code, String message) throws InternalServerError;

    CreateHttpStatus createHttpStatus(HttpStatus status, String message);
}
