package color;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;
import modelo.ExtractorDePixel;


/**
 * Clase de conversion entre los formatos RGB y CMY
 *
 * @author Saul De La O Torres
 * @version 1.0
 */
public class RgbToCmy {
    /**
     * La imagen a convertir
     */
    private Image loImage;
    /**
     * Construye el objeto
     */
    public RgbToCmy() {
        loImage = null;
    }

    /**
     * Convierte una imagen mayor a 8 bits / pixel de resolucion de rgb a cmy
     *
     * @param oImagen la imagen rgb que se convierte a cmy
     * 
     * @return  devuelve la imagen convertida en un objeto Image
     */
    public Image convertirRgbACmy(Image oImagen) {   
        ExtractorDePixel op = new ExtractorDePixel();
        int alto = oImagen.getHeight(null);
        int ancho = oImagen.getWidth(null);
        int[] pixeles = op.handlepixels(oImagen, 0, 0, ancho, alto);
        Cmy [] pixelesCmy = new Cmy[pixeles.length];
        int[] liCanales;
        for(int n = 0; n < alto; n++) {
            for(int m = 0; m < ancho; m++) {
                liCanales = getCanalesRgb(pixeles[n * ancho + m]);
                Rgb rgb = new Rgb((double) liCanales[0],
                                  (double) liCanales[1],
                                  (double) liCanales[2]);
                pixelesCmy[n * ancho + m] = rgb.convertirRgbToCmy();
                }
            }
        Color cmy;
        for(int n = 0; n < alto; n++) {
            for(int m = 0; m < ancho; m++) {
                cmy = new Color(
                        (int) pixelesCmy[n * ancho + m].getC(),
                        (int) pixelesCmy[n * ancho + m].getM(),
                        (int) pixelesCmy[n * ancho + m].getY());
                pixeles[n * ancho + m] = cmy.getRGB();
                }
            }

        JFrame frameTmp = new JFrame();
        loImage = frameTmp.createImage(new MemoryImageSource(ancho,
                alto, pixeles,
                0, ancho));
        return loImage;
    }

    /**
     * Extrae una imagen mayor a 8 bits / pixel de resolucion de rgb a cada
     * canal cyan o magent o yellow
     *
     * @param oImagen la imagen que se convierte
     * @param selector del canal
     * 
     * @return devuelve la imagen en un objeto Image
     */
    
