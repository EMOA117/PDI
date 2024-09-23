package color;

/**
 *
 * @author sdelaot
 */
public class Lab {
    /**
     * Almacen de la Luminancia Y del formato Lab.
     */
    private double l;
    /**
     * Almacen de la Parte de la Crominancia del formato Lab.
     */
    private double a;
    /**
     * Almacen de la Parte de la crominancia del formato Lab.
     */
    private double b;

    /**
     * El Constructor solo inicializa la tercia Lab.
     */
    public Lab() {
        this(0, 0, 0);
    }

    /**
     * El Constructor inicializa por paso de valores a la tercia Lab.
     *
     * @param l es la luminancia
     * @param a es la crominancia 1
     * @param b es la crominancia 2
     */
    public Lab(double l, double a, double b) {
        this.l = l;
        this.a = a;
        this.b = b;
    }
    /**
     * El Constructor de copia.
     *
     * @param lab es el objeto que se copia
     */
    public Lab(Lab lab) {
        this.l = lab.l;
        this.a = lab.a;
        this.b = lab.b;
    }

    /**
     * Pone valores en la tercia Lab.
     *
     * @param l es la luminancia
     * @param a es la crominancia 1
     * @param b es la crominancia 2
     */
    public void setLab(double l, double a, double b) {
        this.l = l;
        this.a = a;
        this.b = b;
    }

    /**
     * Devuelve la luminancia L del formato Lab.
     *
     * @return double el valor de L
     */
    public double getL() {
        return l;
    }

    /**
     * Devuelve el color 1 a del formato Lab.
     *
     * @return double el valor de a
     */
    public double getA() {
        return a;
    }

    /**
     * Devuelve el color 2 b del formato Lab.
     *
     * @return double el valor de b
     */
    public double getB() {
        return b;
    }

    /**
     * Devuelve la tercia de valores Lab
     *
     * @return double devuelve la tripleta Lab
     */
    public double[] getLab() {
        double[] lab = {l, a, b};
        return lab;
    }
    
    /**
     * Devuelve la tercia de valores Lab en un objeto
     *
     * @return devuelve el objeto Lab
     */
    public Lab getLabObject() {
        return this;
    }

    public void setL(double l) {
        this.l = l;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }
    

    @Override
    public String toString() {
        return "Lab{"+"l="+l+", a="+a+", b="+b+'}';
    }
    
    /**************************** CONVERSIONES ******************************/
    /***************************** Lab TO RGB *******************************/
    
    /*************************** END Lab TO RGB *****************************/
    /***************************** Lab TO LMS *******************************/
    /**
     * Convierte del formato Lab al formato LMS.
     *
     * @param l el valor del canal l
     * @param a el valor del canal a
     * @param b el valor del canal b
     *
     * @return devuelve la conversion de Lab a LMS de una triaba lab
     */
    public double[] convertirLabToLMS(double l, double a, double b) {
        //System.out.println( " lab " + l + " " + a + " " + b );
        double[] triadaLMS = new double[3];
        triadaLMS[0] = 0.57735 * l + 0.40825 * a + 0.70711 * b;
        triadaLMS[1] = 0.57735 * l + 0.40825 * a - 0.70711 * b;
        triadaLMS[2] = 0.57735 * l - 0.81650 * a + 0.0     * b;
        //System.out.println( " lms1 " + triadaLMS[0] + " " + triadaLMS[1] + 
        // " " + triadaLMS[2] );
        return triadaLMS;
    }
    /**
     * Convierte del formato Lab al formato LMS.
     *
     * @return devuelve la conversion de Lab a LMS de una triaba lab
     */
    public Lms convertirLabToLms() {
        //System.out.println( " lab " + l + " " + a + " " + b );
        Lms lms = new Lms();
        double L = 0.57735 * l + 0.40825 * a + 0.70711 * b;
        double M = 0.57735 * l + 0.40825 * a - 0.70711 * b;
        double S = 0.57735 * l - 0.81650 * a + 0.0     * b;
        lms.setLms(L, M, S);
        //System.out.println( " lms1 " + triadaLMS[0] + " " + triadaLMS[1] + 
        // " " + triadaLMS[2] );
        return lms;
    }
    /*************************** END Lab TO LMS *****************************/
    /**
     * Convierte Hunter Lab a XYZ
     * @param iluminante iluminante empleado para el calculo
     * @return devuelve el objeto Xyz
     */
    public Xyz convertirLabHunterToXyz(int iluminante) {
        //Reference-X, Y and Z refer to specific illuminants and observers.
        //Common reference values are available below in this same page.
        double referenceX = Iluminante.REFERENCIA[iluminante][0];
        double referenceY = Iluminante.REFERENCIA[iluminante][1];
        double referenceZ = Iluminante.REFERENCIA[iluminante][2];

        double var_Ka = ( 175.0 / 198.04 ) * ( referenceY + referenceX );
        double var_Kb = (  70.0 / 218.11 ) * ( referenceY + referenceZ );

        double Y = ( Math.pow(l / referenceY, 2.0 )) * 100.0;
        double X =   ( a / var_Ka * Math.sqrt( Y / referenceY ) + 
                     ( Y / referenceY ) ) * referenceX;
        double Z = - ( b / var_Kb * Math.sqrt( Y / referenceY ) - 
                     ( Y / referenceY ) ) * referenceZ;
        Xyz xyz = new Xyz(X, Y, Z);
        return xyz;
    }
    /**
     * Convierte de Lab a Xyz
     * @param iluminante el iluminante
     * @return devuelve el objeto Xyz
     */
    public Xyz convertirLabToXyz(int iluminante) {
        //Reference-X, Y and Z refer to specific illuminants and observers.
        //Common reference values are available below in this same page.
        double referenceX = Iluminante.REFERENCIA[iluminante][0];
        double referenceY = Iluminante.REFERENCIA[iluminante][1];
        double referenceZ = Iluminante.REFERENCIA[iluminante][2];

        double var_Y = ( l + 16.0 ) / 116.0;
        double var_X = a / 500.0 + var_Y;
        double var_Z = var_Y - b / 200.0;

        if( Math.pow(var_Y, 3.0)>0.008856 ) {
            var_Y = Math.pow(var_Y, 3.0);
            }
        else {  
            var_Y = ( var_Y - 16.0 / 116.0 ) / 7.787;
            }
        if( Math.pow(var_X, 3.0)>0.008856 ) {
            var_X = Math.pow(var_X, 3.0);
            }
        else {
            var_X = ( var_X - 16.0 / 116.0 ) / 7.787;
            }
        if ( Math.pow(var_Z, 3.0)>0.008856 ) {
            var_Z = Math.pow(var_Z, 3.0);
            }
        else {
            var_Z = ( var_Z - 16.0 / 116.0 ) / 7.787;
            }

        double X = var_X * referenceX;
        double Y = var_Y * referenceY;
        double Z = var_Z * referenceZ;
        Xyz xyz = new Xyz(X, Y, Z);
        return xyz;
    }
}
