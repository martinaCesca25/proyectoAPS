package entry;

import database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPlanSubscriptionHandler {
    protected DatabaseManager dbm;

    public UserPlanSubscriptionHandler(DatabaseManager dbm) {
        this.dbm = dbm;
    }

    public void subscribeClientToPlan(int idSubscripcion, String emailCliente, int idPlan, String categoriaPlan, String emailResponsable) {
        try {
            int edadCliente = 1000;

            String clientExistsQuery = "SELECT * FROM personas WHERE email = '" + emailCliente + "'"; //should exist
            ResultSet clientExistsRS = dbm.makeDatabaseQuery(clientExistsQuery);
            if(!clientExistsRS.next()) {
                System.out.println("Error, el cliente que se intenta suscribir no esta en el sistema.");
                dbm.closeConnection();
                return;
            } else {
                edadCliente = clientExistsRS.getInt("edad");
            }

            dbm.closeConnection();

            String subscriptionExistsQuery = "SELECT * FROM suscripcion WHERE id_suscripcion = " + idSubscripcion; //should NOT exist
            ResultSet subscriptionExistsRS = dbm.makeDatabaseQuery(subscriptionExistsQuery);
            if(subscriptionExistsRS.next()) {
                System.out.println("Error, se esta tratando de crear una suscripcion con un id ya registrado en el sistema.");
                dbm.closeConnection();
                return;
            }

            dbm.closeConnection();

            String planExistsQuery = "SELECT * FROM planes WHERE id_plan = " + idPlan + " AND categoria = '" + categoriaPlan + "'"; //should exist
            ResultSet planExistsRS = dbm.makeDatabaseQuery(planExistsQuery);
            if(!planExistsRS.next()) {
                System.out.println("Error, se esta tratando de subscribir a un plan no registrado en el sistema.");
                dbm.closeConnection();
                return;
            }

            dbm.closeConnection();

            if(edadCliente < 18) {
                String parentExistsQuery = "SELECT * FROM personas WHERE email = '" + emailResponsable + "'"; //should exist
                ResultSet parentExistsRS = dbm.makeDatabaseQuery(parentExistsQuery);
                if(!parentExistsRS.next()) {
                    System.out.println("Error, el responsable elegido no esta en el sistema.");
                    dbm.closeConnection();
                    return;
                } else {
                    int edadResponsable = parentExistsRS.getInt("edad");
                    dbm.closeConnection();
                    if(edadResponsable < 18) {
                        System.out.println("Error, correo elegido para mayor de edad responsable no se corresponde con un usuario mayor de edad.");
                    }
                }
            }

            String subscriptionCreationQuery = "INSERT INTO suscripcion(id_suscripcion) VALUES (" + idSubscripcion + ");";

            String clientSubscriptionQuery = "INSERT INTO cliente_suscripcion(email_cliente, id_suscripcion)" +
                                             "VALUES('" + emailCliente + "', " + idSubscripcion + ")";

            String planSubscriptionQuery = "INSERT INTO suscripcion_plan(id_plan, categoria, id_suscripcion)" +
                                           "VALUES(" + idPlan + ", '" + categoriaPlan + "', " + idSubscripcion + ")";

            dbm.makeDatabaseUpdate(subscriptionCreationQuery);
            dbm.closeConnection();
            dbm.makeDatabaseUpdate(clientSubscriptionQuery);
            dbm.closeConnection();
            dbm.makeDatabaseUpdate(planSubscriptionQuery);

            if(edadCliente < 18) {
                String minorSubcsriptionQuery = "INSERT INTO cliente_suscripcion(email_cliente, id_suscripcion)" +
                                                "VALUES('" + emailResponsable + "', " + idSubscripcion + ")";

                dbm.makeDatabaseUpdate(minorSubcsriptionQuery);
                dbm.closeConnection();
            }

        } catch (SQLException ex) {
            System.out.println("SQL Error while querying the database.");
            ex.printStackTrace();
            return;
        }

        System.out.println("Suscripcion exitosa.");
    }
}
