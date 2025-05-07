package ua.edu.lnu.card.exceptions.exception;

import ua.edu.lnu.card.exceptions.status.ClientErrorStatus;
import ua.edu.lnu.card.exceptions.status.ServerErrorStatus;

public class HttpError extends HttpResponse {
    public HttpError(ClientErrorStatus status, String message) {
        super(status, message);
    }

    public HttpError(ServerErrorStatus status, String message) {
        super(status, message);
    }

    public HttpError(Integer status, String message) {
        super(status, message);
    }
}
