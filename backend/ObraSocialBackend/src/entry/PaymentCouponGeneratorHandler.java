package entry;

import database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentCouponGeneratorHandler {
    protected DatabaseManager dbm;

    public PaymentCouponGeneratorHandler(DatabaseManager dbm) {
        this.dbm = dbm;
    }

    public void generateCoupon(String email_cliente) {
        try {
            int idSubscripcion = 0;
            int idPlan = 0;
            String categoriaPlan = "";
            String nombrePlan = "";
            int costo = 0;
            String clientExistsQuery = "SELECT * FROM cliente_suscripcion WHERE email_cliente = '" + email_cliente + "'"; //should exist
            ResultSet clientSubs = dbm.makeDatabaseQuery(clientExistsQuery);
            if (!clientSubs.next()) {
                System.out.println("Error, el cliente no se encuentra suscripto a ningun plan.");
                dbm.closeConnection();
                return;
            } else {
                idSubscripcion = clientSubs.getInt("id_suscripcion");
            }

            dbm.closeConnection();

            String subscriptionExistsQuery = "SELECT * FROM suscripcion_plan WHERE id_suscripcion = " + idSubscripcion; //should exist
            ResultSet subscriptionExistsRS = dbm.makeDatabaseQuery(subscriptionExistsQuery);
            if (!subscriptionExistsRS.next()) {
                System.out.println("Error, se esta tratando de encontrar una suscripcion con un id no registrado en el sistema.");
                dbm.closeConnection();
                return;
            } else {
                idPlan = subscriptionExistsRS.getInt("id_plan");
                categoriaPlan = subscriptionExistsRS.getString("categoria");
            }

            dbm.closeConnection();

            String planExistsQuery = "SELECT * FROM planes WHERE id_plan = " + idPlan + " AND categoria = '" + categoriaPlan + "'"; //should exist
            ResultSet planExistsRS = dbm.makeDatabaseQuery(planExistsQuery);
            if (!planExistsRS.next()) {
                System.out.println("Error, se esta tratando de encontrar un plan con un id no registrado en el sistema.");
                dbm.closeConnection();
                return;
            } else {
                costo = planExistsRS.getInt("costo");
                nombrePlan = planExistsRS.getString("nombre_plan");
            }

            dbm.closeConnection();

            System.out.println("Usuario: " + email_cliente + ". Plan: " + nombrePlan + " de categoría: " + categoriaPlan + ". Costo: $" + costo);
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error while querying the database.");
        }
    }
}