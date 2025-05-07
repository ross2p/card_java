package ua.edu.lnu.card.exception.status;

import org.springframework.lang.Nullable;

public enum Series {
    INFORMATIONAL(1),
    SUCCESSFUL(2),
    REDIRECTION(3),
    CLIENT_ERROR(4),
    SERVER_ERROR(5);

    private final int value;

    Series(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static HttpStatus valueOf(int code) {
        Series series = resolve(code);
        if (series == null) {
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        }

        return switch (series) {
            case INFORMATIONAL -> InformationalStatus.resolve(code);
            case SUCCESSFUL -> SuccessfulStatus.resolve(code);
            case REDIRECTION -> RedirectionStatus.resolve(code);
            case CLIENT_ERROR -> ClientErrorStatus.resolve(code);
            case SERVER_ERROR -> ServerErrorStatus.resolve(code);
        };
    }

    @Nullable
    public static Series resolve(int statusCode) {
        int seriesCode = statusCode / 100;
        for (Series series : values()) {
            if (series.value == seriesCode) {
                return series;
            }
        }
        return null;
    }
}
