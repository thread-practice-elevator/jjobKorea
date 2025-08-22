package dao;

import model.JobCategory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobCategoryDAO {
    
    public List<JobCategory> getAllJobCategories() {
        List<JobCategory> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM jobcategory ORDER BY name";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                JobCategory category = new JobCategory();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categories;
    }
    
    public JobCategory getJobCategoryById(int id) {
        String sql = "SELECT id, name FROM jobcategory WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    JobCategory category = new JobCategory();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                    return category;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}