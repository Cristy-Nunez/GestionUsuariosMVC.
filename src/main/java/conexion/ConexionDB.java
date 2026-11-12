/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nekom
 */
public class ConexionDB {
     // CORRECCIÓN 1: La URL de conexión a SQL Server estaba mal formada.
    // Faltaba el prefijo "jdbc:sqlserver://" al inicio y el separador ";" 
    // entre el nombre del host y "databaseName". Sin esto, DriverManager 
    // no puede interpretar la cadena de conexión.
    private static final String URL = "jdbc:sqlserver://LAPTOP-I0NJUJV2;"
            + "databaseName=EmpresaDB;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASS = "Cristy123";

    private static Connection connection = null;

    // CORRECCIÓN 2: Este método estaba mal declarado (sin modificador de acceso 
    // y lanzaba UnsupportedOperationException). Se cambió a 'public static' y 
    // se implementó correctamente llamando a closeConnection().
    public static void cerrarConexion() {
        closeConnection();
    }

    // CORRECCIÓN 3: Este método también estaba mal declarado y lanzaba 
    // UnsupportedOperationException. Se cambió a 'public static' y se 
    // implementó como alias de getConnection().
    public static Connection getConexion() {
        return getConnection();
    }

    private ConexionDB() {
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("-> Conexion Exitosa a SQL");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: no se encontro el driver mssql-jdbc -> " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con SQL -> " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
                System.out.println("-> La conexion con SQL esta cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexion -> " + e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // CORRECCIÓN 4 (opcional): Se agregó una prueba mínima para verificar 
        // que la conexión funcione al ejecutar la clase directamente.
        Connection con = ConexionDB.getConnection();
        if (con != null) {
            System.out.println("Prueba de conexion OK");
            ConexionDB.closeConnection();
        } else {
            System.out.println("No se pudo establecer la conexion");
        }
    }
    
}