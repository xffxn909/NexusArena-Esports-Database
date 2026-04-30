import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DeepCheckDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/NexusArena";
        try {
            Connection c = DriverManager.getConnection(url, "root", "xffxn_909");
            Statement s = c.createStatement();
            String[] tables = {"PLAYER", "GAME", "TEAM", "MATCH", "ROSTER", "TOURNAMENT"};
            for (String t : tables) {
                try {
                    ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM `" + t + "`");
                    if(rs.next()) System.out.println(t + " count: " + rs.getInt(1));
                } catch (Exception e) {}
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
