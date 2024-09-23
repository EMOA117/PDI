/**
 * Nombre del paquete al que pertenece la clase.
 */
package color;

/**
 * Interface creada para almacenar las constantes de la clase RGBtoYIQ. Ultima
 * modificacion: 29/oct/2004
 *
 * @author Saul De La O Torres
 * @version 1.0 29 Octubre de 2004.
 */
public interface IYiqRgbConstante {

    /**
     * Cero punto cero.
     */
    double CPC = 0.0;
    /**
     * Doscientos cincuenta y cinco punto cero.
     */
    double DCCPC = 255.0;
    /**
     * Cero punto cero uno.
     */
    double CPCU = 0.01;
    /**
     * Cero punto cero cero uno.
     */
    double CPCCU = 0.001;
    /**
     * Quince.
     */
    double QUINCE = 15.0;
    /**
     * Porcentaje de I (crominancia 1) para conversion de R (rojo).
     */
    double PJE_I_R = 0.956;
    /**
     * Porcentaje de I (crominancia 1) para conversion de G (verde).
     */
    double PJE_I_G = 0.272;
    /**
     * Porcentaje de I (crominancia 1) para conversion de B (azul).
     */
    double PJE_I_B = 1.106;
    /**
     * Porcentaje de Q (crominancia 2) para conversion de R (rojo).
     */
    double PJE_Q_R = 0.621;
    /**
     * Porcentaje de Q (crominancia 2) para conversion de G (verde).
     */
    double PJE_Q_G = 0.647;
    /**
     * Porcentaje de Q (crominancia 2) para conversion de B (azul).
     */
    double PJE_Q_B = 1.703;
}
