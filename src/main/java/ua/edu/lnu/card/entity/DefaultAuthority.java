package ua.edu.lnu.card.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@NoArgsConstructor
public class DefaultAuthority implements GrantedAuthority {
    private String authority;

    public DefaultAuthority(String authority) {
        this.authority = processAuthority(authority);
    }

    public static String processAuthority(String authority) {
        String processedAuthority = authority.toUpperCase();
        if (!processedAuthority.startsWith("ROLE_")) {
            processedAuthority = "ROLE_" + processedAuthority;
        }
        return processedAuthority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = processAuthority(authority);
    }
}
