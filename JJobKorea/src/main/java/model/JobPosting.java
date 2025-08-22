package model;

import java.sql.Date;

public class JobPosting {
    private int id;
    private int companyId;
    private int locationId;
    private Date postDate;
    private Date endDate;
    private Integer salary; // null 가능
    private String education;
    private String career;
    private String contentImage;
    private Integer jobCategoryId; // null 가능
    
    // JOIN 시 사용할 추가 필드들
    private String companyName;
    private String locationName;
    private String jobCategoryName;
    
    public JobPosting() {}
    
    public JobPosting(int id, int companyId, int locationId, Date postDate, Date endDate, 
                     Integer salary, String education, String career, String contentImage, Integer jobCategoryId) {
        this.id = id;
        this.companyId = companyId;
        this.locationId = locationId;
        this.postDate = postDate;
        this.endDate = endDate;
        this.salary = salary;
        this.education = education;
        this.career = career;
        this.contentImage = contentImage;
        this.jobCategoryId = jobCategoryId;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
    
    public int getLocationId() {
        return locationId;
    }
    
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
    
    public Date getPostDate() {
        return postDate;
    }
    
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public Integer getSalary() {
        return salary;
    }
    
    public void setSalary(Integer salary) {
        this.salary = salary;
    }
    
    public String getEducation() {
        return education;
    }
    
    public void setEducation(String education) {
        this.education = education;
    }
    
    public String getCareer() {
        return career;
    }
    
    public void setCareer(String career) {
        this.career = career;
    }
    
    public String getContentImage() {
        return contentImage;
    }
    
    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }
    
    public Integer getJobCategoryId() {
        return jobCategoryId;
    }
    
    public void setJobCategoryId(Integer jobCategoryId) {
        this.jobCategoryId = jobCategoryId;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getLocationName() {
        return locationName;
    }
    
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    public String getJobCategoryName() {
        return jobCategoryName;
    }
    
    public void setJobCategoryName(String jobCategoryName) {
        this.jobCategoryName = jobCategoryName;
    }
    
    @Override
    public String toString() {
        return "JobPosting{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", locationId=" + locationId +
                ", postDate=" + postDate +
                ", endDate=" + endDate +
                ", salary=" + salary +
                ", education='" + education + '\'' +
                ", career='" + career + '\'' +
                ", contentImage='" + contentImage + '\'' +
                ", jobCategoryId=" + jobCategoryId +
                ", companyName='" + companyName + '\'' +
                ", locationName='" + locationName + '\'' +
                ", jobCategoryName='" + jobCategoryName + '\'' +
                '}';
    }
}