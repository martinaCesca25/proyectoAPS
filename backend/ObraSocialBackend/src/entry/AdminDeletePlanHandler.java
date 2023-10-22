package entry;

import database.DatabaseManager;

import java.sql.SQLException;

public class AdminDeletePlanHandler {

    protected DatabaseManager dbm;

    public AdminDeletePlanHandler(DatabaseManager dbm){this.dbm = dbm;}

    public void deletePlan(int idPlan, String category){
        try{
            String deleteQ = "DELETE FROM planes WHERE id_plan = "+idPlan+" AND categoria = '"+category+"';";
            dbm.makeDatabaseUpdate(deleteQ);
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("SQL Error while querying the database.");
        }
    }

}
