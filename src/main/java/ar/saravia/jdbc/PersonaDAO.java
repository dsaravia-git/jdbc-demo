package ar.saravia.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    private final String url = "jdbc:postgresql://localhost:5432/prueba_java";
    private final String usuario = "postgres";
    private final String password = "prueba";


    private Connection obtenerConexion() throws SQLException {
        Connection conexion =
                DriverManager.getConnection(
                        url,
                        usuario,
                        password
                );
        return conexion;
    }

    public int guardar(Persona persona) throws SQLException {

        String sql = "INSERT INTO personas(nombre, edad) VALUES (?, ?)";

        try (
            Connection conexion = obtenerConexion();
            PreparedStatement ps = conexion.prepareStatement(sql)
        )
        {
            ps.setString(1, persona.getNombre());
            ps.setInt(2, persona.getEdad());

            return ps.executeUpdate();
        }
    }

    public List<Persona> listar() throws SQLException {
        List<Persona> personas = new ArrayList<>();
        try (   Connection conexion = obtenerConexion();
                Statement statement = conexion.createStatement();
                ResultSet resultado = statement.executeQuery("SELECT id, nombre, edad FROM personas" )
        )
        {
                while (resultado.next()) {

                Persona persona = new Persona();
                persona.setId(resultado.getInt("id"));
                persona.setNombre(resultado.getString("nombre"));
                persona.setEdad(resultado.getInt("edad"));
                personas.add(persona);
            }
        }
        return personas;
    }

    public int actualizar(Persona persona)throws SQLException {

        String sql = "UPDATE personas SET edad = ? WHERE id = ?";

        try ( Connection conexion = obtenerConexion();
              PreparedStatement ps = conexion.prepareStatement(sql)
        )
        {
            ps.setInt(1, persona.getEdad());
            ps.setInt(2,persona.getId());

            return ps.executeUpdate();
        }
    }

    public int eliminar(Persona persona)throws SQLException {

        String sql = "DELETE FROM personas WHERE id = ?";

        try (
                Connection conexion = obtenerConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)
        )

        {
            ps.setInt(1,persona.getId());
            return ps.executeUpdate();
        }
    }


    public Persona buscarPorId(int id) throws SQLException {

        Persona persona = null;
        String sql = "SELECT id, nombre, edad FROM personas WHERE id = ?";
        try(
                Connection conexion = obtenerConexion();
                PreparedStatement ps = conexion.prepareStatement(sql);
            )
        {
            ps.setInt(1,id);
            ResultSet resultado = ps.executeQuery();

            if(resultado.next()){
                persona = new Persona();
                persona.setId(id);
                persona.setEdad(resultado.getInt("edad"));
                persona.setNombre(resultado.getString("nombre"));
            }
        }
        return persona;
    }

}
