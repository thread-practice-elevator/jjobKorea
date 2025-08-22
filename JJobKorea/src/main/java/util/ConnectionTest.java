package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionTest {

    public static void main(String[] args) {
        // try-with-resources 구문을 사용하여 Connection 객체가 자동으로 닫히도록 합니다.
        try (Connection conn = DBUtil.getConnection()) {
            
            // 1. 연결 성공 확인
            if (conn != null) {
                System.out.println("✅ 데이터베이스 연결 성공!");
                
                // 2. 간단한 쿼리로 데이터 조회 테스트
                String sql = "SELECT * FROM user LIMIT 1"; // user 테이블에서 데이터 1개만 가져오기
                
                try (PreparedStatement pstmt = conn.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {
                    
                    if (rs.next()) {
                        // 조회된 데이터의 username 컬럼 값을 출력
                        String username = rs.getString("username");
                        System.out.println("✅ 데이터 조회 성공: 첫 번째 사용자 이름 -> " + username);
                    } else {
                        System.out.println("🟡 데이터는 없지만 쿼리는 성공적으로 실행되었습니다.");
                    }
                }
                
            } else {
                System.out.println("❌ 데이터베이스 연결 실패: Connection 객체가 null입니다.");
            }

        } catch (SQLException e) {
            System.out.println("❌ 데이터베이스 연결 또는 쿼리 실패!");
            // 실패 시 상세한 오류 내용을 출력합니다.
            e.printStackTrace();
        }
    }
}