/**
 * Paquete al que pertenece la clase.
 */
package color;

/**
 * Paquete para que trabaje la imagen.
 */
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;


/**
 * Clase que extrae los componentes Rojo, Verde y Azul de la imagen.
 * 
 * @author sdelaot
 */
public class ImageToRgb {

    /**
     * Componente a extraer, se convierte en paleta.
     */
    private RgbQuad[] componente;

    /**
     * Construye el objeto por default
     */
    public ImageToRgb() {
        this(256);
    }

    /**
     * Construye el objeto para extraer componentes RGB.
     * 
     * @param colores el numero de colores de la paleta
     */
    public ImageToRgb(int colores) {
        componente = new RgbQuad[colores];
    }

    /**
     * Extrae el componente segun la seleccion hecha.<br>
     * sel = 1 se extrae componente rojo.<br>
     * sel = 2 se extrae componente verde.<br>
     * sel = 3 se extrae componente azul.<br>
     *
     * @param laPaleta la paleta de colores
     * @param sel el selector de extraccion
     *
     * @return devuelve la paleta extraida
     */
    public RgbQuad[] extraerComponente(RgbQuad[] laPaleta, int sel) {
        RgbQuad[] pal = laPaleta;
        for (int n = 0; n < componente.length; n++) {
            //System.out.print( " " + n );
            componente[n] = new RgbQuad();
            if (sel == 1) {
                componente[n].setRGBQuad(pal[n].getR(), 0, 0, 0);
            } else if (sel == 2) {
                componente[n].setRGBQuad(0, pal[n].getG(), 0, 0);
            } else if (sel == 3) {
                componente[n].setRGBQuad(0, 0, pal[n].getB(), 0);
            }
        }
        return componente;
    }

