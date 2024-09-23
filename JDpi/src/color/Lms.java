package color;

/**
 * Clase que almacena la informacion del formato Lms
 * 
 * @author sdelaot
 */
public class Lms {
    /**
     * Luminancia
     */
    private double l;
    /**
     * Color
     */
    private double m;
    /**
     * Color
     */
    private double s;
    /**
     * Crea el objeto de lms por defecto
     */
    public Lms() {
        this(0,0,0);
    }
    /**
     * Crea el objeto lms con los valores
     * @param l la luminancia
     * @param m la crominancia uno
     * @param s la crominancia dos
     */
    public Lms(double l, double m, double s) {
        this.l = l;
        this.m = m;
        this.s = s;
    }
    /**
     * Crea un duplicado del objeto Lms
     * @param lms el objeto que se duplica
     */
    public Lms(Lms lms) {
        this.l = lms.l;
        this.m = lms.m;
        this.s = lms.s;
    }
    /**
     * Pone los valores del objeto Lms
     * @param l la luminancia
     * @param m la crominancia uno
     * @param s la crominancia dos
     */
    public void setLms(double l, double m, double s) {
        this.l = l;
        this.m = m;
        this.s = s;
    }
    /**
     * Devuelve la luminancia
     * @return devuelve l
     */
    public double getL() {
        return l;
    }
    /**
     * Pone la lumninancia
     * @param l el nuevo valor de la luminancia
     */
    public void setL(double l) {
        this.l = l;
    }
    /**
     * Devuelve la crominancia uno
     * @return devuelve m
     */
    public double getM() {
        return m;
    }
    /**
     * Pone la crominancia uno
     * @param m el nuevo valor de la crominancia uno
     */
    public void setM(double m) {
        this.m = m;
    }
    /**
     * Devuelve la crominancia dos
     * @return devuelve s
     */
    public double getS() {
        return s;
    }
    /**
     * Pone la crominancia dos
     * @param s el nuevo valor de la crominancia dos
     */
    public void setS(double s) {
        this.s = s;
    }
    
    /**
     * Devuelve la tercia de valores LMS
     *
     * @return devuelve la cuarteta rgb
     */
    public double[] getLms() {
        double [] lms = {l, m, s};
        return lms;
    }
    /**
     * Devuelve el objeto
     * 
     * @return devuelve el wrgquad
     */
    public Lms getLmsObject() {
        return this;
    }

    /**
     * Devuelve el estado del objeto Lms
     * @return devuelve el string del objeto
     */
    @Override
    public String toString() {
        return "Lms{"+"l="+l+", m="+m+", s="+s+'}';
    }
    
    /**************************** CONVERSIONES ******************************/
    /***************************** LMS TO Lab *******************************/
    /**
     * Convierte del formato LMS al formato Lab.
     *
     * @param triadaLMS los tres valores de LMS
     *
     * @return devuelve la conversion
     */
    public double[] convertirLMSToLab(double[] triadaLMS) {
        double L = triadaLMS[0];
        double M = triadaLMS[1];
        double S = triadaLMS[2];
        double[] triadaLab = new double[3];
        triadaLab[0] = 0.57735 * L + 0.57735 * M + 0.57735 * S;
        triadaLab[1] = 0.40825 * L + 0.40825 * M - 0.81650 * S;
        triadaLab[2] = 0.70711 * L - 0.70711 * M + 0.0 * S;
        return triadaLab;
    }
    /**
     * Convierte del formato LMS al formato Lab.
     *
     * @return devuelve la conversion
     */
    public double[] convertirLMSToLab() {
        double[] triadaLab = new double[3];
        triadaLab[0] = 0.57735 * l + 0.57735 * m + 0.57735 * s;
        triadaLab[1] = 0.40825 * l + 0.40825 * m - 0.81650 * s;
        triadaLab[2] = 0.70711 * l - 0.70711 * m + 0.00000 * s;
        return triadaLab;
    }
    /**
     * Convierte del formato Lms al formato Lab.
     *
     * @return devuelve la conversion
     */
    public Lab convertirLmsToLab() {
        Lab lab = new Lab();
        double L = 0.57735 * l + 0.57735 * m + 0.57735 * s;
        double a = 0.40825 * l + 0.40825 * m - 0.81650 * s;
        double b = 0.70711 * l - 0.70711 * m + 0.0     * s;
        lab.setLab(L, a, b);
        return lab;
    }
    /*************************** END LMS TO Lab *****************************/
    /***************************** LMS TO RGB *******************************/
    /**
     * Convierte del formato LMS al formato RGB.
     *
     * @param triadaLMS el valor de L, M y S
     *
     * @return devuelve la conversion
     */
    public double[] convertirLmsARgbD(double[] triadaLMS) {
        for(int n = 0; n < triadaLMS.length; n++) {
            //triadaLMS[n] = Math.pow(10.0, triadaLMS[n]);
            triadaLMS[n] = Math.exp(triadaLMS[n]);
            }
        //System.out.println( " lms2 " + triadaLMS[0] + " " + triadaLMS[1] + 
        // " " + triadaLMS[2] );
        double L = triadaLMS[0];
        double M = triadaLMS[1];
        double S = triadaLMS[2];
        double[] triadaRGB = new double[3];
        triadaRGB[0] =  4.4679 * L - 3.5873 * M + 0.1193 * S;
        triadaRGB[1] = -1.2186 * L + 2.3809 * M - 0.1624 * S;
        triadaRGB[2] =  0.0497 * L - 0.2439 * M + 1.2045 * S;
        return triadaRGB;
    }
    /**
     * Convierte del formato LMS al formato RGB.
     *
     * @return devuelve la conversion
     */
    public Rgb convertirLmsToRgb() {
        //System.out.println( " lms2 " + triadaLMS[0] + " " + triadaLMS[1] + 
        // " " + triadaLMS[2] );
        double L = Math.exp(l);
        double M = Math.exp(m);
        double S = Math.exp(s);
        Rgb rgb = new Rgb();
        double R =  4.4679 * L - 3.5873 * M + 0.1193 * S;
        double G = -1.2186 * L + 2.3809 * M - 0.1624 * S;
        double B =  0.0497 * L - 0.2439 * M + 1.2045 * S;
        R = Math.round(R);
        G = Math.round(G);
        B = Math.round(B);
        rgb.setRgb(R, G, B);
        return rgb;
    }
    /*************************** END LMS TO RGB *****************************/
}
