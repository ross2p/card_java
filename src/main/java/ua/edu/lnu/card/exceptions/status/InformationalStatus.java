package ua.edu.lnu.card.exceptions.status;

public enum InformationalStatus implements HttpStatus {
    CONTINUE(100, "Continue"),
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),
    PROCESSING(102, "Processing"),
    EARLY_HINTS(103, "EarlyHints"),
    CHECKPOINT(103, "Checkpoint");

    InformationalStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    final int value;
    final String reasonPhrase;

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public static InformationalStatus resolve(int code) {
        for (InformationalStatus status : values()) {
            if (status.value() == code) {
                return status;
            }
        }
        return null;
    }

    public static InformationalStatus valueOf(int code) {
        InformationalStatus status = resolve(code);
        if (status == null) {
            throw new IllegalArgumentException("Unknown HTTP status code: " + code);
        }
        return status;
    }

    @Override
    public Series series() {
        return Series.INFORMATIONAL;
    }
}
