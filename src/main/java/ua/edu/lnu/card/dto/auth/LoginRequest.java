package ua.edu.lnu.card.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.processing.Pattern;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginRequest(
//        @Pattern(message = "Email address must be a valid e-mail address", regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}")
        @JsonProperty(required = true)
        String email,

        @JsonProperty(required = true)
        String password
) implements Serializable {
}
