
package empleado.dao;

import empleado.dominio.Empleado;
import java.util.List;


public interface EmpleadoDAO {
    
    List<Empleado> leerEmpleados();  

    Empleado getEmpleadoPorCodigo(int codigo);
    
    boolean validaCodigo(int codigo_acceso);
    
    void sobreescribirBD(Empleado emp, String passwordEmp);
}
