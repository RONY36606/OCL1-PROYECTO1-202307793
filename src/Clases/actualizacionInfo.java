/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Rony
 */
public class actualizacionInfo {
    public List<Map.Entry<String,Object>> campos;
    public expresionFiltro expresion;
    public actualizacionInfo(List<Map.Entry<String,Object>> campos, expresionFiltro expresion) {
        this.campos = campos;
        this.expresion = expresion;
    }
}
