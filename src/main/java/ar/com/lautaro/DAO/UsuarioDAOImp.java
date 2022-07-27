package ar.com.lautaro.DAO;

import ar.com.lautaro.clases.Usuario;
import ar.com.lautaro.interfaces.DAOUsuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImp extends Conexion implements DAOUsuario {

    private final String REGISTRO = "INSERT INTO Usuario(nombre,apellido,email,telefono) VALUES(?,?,?,?)";
    private final String RECUPERAR = "SELECT * FROM Usuario";
    private final String MODIFICAR = "UPDATE Usuario SET telefono=? WHERE nombre=?";
    private final String ELIMINAR = "DELETE FROM Usuario WHERE nombre=?";

    @Override
    public void registrar(List<Usuario> usuarios) throws SQLException, ClassNotFoundException {
        this.iniciar();
        PreparedStatement accion = this.conexion.prepareStatement(REGISTRO);

        for (Usuario usuario : usuarios) {

            accion.setString(1, usuario.getNombre());
            accion.setString(2, usuario.getApellido());
            accion.setString(3, usuario.getEmail());
            accion.setString(4, usuario.getTelefono());
            accion.executeUpdate();

        }

        System.out.println("Se realizo la actualizacion de la tabla");

        accion.close();
        this.conexion.close();

    }

    @Override
    public List<Usuario> recuperar() throws SQLException, ClassNotFoundException {
        List<Usuario> usuarios = new ArrayList<>();
        this.iniciar();
        Statement accion = this.conexion.createStatement();
        ResultSet resultado = accion.executeQuery(RECUPERAR);

        while (resultado.next()) {

            usuarios.add(new Usuario(
                    resultado.getInt(1),
                    resultado.getString(2),
                    resultado.getString(3),
                    resultado.getString(4),
                    resultado.getString(5)
            ));

        }

        resultado.close();
        accion.close();
        this.conexion.close();
        if (usuarios.isEmpty()) {
            System.out.println("No tiene ningún usuario cargado para mostrar.");
        } else {

            usuarios.forEach(System.out::println);
        }
        System.out.println("\n\n---------------------------------------------------------------------");
        System.out.println("Finalizo la acción de recuperar.");

        return usuarios;
    }

    @Override
    public void modificar(Usuario user) throws SQLException, ClassNotFoundException {
        this.iniciar();
        PreparedStatement accion = this.conexion.prepareStatement(MODIFICAR);
        accion.setString(1, user.getTelefono());
        accion.setString(2, user.getNombre());
        accion.executeUpdate();
        accion.close();
        this.conexion.close();
        System.out.println("Se realizo la actualizacion");
    }

    @Override
    public void eliminar(Usuario user) throws SQLException, ClassNotFoundException {
        this.iniciar();

        PreparedStatement accion = this.conexion.prepareStatement(ELIMINAR);
        accion.setString(1, user.getNombre());
        accion.executeUpdate();
        accion.close();
        this.conexion.close();
        System.out.println("Se realizo el borrado");
    }

    public List<Usuario> SegundoMenu() throws SQLException, ClassNotFoundException {
        List<Usuario> lista = new ArrayList<>();
        this.iniciar();
        Statement accion = this.conexion.createStatement();
        ResultSet resultado = accion.executeQuery(RECUPERAR);

        while (resultado.next()) {

            lista.add(new Usuario(
                    resultado.getInt(1),
                    resultado.getString(2),
                    resultado.getString(3),
                    resultado.getString(4),
                    resultado.getString(5)
            ));

        }
        resultado.close();
        accion.close();
        this.conexion.close();
        return lista;
    }

}
