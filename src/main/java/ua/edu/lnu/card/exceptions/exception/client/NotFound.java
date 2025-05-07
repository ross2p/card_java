package ua.edu.lnu.card.exceptions.exception.client;

import ua.edu.lnu.card.exceptions.exception.ClientError;
import ua.edu.lnu.card.exceptions.status.ClientErrorStatus;

public class NotFound extends ClientError {
    public NotFound(String message) {
        super(ClientErrorStatus.NOT_FOUND, message);
    }
}
