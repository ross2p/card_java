package ua.edu.lnu.card.exceptions.exception;

import ua.edu.lnu.card.exceptions.exception.server.InternalServerError;
import ua.edu.lnu.card.exceptions.status.RedirectionStatus;

public class Redirection extends HttpResponse {

    public Redirection(String message) {
        super(message);
    }

    public Redirection(RedirectionStatus status, String message) {
        super(status, message);
    }

    public Redirection(Integer status, String message) throws InternalServerError {
        super(status, message);
    }
}
