package color;

/**
 *
 * @author sdelaot
 */
public class Ohta {
    /**
     * Almcena la luminancia (I1) contenido en la paleta de colores de la
     * imagen.
     */
    private double Iuno;
    /**
     * Almcena el color (I2) contenido en la paleta de colores de la
     * imagen.
     */
    private double Idos;
    /**
     * Almcena el color (I3) contenido en la paleta de colores de la
     * imagen.
     */
    private double Itres;

    /**
     * El Constructor solo inicializa la tercia I1I2I3.
     */
    public Ohta() {
        this(0, 0, 0);
    }

    /**
     * El Constructor inicializa por paso de valores a la trecia I1I2I3
     *
     * @param Iuno es la luminancia I1
     * @param Idos es el color I2
     * @param Itres es el color I3
     */
    public Ohta(double Iuno, double Idos, double Itres) {
        this.Iuno = Iuno;
        this.Idos = Idos;
        this.Itres = Itres;
    }
    /**
     * Constructor de copia de la tercia I1I2I3
     * 
     * @param ohta el objeto que se copia
     */
    public Ohta(Ohta ohta) {
        this.Iuno = ohta.Iuno;
        this.Idos = ohta.Idos;
        this.Itres = ohta.Itres;
    }

    /**
     * Pone valores en la trecia XYZ
     *
     * @param Iuno es la luminancia I1
     * @param Idos es el color I2
     * @param Itres es el color I3
     */
    public void setOhta(double Iuno, double Idos, double Itres) {
        this.Iuno = Iuno;
        this.Idos = Idos;
        this.Itres = Itres;
    }

    /**
     * Devuelve la luminancia (I1) del I1I2I3
     *
     * @return double
     */
    public double getXIuno() {
        return Iuno;
    }

    /**
     * Devuelve el color (I2) del I1I2I3
     *
     * @return double
     */
    public double getIdos() {
        return Idos;
    }

    /**
     * Devuelve el color (I3) del I1I2I3
     *
     * @return double
     */
    public double getItres() {
        return Itres;
    }

    /**
     * Pone la luminancia
     * @param Iuno el nuevo valor de la luminancia
     */
    public void setIuno(double Iuno) {
        this.Iuno = Iuno;
    }
    /**
     * Pone lal crominancia uno
     * @param Idos el nuevo valor de la crominancia uno
     */
    public void setIdos(double Idos) {
        this.Idos = Idos;
    }
    /**
     * Pone la crominancia dos
     * @param Itres el nuevo valor de la crominancia dos
     */
    public void setItres(double Itres) {
        this.Itres = Itres;
    }

    /**
     * Devuelve la tercia de valores RGB
     *
     * @return devuelve la cuarteta rgb
     */
    public double[] getOhta() {
        double [] ohta = {Iuno, Idos, Itres};
        return ohta;
    }
    /**
     * Devuelve el objeto
     * 
     * @return devuelve el wrgquad
     */
    public Ohta getOhtaObject() {
        return this;
    }
    /**************************** CONVERSIONES ******************************/
}
