package ua.edu.lnu.card.exception.exception;

import ua.edu.lnu.card.exception.exception.server.InternalServerError;
import ua.edu.lnu.card.exception.status.HttpStatus;
import ua.edu.lnu.card.exception.status.InformationalStatus;

public class Informational extends HttpResponse {

    public Informational(InformationalStatus status, String message) {
        super(status, message);
    }

    public Informational(Integer status, String message) throws InternalServerError {
        super(status, message);
    }
}
