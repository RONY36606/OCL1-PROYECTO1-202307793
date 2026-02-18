/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;

public class gestorBD {
    private Map<String, dataBase> bases = new HashMap<>();
    private dataBase baseActual;

    // Crear base de datos
    public void crearBaseDeDatos(String nombre, String ruta) {
        dataBase db = new dataBase(nombre, ruta);
        bases.put(nombre, db);
        baseActual = db;
        guardar(db);
    }

    // Usar base de datos existente
    public void usarBaseDeDatos(String nombre) {
        baseActual = bases.get(nombre);
    }

    // Crear tabla
    public void crearTabla(String nombreTabla) {
        if (baseActual != null) {
            Tabla t = new Tabla(nombreTabla);
            baseActual.tables.put(nombreTabla, t);
            guardar(baseActual);
        }
    }

    // Insertar registro
    public void insertarRegistro(String nombreTabla, Map<String,Object> fila) {
        Tabla t = baseActual.tables.get(nombreTabla);
        if (t != null) {
            t.rows.add(fila);
            guardar(baseActual);
        }
    }

    // Leer registros con filtro
    public List<Map<String,Object>> leer(String nombreTabla, String campoFiltro, Object valorFiltro) {
        Tabla t = baseActual.tables.get(nombreTabla);
        List<Map<String,Object>> resultado = new ArrayList<>();
        if (t != null) {
            for (Map<String,Object> fila : t.rows) {
                if (fila.get(campoFiltro).equals(valorFiltro)) {
                    resultado.add(fila);
                }
            }
        }
        return resultado;
    }

    // Actualizar registros
    public void actualizar(String nombreTabla, String campoFiltro, Object valorFiltro, Map<String,Object> nuevosValores) {
        Tabla t = baseActual.tables.get(nombreTabla);
        if (t != null) {
            for (Map<String,Object> fila : t.rows) {
                if (fila.get(campoFiltro).equals(valorFiltro)) {
                    fila.putAll(nuevosValores);
                }
            }
            guardar(baseActual);
        }
    }

    // Limpiar tabla
    public void limpiarTabla(String nombreTabla) {
        Tabla t = baseActual.tables.get(nombreTabla);
        if (t != null) {
            t.rows.clear();
            guardar(baseActual);
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

    // Guardar base de datos completa en JSON
    private void guardar(dataBase db) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(db.filePath), db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

