package entry;

import database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReimbursementRequestHandler {
    protected DatabaseManager dbm;
    public ReimbursementRequestHandler(DatabaseManager dbm) {
        this.dbm = dbm;
    }

    public void requestReimbursement(String email, int idReintegro, int monto, String orden) {
        try {
            //ver que haya un cliente con email
            String clientExistsQuery = "SELECT * FROM clientes WHERE email = '" + email + "'";
            ResultSet clientExistsRS = dbm.makeDatabaseQuery(clientExistsQuery);
            boolean clientExists = clientExistsRS.next();
            dbm.closeConnection();

            if(!clientExists) {
                System.out.println("Error: el cliente elegido no está registrado en el sistema.");
                return;
            }

            //ver que NO haya un reintegro con esa id
            String reimbursementExistsQuery = "SELECT * FROM reintegros WHERE id_reintegro = " + idReintegro;
            ResultSet reimbursementExistsRS = dbm.makeDatabaseQuery(reimbursementExistsQuery);
            boolean reimbursementExists = reimbursementExistsRS.next();
            dbm.closeConnection();
            if(reimbursementExists) {
                System.out.println("Error: ya existe un reintegro con esa ID en el sistema.");
                return;
            }

            //generar la entrada en reintegro
            String newReimbursementQuery = "INSERT INTO reintegros(id_reintegro, monto, orden) VALUES (" +
                    idReintegro + "," +
                    monto + "," +
                    "'" + orden + "'" +
            ")";
            dbm.makeDatabaseUpdate(newReimbursementQuery);
            dbm.closeConnection();

            //generar la entrada en solicita
            String newRequestsQuery = "INSERT INTO solicita(id_reintegro, email_solicitante) VALUES (" +
                    idReintegro + "," +
                    "'" + email + "'" +
            ")";
            dbm.makeDatabaseUpdate(newRequestsQuery);
            dbm.closeConnection();

            System.out.println("Solicitud de reintegro registrada con exito!");

        } catch (SQLException ex) {
            System.out.println("SQL Error while querying the database.");
            ex.printStackTrace();
        }
    }

    public void mostrarSolicitudes() {
        try {
            String q = "SELECT * FROM solicita NATURAL JOIN reintegros";
            ResultSet rs = dbm.makeDatabaseQuery(q);

            while(rs.next()) {
                int id = rs.getInt("id_reintegro");
                String email = rs.getString("email_solicitante");
                int monto = rs.getInt("monto");
                String orden = rs.getString("orden");

                System.out.println(id + " | " + email + " | " + monto + " | " + orden);
            }

        } catch (SQLException ex) {
            System.out.println("SQL Error while querying the database.");
            ex.printStackTrace();
        }
    }
}
