package libraryfx;
import java.sql.*;
import javax.swing.JOptionPane;
public class Connectivity {
    private static final String USERNAME= "root";
    private static final String PASSWORD= "";
    private static final String CONN= "jdbc:mysql://localhost:3306/library";
    
    Connection conn=null;
    Statement stm= null;
    ResultSet rs= null;   

    public static Connection ConnectDb()
{
    try
    { 
        Connection conn = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
        //JOptionPane.showMessageDialog(null,"Connected");
        return conn;    
    }
    catch(Exception e)
    {
        JOptionPane.showMessageDialog(null,e);
        return null;
    }
}
}