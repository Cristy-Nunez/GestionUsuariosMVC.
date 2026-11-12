/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dao;
import modelo.Empleado;
import conexion.ConexionDB;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author nekom
 */
public class EmpleadoDAO {
    public ArrayList<Empleado> listarActivos(){
        ArrayList<Empleado> lista = new ArrayList<>();
        String sql ="SELECT IdEmpleado, Nombre, Apellido, Cargo, Salario, Departamento " + "FROM Empleados";
        try(Connection con = ConexionDB.getConexion();
               PreparedStatement ps = con.prepareStatement(sql);
               ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Empleado emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("IdEmpleado"));
                emp.setNombre(rs.getString("Nombre"));
                emp.setApellido(rs.getString("Apellido"));
                emp.setCargo(rs.getString("Cargo"));
                emp.setSalario(rs.getDouble("Salario"));
                emp.setDepartamento(rs.getString("Departamento"));
                lista.add(emp);
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
        
    }
    
    public double obterSalario(int idEmpleado){
        String sql = "SELECT salarioBase FROM empleados WHERE idEmpleados = ?";
        try(Connection con = ConexionDB.getConexion();
        PreparedStatement ps =con.prepareStatement(sql)){
            ps.setInt(1, idEmpleado);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return rs.getDouble("Salario");
                }
            }
        }catch(SQLException e){
            e.printStackTrace(); 
        }
        return 0;
        
    }
    
    public boolean insertar(Empleado emp){
        String sql = "INSERT INTO Empleados"
                   + "(Nombre, Apellido, Cargo, Salario, Departamento)"
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getApellido());
            ps.setString(3, emp.getCargo());
            ps.setDouble(4, emp.getSalario());
            ps.setString(5, emp.getDepartamento());
            
            ps.executeUpdate();
            return true;
            
        }catch(SQLException e){
            javax.swing.JOptionPane.showMessageDialog(
                    null, "Error al guardar el empleado: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
