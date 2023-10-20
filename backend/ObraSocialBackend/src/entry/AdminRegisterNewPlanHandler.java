package entry;

import database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRegisterNewPlanHandler {
    protected DatabaseManager dbm;
    public AdminRegisterNewPlanHandler(DatabaseManager dbm) {
        this.dbm = dbm;
    }

    public void registerNewPlan(int idPlan, int price, int minAge, int maxAge, String planName, String category) {
        try {
            String existeQ = "SELECT * FROM planes WHERE categoria = '" + category + "' AND id_plan = '"+idPlan+"'" ;
            ResultSet existeRS = dbm.makeDatabaseQuery(existeQ);

            if(existeRS.next()) {
                System.out.println("Error: ya existe un plan con esa identificacion.");
            } else {
                String planesQ = "INSERT INTO planes(id_plan, categoria, costo, min_edad, max_edad, nombre_plan) VALUES (" +
                        "'" +   idPlan    + "', " +
                        "'" +   category  + "', " +
                        "'" +   price     + "', " +
                        "'" +   minAge    + "', " +
                        "'" +   maxAge    + "', " +
                        "'" +   planName  + "')";
                dbm.makeDatabaseUpdate(planesQ);
                System.out.println("Se ha registrado el plan en el sistema de manera exitosa.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error while querying the database.");
        }
    }
}
