/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;
import java.util.ArrayList; 
import java.util.List; 
import java.util.Map;
/**
 *
 * @author Rony
 */
public class Tabla {
    String name;
    List<Map<String, Object>> rows = new ArrayList<>();
    
    public Tabla(String name){
        this.name = name;
    }
}
