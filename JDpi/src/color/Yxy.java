package color;

/**
 * Clase que almacena la tercia Yxy
 * @author sdelaot
 */
public class Yxy {
    /**
     * La luminancia de la tercia
     */
    private double Y;
    /**
     * La crominancia uno
     */
    private double x;
    /**
     * La crominancia dos
     */
    private double y;
    /**
     * Crea el objeto Yxy por defecto
     */
    public Yxy() {
        this(0,0,0);
    }
    /**
     * Crea el objeto Yxy con todos los valores
     * @param Y la lumninancia
     * @param x la crominancia uno
     * @param y la crominancia dos
     */
    public Yxy(double Y, double x, double y) {
        this.Y = Y;
        this.x = x;
        this.y = y;
    }
    /**
     * Crea el objeto Yxy para duplicarlo
     * @param yxy la crominancia dos
     */
    public Yxy(Yxy yxy) {
        this.Y = yxy.Y;
        this.x = yxy.x;
        this.y = yxy.y;
    }
    /**
     * Crea el objeto Yxy con todos los valores
     * @param Y la lumninancia
     * @param x la crominancia uno
     * @param y la crominancia dos 
     */
    public void setYxy(double Y, double x, double y) {
        this.Y = Y;
        this.x = x;
        this.y = y;
    }
    /**
     * Toma la luminancia
     * 
     * @return devuelve a Y
     */
    public double getY() {
        return Y;
    }
    /**
     * Pone la luminancia
     * @param Y el nuevo valor de la luminancia
     */
    public void setY(double Y) {
        this.Y = Y;
    }
    /**
     * Devuelve la crominancia uno
     * @return devuelve x
     */
    public double getX() {
        return x;
    }
    /**
     * Pone la crominancia uno
     * @param x el nuevo valor de la crominancia uno
     */
    public void setX(double x) {
        this.x = x;
    }
    /**
     * Devuelve el valor de la crominancia dos
     * @return devuelve el valor y
     */
    public double gety() {
        return y;
    }
    /**
     * Pone el valor de la crominancia dos
     * @param y el nuevo valor de la crominancia dos
     */
    public void sety(double y) {
        this.y = y;
    }
    
    /**
     * Devuelve la tercia de valores Yxy
     *
     * @return devuelve la tercia Yxy
     */
    public double[] getYxy() {
        double [] yxy = {Y, x, y};
        return yxy;
    }
    /**
     * Devuelve el objeto
     * 
     * @return devuelve el Yxy
     */
    public Yxy getYxyObject() {
        return this;
    }
    /**************************** CONVERSIONES ******************************/
    /***************************** XYZ TO YXY *******************************/
    /**
     * Convierte de XYZ a YXY
     * @return devuelve el objeto xyz
     */
    public Xyz convertirYxyToXyz() {
        double X = x * ( Y / y );
        double Y = this.Y;
        double Z = ( 1 - x - y ) * ( this.Y / y );
        Xyz xyz = new Xyz(X, Y, Z);
        return xyz;
    }
    /*************************** END XYZ TO YXY *****************************/
}
