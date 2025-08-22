package login;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

    // Allow list for paths that do not require login
    private static final String[] allowList = {"/", "/login", "/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get the requested URI
        String requestURI = httpRequest.getRequestURI();

        try {
            // Check if the current path requires a login check
            if (isLoginCheckPath(requestURI)) {
                
                // Get the session without creating a new one
                HttpSession session = httpRequest.getSession(false);

                // If session is null or userId is not set, redirect to login page
                if (session == null || session.getAttribute("userId") == null) {
                    // Redirect to login with the original URL as a parameter
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return; // Stop the filter chain
                }
            }
            
            // Continue the filter chain
            chain.doFilter(request, response);

        } catch (Exception e) {
            // Re-throw any exceptions
            throw e;
        } finally {
            // Optional logging
            System.out.println("인증 체크 필터 종료: " + requestURI);
        }
    }
    
    /**
     * Determines if the given URI requires a login check.
     * @param requestURI The URI to check.
     * @return true if login check is required, false otherwise.
     */
    private boolean isLoginCheckPath(String requestURI) {
        // Use a simple loop to check against the allow list
        for (String allowedPath : allowList) {
            // Check if the URI starts with an allowed path
            if (requestURI.startsWith(allowedPath)) {
                return false; // No login check required
            }
        }
        return true; // Login check is required
    }
}