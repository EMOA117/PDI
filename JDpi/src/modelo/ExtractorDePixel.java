/**
 * Paquete al que pertenece la clase
 */
package modelo;

/**
 * Librerias para que trabaje la clase
 */
import java.awt.*;
import java.awt.image.*;

/**
 * Clase que obtiene los pixeles de un objeto Image
 *
 * @author Saul De La O Torres
 * @version 1.1
 */
public class ExtractorDePixel {
    /**
     * Crea el objeto
     */
    public ExtractorDePixel() {

    }

    /**
     * Toma los pixeles de la imagen
     *
     * @param img la imagen de donde se toman
     * @param x el inicio en x
     * @param y el inicio en y
     * @param w el ancho de la imagen
     * @param h el alto de la imagen
     *
     * @return devuelve la imagen en una dimension
     */
    public int [] handlepixels(Image img, int x, int y, int w, int h) {
        int [] pixels = new int[w * h];
        PixelGrabber pg = new PixelGrabber(
                img, x, y, w, h, pixels, 0, w);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            System.err.println("Interrupted waiting for pixels!");
            return null;
        }
        if((pg.getStatus() & ImageObserver.ABORT)!=0) {
            System.err.println("Image fetch aborted or errored");
            return null;
            }
        /*
         for (int j = 0; j < h; j++) {
         for (int i = 0; i < w; i++) {
         handlesinglepixel(x+i, y+j, pixels[j * w + i]);
         }
         }*/
        return pixels;
    }
    
    /**
     * Toma los pixeles de la imagen
     *
     * @param img la imagen de donde se toman
     * @param x el inicio en x
     * @param y el inicio en y
     * @param ancho el ancho de la imagen
     * @param alto el alto de la imagen
     *
     * @return devuelve la imagen en una dimension
     */
    public double [] handlepixelsDouble(Image img, int x, int y, int ancho, 
            int alto) {
        int[] pixels = new int[ancho * alto];
        PixelGrabber pg = new PixelGrabber(
                img, x, y, ancho, alto, pixels, 
                0, ancho);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            System.err.println("Interrupted waiting for pixels!");
            return null;
        }
        if((pg.getStatus() & ImageObserver.ABORT) != 0) {
            System.err.println("Image fetch aborted or errored");
            return null;
            }
        double [] pixelesD = new double[pixels.length];
        for(int j=0; j<alto; j++) {
            for(int i=0; i<ancho; i++) {
                 pixelesD[j*ancho+i] = pixels[j*ancho+i];
                }
            }
        return pixelesD;
    }

    /**
     * Toma los pixeles de la imagen
     *
     * @param img la imagen de donde se toman
     * @param x el inicio en x
     * @param y el inicio en y
     * @param ancho el ancho de la imagen
     * @param alto el alto de la imagen
     *
     * @return devuelve la imagen en dos dimensiones
     */
    public int[][] obtenerPixelesEn2D(Image img, int x, int y, int ancho,
            int alto) {
        int[] pixels = new int[ancho * alto];
        PixelGrabber pg = new PixelGrabber(
                img, x, y, ancho, alto, pixels, 
                0, ancho);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            System.err.println("interrupted waiting for pixels!");
            return null;
        }
        if((pg.getStatus() & ImageObserver.ABORT)!=0) {
            System.err.println("image fetch aborted or errored");
            return null;
            }
        int[][] pixeles2D = new int[alto][ancho];
        for(int j=0; j<alto; j++) {
            for(int i=0; i<ancho; i++) {
                pixeles2D[j][i] = pixels[j * ancho + i];
                }
            }
        return pixeles2D;
    }
    /**
     * Toma los pixeles de la imagen
     *
     * @param img la imagen de donde se toman
     * @param x coordenada de inicio en x
     * @param y coordenada de inicio en y
     * @param ancho el ancho de la imagen
     * @param alto el alto de la imagen
     *
     * @return devuelve la imagen en dos dimensiones
     */
    public int [][] obtenerPixelesEnDosDInt(Image img, int x, int y, 
            int ancho, int alto) {
        int[] pixels = new int[ancho * alto];
        PixelGrabber pg = new PixelGrabber(img, x, y, ancho, 
                alto, pixels, 0, ancho);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            System.err.println("interrupted waiting for pixels!");
            return null;
        }
        if((pg.getStatus() & ImageObserver.ABORT)!=0) {
            System.err.println("image fetch aborted or errored");
            return null;
            }
        int [][] pixeles2D = new int[alto][ancho];
        for(int j=0; j<alto; j++) {
            for(int i=0; i<ancho; i++) {
                pixeles2D[j][i] = pixels[j*ancho+i];
                }
            }
        return pixeles2D;
    }
    /**
     * Toma los pixeles de la imagen
     *
     * @param img la imagen de donde se toman
     * @param x coordenada de inicio en x
     * @param y coordenada de inicio en y
     * @param ancho el ancho de la imagen
     * @param alto el alto de la imagen
     *
     * @return devuelve la imagen en dos dimensiones
     */
    public double [][] obtenerPixelesEnDosDDouble(Image img, int x, int y, 
            int ancho, int alto) {
        int[] pixels = new int[ancho * alto];
        PixelGrabber pg = new PixelGrabber(img, x, y, ancho, 
                alto, pixels, 0, ancho);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            System.err.println("interrupted waiting for pixels!");
            return null;
        }
        if((pg.getStatus() & ImageObserver.ABORT)!=0) {
            System.err.println("image fetch aborted or errored");
            return null;
            }
        double [][] pixeles2D = new double[alto][ancho];
        for(int j=0; j<alto; j++) {
            for(int i=0; i<ancho; i++) {
                pixeles2D[j][i] = pixels[j*ancho+i];
                }
            }
        return pixeles2D;
    }
}
