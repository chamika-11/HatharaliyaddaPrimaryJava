
package DBConnector;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    private static final String username="root";
    private static final String password="28803164";
    private static final String dataConn="jdbc:mysql://localhost:3306/student_management";
    private static Connection con=null;
    
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=(Connection) DriverManager.getConnection(dataConn,username,password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.print(ex.getMessage());
        }
        return con;
    }
    
}
