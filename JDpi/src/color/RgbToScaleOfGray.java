/**
 * Nombre del paquete al que pertenece la clase.
 */
package color;

/**
 * Libreria necesaria para el funcionamiento de la clase
 */
import java.awt.*;

/**
 * Esta clase convierte a escala de grises a una imagen en color con una
 * resolucion de 8 bits por pixel. Ultima modificacion 30/may/2004
 *
 * @author Saul De La O Torres.
 * @version 1.0 16 de Septiembre de 2003.
 */
public class RgbToScaleOfGray {
    /**
     * Alto de la imagen a convertir.
     */
    private int alto;
    /**
     * Ancho de la imagen a convertir.
     */
    private int ancho;
    /**
     * Buffer de la imagen en niveles de gris.
     */
    private int[][] imgGris;
    /**
     * Niveles de gris en la image
     */
    private static final int NIVGRIS = 256;

    /**
     * Constructor por default
     */
    public RgbToScaleOfGray() {
        this(0, 0);
    }

    /**
     * El constructor reciba la altura y la anchura de la imagen a convertir,
     * asi como asigna espacio de memoria para el buffer.
     * 
     * @param alto la altura de la imagen en pixeles
     * @param ancho el ancho de la imagen en pixeles
     */
    public RgbToScaleOfGray(int alto, int ancho) {
        this.alto = alto;
        this.ancho = ancho;
        try {
            imgGris = new int[alto][ancho];
        } catch (NegativeArraySizeException e) {
            System.out.println(
                    " Error en " 
                    + "RgbToScaleOfGray.RgbToScaleOfGray()"
                    + " alto o ancho es negativo " + e);
        }
    }

    /**
     * Realiza la conversion de la imagen en color a una de niveles de gris y
     * devuelve el buffer convertido.
     * 
     * @param r el vector del canal rojo
     * @param g el vector del canal verde
     * @param b el vector del canal azul
     * @param imagen la matriz de la imagen que se procesa
     * 
     * @return devuelve la matriz de la imagen convertida 
     */
    public int [][] realizarConversion(int [] r, int [] g, int [] b, 
        int [][] imagen) {
        int[] LUTPaleta = new int[NIVGRIS];
        try {
            for(int n = 0; n < NIVGRIS; n++) {
                LUTPaleta[n] = (r[n] + g[n] + b[n]) / 3;
                }
            for(int y = 0; y < alto; y++) {
                if(imgGris[y] == null) {
                    imgGris[y] = new int[ancho];
                    }
                for(int x = 0; x < ancho; x++) {
                    imgGris[y][x] = LUTPaleta[imagen[y][x]];
                    }
                }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(
                    " Error en RgbToScaleOfGray.realizarConversion()"
                    + " indice fuera de rango " + e);
        } catch (NegativeArraySizeException e) {
            System.out.println(
                    " Error en RgbToScaleOfGray.realizarConversion()"
                    + " indice negativo " + e);
        } catch (NullPointerException e) {
            System.out.println(
                    " Error en RgbToScaleOfGray.realizarConversion()" 
                            + e);
        }
        return imgGris;
    }

    /**
     * Realiza la conversion de la imagen en color a una de niveles de gris y
     * devuelve el buffer convertido.
     * 
     * @param imagen la imagen a convertir
     * 
     * @return devuelve la matriz de la imagen convertida
     */
    public int[][] realizarConversion(int [][] imagen) {
        try {
            Color c;
            for(int y = 0; y < alto; y++) {
                if(imgGris[y] == null) {
                    imgGris[y] = new int[ancho];
                    }
                for (int x = 0; x < ancho; x++) {
                    c = new Color(imagen[y][x]);
                    int rojo = c.getRed();
                    int verde = c.getGreen();
                    int azul = c.getBlue();
                    //System.out.print( " " + rojo + " " + verde + " " + azul );
                    imgGris[y][x] = (rojo + verde + azul) / 3;
                    }
                }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(
                    " Error en RgbToScaleOfGray.realizarConversion()"
                    + " indice fuera de rango " + e);
        } catch (NegativeArraySizeException e) {
            System.out.println(
                    " Error en RgbToScaleOfGray.realizarConversion()"
                    + " indice negativo " + e);
        } catch (NullPointerException e) {
            System.out.println(
                    " Error en RgbToScaleOfGray.realizarConversion()" 
                            + e);
        }
        return imgGris;
    }
}