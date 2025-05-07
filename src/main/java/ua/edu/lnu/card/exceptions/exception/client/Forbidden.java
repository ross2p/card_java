package ua.edu.lnu.card.exceptions.exception.client;

import ua.edu.lnu.card.exceptions.exception.ClientError;
import ua.edu.lnu.card.exceptions.status.ClientErrorStatus;

public class Forbidden extends ClientError {
    public Forbidden(String message) {
        super(ClientErrorStatus.FORBIDDEN, message);
    }
}
