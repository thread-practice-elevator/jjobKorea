package util;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class DBUtil {

	private static final HikariDataSource ds;
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			HikariConfig cfg = new HikariConfig();
			cfg.setJdbcUrl(System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/jjob_korea"));
			cfg.setUsername(System.getenv().getOrDefault("DB_USER", "root"));
			cfg.setPassword(System.getenv().getOrDefault("DB_PASS", "1234"));
			cfg.setMaximumPoolSize(10);
			cfg.setMinimumIdle(2);
			cfg.setPoolName("AppPool");
			ds = new HikariDataSource(cfg);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("MySQL JDBC Driver를 찾을 수 없습니다.", e);
		}
	}

	private DBUtil() {
	}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}