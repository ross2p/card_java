package ua.edu.lnu.card.exceptions.exception;

import ua.edu.lnu.card.exceptions.exception.server.InternalServerError;
import ua.edu.lnu.card.exceptions.status.InformationalStatus;

public class Informational extends HttpResponse {

    public Informational(InformationalStatus status, String message) {
        super(status, message);
    }

    public Informational(Integer status, String message) throws InternalServerError {
        super(status, message);
    }
}
