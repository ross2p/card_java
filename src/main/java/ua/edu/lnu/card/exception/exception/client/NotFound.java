package ua.edu.lnu.card.exception.exception.client;

import ua.edu.lnu.card.exception.exception.ClientError;
import ua.edu.lnu.card.exception.status.ClientErrorStatus;

public class NotFound extends ClientError {
    public NotFound(String message) {
        super(ClientErrorStatus.NOT_FOUND, message);
    }
}
