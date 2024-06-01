package controladores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConexionDB {
       private String url = "jdbc:postgresql://viaduct.proxy.rlwy.net:48431/UMGLibreria";
    private Properties properties = new Properties();
    private static Connection conn = null;

    private ConexionDB() {
        properties.setProperty("user", "postgres");
        properties.setProperty("password", "GYLIDUsIpGEElOqPtcyEFTmhPEKhtIvx");

        try {
            conn = DriverManager.getConnection(url, properties);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            ConexionDB c = new ConexionDB();
            return c.conn;

        } else {
            return conn;

        }
    }
   
}
