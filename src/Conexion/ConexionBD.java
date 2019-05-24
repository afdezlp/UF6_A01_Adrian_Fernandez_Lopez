
package Conexion;

import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBD {
    
    private static final String HOST;
    private static final String PORT;
    private static final String DATABASE;
    private static final String USER;
    private static final String PASSWORD;
    private static final String URL_PARAM;
    private static final String URL;
    
    static {
        HOST = "localhost";
        PORT = "3306";
        DATABASE = "tienda";
        USER = "root";
        PASSWORD = "";
        URL_PARAM = "?serverTimezone = UTC";
        URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + URL_PARAM;
    }
    
    public static java.sql.Connection conectar() {
        java.sql.Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("No se puede conectar a la base de datos " + DATABASE);
            ex.printStackTrace();
            System.exit(1);
        }

        return conexion;
    }

    private static void cargarDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("No se ha cargado el driver JDBC MySQL");
            System.exit(1);
        }
    }
    
    
}
