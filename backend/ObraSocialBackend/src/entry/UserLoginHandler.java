package entry;

import database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoginHandler {
    protected DatabaseManager dbm;
    public UserLoginHandler(DatabaseManager dbm) {
        this.dbm = dbm;
    }

    public void login(String email, String password) {
        String query = "SELECT * FROM personas WHERE email = '" + email + "';";
        try {
            ResultSet rs = dbm.makeDatabaseQuery(query);

            if(rs.next()) {
                String storedPassword = rs.getString("password");

                if(storedPassword.equals(password)) {
                    System.out.println("Ha entrado al sistema exitosamente.");
                } else {
                    System.out.println("Contraseña incorrecta.");
                }
            } else {
                System.out.println("No hay usuarios registrados con ese email.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error while querying the database.");
            e.printStackTrace();
        }
    }
}
