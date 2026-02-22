/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.List;

/**
 *
 * @author Rony
 */
public class lecturaInfo {
    public List<String> campos; //campos seleccionados 
    public String campoFiltro; //campo usado en el filtro 
    public String operador;
    public Object valorFiltro; //valor del filtro 
    public lecturaInfo(List<String> campos, String campoFiltro, String operador, Object valorFiltro) { 
        this.campos = campos; 
        this.campoFiltro = campoFiltro; 
        this.operador = operador;
        this.valorFiltro = valorFiltro; }
}
