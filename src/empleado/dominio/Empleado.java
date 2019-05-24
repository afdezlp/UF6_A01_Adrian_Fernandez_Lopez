
package empleado.dominio;


public class Empleado {
    
    private int codigo;
    private String nombre;
    private String apellido;
    private String password;  

    public Empleado(int codigo, String nombre, String apellido, String password) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
    }

    public Empleado() {
        this(0, null,null,null);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
     @Override
    public String toString() {
        return String.format("%d %s %s %s%n",
                getCodigo(), getNombre(), getApellido(), getPassword());
    }
    
}




