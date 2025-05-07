package ua.edu.lnu.card.exception.exception.server;

import ua.edu.lnu.card.exception.exception.ServerError;
import ua.edu.lnu.card.exception.status.ServerErrorStatus;

public class NotImplemented extends ServerError {
    public NotImplemented(String message) {
        super(ServerErrorStatus.NOT_IMPLEMENTED, message);
    }
}
