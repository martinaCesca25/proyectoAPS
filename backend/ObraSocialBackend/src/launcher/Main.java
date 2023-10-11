package launcher;

import database.DatabaseManager;
import entry.UserLoginHandler;
import entry.UserSignInHandler;
import entry.UserSubscriptionHandler;

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
        demoSubscribe(dbm);
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
}