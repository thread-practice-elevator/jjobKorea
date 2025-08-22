package util;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class DBUtil {

	private static final HikariDataSource ds;
	static {
		HikariConfig cfg = new HikariConfig();
		cfg.setDriverClassName("com.mysql.cj.jdbc.Driver"); // 추가
		cfg.setJdbcUrl("jdbc:mysql://localhost:3306/jjobKorea?serverTimezone=Asia/Seoul&useUnicode=true&characterEncoding=UTF-8");

		cfg.setUsername(System.getenv().getOrDefault("DB_USER", "root"));
		cfg.setPassword(System.getenv().getOrDefault("DB_PASS", "1234"));
		cfg.setMaximumPoolSize(10);
		cfg.setMinimumIdle(2);
		cfg.setPoolName("AppPool");
		ds = new HikariDataSource(cfg);
	}

	private DBUtil() {
	}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}