package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Option;
import model.JobCategory;
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
    public List<Option> findAll(Connection con) throws Exception {
        List<Option> list = new ArrayList<>();
        String sql = "SELECT id, name FROM JobCategory";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Option(rs.getInt("id"), rs.getString("name")));
            }
        }
        return list;
    }

    public boolean existsById(Connection con, int id) throws Exception {
        String sql = "SELECT 1 FROM JobCategory WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}