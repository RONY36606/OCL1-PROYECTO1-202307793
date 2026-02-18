/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;

public class gestorBD {
    //todos los objetos de tipo dataBase que tengamos
    private Map<String, dataBase> bases = new HashMap<>();
    //La base de datos que estamos usando actualmente
    private dataBase baseActual;

    // Crear base de datos
    public void crearBaseDeDatos(String nombre, String ruta) {
        dataBase db = new dataBase(nombre, ruta);
        bases.put(nombre, db); //al crear una nueva bd, la metemos en nuestros registros cawn
        baseActual = db;
        guardar(db);
    }

    // Usar base de datos existente
    public void usarBaseDeDatos(String nombre) {
        baseActual = bases.get(nombre);
    }

    // Crear tabla
    public void crearTabla(String nombreTabla) {
        if (baseActual != null) { //si la bd que usamos actualemente existe, ...
            Tabla t = new Tabla(nombreTabla); //creamos una tabla nueva
            baseActual.tablas.put(nombreTabla, t); // y la metemos en el atributo hashmap de nuestra bd
            guardar(baseActual); //SIEMPRE, guardamos los cambios
        }
    }

    // Insertar registro
    public void insertarRegistro(String nombreTabla, Map<String,Object> fila) {
        Tabla t = baseActual.tablas.get(nombreTabla);
        if (t != null) {
            t.rows.add(fila); //si la tabla existe, le podemos añadir la fila nueva con el registro
            guardar(baseActual);//SIEMPRE, guardamos los cambios
        }
    }

    // Leer registros con filtro
    public List<Map<String,Object>> leer(String nombreTabla, String campoFiltro, Object valorFiltro) {
        Tabla t = baseActual.tablas.get(nombreTabla);//siempre tomamos la tabla antes de hacerle más de algo
        List<Map<String,Object>> resultado = new ArrayList<>(); //creamos un resultado para insertar en la tabla después
        if (t != null) {
            for (Map<String,Object> fila : t.rows) {//vamos recorriendo las filas
                if (fila.get(campoFiltro).equals(valorFiltro)) {
                    resultado.add(fila);
                }
            }
        }
        return resultado;
    }

    // Actualizar registros
    public void actualizar(String nombreTabla, String campoFiltro, Object valorFiltro, Map<String,Object> nuevosValores) {
        Tabla t = baseActual.tablas.get(nombreTabla);
        if (t != null) {
            for (Map<String,Object> fila : t.rows) {
                if (fila.get(campoFiltro).equals(valorFiltro)) { //si la línea que vamos a mainuplar es la correcta
                    fila.putAll(nuevosValores);
                }
            }
            guardar(baseActual);//SIEMPRE, guardamos los cambios
        }
    }

    // Limpiar tabla
    public void limpiarTabla(String nombreTabla) {
        Tabla t = baseActual.tablas.get(nombreTabla);
        if (t != null) {
            t.rows.clear();
            guardar(baseActual);//SIEMPRE, guardamos los cambios
        }
    }

    // Exportar resultados
    public void exportar(String nombreArchivo, List<Map<String,Object>> datos) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(nombreArchivo), datos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Guardar base de datos completa en JSON, siempre ir refrescando
    private void guardar(dataBase db) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(db.filePath), db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

