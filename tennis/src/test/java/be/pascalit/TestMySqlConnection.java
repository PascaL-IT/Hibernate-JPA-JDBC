package be.pascalit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestMySqlConnection {
	
	public static final String MYSQL_JDBC_TENNIS = "jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris";

    public static void main(String... args){
        Connection conn = null;
        try {
            //Seulement avant Java 7/JDBC 4 
            //Class.forName(DRIVER_CLASS_NAME);
            
            //MySQL driver MySQL Connector
            // conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris","COURSDB","COURSDB");
        	conn = DriverManager.getConnection(MYSQL_JDBC_TENNIS,"Pascal","Pascal");
        	
            //Oracle Driver officiel OJDBC Thin
            //conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:tennis","COURSDB","COURSDB");
            
            //Postgres Driver officiel
            //conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tennis","COURSDB","COURSDB");
           
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn!=null) {
                    conn.close();
                    System.out.println("success");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}
