/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
import Clases.errorLexico;
import Clases.token;
import java.io.*;
import javax.swing.*;
//imports de la interfaz, linked list y de la tabla, para los reportes
import Interfaz.interfazInicial;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
//import del lexer
import olc1.proyecto1.pkg202307793.Lexico;
import olc1.proyecto1.pkg202307793.Sintactico;
//import de sym, necesario
import olc1.proyecto1.pkg202307793.sym;


/**
 *
 * @author Rony
 */
public class controladorInterfaz {
    
    private interfazInicial vista;
    private File archivoActual; //archivo que se marcará como el que está en uso

    public controladorInterfaz(interfazInicial vista) {
        this.vista = vista;
        inicializarEventos();
    }

    private void inicializarEventos() {
        //iniciamos los eventos para escuchar qué botones se van a presionar
        vista.crear_archivo_btn.addActionListener(e -> crearNuevoArchivo());
        vista.guardar_archivo_btn.addActionListener(e -> guardarArchivo());
        vista.abrir_archivo_btn.addActionListener(e -> abrirArchivo());
        vista.ejecutar_btn.addActionListener(e -> EjecutarProgramaYgenerarReportes());
    }

    private void crearNuevoArchivo() {
        //creamos un archivo dentro de una carpeta
        //chooser será la entidad que creará el cuadro de explorador de archivos
        JFileChooser chooser = new JFileChooser();
        //este es el título del cuadro del archivo
        chooser.setDialogTitle("Crear nuevo archivo .eli");
        //nombre del nuevo archivo que vamos acrear
        chooser.setSelectedFile(new File("nuevo.eli"));
        //
        if (chooser.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
            //archivo actual será tomado como el archivo que se está ejecutando
            archivoActual = chooser.getSelectedFile();
            try {
                if (archivoActual.createNewFile()) {
                    JOptionPane.showMessageDialog(vista, "Archivo creado: " + archivoActual.getName());
                }
                vista.entrada_programa.setText(""); // limpiar área de entrada
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(vista, "Error al crear archivo: " + ex.getMessage());
            }
        }
    }

    private void guardarArchivo() {
        //si hay un archivo creado y en uso, se ejecuta el bloque de dentro
        if (archivoActual != null) {
            //si hay algo que guardar
            try (FileWriter fw = new FileWriter(archivoActual)) {
                fw.write(vista.entrada_programa.getText()); // se toma lo que haya dentro del área de texto
                JOptionPane.showMessageDialog(vista, "Archivo guardado: " + archivoActual.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(vista, "Error al guardar archivo: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Primero crea un archivo nuevo.");
        }
    }
    
    //Opción para abrir
   

        private void abrirArchivo() {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Abrir archivo .eli");
            if (chooser.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
                archivoActual = chooser.getSelectedFile();
                try (BufferedReader br = new BufferedReader(new FileReader(archivoActual))) {
                    vista.entrada_programa.read(br, null);
                    JOptionPane.showMessageDialog(vista, "Archivo cargado: " + archivoActual.getName());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(vista, "Error al abrir archivo: " + ex.getMessage());
                }
            }
        }
        
    //Opción para generar los reportes
      private void EjecutarProgramaYgenerarReportes(){
          try{
          //tomar el programa fuente
          String entrada = vista.entrada_programa.getText();
          Reader reader = new StringReader(entrada);
          //llamar al lexer
          //solo acepta datos de tipo StringReader
          Lexico lexer  = new Lexico(reader);
          //llamamos al parser a partir del lexer
          Sintactico parser = new Sintactico(lexer);
          //Iterar los tokens
          while (lexer.next_token().sym != sym.EOF){
          
          }
          
          //tomar las listas que dejamos en el jflex
          LinkedList<token> tokens = lexer.getTokens(); 
          LinkedList<errorLexico> errores = lexer.getErrores();
          
          // Llenar tabla de tokens 
          int contador = 0;
          DefaultTableModel modeloTokens = (DefaultTableModel) vista.tablaTokens.getModel(); 
          modeloTokens.setRowCount(0);//limpiamos la tabla antes de llenarla xd 
          for (token t : tokens) { 
              modeloTokens.addRow(new Object[]{ contador, t.getTipo(), t.getLexema(), t.getLinea(), t.getColumna() });
              contador++;
          } 
          // Llenar tabla de errores 
          contador =0;
          DefaultTableModel modeloErrores = (DefaultTableModel) vista.tablaErrores.getModel();
          modeloErrores.setRowCount(0); // limpiar la tabla antes de llenarla xd
          for (errorLexico e : errores) { 
              modeloErrores.addRow(new Object[]{ contador, e.getLexema(), e.getLinea(), e.getColumna(), e.getDescripcion() }); 
              contador++;
          }
          }catch(Exception ex){
              JOptionPane.showMessageDialog(vista, "Error en el análisis léxico :( : " + ex.getMessage());
          }
          
      }



}
