package ua.edu.lnu.card.exception.exception;


import ua.edu.lnu.card.exception.exception.server.InternalServerError;
import ua.edu.lnu.card.exception.status.SuccessfulStatus;

public class Successful extends HttpResponse{
    public Successful(SuccessfulStatus status, String message) {
        super(status, message);
    }

    public Successful(Integer status, String message) throws InternalServerError {
        super(status, message);
    }
}
