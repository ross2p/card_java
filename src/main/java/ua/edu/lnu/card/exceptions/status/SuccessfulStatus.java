package ua.edu.lnu.card.exceptions.status;

public enum SuccessfulStatus implements HttpStatus {

    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
    NO_CONTENT(204, "No Content"),
    RESET_CONTENT(205, "Reset Content"),
    PARTIAL_CONTENT(206, "Partial Content"),
    MULTI_STATUS(207, "Multi-Status"),
    ALREADY_REPORTED(208, "Already Reported"),
    IM_USED(226, "IM Used");

    SuccessfulStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    int value;
    String reasonPhrase;

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public static SuccessfulStatus valueOf(int code) {
        SuccessfulStatus status = resolve(code);
        if (status == null) {
            throw new IllegalArgumentException("Unknown HTTP status code: " + code);
        }
        return status;
    }

    public static SuccessfulStatus resolve(int code) {
        for (SuccessfulStatus status : values()) {
            if (status.value() == code) {
                return status;
            }
        }
        return null;
    }

    @Override
    public Series series() {
        return Series.SUCCESSFUL;
    }
}
