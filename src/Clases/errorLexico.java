/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Rony
 */
public class errorLexico {
    private String lexema;      
    private int linea;          
    private int columna;        
    private String descripcion; 

    public errorLexico(String lexema, int linea, int columna, String descripcion) {
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;
        this.descripcion = descripcion;
    }

    //Solo le metemos guetters
    public String getLexema() { return lexema; }
    public int getLinea() { return linea; }
    public int getColumna() { return columna; }
    public String getDescripcion() { return descripcion; }

    // y un toString
    @Override
    public String toString() {
        return "ErrorLexico{" +
                "lexema='" + lexema + '\'' +
                ", linea=" + linea +
                ", columna=" + columna +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }


}
