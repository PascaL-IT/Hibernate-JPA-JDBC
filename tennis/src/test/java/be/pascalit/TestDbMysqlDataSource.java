package be.pascalit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.MysqlDataSource;

import be.pascalit.util.DbUtils;

public class TestDbMysqlDataSource {

	public static void main(String... args) {
		
		Connection conn = null;
		
		try {
			// MySQL DataSource
			MysqlDataSource mds = new MysqlDataSource();
		
			mds.setServerName("localhost"); // configuration -> method n°2 via parameters (not URL) 
			mds.setPort(3306);
			mds.setDatabaseName("tennis");
			mds.setUseSSL(false);
			mds.setAllowPublicKeyRetrieval(true);
			mds.setServerTimezone("Europe/Paris");
			//mds.setUser("Pascal");
			//mds.setPassword("...");

			System.out.println("Successful connection with MysqlDataSource !");
			
			// conn = mds.getConnection(); 
			conn = mds.getConnection("Pascal","..."); // with user & password
			
			Statement statement = conn.createStatement();
			boolean res = statement.execute("select version(), current_date, now();");
			if (res) {
				int type = statement.getResultSetType();
				System.out.println("ResultSet: type is " + type);
				ResultSet rs = statement.getResultSet();
				if (rs.next()) {
					ResultSetMetaData rsm = rs.getMetaData();
					for (int j = 1; j <= rsm.getColumnCount(); ++j) {
						System.out.println("ResultSet: " + DbUtils.getMetadataLabel(rsm, j) + " is " + rs.getString(j));
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
