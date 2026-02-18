package Clases;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.HashMap; 
import java.util.Map;

/**
 *
 * @author Rony
 */
public class dataBase {
    String nombre;
    String filePath;
    Map<String, Tabla> tables = new HashMap<>(); 
    public dataBase(String name, String filePath) { 
        this.nombre = name; 
        this.filePath = filePath; 
    }
}
