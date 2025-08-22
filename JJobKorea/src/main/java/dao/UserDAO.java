package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

	public boolean usernameExists(Connection con, String username) throws SQLException {
		String sql = "SELECT 1 FROM User WHERE username = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, username);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		}
	}

	public int insert(Connection con, String username, String name, String passwordHash, int locationId,
			int jobCategoryId) throws SQLException {
		String sql = "INSERT INTO User(username, name, password, interestLocationId, interestJobCategoryId) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, username);
			ps.setString(2, name);
			ps.setString(3, passwordHash);
			ps.setInt(4, locationId);
			ps.setInt(5, jobCategoryId);
			return ps.executeUpdate();
		}
	}
}
