import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/NexusArena";
        String user = "root";
        String pass = "xffxn_909";
        try {
            System.out.println("Connecting to MySQL...");
            Connection c = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected!");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SHOW TABLES");
            System.out.println("Tables found:");
            int count = 0;
            while(rs.next()) {
                System.out.println("- " + rs.getString(1));
                count++;
            }
            if (count > 0) {
                ResultSet rs2 = s.executeQuery("SELECT COUNT(*) FROM PLAYER");
                if (rs2.next()) System.out.println("PLAYER count: " + rs2.getInt(1));
            } else {
                System.out.println("ZERO tables found in NexusArena schema!");
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
