/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package color;

/**
 *
 * @author sdelaot
 */
public class YCbCr {
    /**
     * Luminancia
     */
    private double Y;
    /**
     * Croma uno
     */
    private double Cb;
    /**
     * Croma dos
     */
    private double Cr;
    /**
     * Constructor por defecto
     */
    public YCbCr() {
        this(0,0,0);
    }
    /**
     * Constructor que recibe todos los valores
     * @param Y la luminancia
     * @param Cb el croma uno
     * @param Cr el croma dos
     */
    public YCbCr(double Y, double Cb, double Cr) {
        this.Y = Y;
        this.Cb = Cb;
        this.Cr = Cr;
    }
    /**
     * Constructor de copia
     * 
     * @param ycbcr el objeto que se copia
     */
    public YCbCr(YCbCr ycbcr) {
        this.Y = ycbcr.Y;
        this.Cb = ycbcr.Cb;
        this.Cr = ycbcr.Cr;
    }
    
    /**
     * Metodo que recibe todos los valores
     * @param Y la luminancia
     * @param Cb el croma uno
     * @param Cr el croma dos
     */
    public void setYCbCr(double Y, double Cb, double Cr) {
        this.Y = Y;
        this.Cb = Cb;
        this.Cr = Cr;
    }
    /**
     * Devuelve la luminancia
     * @return devuelve Y
     */
    public double getY() {
        return Y;
    }
    /**
     * Pone la luminancia
     * @param Y el nuevo valor de Y
     */
    public void setY(double Y) {
        this.Y = Y;
    }
    /**
     * Devuelve el croma uno
     * @return devuelve Cr
     */
    public double getCb() {
        return Cb;
    }
    /**
     * Pone el croma uno
     * @param Cb el nuevo valor de Cb
     */
    public void setCb(double Cb) {
        this.Cb = Cb;
    }
    /**
     * Devuelve el croma dos
     * @return devuelve Cr
     */
    public double getCr() {
        return Cr;
    }
    /**
     * Ponr el croma dos
     * @param Cr el nuevo valor de Cr
     */
    public void setCr(double Cr) {
        this.Cr = Cr;
    }
    /**
     * Devuelve la tercia de valores YCbCr en un vector 
     * @return devuelve el vector YCbCr
     */
    public double [] getYCbCr() {
        double [] ycbcr = {Y, Cb, Cr};
        return ycbcr;
    }
    /**
     * Devuelve el objeto YCbCr
     * 
     * @return devuelve el YCbCr
     */
    public YCbCr getRgbObject() {
        return this;
    }
    /***************************** CONVERSIONES *****************************/
    /***************************** YCBCR TO RGB *****************************/
    /**
     * Convierte de YCbCr a Rgb
     * @return devuelve el objeto Rgb
     */
    public Rgb convertirYCbCrToRgb() {
        double A = (255.0/219.0) * (Y-16.0);
        double B = (255.0/112.0) * 0.886 * (Cb-128.0);
        double C = (255.0/112.0) * 0.701 * (Cr-128.0);
        double D = 0.114 / 0.587;
        double E = 0.299 / 0.587;
        
        double r = A           +  C;
        double g = A - (B * D) - (C * E);
        double b = A +  B;
        
        Rgb rgb = new Rgb(r, g, b);
        return rgb;
    }
    /**
     * Convierte de YCbCr a Rgb formato jpeg
     * @return devuelve el objeto Rgb
     */
    public Rgb convertirYCbCrToRgbJpeg() {
        double r = Y                         + 1.402000 * (Cr-128.0);
        double g = Y - 0.344136 * (Cb-128.0) - 0.714136 * (Cr-128.0);
        double b = Y + 1.772000 * (Cb-128.0);
        // DEBUG
//        if(r<0 || r>255 || g<0 || g>255 || b<0 || b>255) {
//            System.out.println("1) r " + r + " g " + g + " b " + b);
//            }
        r = reacondicionar(r);
        g = reacondicionar(g);
        b = reacondicionar(b);
        
        Rgb rgb = new Rgb(r, g, b);
        return rgb;
    }
    /**
     * Verifica si los valores estan en rango
     * @param valor
     * @return 
     */
    private double reacondicionar(double valor) {
        if((int)valor<0) {
            valor = 0.0;
            }
        if((int)valor>255) {
            valor = 255.0;
            }
        return valor;
    }
    /*************************** END YCBCR TO RGB ***************************/
}
