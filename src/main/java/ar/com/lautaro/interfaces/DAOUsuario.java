package ar.com.lautaro.interfaces;

import ar.com.lautaro.clases.Usuario;
import java.sql.SQLException;
import java.util.List;

public interface DAOUsuario {

    // CRUD
    // CREATE
    public void registrar(List<Usuario> usuario) throws SQLException, ClassNotFoundException;

    // READ
    public List<Usuario> recuperar() throws SQLException, ClassNotFoundException;

    // Update
    public void modificar(Usuario user) throws SQLException, ClassNotFoundException;

    // Delete
    public void eliminar(Usuario user) throws SQLException, ClassNotFoundException;
}
