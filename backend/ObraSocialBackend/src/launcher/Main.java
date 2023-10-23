package launcher;

import database.DatabaseManager;
import entry.*;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DatabaseManager dbm = new DatabaseManager();

        if(!dbm.databaseExists()) {
            System.out.println("Creando base de datos...");
            dbm.createDatabase();
        }

        //demoSignIn(dbm);
        //demoLogin(dbm);
        //demoSubscribe(dbm);
        //demoRegisterNewPlan(dbm);
        //showSubscriptions(dbm);
        //demoInscripcionPlan(dbm);
        demoModifyPlan(dbm);
        //demoGenerarCupon(dbm);
//        demoDeletePlan(dbm);
    }

    public static void demoDeletePlan(DatabaseManager dbm){
        AdminDeletePlanHandler adminDeletePlanHandler = new AdminDeletePlanHandler(dbm);
        System.out.println("Mostrando planes antes de la eliminacion");
        showPlans(dbm);
        adminDeletePlanHandler.deletePlan(1,"Oro");
        adminDeletePlanHandler.deletePlan(7,"Oro");
        System.out.println("Mostrando planes despues de la eliminacion");
        showPlans(dbm);
    }

    public static void demoLogin(DatabaseManager dbm) {
        UserLoginHandler ulh = new UserLoginHandler(dbm);
        ulh.login("juan@example.com", "password1"); //correct login
        ulh.login("juan@example.com", "password2"); //incorrect password
        ulh.login("tom@example.com", "password1");  //user doesnt exist
    }

    public static void demoSignIn(DatabaseManager dbm) {
        System.out.println("Mostrando usuarios previo a adicion");
        showUsers(dbm);
        System.out.println("");
        System.out.println("Agregando al nuevo usuario...");

        UserSignInHandler ush = new UserSignInHandler(dbm);

        String email = "leclerc@ferrari.com";
        String dni = "123456789";
        String password = "fast_car";
        String nombre = "charles";
        String apellido = "leclerc";
        String telefono = "+54-9-291-5-323924";
        int edad = 25;
        String localidad = "Monaco";

        ush.clientSignIn(email, dni, password, nombre, apellido, telefono, edad, localidad); //Sign in exitoso

        System.out.println("");
        System.out.println("Mostrando usuarios post-adicion");
        showUsers(dbm);

        System.out.println("Mostrando adicion erronea (el usuario ya esta registrado)");
        ush.clientSignIn(email, dni, password, nombre, apellido, telefono, edad, localidad); //Sign in fallido, ya existe el usuario.
    }

    public static void showUsers(DatabaseManager dbm) {
        String query = "SELECT * FROM personas";
        try {
            ResultSet rs = dbm.makeDatabaseQuery(query);

            while(rs.next()){
                System.out.println(rs.getString(1));
            }

        } catch(SQLException ex) {
            System.out.println("Error mostrando usuarios!");
        }
    }
    public static void demoSubscribe(DatabaseManager dbm) {
        UserSubscriptionHandler ush = new UserSubscriptionHandler(dbm);
        System.out.println("Mostrando suscripciones previo a adicion");
        showSubscriptions(dbm);

        System.out.println("");
        System.out.println("Agregando suscripcion");


        String email = "leclerc@ferrari.com";

        ush.clientSubscribe(email); //Subscribe exitoso

        System.out.println("");
        System.out.println("Mostrando suscripciones post-adicion");
        showSubscriptions(dbm);
    }

    public static void showSubscriptions(DatabaseManager dbm) {
        String query = "SELECT * FROM cliente_suscripcion";
        try {
            ResultSet rs = dbm.makeDatabaseQuery(query);

            while(rs.next()){
                System.out.println("El usuario con mail " + rs.getString(1) + " esta suscripto al plan con id " + rs.getInt(2));
            }

        } catch(SQLException ex) {
            System.out.println("Error mostrando usuarios!");
        }
    }

    public static void demoRegisterNewPlan(DatabaseManager dbm) {
        AdminRegisterNewPlanHandler adminRegisterNewPlanHandler = new AdminRegisterNewPlanHandler(dbm);
        System.out.println("Mostrando planes previo a adicion");
        showPlans(dbm);

        System.out.println("");
        System.out.println("Agregando plan");

        int idPlan = 7;
        int price = 1000;
        int minAge = 10;
        int maxAge = 100;
        String category = "Oro";
        String planName = "Platinum God";

        adminRegisterNewPlanHandler.registerNewPlan(idPlan,price,minAge,maxAge,planName,category); //Subscribe exitoso

        System.out.println("");
        System.out.println("Mostrando planes post-adicion");
        showPlans(dbm);
    }
    public static void demoModifyPlan(DatabaseManager dbm) {
        /*
         * Asume que existen en la base de datos dos planes, uno con (6,"Plata) y otro con (1,Oro)
         */
        AdminModifyPlanHandler modifyHandler = new AdminModifyPlanHandler(dbm);
        System.out.println("Mostrando planes previo a modificacion");
        showPlans(dbm);

        System.out.println("\nModificando plan");

        modifyHandler.modifyPlanName(6,"Multi 21 Seb","Plata");
        System.out.println("");
        System.out.println("Mostrando planes post-modificacion nombre");
        showPlans(dbm);

        modifyHandler.modifyPlanPrice(6,300,"Plata");
        System.out.println("");
        System.out.println("Mostrando planes post-modificacion nombre");
        showPlans(dbm);

        modifyHandler.modifyPlanAge(1,10,25,"Oro");
        System.out.println("");
        System.out.println("Mostrando planes post-modificacion edad");
        showPlans(dbm);
    }

    public static void showPlans(DatabaseManager dbm) {
        String query = "SELECT * FROM planes";
        try {
            ResultSet rs = dbm.makeDatabaseQuery(query);
            while(rs.next()){
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getInt(4)+" "+rs.getInt(5)+" "+rs.getString(6));
            }
            rs.close();

        } catch(SQLException ex) {
            System.out.println("Error mostrando planes!"+ex.getMessage());
        }
    }

    public static void demoInscripcionPlan(DatabaseManager dbm) {
        UserPlanSubscriptionHandler upsh = new UserPlanSubscriptionHandler(dbm);

        System.out.println("CASOS DE PRUEBA PARA USER STORY INSCRIPCION A UN PLAN");

        /*
        * Como clientes para esta demo se tiene a:
        *   Juan (mayor de edad)
        *   Timmy (menor de edad)
        * Como planes
        *   (1, 'Oro', 200, 0, 21, 'Plan Juvenil')
        *   (2, 'Plata', 250, 21, 35, 'Plan Estándar')
        * Y como suscripciones ya cargadas
        *   (101)
        *   (102)
        * */
        System.out.println("Tratando de suscribir a un usuario inexistente. Esperado: Error.");
        upsh.subscribeClientToPlan(1, "eugenio@example.com", 2, "Plata", null);
        System.out.println();

        System.out.println("Tratando de suscribir con un plan inexistente. Esperado: Error.");
        upsh.subscribeClientToPlan(10, "juan@example.com", 2, "Plata", null);
        System.out.println();

        System.out.println("Tratando de utilizar un id de suscripcion ya tomado. Esperado: Error.");
        upsh.subscribeClientToPlan(101, "juan@example.com", 2, "Plata", null);
        System.out.println();

        System.out.println("Tratando de suscribir un menor de edad con representante inexistente. Esperado: Error.");
        upsh.subscribeClientToPlan(1, "timmy@example.com", 1, "Oro", "eugenio@example.com");
        System.out.println();

        System.out.println("Tratando de suscribir un usuario valido, con plan valido. Esperado: Exito.");
        upsh.subscribeClientToPlan(1, "juan@example.com", 2, "Plata", null);
        System.out.println();

        System.out.println("Tratando de suscribir un menor de edad valido, con plan valido. Esperado: Exito.");
        upsh.subscribeClientToPlan(2, "timmy@example.com", 1, "Oro", "juan@example.com");

    }
    public static void demoGenerarCupon(DatabaseManager dbm) {
        PaymentCouponGeneratorHandler pcgh= new PaymentCouponGeneratorHandler(dbm);
	System.out.println("\nObteniendo un cupon de pago para juan@example.com");
        pcgh.generateCoupon("juan@example.com");
    }
}