/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Rony
 */
public class expresionFiltro {
    // Para expresiones simples de tipo  edad > 20 y así
    public String campo;
    public String operador;
    public Object valor;
    
    // Para expresiones compuestas con partes varidas como  izq && der
    public expresionFiltro izquierda;
    public String operadorLogico; // "&&", "||", "!"
    public expresionFiltro derecha;
    
    public boolean esCompuesta; //esto nos comprobará si es compuesta o simple xd
    public boolean esNegacion;
    
    // Constructor para expresión simple
    public expresionFiltro(String campo, String operador, Object valor) {
        this.campo = campo;
        this.operador = operador;
        this.valor = valor;
        this.esCompuesta = false;
        this.esNegacion = false;
    }
    
    // Constructor para expresión compuesta con operadores -> (&&, ||)
    public expresionFiltro(expresionFiltro izquierda, String operadorLogico, expresionFiltro derecha) {
        this.izquierda = izquierda;
        this.operadorLogico = operadorLogico;
        this.derecha = derecha;
        this.esCompuesta = true;
        this.esNegacion = false;
    }
    
    // Constructor para negación , osease con esto ->(!)
    public expresionFiltro(expresionFiltro expresion) {
        this.izquierda = expresion;
        this.esNegacion = true;
        this.esCompuesta = false;
    }
}
