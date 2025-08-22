package signup;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import dao.JobCategoryDAO;
import dao.LocationDAO;
import dao.UserDAO;
import dto.Option;
import util.DBUtil;

@WebServlet(urlPatterns = "/signup")
public class SignUp extends HttpServlet {
	private final LocationDAO locationDao = new LocationDAO();
	private final JobCategoryDAO jobDao = new JobCategoryDAO();
	private final UserDAO userDao = new UserDAO();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (Connection con = DBUtil.getConnection()) {
			List<Option> locations = locationDao.findAll(con);
			List<Option> jobs = jobDao.findAll(con);
			req.setAttribute("locations", locations);
			req.setAttribute("jobs", jobs);
			req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String username = req.getParameter("username");
		String name = req.getParameter("name"); // JSP에 name 필드가 없으면 추가
		String pw = req.getParameter("password");
		String confirm = req.getParameter("confirm-password");
		String interestLocation = req.getParameter("interestLocation"); // select name="region" → 값은 Location.id
		String interstJobCategory = req.getParameter("interstJobCategory"); // select name="job-role" → 값은
																			// JobCategory.id
		// 1) 기본 검증
		if (isBlank(username) || isBlank(pw) || isBlank(confirm) || isBlank(interestLocation)
				|| isBlank(interstJobCategory) || !pw.equals(confirm)) {
			req.setAttribute("error", "입력 값을 확인하세요.");
			doGet(req, resp); // 목록 다시 로드해서 폼 표시
			return;
		}
		int locationId, jobCategoryId;
		try {
			locationId = Integer.parseInt(interestLocation);
			jobCategoryId = Integer.parseInt(interstJobCategory);
		} catch (NumberFormatException nfe) {
			req.setAttribute("error", "선택값이 올바르지 않습니다.");
			doGet(req, resp);
			return;
		}
		try (Connection con = DBUtil.getConnection()) {
			// 2) 중복/존재 검증 (좋은 UX를 위해 FK 에러 나기 전에 확인)
			if (userDao.usernameExists(con, username)) {
				req.setAttribute("error", "이미 존재하는 아이디입니다.");
				doGet(req, resp);
				return;
			}
			if (!new LocationDAO().existsById(con, locationId)
					|| !new JobCategoryDAO().existsById(con, jobCategoryId)) {
				req.setAttribute("error", "허용되지 않은 선택값입니다.");
				doGet(req, resp);
				return;
			}
			// 3) 비밀번호 해시 후 INSERT
			String hash = BCrypt.hashpw(pw, BCrypt.gensalt(12));
			int rows = userDao.insert(con, username, (name == null ? "" : name), hash, locationId, jobCategoryId);
			if (rows == 1) {
				resp.sendRedirect(req.getContextPath() + "/login.html");
			} else {
				req.setAttribute("error", "회원가입에 실패했습니다.");
				doGet(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", "서버 오류가 발생했습니다.");
			doGet(req, resp);
		}
	}

	private static boolean isBlank(String s) {
		return s == null || s.isBlank(); // isBlank()는 유니코드 공백까지 인식
	}
}