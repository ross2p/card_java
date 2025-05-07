package ua.edu.lnu.card.exceptions.exception.server;

import ua.edu.lnu.card.exceptions.exception.ServerError;
import ua.edu.lnu.card.exceptions.status.ServerErrorStatus;

public class NotImplemented extends ServerError {
    public NotImplemented(String message) {
        super(ServerErrorStatus.NOT_IMPLEMENTED, message);
    }
}
