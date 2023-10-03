package entry;

import database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSignInHandler {
    protected DatabaseManager dbm;

    public UserSignInHandler(DatabaseManager dbm) {
        this.dbm = dbm;
    }


    public void clientSignIn(String email, String dni, String password, String nombre, String apellido, String telefono, int edad, String localidad) {
        try {
            String existeQ = "SELECT * FROM personas WHERE email = '" + email + "'";
            ResultSet existeRS = dbm.makeDatabaseQuery(existeQ);

            if(existeRS.next()) {
                System.out.println("Error: ya existe un usuario para ese email.");
            } else {
                String personasQ = "INSERT INTO personas(email, dni, password, nombre, apellido, telefono, edad) VALUES (" +
                        "'" + email + "', " +
                        "'" + dni + "', " +
                        "'" + password + "', " +
                        "'" + nombre + "', " +
                        "'" + apellido + "', " +
                        "'" + telefono + "', " +
                        +edad + ")";

                String clientesQ = "INSERT INTO clientes(email, localidad) VALUES (" +
                        "'" + email + "', " +
                        "'" + localidad + "'" + ")";

                dbm.makeDatabaseUpdate(personasQ);
                dbm.makeDatabaseUpdate(clientesQ);

                System.out.println("Se ha registrado en el sistema de manera exitosa.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error while querying the database.");
        }
    }
}
