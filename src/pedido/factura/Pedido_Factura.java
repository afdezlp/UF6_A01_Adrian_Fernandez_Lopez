
package pedido.factura;

import Conexion.ConexionBD;
import empleado.dominio.Empleado;
import excepciones.passwordIncorrectException;
import excepciones.userIncorrectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import producto.dominio.Producto;
import tienda.control.GestionTienda;
import tienda.vista.VistaTienda;
import static tienda.vista.VistaTienda.muestraMensaje;
import utilidades.Color;


public class Pedido_Factura {
    
    Empleado empleado;

    String archivoPorductos = "productos.txt";

    Scanner leerTeclado = new Scanner(System.in);
    
    ArrayList<Producto> productos = new ArrayList<>();
    
    private static ArrayList<Producto> listaProductos = new ArrayList<Producto>();

    public Pedido_Factura(Empleado loginEmpleado) {
        this.empleado = loginEmpleado;
    }

    public Pedido_Factura() {
    }
    
    public List<Producto> leerProductos() {

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

                listaProductos.add(new Producto(codigo, nombre, descripcion, precio));
                

            }

        } catch (SQLException e) {
            System.out.println("Error al leer los productos en la base de datos "
                    + this.getClass().getName());
        }

        return listaProductos;

    }
    
    public void hacerPedido() throws userIncorrectException, passwordIncorrectException {

        switch (VistaTienda.hacerPedido()) {
            case AÑADIR_PRODUCTO_CESTA:

                leerProductos();

                añadirProducto();
                break;
            case VISUALIZAR_PRECIO_TOTAL_CESTA:
                visualizarCesta(productos);
                break;
            case IMPRIMIR_FACTURA:
                imprimirFactura(productos, empleado);
                break;
            case TERMINAR_PEDIDO:
                finalizarSistema(empleado);
                break;

        }

    }
    
    public void añadirProducto() throws userIncorrectException, passwordIncorrectException {

        if (productos.size() > 0) {
            System.err.println("No podemos agregar más poductos");
            hacerPedido();

        }
        int opcion = pedirOpcionCesta(1, 3);

        int iniciar = 0;
        while (iniciar < opcion) {

            boolean ejecutar = true;
            while (ejecutar) {
                int codigoAcceso = 0;
                imprimirProductos();
                
                System.out.print("ENTRE EL CODIGO DEL PRODUCTO A COMPRAR:  ");
                String nuevoCod = leerTeclado.next();

                try {
                    codigoAcceso = Integer.parseInt(nuevoCod);

                } catch (NumberFormatException e) {

                }

                int z = 0;
                if (validaCodigo(codigoAcceso) && productoIncCesta(codigoAcceso)) {

                    for (int i = 0; i < listaProductos.size(); i++) {

                        if (codigoAcceso == listaProductos.get(i).getCodigo()) {
                            z = z + i;
                            productos.add(listaProductos.get(z));

                            System.out.println(Color.GREEN + "Guardado  correctamente " + Color.DEFAULT);

                            
                                ejecutar = false;
                                System.out.println("\n");

                            
                            

                        }

                    }

                } else if (!validaCodigo(codigoAcceso)) {
                    System.err.println("No existe ese codigo de producto");
                }

            }
            iniciar++;

        }

        hacerPedido();
    
}
    private boolean validaCodigo(int code) {
        boolean ejecutar = false;
        try {

            for (int i = 0; i < listaProductos.size(); i++) {
                if (listaProductos.get(i).getCodigo() == code) {
                    ejecutar = true;

                }

            }
        } catch (InputMismatchException e) {
            System.out.println("Debe introducir un valor numerico entero");
        } catch (Exception d) {
            System.out.println("");

        }

        return ejecutar;

    }
    
    private boolean productoIncCesta(int code) {
        boolean ejecutar = true;

        for (int i = 0; i < productos.size(); i++) {
            if (code == productos.get(i).getCodigo()) {
                System.err.println("El Producto seleccionado ya se encuentra en la cesta");
                ejecutar = false;
            }
        }

        return ejecutar;
    }
    
    private void visualizarCesta(List listaProd) throws userIncorrectException, passwordIncorrectException {

        if (listaProd.size() > 0) {

            double x = 0;
            for (int i = 0; i < listaProd.size(); i++) {

                x = x + this.productos.get(i).getPrecio();

            }
            System.out.println(Color.BLUE + "\n\n------------------------------------------------" + Color.DEFAULT);
            System.out.println("Precio de la cesta : " + x + " € ");
            System.out.println(Color.BLUE + "------------------------------------------------\n\n" + Color.DEFAULT);
            

        } else {
            System.out.println(Color.RED + "LA CESTA ESTA VACIA \n\n");
        }
        hacerPedido();
    }
    
     private void imprimirFactura(List listaProd, Empleado empleado) throws userIncorrectException, passwordIncorrectException {

        if (listaProd.size() > 0) {
            double aux = 0;
            System.out.println("\n\nFACTURA:");

            System.out.println(Color.BLUE + "----------------------------------------" + Color.DEFAULT);
            for (int i = 0; i < listaProd.size(); i++) {
                System.out.printf("Codigo:\t\t%d%nNombre:\t\t%s%nDescripción:\t%s%nPrecio\t\t%.2f%n%n", this.listaProductos.get(i).getCodigo(),
                        this.productos.get(i).getNombre(), this.productos.get(i).getDescripcion(), this.listaProductos.get(i).getPrecio());

            }

            System.out.println(Color.BLUE + "----------------------------------------" + Color.DEFAULT);
            for (int i = 0; i < listaProd.size(); i++) {

                aux = aux + this.productos.get(i).getPrecio();

            }

            System.out.println("El Precio Total es: " + aux + "€");

            System.out.println("Atendido por: " + empleado.getNombre() + " " + empleado.getApellido() + "\n\n");
        } else {
            System.out.println(Color.RED + "NO SE PUEDE IMPRIMIR POR QUE NO HAY PRODUCTOS\n\n" + Color.DEFAULT);
        }
        hacerPedido();
    }

    private void finalizarSistema(Empleado empleadoAuten) throws userIncorrectException, passwordIncorrectException {
        GestionTienda gestionTienda = new GestionTienda();
        gestionTienda.menuPrincipal(empleadoAuten);

    }
    
    void imprimirProductos() {
        System.out.println(Color.BLUE + "\n************************************************" + Color.DEFAULT);
        for (Producto producto : listaProductos) {
            System.out.printf("Codigo:\t\t%d%nNombre:\t\t%s%nDescripción:\t%s%nPrecio\t\t%.2f%n%n", producto.getCodigo(),
                    producto.getNombre(), producto.getDescripcion(), producto.getPrecio());

        }
        System.out.println(Color.BLUE + "************************************************\n" + Color.DEFAULT);
    }

    public void vaciarArray() {
        listaProductos.clear();

    }
    
     private static int pedirOpcionCesta(int min, int max) {

        Scanner leerTeclado = new Scanner(System.in);
        int opcion = 0;
        boolean hayError = true;

        while (hayError) {
            System.out.println("Cuantos productos, desea añadir a la cesta?  [1-3]");
            System.out.print("Seleccione una opción: ");
            if (leerTeclado.hasNextInt()) {
                opcion = leerTeclado.nextInt();
                hayError = opcion < min || opcion > max;
                if (hayError) {
                    muestraMensaje("Error, opción no válida. Debe ser entre [" + min + "," + max + "]", Color.RED);
                }
            } else {
                muestraMensaje("Error, opción no válida. Debe ser entre [" + min + "," + max + "]", Color.RED);
                leerTeclado.next();
            }
        }

        return opcion;
    }

    
    
}
