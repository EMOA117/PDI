package color;


/**
 *
 * @author sdelaot
 */
public class Hsv {
    /**
     * Almacen de la Huella H del formato HSI.
     */
    private double H;
    /**
     * Almacen de la Parte de la Saturacion S del formato HSI.
     */
    private double S;
    /**
     * Almacen de la Parte de la lumninancia I del formato HSI.
     */
    private double V;

    /**
     * El Constructor solo inicializa la tercia YIQ.
     */
    public Hsv() {
        this(0, 0, 0);
    }
    /**
     * El Constructor inicializa por paso de valores a la tercia HSI.
     *
     * @param h es la huella
     * @param s es la saturacion
     * @param v es la luminancia
     */
    public Hsv(double h, double s, double v) {
        this.H = h;
        this.S = s;
        this.V = v;
    }
    /**
     * El asigna por paso de valores a la tercia HSI.
     *
     * @param h es la huella
     * @param s es la saturacion
     * @param v es la luminancia
     */
    public void setHSV(double h, double s, double v) {
        this.H = h;
        this.S = s;
        this.V = v;
    }
    /**
     * Devuelve la huella
     * 
     * @return devuelve h
     */
    public double getH() {
        return H;
    }
    /**
     * Devuelve la saturacion
     * 
     * @return devuelve s
     */
    public double getS() {
        return S;
    }
    /**
     * Devuelve la luminancia
     * 
     * @return devuelve i
     */
    public double getV() {
        return V;
    }
    /**
     * Pone la huella
     * 
     * @param H el nuevo valor de huella
     */
    public void setH(double H) {
        this.H = H;
    }
    /**
     * Pone la saturacion
     * 
     * @param S el nuevo valor de saturacion
     */
    public void setS(double S) {
        this.S = S;
    }
    /**
     * Pone la iluminacion
     * 
     * @param V el nuevo valor de iluminacion
     */
    public void setI(double V) {
        this.V = V;
    }
    /**
     * Devuelve la tercia HSV
     * 
     * @return devuelve un arreglo la tercia hsv 
     */
    public double[] getHSV() {
        double[] hsv = {H, S, V};
        return hsv;
    }
    /**************************** CONVERSIONES ******************************/
    /***************************** HSV TO RGB *******************************/
    /**
     * Convierte de HSV a RGB
     * 
     * @return devuelve el objeto Rgb
     */
    public Rgb convertirHsvToRgb() {
        //H, S and V input range = 0 ÷ 1.0
        //R, G and B output range = 0 ÷ 255
        double R;
        double G;
        double B;

        if( S == 0 ) {
           R = V * 255.0;
           G = V * 255.0;
           B = V * 255.0;
            }
        else {
            double varH = H * 6.0;
            if ( varH == 6 ) { //H must be < 1
               varH = 0.0;
                }      
            int var_i = (int)Math.floor(varH);  //Or ... var_i = floor( var_h )
            double varUno = V * ( 1 - S );
            double varDos = V * ( 1 - S * ( varH - var_i ) );
            double varTres = V * ( 1 - S * ( 1 - ( varH - var_i ) ) );
           
            double varR;
            double varG;
            double varB;

            if( var_i == 0 ) { 
                varR = V; 
                varG = varTres; 
                varB = varUno; 
                }
            else 
            if( var_i == 1 ) { 
                varR = varDos; 
                varG = V; 
                varB = varUno; 
                }
            else 
            if( var_i == 2 ) { 
                varR = varUno; 
                varG = V; 
                varB = varTres; 
                }
            else 
            if( var_i == 3 ) { 
                varR = varUno; 
                varG = varDos; 
                varB = V;     
                }
            else 
            if( var_i == 4 ) { 
                varR = varTres; 
                varG = varUno; 
                varB = V;     
                }
            else { 
                varR = V; 
                varG = varUno; 
                varB = varDos; 
                }
            R = varR * 255.0;
            G = varG * 255.0;
            B = varB * 255.0;
        }
        Rgb rgb = new Rgb(R, G, B);
        return rgb;
    }
    /*************************** END HSI TO RGB *****************************/
}
