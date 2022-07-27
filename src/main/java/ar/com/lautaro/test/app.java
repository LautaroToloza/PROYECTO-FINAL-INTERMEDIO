package ar.com.lautaro.test;

import ar.com.lautaro.DAO.UsuarioDAOImp;
import ar.com.lautaro.clases.Usuario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class app {

    /*
    Implementar las siguientes acciones:
    Crear un menú de opciones(con las siguientes acciones) 
    1)Ordenar a elección la lista de envió por alguno de sus características
    2)Solicitar mensaje de los Usuario para guardalo en un txt
    Dejando solamente sus nombre y apellidos en mayuscula 
    3)Solicitar una opcion para guardar los usuarios en un archivo .dat
    4)Recuperar el txt
    5)Recuperar la lista del .dat
    6)Consultar por un usuario ingresando su id y en caso de encontrarlo devolver todos sus datos 
     */
    public static void main(String[] args) {
        ejecutarPrograma();

    }

    public static int cargarNumero(String texto) {
        Scanner leer = new Scanner(System.in);
        System.out.println(texto);
        int numero = leer.nextInt();
        return numero;

    }

    public static String cargarTexto(String texto) {
        Scanner leer = new Scanner(System.in);
        System.out.println(texto);
        String tex = leer.nextLine();
        return tex;

    }

    public static void menuCRUD() {
        System.out.println("1: Registrar a la base de datos la lista de usuarios ingresada.");
        System.out.println("2: Mostrar todos los usuarios.");
        System.out.println("3: Modificar el número de teléfono de un usuario.");
        System.out.println("4: Eliminar un usuario de la base de datos.");
        System.out.println("5: Salir del menú de opciones.");
    }

    public static void escritura(List<Usuario> lista, String direc) throws IOException {
        List<String> usar = lista.stream().map(x -> x.getNombre().toUpperCase() + "|" + x.getApellido().toUpperCase() + "\n").collect(Collectors.toList());
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String fechaEscrita = sdf.format(fecha);
        // Poner el true para no perder la información!!
        BufferedWriter bw = new BufferedWriter(new FileWriter(direc, true));
        bw.write(fechaEscrita + "\n");
        bw.write("-----------------------------------------------------------------------" + "\n");
        for (String e : usar) {
            bw.write(e);
        }
        bw.write("-----------------------------------------------------------------------" + "\n");
        System.out.println("Se escribió el archivo de texto!!!");
        bw.close();

    }

    public static void leer(String direccion) throws FileNotFoundException, IOException {

        FileReader fr = new FileReader(direccion);
        BufferedReader bf = new BufferedReader(fr);
        String linea = bf.readLine();
        while (linea != null) {
            System.out.println(linea);
            linea = bf.readLine();

        }

        bf.close();
        fr.close();

    }

    public static void escrituraDAT(String direc, List<Usuario> lista) throws FileNotFoundException, IOException {

        // no le ponemos el 'true' porque queremos pisar los datos.
        FileOutputStream fo = new FileOutputStream(direc);
        ObjectOutputStream oo = new ObjectOutputStream(fo);
        // writeObject()-> Solo en escritura de objetos 
        oo.writeObject(lista);
        fo.close();
        oo.close();

        System.out.println("Se escribio el archivo dat!!");

    }

    public static void lecturaDAT(String direc) throws FileNotFoundException, IOException, ClassNotFoundException {

        FileInputStream fi = new FileInputStream(direc);
        ObjectInputStream oi = new ObjectInputStream(fi);
        List<Usuario> lista = (List<Usuario>) oi.readObject();
        lista.forEach(System.out::println);
        fi.close();
        oi.close();

    }

    public static void menuArchivos() {

        System.out.println("0: Salir del menú de archivos.");
        System.out.println("1: Ordenar la lista de usuarios alfabeticamente por el nombre.");
        System.out.println("2: Crear un archivo de texto con la lista de los usuarios.");
        System.out.println("3: Crear un archivo 'dat' con la lista de los usuarios.");
        System.out.println("4: Leer el archivo de texto.");
        System.out.println("5: Leer el archivo 'dat'.");
        System.out.println("6: Devolver todos los datos del usuario ingresando su id.");
    }

    public static void ejecutarPrograma() {
        List<Usuario> usuarioEnvio = new ArrayList<>();
        usuarioEnvio.add(new Usuario("Zoe", "Mancini", "zoe@gmail.com", "35125251"));
        usuarioEnvio.add(new Usuario("Martin", "Maldonado", "martin@gmail.com", "3516125451"));
        usuarioEnvio.add(new Usuario("Marcos", "Maldonado", "marcos@gmail.com", "3516156451"));
        usuarioEnvio.add(new Usuario("Zebastian", "Maldonadivia", "mateo@gmail.com", "3514925451"));
        usuarioEnvio.add(new Usuario("Matias", "Marro", "matias@gmail.com", "351651351"));
        usuarioEnvio.add(new Usuario("Manuel", "Mujica", "manuel@gmail.com", "3516125888"));
        usuarioEnvio.add(new Usuario("zul", "Maldonado", "azul@gmail.com", "351612451"));

        List<Usuario> listaUsuarios = new ArrayList<>();

        UsuarioDAOImp usuarioDAO = new UsuarioDAOImp();
        boolean seRegistro = false;

        try {

            System.out.println("\nBienvenido al menú de opciones del CRUD..");
            menuCRUD();
            int op = cargarNumero("\nIngrese su opción: ");
            while (op != 5) {
                switch (op) {
                    case 1:
                        usuarioDAO.registrar(usuarioEnvio);
                        seRegistro = true;
                        break;
                    case 2:
                        listaUsuarios = usuarioDAO.recuperar();
                        break;
                    case 3:
                        String nombre = cargarTexto("Ingrese el nombre del usuario que quiere modificar el número de telefono: ");
                        String telefono = cargarTexto("Ingrese el número de telefono: ");
                        usuarioDAO.modificar(new Usuario(nombre, "", "", telefono));
                        break;
                    case 4:
                        String nombreDos = cargarTexto("Ingrese el nombre del usuario que quiere eliminar: ");
                        usuarioDAO.eliminar(new Usuario(nombreDos, "", "", ""));
                        break;
                    default:
                        System.out.println("Ingresó una opción incorrecta, intente nuevamente.");

                }
                System.out.println("\nMenú de opciones:");
                menuCRUD();
                op = cargarNumero("\nIngrese su opción: ");
            }
            System.out.println("\n-------------------------------------------------------");
            System.out.println("Salió exitosamente del menú de opciones del CRUD!!");

            // Segunda Parte de archivos.
            String direccion = "ListaUsuarios.txt";
            String direccionDos = "ListaUsuariosDAT.dat";
            System.out.println("\n-------------------------------------------------------");
            System.out.println("Bienvenido al menú de opciones de archivos.");
            menuArchivos();
            int opDos = cargarNumero("\nIngrese su opción: ");
            // Copia de los ultimos usuarios cargados, si la carga de usuarios es manual, agregar un contador.
            if (listaUsuarios.isEmpty()) {
                listaUsuarios = usuarioDAO.SegundoMenu();
            }
            int cantidad = listaUsuarios.size();
            int inicio = cantidad - 7;
            List<Usuario> ultimosRegistrados = new ArrayList<>();
            for (int i = inicio; i < listaUsuarios.size(); i++) {
                ultimosRegistrados.add(listaUsuarios.get(i));

            }

            try {

                while (opDos != 0) {

                    switch (opDos) {
                        case 1:
                            // Ordenar por nombre.
                            Collections.sort(ultimosRegistrados, (Usuario o1, Usuario o2) -> o1.getNombre().toLowerCase().compareTo(o2.getNombre().toLowerCase()));
                            System.out.println("Se ordenó la lista!!");
                            break;
                        case 2:

                            escritura(ultimosRegistrados, direccion);
                            break;
                        case 3:
                            escrituraDAT(direccionDos, ultimosRegistrados);
                            break;
                        case 4:
                            leer(direccion);
                            break;
                        case 5:
                            lecturaDAT(direccionDos);
                            break;
                        case 6:

                            if (listaUsuarios.isEmpty()) {
                                System.out.println("No tiene ningún usuario cargado en su base de datos!!");
                            } else {
                                int id = cargarNumero("Ingrese el numero del id a buscar: ");
                                boolean encontro = false;
                                for (Usuario usuario : listaUsuarios) {
                                    if (usuario.getId() == id) {
                                        System.out.println(usuario);
                                        encontro = true;

                                    }
                                    if (encontro) {
                                        break;
                                    }

                                }
                                if (encontro == false) {
                                    System.out.println("No se encontro ningún usuario con ese id.");
                                }
                            }

                            System.out.println("\nFinalizó la busqueda por id!!");
                            break;
                        default:
                            System.out.println("Ingresó una opción incorrecta, intente nuevamente.");

                    }
                    System.out.println("\nMenú de opciones:");
                    menuArchivos();
                    opDos = cargarNumero("\nIngrese su opción: ");
                }
                System.out.println("\n-------------------------------------------------------");
                System.out.println("FINALIZO EL PROGRAMA CORRECTAMENTE!!");

            } catch (IOException ex) {
                System.out.println("Error en los archivos.");
                ex.printStackTrace(System.out);
            }
        } catch (SQLException ex) {
            System.out.println("Error de la aplicacion");
            ex.printStackTrace(System.out);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error en la conexion");
            ex.printStackTrace(System.out);
        } catch (InputMismatchException ex) {
            System.out.println("Error, ingreso un caracter no numeral.");
            ex.printStackTrace(System.out);
        }
    }

}
