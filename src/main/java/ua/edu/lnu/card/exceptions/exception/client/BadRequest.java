package ua.edu.lnu.card.exceptions.exception.client;

import ua.edu.lnu.card.exceptions.exception.ClientError;
import ua.edu.lnu.card.exceptions.status.ClientErrorStatus;

public class BadRequest extends ClientError {
    public BadRequest(String message) {
        super(ClientErrorStatus.BAD_REQUEST, message);
    }
}
