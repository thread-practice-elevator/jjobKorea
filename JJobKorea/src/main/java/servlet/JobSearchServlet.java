package servlet;

import model.JobCategory;
import model.JobPosting;
import model.Location;
import service.JobService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/jobs")
public class JobSearchServlet extends HttpServlet {
    private JobService jobService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        jobService = new JobService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 요청 파라미터 받기
        String keyword = request.getParameter("keyword");
        String jobCategoryIdStr = request.getParameter("jobCategoryId");
        String locationIdStr = request.getParameter("locationId");
        
        // 파라미터 변환
        Integer jobCategoryId = null;
        Integer locationId = null;
        
        if (jobCategoryIdStr != null && !jobCategoryIdStr.trim().isEmpty()) {
            try {
                jobCategoryId = Integer.parseInt(jobCategoryIdStr);
            } catch (NumberFormatException e) {
                // 잘못된 형식이면 무시
            }
        }
        
        if (locationIdStr != null && !locationIdStr.trim().isEmpty()) {
            try {
                locationId = Integer.parseInt(locationIdStr);
            } catch (NumberFormatException e) {
                // 잘못된 형식이면 무시
            }
        }
        
        // 검색 실행
        List<JobPosting> jobPostings = jobService.searchJobs(keyword, jobCategoryId, locationId);
        
        // 필터 옵션을 위한 데이터
        List<JobCategory> jobCategories = jobService.getAllJobCategories();
        List<Location> locations = jobService.getAllLocations();
        
        // request에 데이터 설정
        request.setAttribute("jobPostings", jobPostings);
        request.setAttribute("jobCategories", jobCategories);
        request.setAttribute("locations", locations);
        
        // 검색 조건 유지를 위해 파라미터도 전달
        request.setAttribute("keyword", keyword);
        request.setAttribute("selectedJobCategoryId", jobCategoryId);
        request.setAttribute("selectedLocationId", locationId);
        
        // JSP로 포워드
        request.getRequestDispatcher("/jobSearch.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}