package ua.edu.lnu.card.exceptions.status;

public enum RedirectionStatus implements HttpStatus {
    MULTIPLE_CHOICES(300, "Multiple Choices"),
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    MOVED_TEMPORARILY(302, "Moved Temporarily"),
    SEE_OTHER(303, "See Other"),
    NOT_MODIFIED(304, "Not Modified"),
    USE_PROXY(305, "Use Proxy"),
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    PERMANENT_REDIRECT(308, "Permanent Redirect");

    RedirectionStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    int value;
    String reasonPhrase;

    @Override
    public int value() {
        return 0;
    }

    @Override
    public String getReasonPhrase() {
        return null;
    }

    public static RedirectionStatus valueOf(int code) {
        RedirectionStatus status = resolve(code);
        if (status == null) {
            throw new IllegalArgumentException("Unknown HTTP status code: " + code);
        }
        return status;
    }

    public static RedirectionStatus resolve(int code) {
        for (RedirectionStatus status : values()) {
            if (status.value() == code) {
                return status;
            }
        }
        return null;
    }

    @Override
    public Series series() {
        return Series.REDIRECTION;
    }
}
