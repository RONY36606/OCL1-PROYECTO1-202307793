/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
import java.io.*;
import javax.swing.*;
import Interfaz.interfazInicial;

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



}
