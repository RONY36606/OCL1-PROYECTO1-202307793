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
    // Variables de contexto para export, para incluír el esquema y nombre de la tabla
    private String ultimaTablaLeida = "";
    private List<String> ultimosCamposLeidos = new ArrayList<>();
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
            Map<String,Object> filaLimpia = new LinkedHashMap<>();
        for (Map.Entry<String,Object> entry : fila.entrySet()) {
            String campo = entry.getKey();
            Object valor = entry.getValue();
            // Buscar el tipo del campo en el schema
            String tipo = obtenerTipo(t, campo);
            if (tipo != null) {
                if (tipo.equals("int")) valor = Integer.parseInt(valor.toString());
                else if (tipo.equals("float")) valor = Double.parseDouble(valor.toString());
                else if (valor instanceof String) valor = ((String)valor).replace("\"", "");
            }
            filaLimpia.put(campo, valor);
        }
        t.rows.add(filaLimpia);
        guardar(baseActual);
        }
    }
    //=========================0FUNCIÓN AUXILIAR PARA VERIFICAR EL TIPO DE DATO=================0
    private String obtenerTipo(Tabla t, String campo) {
    for (Map.Entry<String,String> col : t.columnas) {
        if (col.getKey().equals(campo)) return col.getValue();
    }
    return null;
}
    // Leer registros con filtro
    public List<Map<String,Object>> leer(String nombreTabla, List<String> campos, String campoFiltro, String operador, Object valorFiltro) {
        Tabla t = baseActual.tablas.get(nombreTabla);//siempre tomamos la tabla antes de hacerle más de algo
        List<Map<String,Object>> resultado = new ArrayList<>(); //creamos un resultado para insertar en la tabla después
        if (t != null) {
            for (Map<String,Object> fila : t.rows) {//vamos recorriendo las filas
                if (campoFiltro == null || evaluarFiltro(fila, campoFiltro, operador, valorFiltro)) {
                // Solo incluir los campos seleccionados
                Map<String,Object> filaFiltrada = new LinkedHashMap<>();
                for (String campo : campos) {
                    filaFiltrada.put(campo, fila.get(campo));
                }
                resultado.add(filaFiltrada);
            }
            }
        }
        //guardamos la última información obtenida
        ultimaTablaLeida = nombreTabla;
        ultimosCamposLeidos = campos;
        ultimaLectura = resultado;
        return resultado;
    }
    
    //Esta función sirve para valiar las expresiones de los filtros que crearemos
    private boolean evaluarFiltro(Map<String,Object> fila, String campo, 
                               String operador, Object valor) {
    Object val = fila.get(campo);
    if (val == null) return false;
    
    try {
        double v1 = Double.parseDouble(val.toString());
        double v2 = Double.parseDouble(valor.toString());
        switch (operador) {
            case "==": return v1 == v2;
            case "!=": return v1 != v2;
            case ">":  return v1 > v2;
            case "<":  return v1 < v2;
            case ">=": return v1 >= v2;
            case "<=": return v1 <= v2;
        }
    } catch (NumberFormatException e) {
        // Comparación de strings
        String s1 = val.toString().replace("\"", "");
        String s2 = valor.toString().replace("\"", "");
        switch (operador) {
            case "==": return s1.equals(s2);
            case "!=": return !s1.equals(s2);
        }
    }
    return false;
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

    
    //Exportar sin datos
    public void exportar(String nombreArchivo) {
        String nombreLimpio = nombreArchivo.replace("\"", "");
        Tabla t = baseActual.tablas.get(ultimaTablaLeida);
        if (t == null) {
            System.err.println("Error: no hay lectura previa para exportar");
            return;
        }
        
        // Construir objeto de exportación con la estructura correcta
        Map<String, Object> exportObj = new LinkedHashMap<>();
        exportObj.put("table", ultimaTablaLeida);

        // campos con sus tipos desde el esquema de la tabla
        Map<String, String> fields = new LinkedHashMap<>();
        for (String campo : ultimosCamposLeidos) {
            String tipo = obtenerTipo(t, campo);
            fields.put(campo, tipo != null ? tipo : "string");
        }
        exportObj.put("fields", fields);
        
        //ahora toca meter lo de los records
        // solo los campos seleccionados en cada registro
        List<Map<String,Object>> recordsFiltrados = new ArrayList<>();
        for (Map<String,Object> registro : ultimaLectura) {
            Map<String,Object> r = new LinkedHashMap<>(); //un diccionario auxiliar para meter los compos que han sido leídos
            for (String campo : ultimosCamposLeidos) {
                r.put(campo, registro.get(campo));
            }
            recordsFiltrados.add(r);
        }
        exportObj.put("records", recordsFiltrados);
        
        try (FileWriter writer = new FileWriter(nombreLimpio)) {
        new Gson().toJson(exportObj, writer);
        System.out.println("Exportación exitosa: " + nombreLimpio);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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


