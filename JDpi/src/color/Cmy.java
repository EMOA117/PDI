package color;

/**
 *
 * @author sdelaot
 */
public class Cmy {
    /**
     * Color cyan (cian)
     */
    private double c;
    /**
     * Color magent (magenta)
     */
    private double m;
    /**
     * Color yellow (amarillo)
     */
    private double y;
    /**
     * Constructor pode defecto cmy(0,0,0)
     */
    public Cmy() {
        this(0,0,0);
    }
    /**
     * Constructor que recibe los tres colores
     * 
     * @param c Color cyan (cian)
     * @param m Color magent (magenta)
     * @param y Color yellow (amarillo)
     */
    public Cmy(double c, double m, double y) {
        this.c = c;
        this.m = m;
        this.y = y;
    }
    /**
     * Constructor de copia
     * 
     * @param cmy el objeto que se copia
     */
    public Cmy(Cmy cmy) {
        this.c = cmy.c;
        this.m = cmy.m;
        this.y = cmy.y;
    }
    /**
     * Asigna los tres colores
     * 
     * @param c Color cyan (cian)
     * @param m Color magent (magenta)
     * @param y Color yellow (amarillo)
     */
    public void setCmy(double c, double m, double y) {
        this.c = c;
        this.m = m;
        this.y = y;
    }
    /**
     * Devuelve el color cyan
     * 
     * @return devuelve cian
     */
    public double getC() {
        return c;
    }
    /**
     * Pone el color cian
     * 
     * @param c el nuevo color
     */
    public void setC(double c) {
        this.c = c;
    }
    /**
     * Devuelve el color magent
     * 
     * @return devuelve magenta
     */
    public double getM() {
        return m;
    }
    /**
     * Pone el color magenta
     * 
     * @param m el nuevo color
     */
    public void setM(double m) {
        this.m = m;
    }
    /**
     * Devuelve el color yellow
     * 
     * @return devuelve amarillo
     */
    public double getY() {
        return y;
    }
    /**
     * Pone el color yellow
     * 
     * @param y el nuevo color
     */
    public void setY(double y) {
        this.y = y;
    }
    /**
     * Devuelve la tercia de valores CMY
     *
     * @return devuelve la tercia cmy
     */
    public double[] getCmy() {
        double[] rgbq = {c, m, y};
        return rgbq;
    }
    /**
     * Devuelve el objeto
     * 
     * @return devuelve el cmy
     */
    public Cmy getCmyObject() {
        return this;
    }
    /**************************** CONVERSIONES ******************************/
    /***************************** CMY TO RGB *******************************/
    /**
     * Convierte una tripleta CMY en RGB
     * 
     * @return devuelve un objeto Rgb
     */
    public Rgb convertirCmyToRgb() {
        double r = 255.0 - c; // red   = 255 - cian
        double g = 255.0 - m; // green = 255 - cian
        double b = 255.0 - y; // blue  = 255 - yellow
        Rgb rgb = new Rgb(r, g, b);
        return rgb;
    }
    /*************************** END CMY TO RGB *****************************/
}
