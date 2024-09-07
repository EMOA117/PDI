package jdpi;

import modelo.ImageBufferedImage;
import modelo.LectorDeImagen;
import vista.FrameImagen;

/**
 *
 * @author Saul
 */
public class JDpi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String path = "C:\\Users\\EMOA1\\OneDrive\\Imágenes\\";
        String archivoImagen = "narufondo.jpg";
        int queCanal = 4;
        LectorDeImagen lector = new LectorDeImagen(path, archivoImagen);
        lector.leerBufferedImagen();
        ImageBufferedImage buffered = new ImageBufferedImage();
        FrameImagen frame = new FrameImagen(
                                buffered.getImage(
                                    lector.getBufferedImagen(), queCanal, 1), path,archivoImagen);
    }
    
}
