package color;

/**
 * Clase de color Yuv
 * @author sdelaot
 */
public class Yuv {
    /**
     * Almcena el luminancia (Y) contenido en la paleta de colores de la
     * imagen.
     */
    private double Y;
    /**
     * Almcena el color (U) contenido en la paleta de colores de la
     * imagen.
     */
    private double U;
    /**
     * Almcena el color (V) contenido en la paleta de colores de la
     * imagen.
     */
    private double V;

    /**
     * El Constructor solo inicializa la tercia YUV.
     */
    public Yuv() {
        this(0, 0, 0);
    }

    /**
     * El Constructor inicializa por paso de valores a la trecia YUV
     *
     * @param Y es el estimulo Y
     * @param U es el estimulo U
     * @param V es el estimulo V
     */
    public Yuv(double Y, double U, double V) {
        this.Y = Y;
        this.U = U;
        this.V = V;
    }
    /**
     * Constructor de copia de la tercia YUV
     * 
     * @param yuv el objeto que se copia
     */
    public Yuv(Yuv yuv) {
        this.Y = yuv.Y;
        this.U = yuv.U;
        this.V = yuv.V;
    }

    /**
     * Pone valores en la trecia YUV
     *
     * @param Y es la luminancia Y
     * @param U es el color U
     * @param V es el color V
     */
    public void setYuv(double Y, double U, double V) {
        this.Y = Y;
        this.U = U;
        this.V = V;
    }

    /**
     * Devuelve la luminancia (Y) del YUV
     *
     * @return double
     */
    public double getY() {
        return Y;
    }

    /**
     * Devuelve el color (U) del YUV
     *
     * @return double
     */
    public double getU() {
        return U;
    }

    /**
     * Devuelve el color (V) del YUV
     *
     * @return double
     */
    public double getV() {
        return V;
    }
    /**
     * Pone la luminancia
     * @param Y el nuevo valor de la luminancia
     */
    public void setY(double Y) {
        this.Y = Y;
    }   
    /**
     * Pone la crominancia
     * @param U el nuevo valor de la crominancia uno
     */
    public void setU(double U) {
        this.U = U;
    }
    /**
     * Pone la crominancia
     * @param V el nuevo valor de la crominancia uno
     */
    public void setV(double V) {
        this.V = V;
    }
    

    /**
     * Devuelve la tercia de valores YUV
     *
     * @return devuelve la tercia yuv
     */
    public double[] getYuv() {
        double [] yuv = {Y, U, V};
        return yuv;
    }
    /**
     * Devuelve el objeto
     * 
     * @return devuelve el Yuv
     */
    public Yuv getYuvObject() {
        return this;
    }
    /**************************** CONVERSIONES ******************************/
    /***************************** YUV TO HSI *******************************/
    /**
     * Convierte YUV a HSI
     * @return devuelve el objeto hsi
     */
    public Hsi convertirYuvToHsi() {
        double I = Y;
        double H = Math.atan(U/V);
        double S = Math.sqrt(Math.pow(U, 2.0)+Math.pow(V, 2.0));
        Hsi hsi = new Hsi(I, H, S);
        return hsi;
    }
    /***************************** END YUV TO HSI **************************/
}
