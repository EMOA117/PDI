package color;

/**
 * Clase de color del estimulo XYZ
 * @author sdelaot
 */
public class Xyz {
    /**
     * Almcena el estimulo (X) contenido en la paleta de colores de la
     * imagen.
     */
    private double X;
    /**
     * Almcena el estimulo (Y) contenido en la paleta de colores de la
     * imagen.
     */
    private double Y;
    /**
     * Almcena el estimulo (Z) contenido en la paleta de colores de la
     * imagen.
     */
    private double Z;

    /**
     * El Constructor solo inicializa la tercia XYZ.
     */
    public Xyz() {
        this(0, 0, 0);
    }

    /**
     * El Constructor inicializa por paso de valores a la trecia XYZ
     *
     * @param X es el estimulo X
     * @param Y es el estimulo Y
     * @param Z es el estimulo Z
     */
    public Xyz(double X, double Y, double Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }
    /**
     * Constructor de copia de la tercia XYZ
     * 
     * @param xyz el objeto que se copia
     */
    public Xyz(Xyz xyz) {
        this.X = xyz.X;
        this.Y = xyz.Y;
        this.Z = xyz.Z;
    }

    /**
     * Pone valores en la trecia XYZ
     *
     * @param X es el estimulo X
     * @param Y es el estimulo Y
     * @param Z es el estimulo Z
     */
    public void setXyz(double X, double Y, double Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    /**
     * Devuelve el etimulo (X) del XYZ
     *
     * @return double
     */
    public double getX() {
        return X;
    }

    /**
     * Devuelve el estimula (Y) del XYZ
     *
     * @return double
     */
    public double getY() {
        return Y;
    }

    /**
     * Devuelve el estimulo (Z) del XYZ
     *
     * @return double
     */
    public double getZ() {
        return Z;
    }
    /**
     * Pone el estimulo (X) del XYZ
     * @param X el nuevo estimulo X
     */
    public void setX(double X) {
        this.X = X;
    }
    /**
     * Pone el estimulo (Y) del XYZ
     * @param Y el nuevo estimulo Y
     */
    public void setY(double Y) {
        this.Y = Y;
    }
    /**
     * Pone el estimulo (Z) del XYZ
     * @param Z el nuevo estimulo Z
     */
    public void setZ(double Z) {
        this.Z = Z;
    }

    /**
     * Devuelve la tercia de valores RGB
     *
     * @return devuelve la cuarteta rgb
     */
    public double[] getXyz() {
        double [] xyz = {X, Y, Z};
        return xyz;
    }
    /**
     * Devuelve el objeto
     * 
     * @return devuelve el wrgquad
     */
    public Xyz getXyzObject() {
        return this;
    }
    /**************************** CONVERSIONES ******************************/
    /***************************** XYZ TO RGB *******************************/
    /**
     * Convierte Xyz a RGB  
     * @return devuelve el objeto Rgb
     */
    public Rgb convertirXyzToRgb() {
        //X, Y and Z input refer to a D65/2° standard illuminant.
        //sR, sG and sB (standard RGB) output range = 0 ÷ 255

        double varX = X / 100.0;
        double varY = Y / 100.0;
        double varZ = Z / 100.0;

        double varR = varX *  3.2406 + varY * -1.5372 + varZ * -0.4986;
        double varG = varX * -0.9689 + varY *  1.8758 + varZ *  0.0415;
        double varB = varX *  0.0557 + varY * -0.2040 + varZ *  1.0570;

        if( varR>0.0031308 ) {
            varR = 1.055 * (Math.pow(varR, ( 1.0 / 2.4 ))) - 0.055;
            }
        else {
            varR = 12.92 * varR;
            }
        if( varG>0.0031308 ) {
            varG = 1.055 * (Math.pow(varG, ( 1.0 / 2.4 ))) - 0.055;
            }
        else {                     
            varG = 12.92 * varG;
            }
        if( varB>0.0031308 ) {
            varB = 1.055 * (Math.pow(varB, ( 1.0 / 2.4 ))) - 0.055;
            }
        else {                     
            varB = 12.92 * varB;
            }

        double sR = varR * 255;
        double sG = varG * 255;
        double sB = varB * 255;
        Rgb rgb = new Rgb(sR, sG, sB);
        return rgb;
    }
    /**
     * Convierte XYZ a RGB adobe
     * @return devuelve el objeto Rgb
     */
    public Rgb convertirXyzToRgbAdobe() {
        //X, Y and Z input refer to a D65/2° standard illuminant.
        //aR, aG and aB (RGB Adobe 1998) output range = 0 ÷ 255

        double varX = X / 100.0;
        double varY = Y / 100.0;
        double varZ = Z / 100.0;

        double varR = varX *  2.04137 + varY * -0.56495 + varZ * -0.34469;
        double varG = varX * -0.96927 + varY *  1.87601 + varZ *  0.04156;
        double varB = varX *  0.01345 + varY * -0.11839 + varZ *  1.01541;

        varR = Math.pow(varR, ( 1.0 / 2.19921875 ));
        varG = Math.pow(varG, ( 1.0 / 2.19921875 ));
        varB = Math.pow(varB, ( 1.0 / 2.19921875 ));

        double aR = varR * 255.0;
        double aG = varG * 255.0;
        double aB = varB * 255.0;
        Rgb rgb = new Rgb(aR, aG, aB);
        return rgb;
    }
    /************************ END XYZ TO RGB  *******************************/
    /**
     * Convierte de XYZ a YXY
     * @return devuelve el objeto Yxy
     */
    public Yxy convertirXyzToXyz() {
        double Y = this.Y;
        double x = X / ( X + this.Y + Z );
        double y = this.Y / ( X + this.Y + Z );
        Yxy yxy = new Yxy(Y, x, y);
        return yxy;
    }
    /**
     * Convierte de Xyz a Lab Hunter
     * @param iluminante el iluminante empleado para convertir
     * @return devuelve el objeto Lab Hunter
     */
    public Lab convertirXyzToLabHunter(int iluminante) {
        //Reference X, Y and Z refer to specific illuminants and observers.
        //Common reference values are available below in this same page.
        double referenceX = Iluminante.REFERENCIA[iluminante][0];
        double referenceY = Iluminante.REFERENCIA[iluminante][1];
        double referenceZ = Iluminante.REFERENCIA[iluminante][2];

        double varKa = ( 175.0 / 198.04 ) * ( referenceY + referenceX );
        double varKb = (  70.0 / 218.11 ) * ( referenceY + referenceZ );

        double hunterL = 100.0  * 
                            Math.sqrt( Y / referenceY );
        double huntera = varKa * ( ( ( X / referenceX ) - 
                                     ( Y / referenceY ) ) / 
                            Math.sqrt( Y / referenceY ) );
        double hunterb = varKb * ( ( ( Y / referenceY ) - 
                                     ( Z / referenceZ ) ) / 
                            Math.sqrt( Y / referenceY ) );
        Lab lab = new Lab(hunterL, huntera, hunterb);
        return lab;
    }
    /**
     * Convierte de Xyz a Lab
     * @param iluminante el iluminante empleado
     * @return devuelve el objeto Lab
     */
    public Lab convertirXyzToLab(int iluminante) {
        //Reference-X, Y and Z refer to specific illuminants and observers.
        //Common reference values are available below in this same page.
        double referenceX = Iluminante.REFERENCIA[iluminante][0];
        double referenceY = Iluminante.REFERENCIA[iluminante][1];
        double referenceZ = Iluminante.REFERENCIA[iluminante][2];

        double varX = X / referenceX;
        double varY = Y / referenceY;
        double varZ = Z / referenceZ;

        if( varX > 0.008856 ) {
            varX = Math.pow(varX, ( 1.0/3.0 ));
            }
        else {
            varX = ( 7.787 * varX ) + ( 16.0 / 116.0 );
            }
        if( varY > 0.008856 ) {
            varY = Math.pow(varY, ( 1.0/3.0 ));
            }
        else {
            varY = ( 7.787 * varY ) + ( 16.0 / 116.0 );
            }
        if( varZ > 0.008856 ) {
            varZ = Math.pow(varZ, ( 1.0/3.0 ));
            }
        else {
            varZ = ( 7.787 * varZ ) + ( 16.0 / 116.0 );
            }

        double CIEL = ( 116 * varY ) - 16.0;
        double CIEa = 500 * ( varX - varY );
        double CIEb = 200 * ( varY - varZ );
        Lab lab = new Lab(CIEL, CIEa, CIEb);
        return lab;
    }
}
