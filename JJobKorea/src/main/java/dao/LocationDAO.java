package dao;

import model.Location;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
}