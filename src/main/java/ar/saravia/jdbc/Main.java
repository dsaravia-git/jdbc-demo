package ar.saravia.jdbc;

import java.sql.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            PersonaDAO dao = new PersonaDAO();
            List<Persona> personas = dao.listar();

            for (Persona persona : personas)
                System.out.println(
                        persona.getId() + " - " + persona.getNombre() + " - " + persona.getEdad()
                );
        }
        catch(Exception e){
            e.printStackTrace();
        }

        try{
                PersonaDAO dao = new PersonaDAO();
                Persona persona = dao.buscarPorId(1);

                if(persona != null){
                    System.out.println(persona.getId()+" - "+ persona.getNombre()+" - "+persona.getEdad());
                }
            }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}


