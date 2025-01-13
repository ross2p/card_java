package ua.edu.lnu.card.exception.exception.client;

import ua.edu.lnu.card.exception.exception.ClientError;
import ua.edu.lnu.card.exception.status.ClientErrorStatus;

public class BadRequest extends ClientError {
    public BadRequest(String message) {
        super(ClientErrorStatus.BAD_REQUEST, message);
    }
}
