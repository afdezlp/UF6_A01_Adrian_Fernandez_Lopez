
package empleado.dao;

import Conexion.ConexionBD;
import empleado.dominio.Empleado;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EmpleadoDAOImp implements EmpleadoDAO{
    
    private List<Empleado> empleados;

    public EmpleadoDAOImp() {
        
        List<Empleado> listaEmpleados = new ArrayList<>();
        
        try {
            var conexion = ConexionBD.conectar();

            Statement sentencia = conexion.createStatement();

            ResultSet resultado = sentencia.executeQuery("SELECT * FROM empleados;");

            try {

                while (resultado.next()) {
                    int codigo = resultado.getInt("e_codigo");
                    String nombre = resultado.getString("e_nombre");
                    String apellidos = resultado.getString("e_apellidos");
                    String password = resultado.getString("e_password");

                    listaEmpleados.add(new Empleado(codigo, nombre, apellidos, password));
                }

            } catch (Exception ex) {
                System.out.println("Error de lectura en: ");

            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAOImp.class.getName()).log(Level.SEVERE, null, ex);

        }

        setEmpleados(listaEmpleados);

    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
    
    
    @Override
    public List<Empleado> leerEmpleados() {
        List<Empleado> listaEmpleados = new ArrayList<>();
        try {
            var conexion = ConexionBD.conectar();

            Statement sentencia = conexion.createStatement();

            ResultSet resultado = sentencia.executeQuery("SELECT * FROM empleados;");

            try {

                while (resultado.next()) {

                    while (resultado.next()) {
                        int codigo = resultado.getInt("e_codigo");
                        String nombre = resultado.getString("e_nombre");
                        String apellidos = resultado.getString("e_apellidos");
                        String password = resultado.getString("e_password");

                        listaEmpleados.add(new Empleado(codigo, nombre, apellidos, password));
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(EmpleadoDAOImp.class.getName()).log(Level.SEVERE, null, ex);

            }
        } catch (Exception e) {

        }
        return listaEmpleados;
    }

    @Override
    public Empleado getEmpleadoPorCodigo(int codigo) {
        Empleado empleado = null;

        try {
            

                var conexion = ConexionBD.conectar();

                Statement sentencia = conexion.createStatement();

                ResultSet resultado = sentencia.executeQuery("SELECT * FROM empleados where e_codigo=" + codigo + ";");

                while (resultado.next()) {
                    int newCodigo = resultado.getInt("e_codigo");
                    String nombre = resultado.getString("e_nombre");
                    String apellidos = resultado.getString("e_apellidos");
                    String password = resultado.getString("e_password");

                    empleado = new Empleado(newCodigo, nombre, apellidos, password);
                }
            
        } catch (SQLException ex) {
            System.err.println("Error de lectura en base de datos");
        }

        return empleado;
    }
    
    @Override
    public boolean validaCodigo(int codigo_acceso) {
        boolean valida = false;
        for (Empleado emp : empleados) {
            if (codigo_acceso == emp.getCodigo()) {
                valida = true;
            } else {
                valida = false;
            }
        }
        return valida;
    }
    
    public void modificarPassword(Empleado empleado, String passwordEmpleado) {
        List<Empleado> listaEmpleado = new ArrayList<Empleado>();
        listaEmpleado.add(empleado);

        for (int i = 0; i < listaEmpleado.size(); i++) {
            if (listaEmpleado.get(i).getCodigo() == empleado.getCodigo()) {
                listaEmpleado.get(i).setPassword(passwordEmpleado);
            }

        }
        sobreescribirBD(empleado, passwordEmpleado);
        
    }

    @Override
    public void sobreescribirBD(Empleado emp, String passwordEmp) {

        String query;
        Statement sentencia = null;
        ResultSet resultado = null;

        try {
            query = "UPDATE empleados SET e_password = \""
                    + emp.getPassword() + "\" WHERE e_codigo = "
                    + emp.getCodigo() + ";";
            Connection conexion = ConexionBD.conectar();
            sentencia = (Statement) conexion.createStatement();
            sentencia.execute(query);

        } catch (SQLException e) {
            System.err.println("problemas con la base de datos.");
        }


    }
    
    
}

