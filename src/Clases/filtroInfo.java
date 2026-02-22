/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Rony
 */
//esto encapsula los filtros
public class filtroInfo {
    public String campo; 
    public String operador;
    public Object valor; 
    public filtroInfo(String campo, String operador, Object valor) { 
        this.campo = campo; this.valor = valor; this.operador = operador;}
}
