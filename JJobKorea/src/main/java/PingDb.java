import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBUtil;

@WebServlet("/_ping-db")
public class PingDb extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/plain; charset=UTF-8");
    try (Connection con = DBUtil.getConnection();
         PreparedStatement ps = con.prepareStatement("SELECT 1");
         ResultSet rs = ps.executeQuery()) {
      rs.next();
      resp.getWriter().println("OK " + rs.getInt(1));
    } catch (Exception e) {
      e.printStackTrace(resp.getWriter());
    }
  }
}