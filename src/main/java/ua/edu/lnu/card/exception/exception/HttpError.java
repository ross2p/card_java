package ua.edu.lnu.card.exception.exception;


import ua.edu.lnu.card.exception.status.ClientErrorStatus;
import ua.edu.lnu.card.exception.status.ServerErrorStatus;

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
