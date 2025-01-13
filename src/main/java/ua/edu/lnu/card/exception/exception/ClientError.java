package ua.edu.lnu.card.exception.exception;

import ua.edu.lnu.card.exception.status.ClientErrorStatus;

public class ClientError extends HttpError {

    public ClientError(ClientErrorStatus status, String message) {
        super(status, message);
    }

    public ClientError(Integer status, String message) throws HttpError {
        super(status, message);
    }
}
