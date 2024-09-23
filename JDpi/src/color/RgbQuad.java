/**
 * Nombre del paquete al que pertenece la clase.
 */
package color;

/**
 * Clase para almacenar la paleta de colores en memoria de un archivo de imagen
 * en formato BMP. Ultima modificacion 30/may/2004
 *
 * @author Saul De La O Torres
 * @version 1.0 20/jun/2003
 */
public class RgbQuad {
    /**
     * Almcena el color rojo (red) contenido en la paleta de colores de la
     * imagen.
     */
    private int r;
    /**
     * Almcena el color verde (green) contenido en la paleta de colores de la
     * imagen.
     */
    private int g;
    /**
     * Almcena el color alzul (blue) contenido en la paleta de colores de la
     * imagen.
     */
    private int b;
    /**
     * El nulo que conpleta el cuarteto de la paleta de colores de la imagen.
     */
    private int nl;

    /**
     * El Constructor solo inicializa la cuarteta RGBQ.
     */
    public RgbQuad() {
        this(0, 0, 0, 0);
    }

    /**
     * El Constructor inicializa por paso de valores a la cuarteta RGBQ
     *
     * @param r es el color rojo
     * @param g es el color verde
     * @param b es el color azul
     * @param nl no es un color
     */
    public RgbQuad(int r, int g, int b, int nl) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.nl = nl;
    }
    /**
     * Constructor de copia de la cuarteta rgb quad
     * 
     * @param rgbq el objeto que se copia
     */
    public RgbQuad(RgbQuad rgbq) {
        this.r = rgbq.r;
        this.g = rgbq.g;
        this.b = rgbq.b;
        this.nl = rgbq.nl;
    }

    /**
     * Pone valores en la cuarteta.RGBQ
     *
     * @param r es el color rojo
     * @param g es el color verde
     * @param b es el color azul
     * @param nl no es un color
     */
    public void setRGBQuad(int r, int g, int b, int nl) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.nl = nl;
    }

    /**
     * Devuelve el rojo (red) del RGBQ
     *
     * @return int
     */
    public int getR() {
        return r;
    }

    /**
     * Devuelve el verde (green) del RGBQ
     *
     * @return int
     */
    public int getG() {
        return g;
    }

    /**
     * Devuelve el azul (blue) del RGBQ
     *
     * @return int
     */
    public int getB() {
        return b;
    }

    /**
     * Devuelve el cuarteto de valores RGBQ
     *
     * @return devuelve la cuarteta rgbq
     */
    public int[] getRGBQ() {
        int[] rgbq = {r, g, b, nl};
        return rgbq;
    }
    /**
     * Devuelve el objeto
     * 
     * @return devuelve el wrgquad
     */
    public RgbQuad getRGBQuad() {
        return this;
    }
}
