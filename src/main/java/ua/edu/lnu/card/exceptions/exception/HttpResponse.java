package ua.edu.lnu.card.exceptions.exception;

import lombok.Data;
import lombok.ToString;
import ua.edu.lnu.card.exceptions.exception.server.InternalServerError;
import ua.edu.lnu.card.exceptions.status.*;

@ToString
@Data
public class HttpResponse extends RuntimeException {
    private final Integer status;
    private final String message;
    private final String name;

    public HttpResponse(Integer status, String message, String name) {
        this.status = status;
        this.message = message;
        this.name = name;
    }

    public HttpResponse(String message) {
        this(ServerErrorStatus.INTERNAL_SERVER_ERROR, message);
    }

    public HttpResponse(HttpStatus status, String message) {
        this(status.value(), message, status.toString());
    }

    public HttpResponse(Integer status, String message) {
        this(HttpStatus.valueOf(status), message);
    }

    public static boolean isHttpResponse(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj instanceof HttpResponse;
    }

    public static HttpResponse createHttpStatus(Integer code, String message) throws InternalServerError {
        HttpStatus status = HttpStatus.valueOf(code);
        return createHttpStatus(status, message);
    }

    public static HttpResponse createHttpStatus(HttpStatus status, String message) {
        Series series = status.series();
        switch (series) {
            case INFORMATIONAL:
                return new Informational((InformationalStatus) status, message);
            case SUCCESSFUL:
                return new Successful((SuccessfulStatus) status, message);
            case REDIRECTION:
                return new Redirection((RedirectionStatus) status, message);
            case CLIENT_ERROR:
                return new ClientError((ClientErrorStatus) status, message);
            case SERVER_ERROR:
                return new ServerError((ServerErrorStatus) status, message);
            default:
                return new InternalServerError(message);
        }
    }

}
