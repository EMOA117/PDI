/**
 * Nombre del paquete al que pertenece la clase.
 */
package color;

/**
 * Clase para realizar conversiones entre formatos RGB == YIQ Ultima
 * modificacion: 1/jun/2004 29/oct/2004
 *
 * @author Saul De La O Torres
 * @version 1.0 1/jun/2004
 */
public class Yiq implements IYiqRgbConstante {
    /**
     * Almacen de la Luminancia Y del formato YIQ.
     */
    private double Y;
    /**
     * Almacen de la Parte de la Crominancia del formato YIQ.
     */
    private double I;
    /**
     * Almacen de la Parte de la crominancia del formato YIQ.
     */
    private double Q;

    /**
     * El Constructor solo inicializa la tercia YIQ.
     */
    public Yiq() {
        this(0, 0, 0);
    }

    /**
     * El Constructor inicializa por paso de valores a la tercia YIQ.
     *
     * @param y es la luminancia
     * @param i es la crominancia 1
     * @param q es la crominancia 2
     */
    public Yiq(double y, double i, double q) {
        this.Y = y;
        this.I = i;
        this.Q = q;
    }
    /**
     * El Constructor de copia.
     *
     * @param yiq es el objeto que se copia
     */
    public Yiq(Yiq yiq) {
        this.Y = yiq.Y;
        this.I = yiq.I;
        this.Q = yiq.Q;
    }

    /**
     * Pone valores en la tercia YIQ.
     *
     *
     * @param y es la luminancia
     * @param i es la crominancia 1
     * @param q es la crominancia 2
     */
    public void setYIQ(double y, double i, double q) {
        this.Y = y;
        this.I = i;
        this.Q = q;
    }

    /**
     * Devuelve la luminancia Y del formato YIQ.
     *
     * @return double el valor de Y
     */
    public double getY() {
        return Y;
    }

    /**
     * Devuelve el color 1 I del formato YIQ.
     *
     * @return double el valor de I
     */
    public double getI() {
        return I;
    }

    /**
     * Devuelve el color 2 Q del formato YIQ.
     *
     * @return double el valor de Q
     */
    public double getQ() {
        return Q;
    }

    /**
     * Devuelve la tercia de valores YIQ
     *
     * @return double devuelve la tripleta YIQ
     */
    public double[] getYIQ() {
        double[] yiq = {Y, I, Q};
        return yiq;
    }
    
    /**
     * Devuelve la tercia de valores YIQ en un objeto
     *
     * @return devuelve el objeto Yiq
     */
    public Yiq getYiq() {
        return this;
    }
    /**************************** CONVERSIONES ******************************/
    /***************************** RGB TO YIQ *******************************/
    /**
     * Convierte YIQ a RGB
     * 
     * @return devuelve el objeto Rgb 
     */
    public Rgb convertirYiqToRgb() {
        double r = Y + PJE_I_R * I + PJE_Q_R * Q;
        if(r < CPCCU) {
            r = CPC;
            }
        if(r > DCCPC) {
            r = DCCPC;
            }

        double g = Y - PJE_I_G * I - PJE_Q_G * Q;
        if(g < CPCCU) {
            g = CPC;
            }
        if(g > DCCPC) {
            g = DCCPC;
            }

        double b = Y - PJE_I_B * I + PJE_Q_B * Q;
        if(b < CPCCU) {
            b = CPC;
            }
        if(b > DCCPC) {
            b = DCCPC;
            }
        Rgb rgb = new Rgb(r, g, b);
        return rgb;
    }
    /*************************** END RGB TO YIQ *****************************/
}
