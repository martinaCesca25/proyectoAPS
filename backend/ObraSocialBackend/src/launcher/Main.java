package launcher;

import database.DatabaseManager;
import entry.UserLoginHandler;
import entry.UserSignInHandler;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        DatabaseManager dbm = new DatabaseManager();
        if(!dbm.databaseExists()) {
            System.out.println("Creating database...");
            dbm.createDatabase();
        }

        System.out.println("Database exists and has information.");
        dbm.test();
        demoSignIn(dbm);
    }

    public static void demoLogin(DatabaseManager dbm) {
        UserLoginHandler ulh = new UserLoginHandler(dbm);
        ulh.login("juan@example.com", "password1"); //correct login
        ulh.login("juan@example.com", "password2"); //incorrect password
        ulh.login("tom@example.com", "password1");  //user doesnt exist
    }

    public static void demoSignIn(DatabaseManager dbm) {
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
        ush.clientSignIn(email, dni, password, nombre, apellido, telefono, edad, localidad); //Sign in fallido, ya existe el usuario.
    }
}