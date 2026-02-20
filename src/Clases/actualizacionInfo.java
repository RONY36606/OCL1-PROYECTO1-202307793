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
    public String campoFiltro; 
    public Object valorFiltro; 
    public actualizacionInfo(List<Map.Entry<String,Object>> campos, String campoFiltro, Object valorFiltro) 
    { this.campos = campos; this.campoFiltro = campoFiltro; this.valorFiltro = valorFiltro; 
    }
}
