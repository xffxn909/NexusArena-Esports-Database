import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckViewData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/NexusArena";
        try {
            Connection c = DriverManager.getConnection(url, "root", "xffxn_909");
            Statement s = c.createStatement();
            System.out.println("--- GLOBAL LEADERBOARD ---");
            ResultSet rs = s.executeQuery("SELECT * FROM vw_global_leaderboard LIMIT 5");
            while(rs.next()) System.out.println(rs.getString(1) + " | " + rs.getString(2));
            
            System.out.println("\n--- PLAYERS ---");
            ResultSet rs2 = s.executeQuery("SELECT username FROM player LIMIT 5");
            while(rs2.next()) System.out.println(rs2.getString(1));
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
