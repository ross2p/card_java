package ua.edu.lnu.card.exception.exception.client;

import ua.edu.lnu.card.exception.exception.ClientError;
import ua.edu.lnu.card.exception.status.ClientErrorStatus;

public class Unauthorized extends ClientError {
    public Unauthorized(String message) {
        super(ClientErrorStatus.UNAUTHORIZED, message);
    }
}
