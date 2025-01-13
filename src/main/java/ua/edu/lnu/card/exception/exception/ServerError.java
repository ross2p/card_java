package ua.edu.lnu.card.exception.exception;

import lombok.ToString;
import ua.edu.lnu.card.exception.status.ServerErrorStatus;

public class ServerError extends HttpError {
    public ServerError(ServerErrorStatus status, String message) {
        super(status, message);
    }

    public ServerError(Integer status, String message) {
        super(status, message);
    }
}
