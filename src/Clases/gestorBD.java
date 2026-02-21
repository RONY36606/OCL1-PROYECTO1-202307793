/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

//PARA LA CREACIÓN DE LOS ARCHIVOS DE TIPO JSON, USAREMOS LA LIBRERÍA GSON de google
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//Clase GestorBD
public class gestorBD {
    //todos los objetos de tipo dataBase que tengamos
    private Map<String, dataBase> bases = new HashMap<>();
    //La base de datos que estamos usando actualmente
    private dataBase baseActual;
    //Objeto Gson para convertir a JSON
    private Gson gson = new Gson();
    //ultimo read visto
    private List<Map<String,Object>> ultimaLectura = new ArrayList<>();
    //geter
    public List<Map<String,Object>> getUltimaLectura() {
            return ultimaLectura;
    }   

    // Crear base de datos
    public void crearBaseDeDatos(String nombre, String ruta) {
        String rutaLimpia = ruta.replace("\"", "");
        dataBase db = new dataBase(nombre, rutaLimpia);
        bases.put(nombre, db); //al crear una nueva bd, la metemos en nuestros registros cawn
        baseActual = db;
        guardar(db);
    }

    // Usar base de datos existente
    public void usarBaseDeDatos(String nombre) {
        baseActual = bases.get(nombre);
    }

    // Crear tabla
    public void crearTabla(String nombreTabla, List<Map.Entry<String,String>> columnas) {
        if (baseActual != null) { //si la bd que usamos actualemente existe, ...
            Tabla t = new Tabla(nombreTabla); //creamos una tabla nueva
            //guardar las columnas en la tabla 
            for (Map.Entry<String,String> col : columnas) { 
                t.agregarColumna(col.getKey(), col.getValue()); }
            baseActual.tablas.put(nombreTabla, t); // y la metemos en el atributo hashmap de nuestra bd
            guardar(baseActual); //SIEMPRE, guardamos los cambios
        }
    }

    // Insertar registro
    public void insertarRegistro(String nombreTabla, Map<String,Object> fila) {
        Tabla t = baseActual.tablas.get(nombreTabla);
        if (t != null) {
            // Limpiar comillas de todos los valores string
            Map<String,Object> filaLimpia = new HashMap<>();
            for (Map.Entry<String,Object> entry : fila.entrySet()) {
                Object valor = entry.getValue();
                if (valor instanceof String) {
                    valor = ((String) valor).replace("\"", "");
                }
                filaLimpia.put(entry.getKey(), valor);
            }
            t.rows.add(filaLimpia); //si la tabla existe, le podemos añadir la fila nueva con el registro
            guardar(baseActual);//SIEMPRE, guardamos los cambios
        }
    }

    // Leer registros con filtro
    public List<Map<String,Object>> leer(String nombreTabla, String campoFiltro, Object valorFiltro) {
        Tabla t = baseActual.tablas.get(nombreTabla);//siempre tomamos la tabla antes de hacerle más de algo
        List<Map<String,Object>> resultado = new ArrayList<>(); //creamos un resultado para insertar en la tabla después
        if (t != null) {
            for (Map<String,Object> fila : t.rows) {//vamos recorriendo las filas
                if (campoFiltro == null || fila.get(campoFiltro).equals(valorFiltro)) {
                    resultado.add(fila);
                }
            }
        }
        ultimaLectura = resultado;
        return resultado;
    }

    // Actualizar registros
    public List<Map<String,Object>> actualizar(String nombreTabla, List<Map.Entry<String,Object>> campos, String campoFiltro, Object valorFiltro) {
    Tabla t = baseActual.tablas.get(nombreTabla);
    List<Map<String,Object>> resultado = new ArrayList<>();
    if (t != null) {
        for (Map<String,Object> fila : t.rows) {
            if (campoFiltro == null || fila.get(campoFiltro).equals(valorFiltro)) { //varifcamos que la línea a manipular sea la correcta
                // aplicar actualizaciones
                for (Map.Entry<String,Object> entry : campos) {
                    fila.put(entry.getKey(), entry.getValue());
                }
                resultado.add(fila);
            }
        }
        guardar(baseActual);//SIEMPRE GUARDAMOS LOS CAMBIOS
    }
    return resultado;
}


    // Limpiar tabla
    public void limpiarTabla(String nombreTabla) {
        Tabla t = baseActual.tablas.get(nombreTabla);
        if (t != null) {
            t.rows.clear(); //Eliminamos todas las líneas de la tabla
            guardar(baseActual);//SIEMPRE, guardamos los cambios
        }
    }

    // Exportar resultados o consultas
    public void exportar(String nombreArchivo, List<Map<String,Object>> datos) {
        String nombreLimpio = nombreArchivo.replace("\"", "");
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            gson.toJson(datos, writer); //convertimos la lista de resultados a JSON
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Exportar sin datos
    public void exportar(String nombreArchivo) {
        String nombreLimpio = nombreArchivo.replace("\"", "");
        exportar(nombreLimpio, ultimaLectura);
    }

    // Guardar base de datos completa en JSON, siempre ir refrescando
    private void guardar(dataBase db) {
        try (FileWriter writer = new FileWriter(db.filePath)) {
            gson.toJson(db, writer); //convertimos la base de datos completa a JSON
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


