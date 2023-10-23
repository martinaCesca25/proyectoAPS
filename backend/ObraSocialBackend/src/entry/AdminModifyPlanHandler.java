package entry;

import database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminModifyPlanHandler {
    protected DatabaseManager dbm;
    public AdminModifyPlanHandler(DatabaseManager dbm) {
        this.dbm = dbm;
    }

    public void modifyPlanPrice(int idPlan, int price, String category) {
        try {
            if(!dbm.planExist(idPlan,category)) {
                System.out.println("Error: no existe un plan con esa identificacion.");
            } else {
                if (price >= 0) {

                    System.out.println("\n Intentado moodificar los planes");
                    String planesQ = "UPDATE planes SET "+
                            "costo = '"+price+"'"+
                            "WHERE " +
                            "id_plan = '" +   idPlan    + "' AND " +
                            "categoria = '" +   category  + "'";
                    dbm.makeDatabaseUpdate(planesQ);
                    System.out.println("Se ha modificado el plan en el sistema de manera exitosa.");
                } else {
                    System.out.println("ERROR: El precio del plan debe ser mayor o igual a 0.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error while querying the database.");
        }
    }
    public void modifyPlanAge(int idPlan, int minAge, int maxAge, String category) {
        try {
            if(!dbm.planExist(idPlan,category)) {
                System.out.println("Error: no existe un plan con esa identificacion.");
            } else {
                System.out.println("\n Intentado moodificar los planes");
                if (minAge >= 0 && maxAge >= 0 & maxAge >= minAge) {

                    String planesQ = "UPDATE planes SET "+
                            "max_edad = '"+maxAge+"'"+
                            ", min_edad = '"+minAge+"'"+
                            "WHERE " +
                            "id_plan = '" +   idPlan    + "' AND " +
                            "categoria = '" +   category  + "'";
                    dbm.makeDatabaseUpdate(planesQ);
                    System.out.println("Se ha modificado el plan en el sistema de manera exitosa.");
                } else {
                    System.out.println("ERROR: las edades en los planes deben ser positivas y la edad maxima debe ser mas grande que la edad minima");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error while querying the database.");
        }
    }
    public void modifyPlanName(int idPlan, String name, String category) {
        try {
            if(!dbm.planExist(idPlan,category)) {
                System.out.println("Error: no existe un plan con esa categoria e id.");
            } else {
                System.out.println("\n Intentado moodificar los planes");
                String planesQ = "UPDATE planes SET "+
                        "nombre_plan = '"+name+"'"+
                        "WHERE " +
                        "id_plan = '" +   idPlan    + "' AND " +
                        "categoria = '" +   category  + "'";
                dbm.makeDatabaseUpdate(planesQ);
                System.out.println("Se ha modificado el plan en el sistema de manera exitosa.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error while querying the database.");
        }
    }
}
