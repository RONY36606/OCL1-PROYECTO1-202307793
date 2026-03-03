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
    
    public expresionFiltro expresion;
    public List<String> campos; //campos seleccionados 
    public lecturaInfo(List<String> campos, expresionFiltro expresion) { 
        this.campos = campos; 
        this.expresion = expresion; 
    }
}
