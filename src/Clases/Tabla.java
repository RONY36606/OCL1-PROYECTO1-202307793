/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;
import java.util.AbstractMap;
import java.util.ArrayList; 
import java.util.List; 
import java.util.Map;
/**
 *
 * @author Rony
 */
public class Tabla {
    String name;
    //cada fila es un objeto de un hash de fila:valor
    List<Map<String, Object>> rows = new ArrayList<>();
    //lista de columnas, cada columna es una de tipo nombre-tipo
    public List<Map.Entry<String,String>> columnas = new ArrayList<>();
    
    public Tabla(String name){
        this.name = name;
    }
    
    //agregar una columna con su tipo 
    public void agregarColumna(String nombreCampo, String tipo) { 
        columnas.add(new AbstractMap.SimpleEntry<>(nombreCampo, tipo)); }
}
