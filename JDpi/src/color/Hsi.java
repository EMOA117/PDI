package color;

/**
 *
 * @author sdelaot
 */
public class Hsi implements IRgbHsiConstante {
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
    private double I;

    /**
     * El Constructor solo inicializa la tercia YIQ.
     */
    public Hsi() {
        this(0, 0, 0);
    }
    /**
     * El Constructor inicializa por paso de valores a la tercia HSI.
     *
     * @param h es la huella
     * @param s es la saturacion
     * @param i es la luminancia
     */
    public Hsi(double h, double s, double i) {
        this.H = h;
        this.S = s;
        this.I = i;
    }
    /**
     * El asigna por paso de valores a la tercia HSI.
     *
     * @param h es la huella
     * @param s es la saturacion
     * @param i es la luminancia
     */
    public void setHSI(double h, double s, double i) {
        this.H = h;
        this.S = s;
        this.I = i;
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
    public double getI() {
        return I;
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
     * @param I el nuevo valor de iluminacion
     */
    public void setI(double I) {
        this.I = I;
    }
    /**
     * Devuelve la tercia HSI
     * 
     * @return devuelve un arreglo la tercia hsi 
     */
    public double[] getHSI() {
        double[] hsi = {H, S, I};
        return hsi;
    }

    @Override
    public String toString() {
        return "Hsi{"+"H="+H+", S="+S+", I="+I+'}';
    }
    
    /**************************** CONVERSIONES ******************************/
    /***************************** HSI TO RGB *******************************/
    /**
     * Convierte del formato HSI a RGB
     * 
     * @return devuelve el rgb convertido de aqui.
     */
    public Rgb convertirHsiToRgb() {
        double R = 0;
        double G = 0;
        double B = 0;
        H *= 360.0;
        double division;
        if((int)H>=0 && (int)H<120) {
            division = (S*cos(H))/cos(60.0-H);
            B = I * (1.0 - S);
            R = I * (1.0 + division);
            G = (3.0 * I) - (R+B);
            //System.out.println("1) H " + H + " R " + R + " G " + G + " B " + B);
            }
        else
        if((int)H>=120 && (int)H<240) {
            division = (S*cos(H - 120.0))/cos(60.0 - (H - 120.0));
            R = I * (1 - S);
            G = I * (1 + division);
            B = (3.0 * I) - (R+G);
            //System.out.println("2) H " + H + " R " + R + " G " + G + " B " + B);
            }
        else
        if((int)H>240 && (int)H<360) {
            division = (S*cos(H - 240.0))/cos(60.0-(H - 240.0));
            //System.out.println("division " + division );
            G = I * (1 - S);
            B = I * (1 + division);
            R = (3.0 * I) - (G+B);
            //System.out.println("3) H " + H + " R " + R + " G " + G + " B " + B);
            }
        
        R *= 255.0;
        G *= 255.0;
        B *= 255.0;
        R = Math.round(R);
        G = Math.round(G);
        B = Math.round(B);
        // DEBUG 
        //System.out.println("A\t" + R + "\t" + G + "\t" + B);
        if((int)R>255) {
            R = 255.0;
            }
        if((int)G>255) {
            G = 255.0;
            }
        if((int)B>255) {
            B = 255.0;
            }
        if((int)R<0) {
            R = 0.0;
            }
        if((int)G<0) {
            G = 0.0;
            }
        if((int)B<0) {
            B = 0.0;
            }
        // DEBUG 
        //System.out.println("B\t" + R + "\t" + G + "\t" + B);
        Rgb rgb = new Rgb(R, G, B);
        return rgb;
    }
    /**
     * Calcula el coseno de theta
     * 
     * @param theta el valor a calcular su coseno
     * 
     * @return devuelve el coseno de theta
     */
    private double cos(double theta) {
        return Math.cos(theta*0.0174533);
    }
    /*************************** END HSI TO RGB *****************************/
}
