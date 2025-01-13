package ua.edu.lnu.card.exception.exception.client;

import ua.edu.lnu.card.exception.exception.ClientError;
import ua.edu.lnu.card.exception.status.ClientErrorStatus;

public class Forbidden extends ClientError {
    public Forbidden(String message) {
        super(ClientErrorStatus.FORBIDDEN, message);
    }
}
