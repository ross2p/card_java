package ua.edu.lnu.card.exception.exception.server;

import ua.edu.lnu.card.exception.exception.ServerError;
import ua.edu.lnu.card.exception.status.ServerErrorStatus;

public class InternalServerError extends ServerError {
    public InternalServerError(String message) {
        super(ServerErrorStatus.INTERNAL_SERVER_ERROR, message);
    }
}
