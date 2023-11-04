package entry;

import database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminReimbursementsAndBenefitsHandler {
    protected DatabaseManager dbm;
    public AdminReimbursementsAndBenefitsHandler(DatabaseManager dbm){
        this.dbm=dbm;
    }
    public void adminReimbursements(int idReintegro, String emailAdmin) {
        try {
            //ver que haya una solicitud de reintegro con el id del reintegro
            String requestExistsQuery = "SELECT * FROM solicita WHERE id_reintegro = " + idReintegro;
            ResultSet requestExistsRS = dbm.makeDatabaseQuery(requestExistsQuery);
            boolean requestExists = requestExistsRS.next();
            dbm.closeConnection();

            if (!requestExists) {
                System.out.println("Error: la solicitud no está registrada en el sistema.");
                return;
            }
            //ver que haya un admin con email
            String adminExistsQuery = "SELECT * FROM empleados WHERE email = '" + emailAdmin + "'";
            ResultSet adminExistsRS = dbm.makeDatabaseQuery(adminExistsQuery);
            boolean adminExists = adminExistsRS.next();
            dbm.closeConnection();

            if (!adminExists) {
                System.out.println("Error: el empleado elegido no está registrado en el sistema.");
                return;
            }
            //ver que ya no haya una aprobacion del reintegro
            String aprovedExistsQuery = "SELECT * FROM aprueba WHERE id_reintegro = " + idReintegro;
            ResultSet aprovedExistsRS = dbm.makeDatabaseQuery(aprovedExistsQuery);
            boolean aprovedExists = aprovedExistsRS.next();
            dbm.closeConnection();

            if (aprovedExists) {
                System.out.println("Error: el reintegro ya fue aprobado.");
                return;
            }

            //generar la aprobacion del reintegro
            String newRequestsQuery = "INSERT INTO aprueba(id_reintegro, email_aprobador) VALUES (" +
                    idReintegro + "," +
                    "'" + emailAdmin + "'" +
                    ")";
            dbm.makeDatabaseUpdate(newRequestsQuery);
            dbm.closeConnection();

            System.out.println("Aprobación de reintegro registrada con exito!");
        }catch (SQLException ex) {
            System.out.println("SQL Error while querying the database.");
            ex.printStackTrace();
        }
    }
    //crea una prestacion y se la asigna a un cubrimiento de un plan
    public void adminBenefits(int idPrestacion, String nombrePrestacion, int idPlan, int porcentaje) {
        try {
            //ver que no haya una prestacion con ese id y nombre
            String benefitExistsQuery = "SELECT * FROM prestaciones WHERE id_prestacion = " + idPrestacion;
            ResultSet benefitExistsRS = dbm.makeDatabaseQuery(benefitExistsQuery);
            boolean benefitExists = benefitExistsRS.next();
            dbm.closeConnection();

            if (benefitExists) {
                System.out.println("Error: ya hay una prestación registrada en el sistema con ese id.");
                return;
            }

            String benefitExistsQueryBis = "SELECT * FROM prestaciones WHERE nombre_prestacion = '" + nombrePrestacion + "'";
            ResultSet benefitExistsRSBis = dbm.makeDatabaseQuery(benefitExistsQueryBis);
            boolean benefitExistsBis = benefitExistsRSBis.next();
            dbm.closeConnection();

            if (benefitExistsBis) {
                System.out.println("Error: ya hay una prestación registrada en el sistema con ese nombre.");
                return;
            }
            //ver que exista un plan con ese id
            String planExistsQuery = "SELECT * FROM planes WHERE id_plan = " + idPlan;
            ResultSet planExistsRS = dbm.makeDatabaseQuery(planExistsQuery);
            boolean planExists = planExistsRS.next();
            dbm.closeConnection();

            if (!planExists) {
                System.out.println("Error: el plan no se encuentra registrado.");
                return;
            }
            //generar la prestacion
            String newRequestsQuery = "INSERT INTO prestaciones(id_prestacion, nombre_prestacion) VALUES (" +
                    idPrestacion + "," +
                    "'" + nombrePrestacion + "'" +
                    ")";
            dbm.makeDatabaseUpdate(newRequestsQuery);
            dbm.closeConnection();

            //generar el cubrimiento
            String newRequestsQueryBis = "INSERT INTO cubrimientos(id_prestacion, id_plan, porcentaje) VALUES (" +
                    idPrestacion + "," +
                    idPlan + "," +
                    porcentaje + ")";
            dbm.makeDatabaseUpdate(newRequestsQueryBis);
            dbm.closeConnection();
        } catch (SQLException ex) {
            System.out.println("SQL Error while querying the database.");
            ex.printStackTrace();
        }
    }
    public void mostrarAprobaciones() {
        try {
            String q = "SELECT * FROM aprueba";
            ResultSet rs = dbm.makeDatabaseQuery(q);

            while(rs.next()) {
                int id = rs.getInt("id_reintegro");
                String email = rs.getString("email_aprobador");

                System.out.println(id + " | " + email );
            }

        } catch (SQLException ex) {
            System.out.println("SQL Error while querying the database.");
            ex.printStackTrace();
        }
    }
    public void mostrarPrestaciones() {
        try {
            String q = "SELECT * FROM prestaciones NATURAL JOIN cubrimientos";
            ResultSet rs = dbm.makeDatabaseQuery(q);

            while(rs.next()) {
                int id = rs.getInt("id_prestacion");
                int idP= rs.getInt("id_plan");
                String nombre = rs.getString("nombre_prestacion");
                int porcentaje = rs.getInt("porcentaje");

                System.out.println(id + " | " + nombre + " | " + idP + " | " + porcentaje);
            }

        } catch (SQLException ex) {
            System.out.println("SQL Error while querying the database.");
            ex.printStackTrace();
        }
    }
}
