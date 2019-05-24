
package producto.control;

import Conexion.ConexionBD;
import empleado.dominio.Empleado;
import excepciones.passwordIncorrectException;
import excepciones.userIncorrectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pedido.factura.Pedido_Factura;
import producto.dao.ProductoDAOImp;
import producto.dominio.Producto;
import tienda.vista.VistaTienda;
import utilidades.Color;


public class GestionaProductos {
    
    Scanner leerTeclado = new Scanner(System.in);
    Empleado empleadoValido;
    List<Producto> productos = new ArrayList<>();
    List<Producto> listaProductos = new ArrayList<>();

    public GestionaProductos(Empleado empleadoValido) {
        this.empleadoValido = empleadoValido;
    }
    
    public List<Producto> leerProductos(){
        String query = "SELECT * FROM productos";

        try (
                var conexion = ConexionBD.conectar(); 
                var sentencia = conexion.createStatement(); 
                var resultado = sentencia.executeQuery(query);) {

            
            while (resultado.next()) {
                var codigo = resultado.getInt("p_codigo");
                var nombre = resultado.getString("p_nombre");
                var descripcion = resultado.getString("p_descripcion");
                var precio = resultado.getDouble("p_precio");

                productos.add(new Producto(codigo, nombre, descripcion, precio));
                listaProductos.add(new Producto(codigo, nombre, descripcion, precio));

            }

        } catch (SQLException e) {
            System.out.println("Error al leer los productos en la base de datos "
                    + this.getClass().getName());
        }

        return productos;

    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
    public boolean validarCodigo(int codigoProducto) {
        boolean validaCodProd = false;
        for (int i= 0; i< productos.size(); i++) {
            if (codigoProducto == productos.get(i).getCodigo()) {
                validaCodProd = true;
            } 
        }
        return validaCodProd;
    }
    
    public void cambiarCodigo() throws userIncorrectException, passwordIncorrectException {

        int nuevoCodProd = 0;
        boolean validnuevoCodProd = false;
        boolean validaFormato = true;

        while (!validnuevoCodProd) {

            validaFormato = true;
            listaArchivoProductos();
            System.out.print("Introduce el codigo del Producto: ");
            String nuevoCodProdtext = leerTeclado.nextLine();

            try {
                nuevoCodProd = Integer.parseInt(nuevoCodProdtext);

            } catch (NumberFormatException e) {
                System.err.println("Error - Introduce un valor numerico\n");
                validaFormato = false;
            }

            if (validaFormato) {
                if (validarCodigo(nuevoCodProd)) {

                    validnuevoCodProd = true;
                } else {
                    System.err.println("El codigo del producto no existe\n");
                }
            }
        }

        boolean cambiarCodProd = true;
        while (cambiarCodProd) {

            boolean codigoExistente = false;
            System.out.println("Introduzca el nuevo codigo del Producto: ");
            int nuevoCodigo = leerTeclado.nextInt();

            for (Producto prod : productos) {
                if (nuevoCodigo == prod.getCodigo()) {
                    codigoExistente = true;
                }
            }
            if (!codigoExistente) {
                var nuevoProducto = new ProductoDAOImp();
                nuevoProducto.modificarCodigoProd(nuevoCodProd, nuevoCodigo);
                System.out.println(Color.GREEN + "Cambiado exitosamente" + Color.DEFAULT);
                cambiarCodProd = false;
            } else {
                System.err.println("Ha introducido un codigo que ya pertenece a un Producto");
            }
        }
        
        VistaTienda.modificarProducto(empleadoValido);

    }
    
    public void cambiarNombre() throws userIncorrectException, passwordIncorrectException {

        int nuevoCodProd = 0;
        boolean validnuevoCodProd = false;
        boolean validaFormato = true;

        while (!validnuevoCodProd) {

            validaFormato = true;
            listaArchivoProductos();
            System.out.print("INTRODUCE EL CODIGO DEL PRODUCTO: ");
            String nuevoCodProdtext = leerTeclado.nextLine();

            try {
                nuevoCodProd = Integer.parseInt(nuevoCodProdtext);

            } catch (NumberFormatException e) {
                System.err.println("Error - Introduce un valor numerico\n");
                validaFormato = false;
            }

            if (validaFormato) {
                if (validarCodigo(nuevoCodProd)) {

                    validnuevoCodProd = true;
                } else {
                    System.err.println("El codigo del producto no existe\n");
                }
            }
        }
        System.out.println("INTRODUZCA UN NUEVO NOMBRE PARA EL PRODUCTO ");

        String nuevoNombre = leerTeclado.nextLine();

        var nuevoProducto = new ProductoDAOImp();
        nuevoProducto.modificarNombreProd(nuevoCodProd, nuevoNombre);
        System.out.println(Color.GREEN + "Nombre cambiado exitosamente" + Color.DEFAULT);

        
        VistaTienda.modificarProducto(empleadoValido);

    }
    
    public void cambiarPrecio() throws userIncorrectException, passwordIncorrectException {

        int nuevoCodProd = 0;
        boolean validnuevoCodProd = false;
        boolean validaFormato = true;

        while (!validnuevoCodProd) {

            validaFormato = true;
            listaArchivoProductos();
            System.out.print("INTRODUZCA EL CODIGO DEL PRODUCTO: ");
            String nuevoCodProdtext = leerTeclado.nextLine();

            try {
                nuevoCodProd = Integer.parseInt(nuevoCodProdtext);

            } catch (NumberFormatException e) {
                System.err.println("Error - Introduce un valor numerico\n");
                validaFormato = false;
            }

            if (validaFormato) {
                if (validarCodigo(nuevoCodProd)) {

                    validnuevoCodProd = true;
                } else {
                    System.err.println("El codigo del producto no existe\n");
                }
            }
        }
        
        System.out.println("INTRODUZCA UN NUEVO PRECIO PARA EL PRODUCTO ");
        double precioProd = 0;
        String nuevoCodigo = leerTeclado.next();
        try {
            precioProd = Double.parseDouble(nuevoCodigo);

        } catch (NumberFormatException e) {
            System.err.println("Error - Introduzca un valor numerico double\n");
            validaFormato = false;
        }

        var nuevoProd = new ProductoDAOImp();
        nuevoProd.modificarPrecioProd(nuevoCodProd, precioProd);

        System.out.println(Color.GREEN + "Precio cambiado exitosamente" + Color.DEFAULT);

        //para volver a la pantalla de producto
        VistaTienda.modificarProducto(empleadoValido);
        Pedido_Factura pedido = new Pedido_Factura();
        pedido.leerProductos();
    }
    
    public void listaArchivoProductos() {
        System.out.println(Color.BLUE + "\n************************************************"+Color.DEFAULT);
        for (Producto prod : productos) {
            System.out.printf("Codigo:\t\t%d%nNombre:\t\t%s%nDescripción:\t%s%nPrecio\t\t%.2f%n%n", 
                    prod.getCodigo(),
                    prod.getNombre(), prod.getDescripcion(), prod.getPrecio());

        }
        System.out.println(Color.BLUE + "************************************************\n"+Color.DEFAULT);
    }

    }

