package bankingProject;
import java.sql.*;

public class OracleConnection {

	public static void main(String[] args) {
		
		Connection con = null;

        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Connect to Oracle DB
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "system";        // or your user
            String password = "prakash@0607"; // your actual password

            con = DriverManager.getConnection(url, username, password);

            if (con != null) {
                System.out.println("✅ Connected to Oracle Database!");
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

	}

}
