package service;

import dao.JobCategoryDAO;
import dao.JobPostingDAO;
import dao.LocationDAO;
import model.JobCategory;
import model.JobPosting;
import model.Location;

import java.util.List;

public class JobService {
    private JobPostingDAO jobPostingDAO;
    private JobCategoryDAO jobCategoryDAO;
    private LocationDAO locationDAO;
    
    public JobService() {
        this.jobPostingDAO = new JobPostingDAO();
        this.jobCategoryDAO = new JobCategoryDAO();
        this.locationDAO = new LocationDAO();
    }
    
    public List<JobPosting> searchJobs(String keyword, Integer jobCategoryId, Integer locationId) {
        return jobPostingDAO.searchJobPostings(keyword, jobCategoryId, locationId);
    }
    
    public List<JobPosting> getAllJobs() {
        return jobPostingDAO.getAllJobPostings();
    }
    
    public JobPosting getJobById(int id) {
        return jobPostingDAO.getJobPostingById(id);
    }
    
    public List<JobCategory> getAllJobCategories() {
        return jobCategoryDAO.getAllJobCategories();
    }
    
    public List<Location> getAllLocations() {
        return locationDAO.getAllLocations();
    }
    
    public JobCategory getJobCategoryById(int id) {
        return jobCategoryDAO.getJobCategoryById(id);
    }
    
    public Location getLocationById(int id) {
        return locationDAO.getLocationById(id);
    }
}