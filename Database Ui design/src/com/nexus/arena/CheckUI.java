import com.nexus.arena.dao.NexusDAO;
import com.nexus.arena.dao.DatabaseConnection;

public class CheckUI {
    public static void main(String[] args) {
        try {
            System.out.println("Testing DatabaseConnection...");
            boolean ok = DatabaseConnection.testConnection();
            System.out.println("Connection OK: " + ok);
            
            System.out.println("Testing NexusDAO.getAllPlayers()...");
            NexusDAO dao = new NexusDAO();
            java.util.List pList = dao.getAllPlayers();
            System.out.println("DAO returned " + pList.size() + " players.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
