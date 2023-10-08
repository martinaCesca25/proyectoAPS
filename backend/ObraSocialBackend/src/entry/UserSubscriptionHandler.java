package entry;

import database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSubscriptionHandler {
    protected DatabaseManager dbm;
    protected int idCounter=1;

    public UserSubscriptionHandler(DatabaseManager dbm) {
        this.dbm = dbm;
    }


    public void clientSubscribe(String email) {
        try {
                String cliente_suscripcion = "INSERT INTO cliente_suscripcion(email_cliente, id_suscripcion) VALUES (" +
                        "'" + email + "', " +
                        "'" + idCounter + ")";

                dbm.makeDatabaseUpdate(cliente_suscripcion);

                System.out.println("Se ha suscripto en el sistema de manera exitosa.");
            } catch (SQLException e) {
            System.out.println("SQL Error while querying the database.");
        }
    }
}

