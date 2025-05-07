package ua.edu.lnu.card.exception.status;

public interface HttpStatus {
    int value();

    String getReasonPhrase();

    Series series();

    static HttpStatus valueOf(int code) {
        HttpStatus status = resolve(code);
        if (status == null) {
            throw new IllegalArgumentException("Unknown HTTP status code: " + code);
        }
        return status;
    }

    static HttpStatus resolve(int code) {
        HttpStatus httpStatus = Series.valueOf(code);
        if (httpStatus == null) {
            throw new IllegalArgumentException("Unknown HTTP status code: " + code);
        }
        return httpStatus;
    }
}
