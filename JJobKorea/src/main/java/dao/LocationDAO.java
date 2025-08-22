package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Option;
import model.Location;
public class LocationDAO {
    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT id, name FROM location ORDER BY name";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Location location = new Location();
                location.setId(rs.getInt("id"));
                location.setName(rs.getString("name"));
                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }
    public Location getLocationById(int id) {
        String sql = "SELECT id, name FROM location WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Location location = new Location();
                    location.setId(rs.getInt("id"));
                    location.setName(rs.getString("name"));
                    return location;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Option> findAll(Connection con) throws Exception {
        List<Option> list = new ArrayList<>();
        String sql = "SELECT id, name FROM Location";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Option(rs.getInt("id"), rs.getString("name")));
            }
        }
        return list;
    }

    public boolean existsById(Connection con, int id) throws Exception {
        String sql = "SELECT 1 FROM Location WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}











