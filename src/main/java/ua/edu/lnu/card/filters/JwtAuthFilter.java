package ua.edu.lnu.card.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.edu.lnu.card.exception.exception.HttpError;
import ua.edu.lnu.card.exception.exception.client.BadRequest;
import ua.edu.lnu.card.exception.exception.server.InternalServerError;
import ua.edu.lnu.card.utils.JwtUtils;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!hasAuthorizationBearer(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = getAccessToken(request);
            if (!jwtUtils.validateToken(token)) {
                throw new BadRequest("Invalid JWT token");
            }

            setAuthenticationContext(token, request);
            filterChain.doFilter(request, response);
        } catch (HttpError e) {
            log.error("HttpError: ", e);
            handleException(response, e);
        } catch (Exception e) {
            log.error("Unexpected error: ", e);
            handleException(response, new InternalServerError(e.getMessage()));
        }
    }

    private void handleException(HttpServletResponse response, HttpError e) throws IOException {
        response.setContentType("application/json");
        response.setStatus(e.getStatus());

        Map<String, Object> responseBody = Map.of(
                "status", e.getStatus(),
                "message", e.getMessage(),
                "name", e.getName());

        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(responseBody);

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header.substring(7);
    }

    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return !ObjectUtils.isEmpty(header) && header.startsWith("Bearer ");
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        System.out.println("setAuthenticationContext");

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }

        UserDetails userDetails = getUserDetails(token);
        System.out.println("userDetails: " + userDetails);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authRequest);
    }

    private UserDetails getUserDetails(String token) {
        String username = jwtUtils.getSubject(token);
        return userDetailsService.loadUserByUsername(username);
    }
}
