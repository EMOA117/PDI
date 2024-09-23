package color;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import modelo.ExtractorDePixel;

/**
 * Clase de conversion entre los formatos RGB y CMY
 *
 * @author Saul De La O Torres
 * @version 1.0
 */
public class CmyToRgb {
    /**
     * La imagen a convertir
     */
    private Image loImage;
    /**
     * Construye el objeto
     */
    public CmyToRgb() {
        loImage = (Image) null;
    }

    /**
     * Convierte una imagen mayor a 8 bits / pixel de resolucion de cmy a rgb
     *
     * @param oImagen la imagen que se convierte
     * 
     * @return devuelve la imagen convertida en un objeto Image
     */
    public Image convertirCmyARgb(Image oImagen) {
        ExtractorDePixel op = new ExtractorDePixel();
        int alto = oImagen.getHeight(null);
        int ancho = oImagen.getWidth(null);
        int [] pixeles = op.handlepixels(oImagen, 0, 0, ancho, alto);
        Rgb [] pixelesRgb = new Rgb[pixeles.length];
        int [] liCanales;
        for(int n = 0; n < alto; n++) {
            for(int m = 0; m < ancho; m++) {
                liCanales = getCanales(pixeles[n * ancho + m]);
                Cmy cmy = new Cmy(
                        (double) liCanales[0], 
                        (double) liCanales[1], 
                        (double) liCanales[2]);
                pixelesRgb[n * ancho + m] = cmy.convertirCmyToRgb();
                }
            }
        pixelesRgb = normalizarCanales(pixelesRgb, alto, ancho);
        Color rgb;
        for(int n = 0; n < alto; n++) {
            for(int m = 0; m < ancho; m++) {
                rgb = new Color(
                        (int) pixelesRgb[n * ancho + m].getR(),
                        (int) pixelesRgb[n * ancho + m].getG(),
                        (int) pixelesRgb[n * ancho + m].getB());
                pixeles[n * ancho + m] = rgb.getRGB();
                }
            }

        JFrame frameTmp = new JFrame();
        loImage = frameTmp.createImage(new MemoryImageSource(ancho,
                alto, pixeles,
                0, ancho));
        return loImage;
    }

    /**
     * Estrae una imagen mayor a 8 bits / pixel de resolucion de cmy a cada
     * canal red o green o blue
     *
     * @param oImagen la imagen que se convierte
     * @param selector del canal
     * 
     * @return devuelve la imagen convertida en un objeto Image
     */
    public Image convertirCmyARgb(Image oImagen, int selector) {
        ExtractorDePixel op = new ExtractorDePixel();
        int alto = oImagen.getHeight(null);
        int ancho = oImagen.getWidth(null);
        int[] pixeles = op.handlepixels(oImagen, 0, 0, ancho, alto);
        Rgb [] pixelesRgb = new Rgb[pixeles.length];
        int [] liCanales;
        for(int n = 0; n < alto; n++) {
            for(int m = 0; m < ancho; m++) {
                liCanales = getCanales(pixeles[n * ancho + m]);
                Cmy cmy = null;
                switch(selector) {
                    case 1: // calcula canal red
                        cmy = new Cmy(
                            (double) liCanales[0], 
                            (double) 0.0, 
                            (double) 0.0);
                        pixelesRgb[n * ancho + m] = cmy.convertirCmyToRgb();
                        break; 
                    case 2: // calcula canal green
                        cmy = new Cmy(
                            (double) 0.0, 
                            (double) liCanales[1], 
                            (double) 0.0);
                        pixelesRgb[n * ancho + m] = cmy.convertirCmyToRgb();
                        break; 
                    case 3: // calcula canal blue
                        cmy = new Cmy(
                            (double) 0.0, 
                            (double) 0.0, 
                            (double) liCanales[2]);
                        pixelesRgb[n * ancho + m] = cmy.convertirCmyToRgb();
                        break;
                    }
                }
            }
        pixelesRgb = normalizarCanales(pixelesRgb, alto, ancho);
        Color[] rgb = new Color[256];
        for(int n = 0; n < rgb.length; n++) {
            rgb[n] = new Color(n, n, n);
            }
        for(int n = 0; n < alto; n++) {
            for(int m = 0; m < ancho; m++) {
                switch(selector) {
                    case 1:
                        pixeles[n * ancho + m]
                            = rgb[
                                (int)pixelesRgb[n * ancho + m].getR()].getRGB();
                        break;
                    case 2:
                        pixeles[n * ancho + m] =
                            rgb[
                                (int)pixelesRgb[n * ancho + m].getG()].getRGB();
                        break;
                    case 3:
                        pixeles[n * ancho + m] =
                            rgb[
                                (int)pixelesRgb[n * ancho + m].getB()].getRGB();
                        break;
                    }
                }
            }
        JFrame frameTmp = new JFrame();
        loImage = frameTmp.createImage(new MemoryImageSource(ancho,
                alto, pixeles,
                0, ancho));
        return loImage;
    }
    
