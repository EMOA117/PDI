package jdpi;

import modelo.ImageBufferedImage;
import modelo.LectorDeImagen;
import vista.FrameImagen;
import javax.swing.*;
import java.io.File;
import vista.FramePrincipal;

/**
 *
 * @author Saul
 */
public class JDpi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FramePrincipal frame = new FramePrincipal();
        frame.setVisible(true);
        /*
        // Crear un JFileChooser para seleccionar la imagen
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");
        
        // Filtro para solo permitir archivos de imagen
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("jpge", "jpg"));
        
        int userSelection = fileChooser.showOpenDialog(null);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            String path = fileToOpen.getParent(); // Obtiene la ruta del directorio
            String archivoImagen = fileToOpen.getName(); // Obtiene el nombre del archivo
            
            // Concatenar la ruta correctamente
            String rutaCompleta = path + File.separator + archivoImagen;
            path = path+ File.separator; 
            System.out.println("Ruta de la imagen seleccionada: " + rutaCompleta);
            
            int queCanal = 4;
            LectorDeImagen lector = new LectorDeImagen(path, archivoImagen);
            
            try {
                lector.leerBufferedImagen();
                ImageBufferedImage buffered = new ImageBufferedImage();
                FrameImagen frame = new FrameImagen(
                                        buffered.getImage(
                                            lector.getBufferedImagen(), queCanal, 1), path, archivoImagen);
                // Maximizar el frame al tamaño de la pantalla
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

                // Si deseas ocultar la barra de título, puedes usar esta línea:
                // frame.setUndecorated(true);

                // Hacer visible el frame
                frame.setVisible(true);
                
            } catch (NullPointerException e) {
                System.out.println("Error al leer la imagen: " + e.getMessage());
            }
            
        } else {
            System.out.println("No se seleccionó ningún archivo");
        }
        */
        
    }
    
}
