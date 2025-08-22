package dao;

import java.sql.*;
import java.util.*;
import dto.Option;

public class LocationDao {
	public List<Option> findAll(Connection con) throws SQLException {
		List<Option> list = new ArrayList<>();
		String sql = "SELECT id, name FROM Location ORDER BY name";
		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next())
				list.add(new Option(rs.getInt("id"), rs.getString("name")));
		}
		return list;
	}

	public boolean existsById(Connection con, int id) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement("SELECT 1 FROM Location WHERE id=?")) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		}
	}
}