    /**
     * Extrae una imagen mayor a 8 bits / pixel de resolucion de cmy a cada
     * canal red o green o blue
     *
     * @param oImagen la imagen que se convierte
     * @param selector del canal
     * 
     * @return devuelve la imagen extraida en un objeto Image 
     */
    public Image splitCmyARgb(Image oImagen, int selector) {
        ExtractorDePixel op = new ExtractorDePixel();
        int alto = oImagen.getHeight(null);
        int ancho = oImagen.getWidth(null);
        int[] pixeles = op.handlepixels(oImagen, 0, 0, ancho, alto);
        Rgb [] pixelesRgb = new Rgb[pixeles.length];
        int[] liCanales;
        for(int n = 0; n < alto; n++) {
            for(int m = 0; m < ancho; m++) {
                liCanales = getCanales(pixeles[n * ancho + m]);
                Cmy cmy = new Cmy(
                        (double) liCanales[0], 
                        (double) liCanales[1], 
                        (double) liCanales[2]);
                pixelesRgb[n * ancho + m] = cmy.convertirCmyToRgb();
                }
            }
        Color rgb = null;
        for(int n = 0; n < alto; n++) {
            for(int m = 0; m < ancho; m++) {
                switch(selector) {
                    case 1: // crea la imagen en red
                        rgb = new Color(
                                255 - (int)pixelesRgb[n * ancho + m].getR(),
                                0,
                                0);
                        break; 
                    case 2: // crea la imagen en green
                        rgb = new Color(
                                0,
                                255 - (int)pixelesRgb[n * ancho + m].getG(),
                                0);
                        break;
                    case 3: // crea la imagen en blue
                        rgb = new Color(
                                0,
                                0,
                                255 - (int)pixelesRgb[n * ancho + m].getB());
                        break;
                    }
                pixeles[n * ancho + m] = rgb.getRGB();
            }
        }
        JFrame frameTmp = new JFrame();
        loImage = frameTmp.createImage(new MemoryImageSource(ancho,
                alto, pixeles,
                0, ancho));
        return loImage;
    }

    /**
     * Extrae los canales RGB y los devuelve en un arreglo
     * 
     * @param pixel el pixel donde se entraen los canales rojo, verde y azul
     * 
     * @return devuelve los canales c m e y por separado
     */
    public int[] getCanales(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int cyan = (pixel >> 16) & 0xff;
        int magent = (pixel >> 8) & 0xff;
        int yellow = (pixel) & 0xff;
        //System.out.print( " " +  nuevoPixel );
        int[] canales = {
            cyan,
            magent,
            yellow
        };
        return canales;
    }

    /**
     * Normaliza a I y Q para desplegarlos en el rango de 256 niveles de gris.
     * 
     * @param img el vector de la imagen sin normalizar
     * @param alto la altura de la imagen en pixeles
     * @param ancho el ancho de la imagen en pixeles
     * 
     * @return devuelve el vector normalizado 
     */
    public Rgb [] normalizarCanales(Rgb [] img, int alto, int ancho) {
        double maxR = -50000.0;
        double minR =  50000.0;
        double maxG = -50000.0;
        double minG =  50000.0;
        double maxB = -50000.0;
        double minB =  50000.0;
        for(int y = 0; y < alto; y++) {
            for(int x = 0; x < ancho; x++) {
                // maximo y minimo para canal R
                if(img[y * ancho + x].getR() > maxR) {
                    maxR = img[y * ancho + x].getR();
                    }
                if(img[y * ancho + x].getR() < minR) {
                    minR = img[y * ancho + x].getR();
                    }
                // maximo y minimo para canal G
                if(img[y * ancho + x].getG() > maxG) {
                    maxG = img[y * ancho + x].getG();
                    }
                if(img[y * ancho + x].getG() < minG) {
                    minG = img[y * ancho + x].getG();
                    }
                // maximo y minimo para canal B
                if(img[y * ancho + x].getB() > maxB) {
                    maxB = img[y * ancho + x].getB();
                    }
                if(img[y * ancho + x].getB() < minB) {
                    minB = img[y * ancho + x].getB();
                    }
                }
            }
        //System.out.println( " max " + max + " min " + min );
        Rgb [] imagen = new Rgb[alto * ancho];
        for(int y = 0; y < alto; y++) {
            //if( imagen[y]==null ) imagen[y] = new double[ancho];
            for(int x = 0; x < ancho; x++) {
                imagen[y * ancho + x] = new Rgb();
                // canal R
                imagen[y * ancho + x].setR( 
                        (img[y * ancho + x].getR() - minR) * 
                                (255.0 / (maxR - minR)));
                // canal G
                imagen[y * ancho + x].setG( 
                        (img[y * ancho + x].getG() - minG) * 
                                (255.0 / (maxG - minG)));
                // canal B
                imagen[y * ancho + x].setB( 
                        (img[y * ancho + x].getB() - minB) * 
                                (255.0 / (maxB - minB)));
                }
            }
        return imagen;
    }
}
