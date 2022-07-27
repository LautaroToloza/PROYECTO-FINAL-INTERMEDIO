package ar.com.lautaro.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private final String URL = "jdbc:mysql://localhost:3306/Base_Caso";
    private final String USUARIO = "root";
    private final String CONTRASEÑA = "";
    protected Connection conexion;

    public void iniciar() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }

}
