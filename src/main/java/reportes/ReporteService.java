/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import conexion.ConexionDB;
import java.net.URL;
import java.sql.Connection;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteService {

    public static void imprimirReporteGeneral() {
        // 1. Obtener la conexión a SQL Server
        Connection cn = ConexionDB.getConexion();
        if (cn == null) {
            System.out.println("No se pudo conectar para generar el reporte");
            return;
        }

        try {
            // 2. Buscar el archivo .jrxml en la carpeta resources (src/main/resources)
            URL urlReporte = ReporteService.class.getResource("/ReporteGeneral.jrxml");
            
            if (urlReporte == null) {
                System.out.println("ERROR: No se encontró /ReporteGeneral.jrxml en resources.");
                return;
            }
            
            String rutaAbsoluta = urlReporte.getPath();

            // 3. Compilar la plantilla
            JasperReport reporte = JasperCompileManager.compileReport(rutaAbsoluta);
            
            // 4. Llenar el reporte con los datos de SQL Server
            JasperPrint impresion = JasperFillManager.fillReport(reporte, null, cn);

            // 5. Mostrar el visor de JasperReports
            JasperViewer.viewReport(impresion, false);

        } catch (Exception e) {
            System.err.println("Error al generar el reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }
}