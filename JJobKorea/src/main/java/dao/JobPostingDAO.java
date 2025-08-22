package dao;

import model.JobPosting;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobPostingDAO {
    
    public List<JobPosting> searchJobPostings(String keyword, Integer jobCategoryId, Integer locationId) {
        List<JobPosting> jobPostings = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        
        sql.append("SELECT jp.id, jp.companyId, jp.locationId, jp.postDate, jp.endDate, ");
        sql.append("jp.salary, jp.education, jp.career, jp.contentImage, jp.jobCategoryId, ");
        sql.append("c.name AS companyName, l.name AS locationName, jc.name AS jobCategoryName ");
        sql.append("FROM jobposting jp ");
        sql.append("INNER JOIN company c ON jp.companyId = c.id ");
        sql.append("INNER JOIN location l ON jp.locationId = l.id ");
        sql.append("LEFT JOIN jobcategory jc ON jp.jobCategoryId = jc.id ");
        sql.append("WHERE 1=1 ");
        
        List<Object> parameters = new ArrayList<>();
        
        // 키워드 검색 (회사명에서 검색)
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND c.name LIKE ? ");
            parameters.add("%" + keyword.trim() + "%");
        }
        
        // 직무 카테고리 필터
        if (jobCategoryId != null && jobCategoryId > 0) {
            sql.append("AND jp.jobCategoryId = ? ");
            parameters.add(jobCategoryId);
        }
        
        // 지역 필터
        if (locationId != null && locationId > 0) {
            sql.append("AND jp.locationId = ? ");
            parameters.add(locationId);
        }
        
        // 만료되지 않은 공고만
        sql.append("AND jp.endDate >= CURDATE() ");
        sql.append("ORDER BY jp.postDate DESC");
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            // 파라미터 설정
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    JobPosting jobPosting = new JobPosting();
                    jobPosting.setId(rs.getInt("id"));
                    jobPosting.setCompanyId(rs.getInt("companyId"));
                    jobPosting.setLocationId(rs.getInt("locationId"));
                    jobPosting.setPostDate(rs.getDate("postDate"));
                    jobPosting.setEndDate(rs.getDate("endDate"));
                    jobPosting.setSalary(rs.getObject("salary", Integer.class));
                    jobPosting.setEducation(rs.getString("education"));
                    jobPosting.setCareer(rs.getString("career"));
                    jobPosting.setContentImage(rs.getString("contentImage"));
                    jobPosting.setJobCategoryId(rs.getObject("jobCategoryId", Integer.class));
                    
                    // JOIN된 정보
                    jobPosting.setCompanyName(rs.getString("companyName"));
                    jobPosting.setLocationName(rs.getString("locationName"));
                    jobPosting.setJobCategoryName(rs.getString("jobCategoryName"));
                    
                    jobPostings.add(jobPosting);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return jobPostings;
    }
    
    public List<JobPosting> getAllJobPostings() {
        return searchJobPostings(null, null, null);
    }
    
    public JobPosting getJobPostingById(int id) {
        String sql = "SELECT jp.id, jp.companyId, jp.locationId, jp.postDate, jp.endDate, " +
                    "jp.salary, jp.education, jp.career, jp.contentImage, jp.jobCategoryId, " +
                    "c.name AS companyName, l.name AS locationName, jc.name AS jobCategoryName " +
                    "FROM jobposting jp " +
                    "INNER JOIN company c ON jp.companyId = c.id " +
                    "INNER JOIN location l ON jp.locationId = l.id " +
                    "LEFT JOIN jobcategory jc ON jp.jobCategoryId = jc.id " +
                    "WHERE jp.id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    JobPosting jobPosting = new JobPosting();
                    jobPosting.setId(rs.getInt("id"));
                    jobPosting.setCompanyId(rs.getInt("companyId"));
                    jobPosting.setLocationId(rs.getInt("locationId"));
                    jobPosting.setPostDate(rs.getDate("postDate"));
                    jobPosting.setEndDate(rs.getDate("endDate"));
                    jobPosting.setSalary(rs.getObject("salary", Integer.class));
                    jobPosting.setEducation(rs.getString("education"));
                    jobPosting.setCareer(rs.getString("career"));
                    jobPosting.setContentImage(rs.getString("contentImage"));
                    jobPosting.setJobCategoryId(rs.getObject("jobCategoryId", Integer.class));
                    
                    // JOIN된 정보
                    jobPosting.setCompanyName(rs.getString("companyName"));
                    jobPosting.setLocationName(rs.getString("locationName"));
                    jobPosting.setJobCategoryName(rs.getString("jobCategoryName"));
                    
                    return jobPosting;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}