import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.DBUtil;

public class DBTest {

	public static void main(String[] args) throws SQLException {
		
		try (Connection con = DBUtil.getConnection();
			     PreparedStatement ps = con.prepareStatement(
			       "INSERT INTO User(username, name, password) VALUES (?,?,?)")) {
			  ps.setString(1, "test1");
			  ps.setString(2, "test2");
			  ps.setString(3, "a1234!");
			  ps.executeUpdate();
			}
	}

}
