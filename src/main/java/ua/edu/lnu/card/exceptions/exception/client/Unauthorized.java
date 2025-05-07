package ua.edu.lnu.card.exceptions.exception.client;

import ua.edu.lnu.card.exceptions.exception.ClientError;
import ua.edu.lnu.card.exceptions.status.ClientErrorStatus;

public class Unauthorized extends ClientError {
    public Unauthorized(String message) {
        super(ClientErrorStatus.UNAUTHORIZED, message);
    }
}
