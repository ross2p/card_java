package ua.edu.lnu.card.exceptions.exception.server;

import ua.edu.lnu.card.exceptions.exception.ServerError;
import ua.edu.lnu.card.exceptions.status.ServerErrorStatus;

public class InternalServerError extends ServerError {
    public InternalServerError(String message) {
        super(ServerErrorStatus.INTERNAL_SERVER_ERROR, message);
    }
}
