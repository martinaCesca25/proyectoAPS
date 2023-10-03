package launcher;

import database.DatabaseManager;
import login.UserLoginHandler;

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
    }

    public static void demoLogin(DatabaseManager dbm) {
        UserLoginHandler ulh = new UserLoginHandler(dbm);
        ulh.login("juan@example.com", "password1"); //correct login
        ulh.login("juan@example.com", "password2"); //incorrect password
        ulh.login("tom@example.com", "password1");  //user doesnt exist
    }
}