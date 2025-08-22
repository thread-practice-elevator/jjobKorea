<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${jobPosting.companyName} - 채용공고 상세</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .detail-container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
        }
        
        .detail-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            border-radius: 10px;
            margin-bottom: 30px;
            text-align: center;
        }
        
        .detail-content {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .info-item {
            padding: 15px;
            background: #f8f9fa;
            border-radius: 8px;
            border-left: 4px solid #667eea;
        }
        
        .info-label {
            font-weight: 700;
            color: #555;
            margin-bottom: 5px;
        }
        
        .info-value {
            font-size: 16px;
            color: #333;
        }
        
        .salary-highlight {
            color: #e74c3c;
            font-weight: 700;
            font-size: 18px;
        }
        
        .close-btn {
            background: #6c757d;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }
        
        .close-btn:hover {
            background: #5a6268;
        }
    </style>
</head>
<body>
    <div class="detail-container">
        <div class="detail-header">
            <h1>${jobPosting.companyName}</h1>
            <p>채용공고 상세 정보</p>
        </div>
        
        <div class="detail-content">
            <div class="info-grid">
                <div class="info-item">
                    <div class="info-label">회사명</div>
                    <div class="info-value">${jobPosting.companyName}</div>
                </div>
                
                <div class="info-item">
                    <div class="info-label">직무 카테고리</div>
                    <div class="info-value">
                        ${jobPosting.jobCategoryName != null ? jobPosting.jobCategoryName : '미분류'}
                    </div>
                </div>
                
                <div class="info-item">
                    <div class="info-label">근무 지역</div>
                    <div class="info-value">${jobPosting.locationName}</div>
                </div>
                
                <div class="info-item">
                    <div class="info-label">학력 요구사항</div>
                    <div class="info-value">
                        ${jobPosting.education != null ? jobPosting.education : '학력무관'}
                    </div>
                </div>
                
                <div class="info-item">
                    <div class="info-label">경력 요구사항</div>
                    <div class="info-value">
                        ${jobPosting.career != null ? jobPosting.career : '경력무관'}
                    </div>
                </div>
                
                <c:if test="${jobPosting.salary != null}">
                    <div class="info-item">
                        <div class="info-label">연봉</div>
                        <div class="info-value salary-highlight">
                            <fmt:formatNumber value="${jobPosting.salary}" pattern="#,###"/>만원
                        </div>
                    </div>
                </c:if>
                
                <div class="info-item">
                    <div class="info-label">게시일</div>
                    <div class="info-value">${jobPosting.postDate}</div>
                </div>
                
                <div class="info-item">
                    <div class="info-label">마감일</div>
                    <div class="info-value">${jobPosting.endDate}</div>
                </div>
            </div>
            
            <c:if test="${jobPosting.contentImage != null}">
                <div class="content-image" style="margin-top: 30px;">
                    <h3 style="margin-bottom: 15px; color: #333;">채용공고 관련 자료</h3>
                    <div class="image-container" style="border: 2px solid #eee; padding: 20px; border-radius: 8px; text-align: center; background: #f9f9f9;">
                        
                        <!-- 프록시를 통한 이미지 표시 -->
                        <div class="image-display" style="margin-bottom: 15px;">
                            <img id="jobImage" src="image?jobId=${jobPosting.id}" alt="채용공고 이미지" 
                                 style="max-width: 100%; max-height: 400px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);"
                                 onload="hideErrorMessage()"
                                 onerror="showErrorMessage()">
                        </div>
                        
                        <!-- 에러 시 링크 표시 -->
                        <div id="image-error" style="display: none;">
                            <p style="color: #666; margin-bottom: 10px;">이미지를 직접 표시할 수 없습니다.</p>
                            <a href="${jobPosting.contentImage}" target="_blank" 
                               style="color: #667eea; text-decoration: none; font-weight: 600; padding: 8px 16px; border: 2px solid #667eea; border-radius: 5px; display: inline-block;">
                                📷 이미지 보기
                            </a>
                        </div>
                        
                        <!-- 원본 링크 -->
                        <div style="margin-top: 15px; padding-top: 15px; border-top: 1px solid #ddd;">
                            <small style="color: #888;">
                                원본 링크: 
                                <a href="${jobPosting.contentImage}" target="_blank" style="color: #667eea;">
                                    ${jobPosting.contentImage}
                                </a>
                            </small>
                        </div>
                    </div>
                </div>
                
                <script>
                    function showErrorMessage() {
                        document.getElementById('jobImage').style.display = 'none';
                        document.getElementById('image-error').style.display = 'block';
                    }
                    
                    function hideErrorMessage() {
                        document.getElementById('image-error').style.display = 'none';
                        document.getElementById('jobImage').style.display = 'block';
                    }
                </script>
            </c:if>
            
            <div style="text-align: center; margin-top: 30px;">
                <button onclick="window.close()" class="close-btn">창 닫기</button>
            </div>
        </div>
    </div>
</body>
</html>