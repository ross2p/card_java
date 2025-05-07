package ua.edu.lnu.card.exceptions.exception;

import ua.edu.lnu.card.exceptions.status.ServerErrorStatus;

public class ServerError extends HttpError {
    public ServerError(ServerErrorStatus status, String message) {
        super(status, message);
    }

    public ServerError(Integer status, String message) {
        super(status, message);
    }
}
