package color;

/**
 *
 * @author sdelaot
 */
public class Rgb implements IBmpConstante, IRgbYiqConstante {
    /**
     * Almcena el color rojo (red) contenido en la paleta de colores de la
     * imagen.
     */
    private double r;
    /**
     * Almcena el color verde (green) contenido en la paleta de colores de la
     * imagen.
     */
    private double g;
    /**
     * Almcena el color alzul (blue) contenido en la paleta de colores de la
     * imagen.
     */
    private double b;

    /**
     * El Constructor solo inicializa el RGBQ.
     */
    public Rgb() {
        this(0, 0, 0);
    }

    /**
     * El constructor inicializa por paso de valores al RGB
     *
     * @param r es el color rojo
     * @param g es el color verde
     * @param b es el color azul
     */
    public Rgb(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    /**
     * Constructor de copia del rgb
     * 
     * @param rgbq el objeto que se copia
     */
    public Rgb(Rgb rgbq) {
        this.r = rgbq.r;
        this.g = rgbq.g;
        this.b = rgbq.b;
    }

    /**
     * Pone valores en RGBQ
     *
     * @param r es el color rojo
     * @param g es el color verde
     * @param b es el color azul
     */
    public void setRgb(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Devuelve el rojo (red) del RGB
     *
     * @return double
     */
    public double getR() {
        return r;
    }

    /**
     * Devuelve el verde (green) del RGB
     *
     * @return double
     */
    public double getG() {
        return g;
    }

    /**
     * Devuelve el azul (blue) del RGB
     *
     * @return double
     */
    public double getB() {
        return b;
    }
    /**
     * Pone el valor de rojo (red)
     * @param r el nuevo valor de r
     */
    public void setR(double r) {
        this.r = r;
    }
    /**
     * Pone el valor del verde (green)
     * @param g el nuevo valor de g
     */
    public void setG(double g) {
        this.g = g;
    }
    /**
     * Pone el valor del azul (blue)
     * @param b el nuevo valor de b
     */
    public void setB(double b) {
        this.b = b;
    }

    /**
     * Devuelve la tercia de valores RGB en un vector 
     *
     * @return devuelve el vector rgb
     */
    public double[] getRgb() {
        double [] rgb = {r, g, b};
        return rgb;
    }
    /**
     * Devuelve el objeto Rgb
     * 
     * @return devuelve el Rgb
     */
    public Rgb getRgbObject() {
        return this;
    }

    @Override
    public String toString() {
        return "Rgb{"+"r="+r+", g="+g+", b="+b+'}';
    }
    
    /**************************** CONVERSIONES ******************************/
    /***************************** RGB TO CMY *******************************/
    /**
     * Convierte un RGB en CMY
     * 
     * @return devuelve un objeto Cmy
     */
    public Cmy convertirRgbToCmy() {
        double c = 255.0 - r; // cian    = 255 - red
        double m = 255.0 - g; // magenta = 255 - green
        double y = 255.0 - b; // yellow  = 255 - blue
        Cmy cmy = new Cmy(c, m, y);
        return cmy;
    }
    /*************************** END RGB TO CMY *****************************/
    /*************************** RGB TO CMYK ********************************/
    /**
     * Convierte una tripleta CMY en RGB
     * 
     * @return devuelve un objeto Rgb
     */
    public Cmyk convertirRgbCmyk() {
        ColorFuncion funcion = new ColorFuncion();
        double k = funcion.min(255.0-r, 255.0-g, 255.0-b);
        double c = (255.0-r-k) / (255.0-k); 
        double m = (255.0-g-k) / (255.0-k); 
        double y = (255.0-b-k) / (255.0-k); 
        Cmyk cmyk = new Cmyk(c, m, y, k);
        return cmyk;
    }
    /************************* END RGB TO CMYK ******************************/
    /***************************** RGB TO HSV *******************************/
    /**
     * Convierte RGB a HSV 
     * @return devuelve el objeto Hsv
     */
    public Hsv convertirRgbToHsv() {
        //R, G and B input range = 0 ÷ 255
        //H, S and V output range = 0 ÷ 1.0

        double varR = ( r / 255.0 );
        double varG = ( g / 255.0 );
        double varB = ( b / 255.0 );

        ColorFuncion funcion = new ColorFuncion();
        
        double varMin = funcion.min(varR, varG, varB );//Min. value of RGB
        double varMax = funcion.max(varR, varG, varB );//Max. value of RGB
        double deltaMax = varMax - varMin;             //Delta RGB value

        double V = varMax;
        double H = 0;
        double S = 0;

        if( deltaMax == 0 ) {  //This is a gray, no chroma...
            H = 0;
            S = 0;
            }
        else {   //Chromatic data...
            S = deltaMax / varMax;

            double deltaR = ( ( ( varMax - varR ) / 6 ) + ( deltaMax / 2 ) ) / 
                            deltaMax;
            double deltaG = ( ( ( varMax - varG ) / 6 ) + ( deltaMax / 2 ) ) / 
                            deltaMax;
            double deltaB = ( ( ( varMax - varB ) / 6 ) + ( deltaMax / 2 ) ) / 
                            deltaMax;

            if( varR == varMax ) {
                H = deltaB - deltaG;
                }
            else 
            if( varG == varMax ) {
                H = ( 1.0 / 3.0 ) + deltaR - deltaB;
                }
            else 
            if( varB == varMax ) {
               H = ( 2.0 / 3.0 ) + deltaG - deltaR;
            }

            if ( H < 0 ) {
                H += 1.0;
                }
            if ( H > 1 ) {
                H -= 1.0;
                }
            }
        Hsv hsv = new Hsv(H, S, V);
        return hsv;
    }
    /*************************** END RGB TO HSV *****************************/
    /***************************** RGB TO HSI *******************************/
    /**
     * Convierte la triada rgb en una triada hsi
     *
     *
     * @return devuelve la tiada hsi convertida
     */
    public Hsi convertirRGBtoHSI() {
        double R = r / (r+g+b+0.000001);
        double G = g / (r+g+b+0.000001);
        double B = b / (r+g+b+0.000001);
        double I = calcularI(r/255.0, g/255.0, b/255.0);
        double S = calcularS(R, G, B);
        double H = calcularH(R, G, B);
        // DEBUG
        //System.out.print("rgb: " + r + " " + g + " " + b);
        //System.out.println(" hsi: " + H + " " + S + " " + I);
        Hsi hsi = new Hsi( H, S, I );
        return hsi;
    }
    /**
     * Calcula el valor de H
     *
     * @param R el valor del canal rojo
     * @param G el valor del canal verde
     * @param B el valor del canal azul
     *
     * @return devuelve el valor calculado de H
     */
    private double calcularH(double R, double G, double B) {
        double H;
        double divisor;
        double dividendo;
        dividendo = 0.5 * ((R-G)+(R-B));
        //System.out.println("dividendo " + dividendo + " " + B + " " + G);
        
        divisor = Math.sqrt( Math.pow(R-G, 2.0) + ((R-B)*(G-B)) );
        //System.out.println("divisor " + divisor + " " + B + " " + G);
        double division = dividendo / (divisor+0.000001);
        //System.out.println("division " + division + " " + B + " " + G);
        H = Math.acos(division);
        //System.out.println("0) H " + H + " radianes");
        H = (H / Math.PI) * 180;
        //System.out.println("1) H " + H + " grados");
       
        if( B>G ) {
            H = 360 - H;
            }
//        else {
//            H = theta;
//            }
        //System.out.println("2) H " + H);
        H /= 360.0;
        //System.out.println("3) H " + H);
        return H;
    }
    /**
     * Calcula el valor de S
     *
     * @param R el valor del canal rojo
     * @param G el valor del canal verde
     * @param B el valor del canal azul
     *
     * @return devuelve el valor calculado de S
     */
    private double calcularS(double R, double G, double B) {
        double valorS;
        double divisor = R + G + B;
        ColorFuncion funcion = new ColorFuncion();
        double min = funcion.min(R, G, B);
        //System.out.println("min " + min + " R " + R + " G " + G + " B " + B);
        valorS = 1.0 - (3.0/(R+G+B+0.000001)) * min;
        return valorS;
    }
    /**
     * Calcula el valor de V
     *
     * @param R el valor del canal rojo
     * @param G el valor del canal verde
     * @param B el valor del canal azul
     *
     * @return devuelve el valor calculado de V
     */
    private double calcularI(double R, double G, double B) {
        double valorV = (R + G + B) / 3.0;
        return valorV;
    }
    /*************************** END RGB TO HSI *****************************/
    /***************************** RGB TO LMS *******************************/
    /**
     * Convierte del formato RGB al formato LMS.
     *
     * @param r el valor del canal rojo
     * @param g el valor del canal verde
     * @param b el valor del canal azul
     *
     * @return devuelve la conversion de RGB a LMS de una triaba rgb
     */
    public double[] covertirRgbALmsD(int r, int g, int b) {
        double[] triadaLMS = new double[3];
        triadaLMS[0] = 0.3811 * r + 0.5783 * g + 0.0402 * b;
        triadaLMS[1] = 0.1967 * r + 0.7244 * g + 0.0782 * b;
        triadaLMS[2] = 0.0241 * r + 0.1288 * g + 0.8444 * b;
        for(int n = 0; n < triadaLMS.length; n++) {
            triadaLMS[n] = Math.log1p(triadaLMS[n]);
            }
        return triadaLMS;
    }
    /**
     * Convierte del formato RGB al formato LMS.
     *
     * @return devuelve la conversion de RGB a LMS de una triaba rgb
     */
    public double[] covertirRgbALmsD() {
        double[] triadaLMS = new double[3];
        triadaLMS[0] = 0.3811 * r + 0.5783 * g + 0.0402 * b;
        triadaLMS[1] = 0.1967 * r + 0.7244 * g + 0.0782 * b;
        triadaLMS[2] = 0.0241 * r + 0.1288 * g + 0.8444 * b;
        for(int n = 0; n < triadaLMS.length; n++) {
            triadaLMS[n] = Math.log(triadaLMS[n]);
            }
        return triadaLMS;
    }
    /**
     * Convierte del formato RGB al formato LMS.
     *
     * @return devuelve la conversion de RGB a LMS de una triaba rgb
     */
    public Lms convertirRgbALms() {
        Lms lms = new Lms();
        double L = 0.3811 * r + 0.5783 * g + 0.0402 * b;
        double M = 0.1967 * r + 0.7244 * g + 0.0782 * b;
        double S = 0.0241 * r + 0.1288 * g + 0.8444 * b;

        L = Math.log(L);
        M = Math.log(M);
        S = Math.log(S);
        
        lms.setLms(L, M, S);
        
        return lms;
    }
    /*************************** END RGB TO LMS *****************************/
    /***************************** RGB TO XYZ *******************************/
    /**
     * Convierte RGB a XYZ
     * @return devuelve un objeto xyz
     */
    public Xyz convertirRgbToXyz1982() {
        double R = r / 255.0;
        double G = g / 255.0;
        double B = b / 255.0;
        ColorFuncion funcion = new ColorFuncion();
        double gR = funcion.calcularGx(r);
        double gG = funcion.calcularGx(g);
        double gB = funcion.calcularGx(b);
        double X = 0.490*gR + 0.310*gG + 0.200*gB;
        double Y = 0.177*gR + 0.812*gG + 0.011*gB;
        double Z = 0.000*gR + 0.010*gG + 0.990*gB;
        
        Xyz xyz = new Xyz(X, Y, Z);
        return xyz;
    }
    /**
     * Convierte RGB a XYZ
     * @return devuelve un objeto xyz
     */
    public Xyz convertirRgbToXyzEBU() {
        double R = r / 255.0;
        double G = g / 255.0;
        double B = b / 255.0;
        ColorFuncion funcion = new ColorFuncion();
        double gR = funcion.calcularGx(r);
        double gG = funcion.calcularGx(g);
        double gB = funcion.calcularGx(b);
        double X = 0.430*gR + 0.343*gG + 0.178*gB;
        double Y = 0.222*gR + 0.707*gG + 0.071*gB;
        double Z = 0.020*gR + 0.130*gG + 0.939*gB;
        
        Xyz xyz = new Xyz(X, Y, Z);
        return xyz;
    }
    /**
     * Convierte RGB a XYZ
     * @return devuelve un objeto xyz
     */
    public Xyz convertirRgbToXyzFFC() {
        double R = r / 255.0;
        double G = g / 255.0;
        double B = b / 255.0;
        ColorFuncion funcion = new ColorFuncion();
        double gR = funcion.calcularGx(r);
        double gG = funcion.calcularGx(g);
        double gB = funcion.calcularGx(b);
        double X = 0.607*gR + 0.174*gG + 0.200*gB;
        double Y = 0.299*gR + 0.587*gG + 0.114*gB;
        double Z = 0.000*gR + 0.066*gG + 1.116*gB;
        
        Xyz xyz = new Xyz(X, Y, Z);
        return xyz;
    }

    /**
     * Convierte RGB a XYZ
     * @return devuelve el objeto Xyz
     */
    public Xyz convertirRgbToXyzTwo() {
        //sR, sG and sB (Standard RGB) input range = 0 ÷ 255
        //X, Y and Z output refer to a D65/2° standard illuminant.

        double varR = ( r / 255.0 );
        double varG = ( r / 255.0 );
        double varB = ( r / 255.0 );

        if( varR > 0.04045 ) {
            varR = Math.pow((( varR + 0.055 ) / 1.055 ), 2.4);
            }
        else {
            varR = varR / 12.92;
            }
        if( varG > 0.04045 ) {
            varG = Math.pow((( varG + 0.055 ) / 1.055 ), 2.4);
            }
        else {
            varG = varG / 12.92;
            }
        if( varB > 0.04045 ) {
            varB = Math.pow((( varB + 0.055 ) / 1.055 ), 2.4);
            }
        else {
            varB = varB / 12.92;
            }

        varR = varR * 100;
        varG = varG * 100;
        varB = varB * 100;

        double X = varR * 0.4124 + varG * 0.3576 + varB * 0.1805;
        double Y = varR * 0.2126 + varG * 0.7152 + varB * 0.0722;
        double Z = varR * 0.0193 + varG * 0.1192 + varB * 0.9505;
        
        Xyz xyz = new Xyz(X, Y, Z);
        return xyz;
    }
    /**
     * Convierte de RGB Adobe a XYZ
     * @return devuelve el objeto Xyz
     */
    public Xyz convertirRgbAdobeToXyz() {
        //aR, aG and aB (RGB Adobe 1998) input range = 0 ÷ 255
        //X, Y and Z output refer to a D65/2° standard illuminant.

        double varR = ( r / 255.0 );
        double varG = ( g / 255.0 );
        double varB = ( b / 255.0 );

        varR = Math.pow(varR, 2.19921875);
        varG = Math.pow(varG, 2.19921875);
        varB = Math.pow(varB, 2.19921875);

        varR = varR * 100.0;
        varG = varG * 100.0;
        varB = varB * 100.0;

        double X = varR * 0.57667 + varG * 0.18555 + varB * 0.18819;
        double Y = varR * 0.29738 + varG * 0.62735 + varB * 0.07527;
        double Z = varR * 0.02703 + varG * 0.07069 + varB * 0.99110;
        Xyz xyz = new Xyz(X, Y, Z);
        return xyz;
    }
    /*************************** END RGB TO XYZ *****************************/
    /**************************** RGB TO YCBCR ******************************/
    /**
     * Convierte de RGB a YCbCr 
     * 
     * @return devuelve el objeto YCbCr
     */
    public YCbCr convertirRgbAYCbCrTwo() {
        double dRed   = r / 255.0;
        double dGreen = g / 255.0;
        double dBlue  = b / 255.0;
        double Y  =  16.0 + ( 65.481*dRed + 128.553*dGreen +  24.996*dBlue);
        double Cb = 128.0 + (-37.797*dRed + -74.203*dGreen + 112.0  *dBlue);
        double Cr = 128.0 + (112.0  *dRed + -93.786*dGreen + -18.214*dBlue);
        YCbCr  ycbcr = new YCbCr( Y, Cb, Cr );
        return ycbcr;
    }
    /**
     * Convierte de RGB a YCbCr
     * 
     * @return devuelve el objeto YCbCr
     */
    public YCbCr convertirRgbAYCbCr() {
        double Y = ( 0.299 * r + 0.587 * g +  0.114 * b );
        double Cb =   0.56 * (b - Y);
        double Cr =   0.71 * (r  - Y);
        YCbCr ycbcr = new YCbCr(Y, Cb, Cr);
        return ycbcr;
    }
    /**
     * Convierte de RGB a YCbCr formato Jpeg
     * 
     * @return devuelve el objeto YCbCr
     */
    public YCbCr convertirRgbAYCbCrJpeg() {
        double Y  =   0.0 + (0.299000*r) + (0.587000*g) + (0.114000*b);
        double Cb = 128.0 - (0.168736*r) - (0.331264*g) + (0.500000*b);
        double Cr = 128.0 + (0.500000*r) - (0.418688*g) - (0.081312*b);
        YCbCr  ycbcr = new YCbCr( Y, Cb, Cr );
        return ycbcr;
    }
    /************************** END RGB TO YCBCR ****************************/
    /***************************** RGB TO YIQ *******************************/
    /**
     * Con los datos de la paleta de colores en formato RGB listos, se realiza 
     * la conversion de RGB a YIQ.
     * @return devuelve el objeto Yiq
     */
    public Yiq convertirRgbAYiq() {
	double Y = PJE_R_Y * r + PJE_G_Y * g + PJE_B_Y * b;
        double I = PJE_R_I * r - PJE_G_I * g - PJE_B_I * b;
        double Q = PJE_R_Q * r - PJE_G_Q * g + PJE_B_Q * b;
        // DEBUG
        //System.out.println("Y= " + Y + ", I= " + I + ", Q= " + Q);
	Yiq yiq = new Yiq(Y, I, Q);
        return yiq;
    }
    /*************************** END RGB TO YIQ *****************************/
    /***************************** RGB TO YUV *******************************/
    /**
     * Convierte RGB a YUV
     * @param selector 1 o 2 valores correctos
     * @return devuelve un objeto Yuv
     */
    public Yuv convertirRgbToYuv(int selector) {
        double Y;
        double U; 
        double V;
        Y =  0.299*r + 0.587*g + 0.114*b;
        if(selector==1) {
            U = -0.147*r - 0.289*g + 0.437*b; 
            V =  0.615*r - 0.515*g - 0.100*b;
            }
        else {
            U = 0.493*(b-Y);
            V = 0.877*(r-Y);
            }
        
        Yuv yuv = new Yuv(Y, U, V);
        return yuv;
    }
    /*************************** END RGB TO YUV *****************************/
    /***************************** RGB TO Otha ******************************/
    /**
     * Convierte RGB a Ohta I1I2I3
     * @return devuelve el objeto ohta
     */
    public Ohta convertirRgbToOhta() {
        double Iuno = (r+g+b) / 3.0;
        double Idos = (r-g) / 2.0;
        double Itres = ((2*g)-r-b) / 4.0;
        Ohta ohta = new Ohta(Iuno, Idos, Itres);
        return ohta;
    }
    /*************************** END RGB TO YUV *****************************/
}
