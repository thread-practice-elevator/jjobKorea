package login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBUtil;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("username");
        String pw = request.getParameter("password");

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        System.out.println("DEBUG: 로그인 시도 - ID=" + id + ", PW=" + pw);

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT password FROM user WHERE username = ?")) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    String storedHash = rs.getString("password");

                    System.out.println("DEBUG: DB 패스워드 해시=" + storedHash);

                    if (BCrypt.checkpw(pw, storedHash)) {
                        System.out.println("DEBUG: 로그인 성공");

                        HttpSession session = request.getSession();
                        session.setAttribute("userId", id);
                        System.out.println("DEBUG: 세션 생성, userId=" + session.getAttribute("userId"));

                        response.sendRedirect(request.getContextPath() + "/jobListingPage.html");
                        System.out.println("DEBUG: jobListingPage.html로 리다이렉트");
                        return;
                    } else {
                        System.out.println("DEBUG: 비밀번호 불일치");
                        sendLoginError(request, response);
                    }
                } else {
                    System.out.println("DEBUG: ID 없음");
                    sendLoginError(request, response);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("loginError", "서버 오류가 발생했습니다: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void sendLoginError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DEBUG: 로그인 실패 - 오류 페이지로 포워드");
        request.setAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }
}
