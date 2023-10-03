package launcher;

import database.DatabaseManager;

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
}