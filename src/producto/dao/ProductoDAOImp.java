
package producto.dao;

import Conexion.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import producto.dominio.Producto;


public class ProductoDAOImp implements ProductoDAO{
    
    List<Producto> productos;

    public ProductoDAOImp() {
        
        List<Producto> listaProductos = new ArrayList<>();
        String query = "SELECT * FROM productos";
        Statement sentencia;
        ResultSet resultado = null;
        

        try {
            Connection conexion = ConexionBD.conectar();
            sentencia = (Statement) conexion.createStatement();
            resultado = sentencia.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("fallo al conectar con la base de datos ");
        } finally {

            int codigoProducto = 0;
            String nombreProducto = null;
            String descripcionProducto = null;
            double precioProducto = 0.0;

            try {

                while (resultado.next()) {
                    codigoProducto = resultado.getInt("p_codigo");
                    nombreProducto = resultado.getString("p_nombre");
                    descripcionProducto = resultado.getString("p_descripcion");
                    precioProducto = resultado.getDouble("p_precio");

                    listaProductos.add(new Producto(codigoProducto, nombreProducto, descripcionProducto, precioProducto));
                }
            } catch (SQLException ex) {
                System.err.println("no se puede acceder a la base de datos");
            }

        }

        setProductos(listaProductos);

    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
    

    
    @Override
    public List<Producto> leerProductos() {

        Producto productosObject = new Producto();
        List<Producto> listaProductos = new ArrayList<>();

        try {
            var conexion = ConexionBD.conectar();

            Statement sentencia = conexion.createStatement();

            ResultSet resultado = sentencia.executeQuery("SELECT * FROM productos;");

            while (resultado.next()) {

                while (resultado.next()) {
                    int codigo = resultado.getInt("p_codigo");
                    String nombre = resultado.getString("p_nombre");
                    String descripcion = resultado.getString("p_descripcion");
                    Double precio = resultado.getDouble("p_precio");
                    
                    productosObject = new Producto (codigo, nombre, descripcion, precio);
                    listaProductos.add(new Producto(codigo, nombre, descripcion, precio));
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error de lectura en: ");

        }
        return (List<Producto>) productosObject;
    }
    
    public void modificarNombreProd(int codigo_producto, String nuevoNombre) {
        
        Producto prod = new Producto();
        for( int i = 0; i < productos.size();i++){
            if(productos.get(i).getCodigo() == codigo_producto){
                prod.setCodigo(productos.get(i).getCodigo());
                prod.setDescripcion(productos.get(i).getDescripcion());
                prod.setPrecio(productos.get(i).getPrecio());
                prod.setNombre(nuevoNombre);
                
                productos.set(i,prod);
            }
        }
        sobreescribirBD(codigo_producto, prod);
    }

    
    public void modificarCodigoProd(int codigo_producto, int nuevoCodigo) {
        
        Producto prod = new Producto();
        for( int i = 0; i < productos.size();i++){
            if(productos.get(i).getCodigo() == codigo_producto){
                prod.setNombre(productos.get(i).getNombre());
                prod.setDescripcion(productos.get(i).getDescripcion());
                prod.setPrecio(productos.get(i).getPrecio());
                prod.setCodigo(nuevoCodigo);
                
                productos.set(i,prod);
            }
        }
        sobreescribirBD(codigo_producto, prod);
    }

    
    public void modificarPrecioProd(int codigo_producto, double nuevoPrecio) {
        
        Producto prod = new Producto();
        for( int i = 0; i < productos.size();i++){
            if(productos.get(i).getCodigo() == codigo_producto){
                prod.setCodigo(productos.get(i).getCodigo());
                prod.setNombre(productos.get(i).getNombre());
                prod.setDescripcion(productos.get(i).getDescripcion());
                prod.setPrecio(nuevoPrecio);
                
                productos.set(i,prod);
            }
        }
        sobreescribirBD(codigo_producto, prod);
    }

    
    
    
    @Override
    public void sobreescribirBD(int codigoProd, Producto producto) {
        String query;
        Statement sentencia = null;
        ResultSet resultado = null;

        try {
            query = "UPDATE productos SET "
                    + "p_codigo = " + producto.getCodigo() + ", "
                    + "p_nombre = " + "\"" + producto.getNombre() + "\"" + ", "
                    + "p_descripcion = " + "\"" + producto.getDescripcion() + "\"" + ", "
                    + "p_precio = " + producto.getPrecio() + " "
                    + "WHERE p_codigo = " + codigoProd;
            

            Connection conexion = ConexionBD.conectar();
            sentencia = (Statement) conexion.createStatement();
            sentencia.execute(query);

        } catch (SQLException e) {
            System.err.println("no se puede conectara a las bases de datos");
        }

       
        productos = (new ProductoDAOImp()).leerProductos();
        
    }

    
    
    

    
    
    
    
}
