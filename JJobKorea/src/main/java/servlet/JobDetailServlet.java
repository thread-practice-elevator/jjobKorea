package servlet;

import model.JobPosting;
import service.JobService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/jobDetail")
public class JobDetailServlet extends HttpServlet {
    private JobService jobService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        jobService = new JobService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idStr = request.getParameter("id");
        
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "채용공고 ID가 필요합니다.");
            return;
        }
        
        try {
            int jobId = Integer.parseInt(idStr);
            JobPosting jobPosting = jobService.getJobById(jobId);
            
            if (jobPosting == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "채용공고를 찾을 수 없습니다.");
                return;
            }
            
            request.setAttribute("jobPosting", jobPosting);
            request.getRequestDispatcher("/jobDetail.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 채용공고 ID입니다.");
        }
    }
}