    /**
     * Extrae el componente segun la seleccion hecha.<br>
     * sel = 1 se extrae componente rojo.<br>
     * sel = 2 se extrae componente verde.<br>
     * sel = 3 se extrae componente azul.<br>
     *
     * @param imagen la matriz de imagen
     * @param alto la altura de la imagen en pixeles
     * @param ancho el ancho de la imagen en pixeles
     * @param sel el selector de extraccion
     *
     * @return devuelve la matriz extraida
     */
    public int[][] extraerComponente(int[][] imagen, int alto, int ancho,
        int sel) {
        int[][] subImagen = new int[alto][ancho];
        try {
            Color c1 = null;
            Color c2 = null;
            for (int y = 0; y < alto; y++) {
                if (subImagen[y] == null) {
                    subImagen[y] = new int[ancho];
                }
                for (int x = 0; x < ancho; x++) {
                    c1 = new Color(imagen[y][x]);
                    if (sel == 1) {
                        c2 = new Color(c1.getRed(), 0, 0);
                    } else if (sel == 2) {
                        c2 = new Color(0, c1.getGreen(), 0);
                    } else if (sel == 3) {
                        c2 = new Color(0, 0, c1.getBlue());
                    }
                    //System.out.print( " " + rojo + " " + verde + " " + azul );
                    subImagen[y][x] = c2.getRGB();
                    c1 = null;
                    c2 = null;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(
                " Error en ImageToRgb.extraerComponente()"
                + " indice fuera de rango " + e);
        } catch (NegativeArraySizeException e) {
            System.out.println(
                " Error en ImageToRgb.extraerComponente()"
                + " indice negativo " + e);
        } catch (NullPointerException e) {
            System.out.println(
                " Error en ImageToRgb.extraerComponente()" + e);
        }
        return subImagen;
    }

    /**
     * Extrae el componente segun la seleccion hecha.<br>
     * sel = 1 se extrae componente rojo.<br>
     * sel = 2 se extrae componente verde.<br>
     * sel = 3 se extrae componente azul.<br>
     *
     * @param pixeles el vector de pixeles de donde se extrae el componente
     * @param alto la altura de la imagen en pixeles
     * @param ancho el ancho de la imagen en pixeles
     * @param sel el selector de extraccion
     *
     * @return devuelve el vector extraido
     */
    public int[] extraerComponente(int[] pixeles, int alto, int ancho,
        int sel) {
        int[] imagen = new int[alto * ancho];
        try {
            Color c1;
            Color c2 = null;
            for (int j = 0; j < alto; j++) {
                for (int i = 0; i < ancho; i++) {
                    c1 = new Color(pixeles[j * ancho + i]);
                    if (sel == 1) {
                        c2 = new Color(c1.getRed(), 0, 0);
                    } else if (sel == 2) {
                        c2 = new Color(0, c1.getGreen(), 0);
                    } else if (sel == 3) {
                        c2 = new Color(0, 0, c1.getBlue());
                    }
                    //System.out.print( " " + rojo + " " + verde + " " + azul );
                    imagen[j * ancho + i] = c2.getRGB();
                    c1 = null;
                    c2 = null;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(
                " Error en ImageToRgb.extraerComponente()"
                + " indice fuera de rango " + e);
        } catch (NegativeArraySizeException e) {
            System.out.println(
                " Error en ImageToRgb.extraerComponente()"
                + " indice negativo " + e);
        } catch (NullPointerException e) {
            System.out.println(
                " Error en ImageToRgb.extraerComponente()" + e);
        }
        return imagen;
    }

    /**
     * Extrae el componente segun la seleccion hecha.<br>
     * sel = 1 se extrae componente rojo.<br>
     * sel = 2 se extrae componente verde.<br>
     * sel = 3 se extrae componente azul.<br>
     *
     * @param pixeles el vector de pixeles de donde se extrae el componente
     * @param alto la altura de la imagen en pixeles
     * @param ancho el ancho de la imagen en pixeles
     * @param sel el selector de extraccion
     *
     * @return devuelve el vector extraido
     */
    public int[] extraerComponenteSkin(int[] pixeles, int alto, int ancho,
        int sel) {
        int[] imagen = new int[alto * ancho];
        try {
            Color c1;
            Color c2;
            for (int j = 0; j < alto; j++) {
                for (int i = 0; i < ancho; i++) {
                    c1 = new Color(pixeles[j * ancho + i]);
                    if (sel == 1) {
                        c2 = new Color(c1.getRed(), 0, 0);
                        imagen[j * ancho + i] = c2.getRed();
                    } else if (sel == 2) {
                        c2 = new Color(0, c1.getGreen(), 0);
                        imagen[j * ancho + i] = c2.getGreen();
                    } else if (sel == 3) {
                        c2 = new Color(0, 0, c1.getBlue());
                        imagen[j * ancho + i] = c2.getBlue();
                    }
                    //System.out.print( " " + rojo + " " + verde + " " + azul );
                    c1 = null;
                    c2 = null;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(
                " Error en ImageToRgb.extraerComponente()"
                + " indice fuera de rango " + e);
        } catch (NegativeArraySizeException e) {
            System.out.println(
                " Error en ImageToRgb.extraerComponente()"
                + " indice negativo " + e);
        } catch (NullPointerException e) {
            System.out.println(
                " Error en ImageToRgb.extraerComponente()" + e);
        }
        return imagen;
    }

    /**
     * Devuelve un objeto Image, convertido desde el bufer de la imagen Bmp.
     * 
     * @param pixeles el vector de pixeles de donde se extrae el componente
     * @param alto la altura de la imagen en pixeles
     * @param ancho el ancho de la imagen en pixeles
     *
     * @return devuelve la imagen que es un objeto Image.
     */
    public Image getImagen(int[] pixeles, int alto, int ancho) {
        JFrame frameTmp = new JFrame();
        Image img = frameTmp.createImage(new MemoryImageSource(ancho,
            alto, pixeles,
            0, ancho));
        return img;
    }
}
