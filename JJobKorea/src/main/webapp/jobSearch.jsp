<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>채용공고 검색 - JJob Korea</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>JJob Korea 채용공고 검색</h1>
        </header>
        
        <main>
            <!-- 검색 폼 -->
            <section class="search-section">
                <form action="jobs" method="get" class="search-form">
                    <div class="search-row">
                        <div class="search-field">
                            <label for="keyword">키워드 검색</label>
                            <input type="text" id="keyword" name="keyword" 
                                   placeholder="회사명을 입력하세요" 
                                   value="${keyword != null ? keyword : ''}">
                        </div>
                        
                        <div class="search-field">
                            <label for="jobCategoryId">직무 카테고리</label>
                            <select id="jobCategoryId" name="jobCategoryId">
                                <option value="">전체 직무</option>
                                <c:forEach var="category" items="${jobCategories}">
                                    <option value="${category.id}" 
                                            ${selectedJobCategoryId != null && selectedJobCategoryId == category.id ? 'selected' : ''}>
                                        ${category.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="search-field">
                            <label for="locationId">지역</label>
                            <select id="locationId" name="locationId">
                                <option value="">전체 지역</option>
                                <c:forEach var="location" items="${locations}">
                                    <option value="${location.id}" 
                                            ${selectedLocationId != null && selectedLocationId == location.id ? 'selected' : ''}>
                                        ${location.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="search-field">
                            <button type="submit" class="search-btn">검색</button>
                        </div>
                    </div>
                </form>
            </section>
            
            <!-- 검색 결과 -->
            <section class="results-section">
                <div class="results-header">
                    <h2>검색 결과</h2>
                    <span class="results-count">총 ${jobPostings.size()}개의 채용공고</span>
                </div>
                
                <c:choose>
                    <c:when test="${empty jobPostings}">
                        <div class="no-results">
                            <p>검색 조건에 맞는 채용공고가 없습니다.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="job-list">
                            <c:forEach var="job" items="${jobPostings}">
                                <div class="job-card">
                                    <div class="job-content-wrapper" style="display: flex; gap: 20px; align-items: flex-start;">
                                        <!-- 이미지 썸네일 -->
                                        <c:if test="${job.contentImage != null}">
                                            <div class="job-thumbnail" style="flex-shrink: 0;">
                                                <img src="image?jobId=${job.id}" alt="채용공고 썸네일"
                                                     style="width: 80px; height: 80px; object-fit: cover; border-radius: 8px; border: 2px solid #eee;"
                                                     onerror="this.style.display='none';">
                                            </div>
                                        </c:if>
                                        
                                        <!-- 채용공고 정보 -->
                                        <div class="job-info-wrapper" style="flex: 1;">
                                            <div class="job-header">
                                                <h3 class="company-name">${job.companyName}</h3>
                                                <span class="job-category">${job.jobCategoryName}</span>
                                            </div>
                                            
                                            <div class="job-info">
                                                <div class="job-detail">
                                                    <span class="label">지역:</span>
                                                    <span class="value">${job.locationName}</span>
                                                </div>
                                                
                                                <div class="job-detail">
                                                    <span class="label">학력:</span>
                                                    <span class="value">${job.education}</span>
                                                </div>
                                                
                                                <div class="job-detail">
                                                    <span class="label">경력:</span>
                                                    <span class="value">${job.career}</span>
                                                </div>
                                                
                                                <c:if test="${job.salary != null}">
                                                    <div class="job-detail">
                                                        <span class="label">연봉:</span>
                                                        <span class="value salary">
                                                            <fmt:formatNumber value="${job.salary}" pattern="#,###"/>만원
                                                        </span>
                                                    </div>
                                                </c:if>
                                            </div>
                                            
                                            <div class="job-dates">
                                                <span class="post-date">게시일: ${job.postDate}</span>
                                                <span class="end-date">마감일: ${job.endDate}</span>
                                            </div>
                                            
                                            <div class="job-actions">
                                                <button onclick="viewJobDetail(${job.id})" class="detail-btn">
                                                    상세보기
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </section>
        </main>
    </div>
    
    <script src="js/jobSearch.js"></script>
</body>
</html>