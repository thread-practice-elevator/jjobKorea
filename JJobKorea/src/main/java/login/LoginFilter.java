package login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class LoginFilter implements Filter {

    // 로그인 없이 접근 허용할 경로
    private static final String[] allowList = {"/", "/login", "/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // contextPath 포함 전체 URI
        String contextPath = httpRequest.getContextPath();
        String requestURI = httpRequest.getRequestURI().substring(contextPath.length());

        try {
            if (isLoginCheckPath(requestURI)) {

                HttpSession session = httpRequest.getSession(false);

                if (session == null || session.getAttribute("userId") == null) {
                    // contextPath 포함해서 로그인 페이지로 redirect
                	httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?redirectURL=" + requestURI);

                    return;
                }
                
            }
            
            

            chain.doFilter(request, response);

        } catch (Exception e) {
            throw e;
        } finally {
            System.out.println("인증 체크 필터 종료: " + requestURI);
        }
    }

    // 허용 경로인지 확인
    private boolean isLoginCheckPath(String requestURI) {
        for (String allowedPath : allowList) {
            if (allowedPath.equals("/")) {
                if (requestURI.equals("/")) return false;
            } else {
                if (requestURI.startsWith(allowedPath)) return false;
            }
        }
        return true;
    }

}
