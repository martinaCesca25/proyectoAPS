package database;

import java.io.File;
import java.sql.*;

public class DatabaseManager {
    private static final String DATABASE_NAME = "database";
    private static final String DATABASE_DRIVER = "jdbc:sqlite:";
    private static final String DATABASE_EXTENSION = ".db";
    private static final String FULL_DATABASE_DRIVER = DATABASE_DRIVER + "./" + DATABASE_NAME + DATABASE_EXTENSION;

    public boolean databaseExists() {
        return new File("./" + DATABASE_NAME + DATABASE_EXTENSION).exists();
    }

    public void test() {
        String q = "select * from personas";

        try {
            ResultSet rs = makeDatabaseQuery(q);

            while(rs.next())
                System.out.println(rs.getString(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createDatabase() {
        createTables();
        addData();
    }

    public ResultSet makeDatabaseQuery(String query) throws SQLException {
        Connection c = DriverManager.getConnection(FULL_DATABASE_DRIVER);
        Statement s = c.createStatement();
        s.setQueryTimeout(30);
        return s.executeQuery(query);
    }

    public void makeDatabaseUpdate(String query) throws SQLException {
        Connection c = DriverManager.getConnection(FULL_DATABASE_DRIVER);
        Statement s = c.createStatement();
        s.setQueryTimeout(30);
        s.executeUpdate(query);
    }

    protected void createTables() {
        try {
            String personas = "CREATE TABLE personas("
                    + "email STRING, "
                    + "dni INTEGER, "
                    + "password STRING, "
                    + "nombre STRING, "
                    + "apellido STRING, "
                    + "telefono STRING, "
                    + "edad INTEGER, "
                    + "PRIMARY KEY(email)"
                    + ")";

            String clientes = "CREATE TABLE clientes("
                    + "email STRING, "
                    + "localidad STRING, "
                    + "PRIMARY KEY(email)"
                    + ")";

            String empleados = "CREATE TABLE empleados("
                    + "email STRING, "
                    + "cargo STRING, "
                    + "PRIMARY KEY(email)"
                    + ")";

            String reintegros = "CREATE TABLE reintegros("
                    + "id_reintegro INTEGER, "
                    + "monto INTEGER, "
                    + "orden STRING, "
                    + "PRIMARY KEY(id_reintegro)"
                    + ")";

            String solicita = "CREATE TABLE solicita("
                    + "id_reintegro INTEGER, "
                    + "email_solicitante STRING, "
                    + "PRIMARY KEY(id_reintegro)"
                    + ")";

            String aprueba = "CREATE TABLE aprueba("
                    + "id_reintegro INTEGER, "
                    + "email_aprobador STRING, "
                    + "PRIMARY KEY(id_reintegro)"
                    + ")";

            String suscripcion = "CREATE TABLE suscripcion("
                    + "id_suscripcion INTEGER, "
                    + "PRIMARY KEY(id_suscripcion)"
                    + ")";

            //Cubre tambien la relacion responsable adulto!
            String suscripcionMenorDeEdad = "CREATE TABLE suscripcion_menor_edad("
                    + "id_suscripcion INTEGER, "
                    + "email_responsable_adulto STRING,"
                    + "PRIMARY KEY(id_suscripcion)"
                    + ")";

            String clienteSuscripcion = "CREATE TABLE cliente_suscripcion("
                    + "email_cliente STRING, "
                    + "id_suscripcion INTEGER, "
                    + "PRIMARY KEY(email_cliente)"
                    + ")";

            //Cubre tambien la relacion paga!
            String pago = "CREATE TABLE pago("
                    + "id_pago INTEGER, "
                    + "monto INTEGER, "
                    + "fecha TIMESTAMP, "
                    + "id_suscripcion INTEGER, "
                    + "PRIMARY KEY(id_pago)"
                    + ")";

            String planes = "CREATE TABLE planes("
                    + "id_plan INTEGER, "
                    + "categoria STRING, "
                    + "costo INTEGER, "
                    + "min_edad INTEGER, "
                    + "max_edad INTEGER, "
                    + "nombre_plan STRING, "
                    + "PRIMARY KEY(id_plan, categoria)"
                    + ")";

            String suscripcionPlan = "CREATE TABLE suscripcion_plan("
                    + "id_plan INTEGER, "
                    + "categoria STRING, "
                    + "id_suscripcion INTEGER, "
                    + "PRIMARY KEY(id_suscripcion)"
                    + ")";

            String prestaciones = "CREATE TABLE prestaciones("
                    + "id_prestacion INTEGER, "
                    + "nombre_prestacion STRING, "
                    + "PRIMARY KEY(id_prestacion)"
                    + ")";

            String cubrimientos = "CREATE TABLE cubrimientos("
                    + "id_prestacion INTEGER, "
                    + "id_plan INTEGER, "
                    + "porcentaje INTEGER, "
                    + "PRIMARY KEY(id_plan, id_prestacion)"
                    + ")";

            String[] queries = {
                    personas,
                    clientes,
                    empleados,
                    reintegros,
                    solicita,
                    aprueba,
                    suscripcion,
                    suscripcionMenorDeEdad,
                    clienteSuscripcion,
                    pago,
                    planes,
                    suscripcionPlan,
                    prestaciones,
                    cubrimientos
            };


            for(String q : queries)
                makeDatabaseUpdate(q);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void addData() {
        String q = "-- Agregar datos a la tabla personas\n" +
                "INSERT INTO personas(email, dni, password, nombre, apellido, telefono, edad)\n" +
                "VALUES\n" +
                "    ('juan@example.com', 123456789, 'password1', 'Juan', 'González', '123-456-7890', 35),\n" +
                "    ('maria@example.com', 987654321, 'password2', 'Maria', 'López', '987-654-3210', 28),\n" +
                "    ('carlos@ospifak.com', 555555555, 'password3', 'Carlos', 'Martínez', '555-555-5555', 42),\n" +
                "    ('laura@ospifak.com', 444444444, 'password4', 'Laura', 'Fernández', '444-444-4444', 22),\n" +
                "    ('martin@ospifak.com', 744454414, 'password5', 'Martin', 'Fernández', '444-444-4423', 77);\n" +
                "\n" +
                "-- Agregar datos a la tabla clientes\n" +
                "INSERT INTO clientes(email, localidad)\n" +
                "VALUES\n" +
                "    ('juan@example.com', 'Buenos Aires'),\n" +
                "    ('maria@example.com', 'Madrid'),\n" +
                "    ('martin@ospifak.com', 'Buenos Aires');\n" +
                "\n" +
                "-- Agregar datos a la tabla empleados\n" +
                "INSERT INTO empleados(email, cargo)\n" +
                "VALUES\n" +
                "    ('carlos@ospifak.com', 'Gerente de Ventas'),\n" +
                "    ('laura@ospifak.com', 'Asistente de Recursos Humanos');\n" +
                "\n" +
                "-- Agregar datos a la tabla reintegros\n" +
                "INSERT INTO reintegros(id_reintegro, monto, orden)\n" +
                "VALUES\n" +
                "    (1, 500, 'Orden123'),\n" +
                "    (2, 750, 'Orden456');\n" +
                "\n" +
                "-- Agregar datos a la tabla solicita\n" +
                "INSERT INTO solicita(id_reintegro, email_solicitante)\n" +
                "VALUES\n" +
                "    (1, 'juan@example.com'),\n" +
                "    (2, 'maria@example.com');\n" +
                "\n" +
                "-- Agregar datos a la tabla aprueba\n" +
                "INSERT INTO aprueba(id_reintegro, email_aprobador)\n" +
                "VALUES\n" +
                "    (1, 'carlos@ospifak.com'),\n" +
                "    (2, 'laura@ospifak.com');\n" +
                "\n" +
                "-- Agregar datos a la tabla suscripcion\n" +
                "INSERT INTO suscripcion(id_suscripcion)\n" +
                "VALUES\n" +
                "    (101),\n" +
                "    (102);\n" +
                "\n" +
                "-- Agregar datos a la tabla suscripcion_menor_edad\n" +
                "INSERT INTO suscripcion_menor_edad(id_suscripcion, email_responsable_adulto)\n" +
                "VALUES\n" +
                "    (101, 'juan@example.com'),\n" +
                "    (102, 'maria@example.com');\n" +
                "\n" +
                "-- Agregar datos a la tabla cliente_suscripcion\n" +
                "INSERT INTO cliente_suscripcion(email_cliente, id_suscripcion)\n" +
                "VALUES\n" +
                "    ('juan@example.com', 101),\n" +
                "    ('maria@example.com', 102);\n" +
                "\n" +
                "-- Agregar datos a la tabla pago\n" +
                "INSERT INTO pago(id_pago, monto, fecha, id_suscripcion)\n" +
                "VALUES\n" +
                "    (201, 100, '2023-08-01', 101),\n" +
                "    (202, 150, '2023-08-02', 102);\n" +
                "\n" +
                "-- Agregar datos a la tabla planes\n" +
                "INSERT INTO planes(id_plan, categoria, costo, min_edad, max_edad, nombre_plan)\n" +
                "VALUES\n" +
                "    (1, 'Oro', 200, 0, 21, 'Plan Juvenil'),\n" +
                "    (2, 'Plata', 250, 21, 35, 'Plan Estándar'),\n" +
                "    (3, 'Cobre', 300, 35, 55, 'Plan Familiar'),\n" +
                "    (4, 'Oro', 350, 55, 100, 'Plan de Jubilación Premium'),\n" +
                "    (5, 'Oro', 400, 0, 100, 'Plan Premium'),\n" +
                "    (6, 'Plata', 250, 55, 100, 'Plan de Jubilación');\n" +
                "\n" +
                "-- Agregar datos a la tabla suscripcion_plan\n" +
                "INSERT INTO suscripcion_plan(id_plan, categoria, id_suscripcion)\n" +
                "VALUES\n" +
                "    (1, 'Oro', 101),\n" +
                "    (2, 'Plata', 102);\n" +
                "\n" +
                "-- Agregar datos a la tabla prestaciones\n" +
                "INSERT INTO prestaciones(id_prestacion, nombre_prestacion)\n" +
                "VALUES\n" +
                "    (1, 'Consulta Médica General'),\n" +
                "    (2, 'Limpieza Dental');\n" +
                "\n" +
                "-- Agregar datos a la tabla cubrimientos\n" +
                "INSERT INTO cubrimientos(id_prestacion, id_plan, porcentaje)\n" +
                "VALUES\n" +
                "    (1, 1, 90),\n" +
                "    (1, 2, 80),\n" +
                "    (1, 3, 70),\n" +
                "    (1, 4, 60),\n" +
                "    (2, 1, 85),\n" +
                "    (2, 2, 75),\n" +
                "    (2, 3, 65),\n" +
                "    (2, 4, 55);";

        try {
            makeDatabaseUpdate(q);
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}
