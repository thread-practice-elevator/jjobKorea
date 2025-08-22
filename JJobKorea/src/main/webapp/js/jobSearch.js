// 채용공고 상세보기 함수
function viewJobDetail(jobId) {
    // 새 창에서 상세보기 페이지 열기
    window.open('jobDetail?id=' + jobId, '_blank', 'width=800,height=600');
}

// 검색 폼 실시간 유효성 검사
document.addEventListener('DOMContentLoaded', function() {
    const searchForm = document.querySelector('.search-form');
    const keywordInput = document.getElementById('keyword');
    const jobCategorySelect = document.getElementById('jobCategoryId');
    const locationSelect = document.getElementById('locationId');
    
    // 엔터키로 검색
    keywordInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            searchForm.submit();
        }
    });
    
    // 셀렉트 박스 변경시 자동 검색 (선택사항)
    /*
    jobCategorySelect.addEventListener('change', function() {
        searchForm.submit();
    });
    
    locationSelect.addEventListener('change', function() {
        searchForm.submit();
    });
    */
    
    // 검색 초기화 함수
    window.resetSearch = function() {
        keywordInput.value = '';
        jobCategorySelect.value = '';
        locationSelect.value = '';
        searchForm.submit();
    };
    
    // 키워드 입력 시 placeholder 효과
    keywordInput.addEventListener('focus', function() {
        this.placeholder = '';
    });
    
    keywordInput.addEventListener('blur', function() {
        if (this.value === '') {
            this.placeholder = '회사명을 입력하세요';
        }
    });
});

// 채용공고 카드 호버 효과
document.addEventListener('DOMContentLoaded', function() {
    const jobCards = document.querySelectorAll('.job-card');
    
    jobCards.forEach(card => {
        card.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-5px)';
        });
        
        card.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
        });
    });
});

// 검색 결과 통계 업데이트
function updateResultsStats() {
    const jobCards = document.querySelectorAll('.job-card');
    const resultsCount = document.querySelector('.results-count');
    
    if (resultsCount) {
        resultsCount.textContent = `총 ${jobCards.length}개의 채용공고`;
    }
}

// 페이지 로드 완료 후 실행
window.addEventListener('load', function() {
    updateResultsStats();
    
    // 검색 폼 애니메이션
    const searchSection = document.querySelector('.search-section');
    if (searchSection) {
        searchSection.style.opacity = '0';
        searchSection.style.transform = 'translateY(-20px)';
        
        setTimeout(() => {
            searchSection.style.transition = 'all 0.5s ease';
            searchSection.style.opacity = '1';
            searchSection.style.transform = 'translateY(0)';
        }, 100);
    }
});