    //para grises
    public Image convertirRgbACmy(Image oImagen, int selector) {
        ExtractorDePixel op = new ExtractorDePixel();
        int alto = oImagen.getHeight(null);
        int ancho = oImagen.getWidth(null);
        int[] pixeles = op.handlepixels(oImagen, 0, 0, ancho, alto);
        Cmy[] pixelesCmy = new Cmy[pixeles.length];
        int[] liCanales;
        Rgb rgb = null;

        for (int n = 0; n < alto; n++) {
            for (int m = 0; m < ancho; m++) {
                liCanales = getCanalesRgb(pixeles[n * ancho + m]);
                rgb = new Rgb((double) liCanales[0], (double) liCanales[1], (double) liCanales[2]);
                pixelesCmy[n * ancho + m] = rgb.convertirRgbToCmy();
            }
        }

        // Ajustar la imagen según el selector
        for (int n = 0; n < alto; n++) {
            for (int m = 0; m < ancho; m++) {
                int canalValue;
                switch (selector) {
                    case 1: // Canal Cian
                        canalValue = (int) pixelesCmy[n * ancho + m].getC();
                        break;
                    case 2: // Canal Magenta
                        canalValue = (int) pixelesCmy[n * ancho + m].getM();
                        break;
                    case 3: // Canal Amarillo
                        canalValue = (int) pixelesCmy[n * ancho + m].getY();
                        break;
                    default:
                        canalValue = 0; // Valor predeterminado
                }
                // Convertir el valor del canal a escala de grises
                pixeles[n * ancho + m] = new Color(canalValue, canalValue, canalValue).getRGB();
            }
        }
        JFrame frameTmp = new JFrame();
        loImage = frameTmp.createImage(new MemoryImageSource(ancho,
                alto, pixeles,
                0, ancho));
        return loImage;
    }
    /**
     * Estrae una imagen mayor a 8 bits / pixel de resolucion de rgb a cada
     * canal cyan o magent o yellow
     *
     * @param oImagen la imagen que se convierte
     * @param selector del canal
     * 
     * @return devuelve la extraccion en un objeto Image
     */
    public Image splitRgbACmy(Image oImagen, int selector) {
        ExtractorDePixel op = new ExtractorDePixel();
        int alto = oImagen.getHeight(null);
        int ancho = oImagen.getWidth(null);
        int[] pixeles = op.handlepixels(oImagen, 0, 0, ancho, alto);
        Cmy [] pixelesCmy = new Cmy[pixeles.length];
        int[] liCanales;
        for(int n = 0; n < alto; n++) {
            for(int m = 0; m < ancho; m++) {
                liCanales = getCanalesRgb(pixeles[n * ancho + m]);
                Rgb rgb = new Rgb((double) liCanales[0],
                                  (double) liCanales[1],
                                  (double) liCanales[2]);
                pixelesCmy[n * ancho + m] = rgb.convertirRgbToCmy();
                }
            }
        Color cmy = (Color) null;
        for (int n = 0; n < alto; n++) {
            for (int m = 0; m < ancho; m++) {
                switch (selector) {
                    case 1:
                        // Imagen original en canal Cyan
                        cmy = new Color(
                                0,
                                (int) pixelesCmy[n * ancho + m].getM(),
                                (int) pixelesCmy[n * ancho + m].getY());
                        break;
                    case 2:
                        // Imagen original en canal Magenta
                        cmy = new Color(
                                (int) pixelesCmy[n * ancho + m].getC(),
                                0,
                                (int) pixelesCmy[n * ancho + m].getY());
                        break;
                    case 3:
                        // Imagen original en canal Yellow
                        cmy = new Color(
                                (int) pixelesCmy[n * ancho + m].getC(),
                                (int) pixelesCmy[n * ancho + m].getM(),
                                0);
                        break;
                    case 4: // Cyan/gris
                        int cianGris = (int) pixelesCmy[n * ancho + m].getC();
                        cmy = new Color(cianGris, cianGris, cianGris); // Escala de grises con canal Cyan
                        break;
                    case 5: // Magenta/gris
                        int magentaGris = (int) pixelesCmy[n * ancho + m].getM();
                        cmy = new Color(magentaGris, magentaGris, magentaGris); // Escala de grises con canal Magenta
                        break;
                    case 6: // Yellow/gris
                        int amarilloGris = (int) pixelesCmy[n * ancho + m].getY();
                        cmy = new Color(amarilloGris, amarilloGris, amarilloGris); // Escala de grises con canal Yellow
                        break;
                    default:
                        cmy = new Color(0, 0, 0); // Caso por defecto
                        break;
                }
                // Asigna el color convertido al píxel correspondiente
                pixeles[n * ancho + m] = cmy.getRGB();
                cmy = null;
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
     * @param pixel el pixel de donde se extraen los canales rojo, verde y azul
     *
     * @return devuelve los canales r g b por separado 
     */
    public int [] getCanalesRgb(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        //System.out.print( " " +  nuevoPixel );
        int[] canales = {
            red,
            green,
            blue
        };
        return canales;
    }

    /**
     * Normaliza a I y Q para desplegarlos en el rango de 256 niveles de gris.
     * 
     * @param img la imagen a normalizar
     * @param alto la altura de la imagen en pixeles
     * @param ancho el ancho de la imagen en pixeles
     * 
     * @return devuelve el vector normalizado
     */
    public Cmy [] normalizarCanales(Cmy [] img, int alto, int ancho) {
        double maxC = -50000.0;
        double minC = 50000.0;
        double maxM = -50000.0;
        double minM = 50000.0;
        double maxY = -50000.0;
        double minY = 50000.0;
        for(int y = 0; y < alto; y++) {
            for(int x = 0; x < ancho; x++) {
                // camal cyan
                if(img[y * ancho + x].getC() > maxC) {
                    maxC = img[y * ancho + x].getC();
                    }
                if(img[y * ancho + x].getC() < minC) {
                    minC = img[y * ancho + x].getC();
                    }
                // camal magenta
                if(img[y * ancho + x].getM() > maxM) {
                    maxM = img[y * ancho + x].getM();
                    }
                if(img[y * ancho + x].getM() < minM) {
                    minM = img[y * ancho + x].getM();
                    }
                // camal yellow
                if(img[y * ancho + x].getY() > maxY) {
                    maxY = img[y * ancho + x].getY();
                    }
                if(img[y * ancho + x].getY() < minY) {
                    minY = img[y * ancho + x].getY();
                    }
                }
            }
        //System.out.println( " max " + max + " min " + min );
        Cmy [] imagen = new Cmy[alto * ancho];
        for(int y = 0; y < alto; y++) {
            //if( imagen[y]==null ) imagen[y] = new double[ancho];
            for(int x = 0; x < ancho; x++) {
                imagen[y * ancho + x] = new Cmy( 
                    (img[y * ancho + x].getC() - minC) * (255.0/(maxC - minC)),
                    (img[y * ancho + x].getM() - minM) * (255.0/(maxM - minM)),
                    (img[y * ancho + x].getY() - minY) * (255.0/(maxY - minY))
                    );
                }
            }
        return imagen;
    }
}
