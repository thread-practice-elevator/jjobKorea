package servlet;

import service.JobService;
import model.JobPosting;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet("/image")
public class ImageProxyServlet extends HttpServlet {
    private JobService jobService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        jobService = new JobService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String jobIdStr = request.getParameter("jobId");
        
        if (jobIdStr == null || jobIdStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Job ID is required");
            return;
        }
        
        try {
            int jobId = Integer.parseInt(jobIdStr);
            JobPosting jobPosting = jobService.getJobById(jobId);
            
            if (jobPosting == null || jobPosting.getContentImage() == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
                return;
            }
            
            String imageUrl = jobPosting.getContentImage();
            
            // ibb.co 링크를 직접 이미지 URL로 변환
            if (imageUrl.contains("ibb.co")) {
                // ibb.co/xxxxx 형태를 i.ibb.co/xxxxx.jpg로 변환 시도
                String imageId = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                String directImageUrl = "https://i.ibb.co/" + imageId + ".jpg";
                
                // 여러 확장자 시도
                String[] extensions = {".jpg", ".png", ".jpeg", ".gif"};
                boolean imageFound = false;
                
                for (String ext : extensions) {
                    String testUrl = "https://i.ibb.co/" + imageId + ext;
                    if (proxyImage(testUrl, response)) {
                        imageFound = true;
                        break;
                    }
                }
                
                if (!imageFound) {
                    // 원본 URL로 리다이렉트
                    response.sendRedirect(imageUrl);
                }
            } else {
                // 일반 이미지 URL인 경우 직접 프록시
                proxyImage(imageUrl, response);
            }
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Job ID");
        }
    }
    
    private boolean proxyImage(String imageUrl, HttpServletResponse response) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);
            
            // User-Agent 설정으로 차단 우회
            connection.setRequestProperty("User-Agent", 
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String contentType = connection.getContentType();
                if (contentType != null && contentType.startsWith("image/")) {
                    response.setContentType(contentType);
                    
                    try (InputStream inputStream = connection.getInputStream();
                         OutputStream outputStream = response.getOutputStream()) {
                        
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        outputStream.flush();
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Image proxy error for URL: " + imageUrl + " - " + e.getMessage());
        }
        return false;
    }
}