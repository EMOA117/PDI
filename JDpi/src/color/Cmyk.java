package color;

/**
 *
 * @author sdelaot
 */
public class Cmyk {
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
     * Color black o mejor ausencia de color (negro)
     */
    private double k;
    /**
     * Constructor pode defecto cmyk(0,0,0,0)
     */
    public Cmyk() {
        this(0,0,0,0);
    }
    /**
     * Constructor que recibe los tres colores
     * 
     * @param c Color cyan (cian)
     * @param m Color magent (magenta)
     * @param y Color yellow (amarillo)
     * @param k Color black (negro)
     */
    public Cmyk(double c, double m, double y, double k) {
        this.c = c;
        this.m = m;
        this.y = y;
        this.k = k;
    }
    /**
     * Constructor de copia
     * 
     * @param cmyk el objeto que se copia
     */
    public Cmyk(Cmyk cmyk) {
        this.c = cmyk.c;
        this.m = cmyk.m;
        this.y = cmyk.y;
        this.k = cmyk.k;
    }
    /**
     * Asigna los tres colores
     * 
     * @param c Color cyan (cian)
     * @param m Color magent (magenta)
     * @param y Color yellow (amarillo)
     * @param k Color black (negro)
     */
    public void setCmy(double c, double m, double y, double k) {
        this.c = c;
        this.m = m;
        this.y = y;
        this.k = k;
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
     * Devuelve el color black
     * 
     * @return devuelve negro
     */
    public double getK() {
        return k;
    }
    
    /**
     * Pone el color yellow
     * 
     * @param k el nuevo color
     */
    public void setK(double k) {
        this.k = k;
    }
    
    /**
     * Devuelve la cuarteta de valores CMYK
     *
     * @return devuelve lacuarteta Cmyk
     */
    public double[] getCmyk() {
        double[] cmyk = {c, m, y, k};
        return cmyk;
    }
    /**
     * Devuelve el objeto
     * 
     * @return devuelve el Cmyk
     */
    public Cmyk getCmykObject() {
        return this;
    }
    /**************************** CONVERSIONES ******************************/
    /***************************** CMYK TO RGB ******************************/
    /**
     * Convierte una tripleta CMY en RGB
     * 
     * @return devuelve un objeto Rgb
     */
    public Rgb convertirCmykToRgb() {
        ColorFuncion funcion = new ColorFuncion();
        double r = funcion.menor(255.0, c*(255.0-k)+k); 
        double g = funcion.menor(255.0, m*(255.0-k)+k); 
        double b = funcion.menor(255.0, y*(255.0-k)+k); 
        Rgb rgb = new Rgb(r, g, b);
        return rgb;
    }
    /*************************** END CMYK TO RGB ****************************/
}
