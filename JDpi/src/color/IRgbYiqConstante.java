/**
 * Nombre del paquete al que pertenece la clase.
 */
package color;

/**
 * Interface creada para almacenar las constantes de la clase RGBtoYIQ. Ultima
 * modificacion 29/oct/2004
 *
 * @author Saul De La O Torres
 * @version 1.0 29 Octubre de 2004.
 */
public interface IRgbYiqConstante {
    /**
     * Porcentaje de R (rojo) para conversion de Y (luminancia).
     */
    double PJE_R_Y = 0.299;
    /**
     * Porcentaje de R (rojo) para conversion de I (color 1).
     */
    double PJE_R_I = 0.596;
    /**
     * Porcentaje de R (rojo) para conversion de Q (color 2).
     */
    double PJE_R_Q = 0.211;
    /**
     * Porcentaje de G (verde) para conversion de Y (luminancia).
     */
    double PJE_G_Y = 0.587;
    /**
     * Porcentaje de G (verde) para conversion de I (color 1).
     */
    double PJE_G_I = 0.274;
    /**
     * Porcentaje de G (verde) para conversion de Q (color 2).
     */
    double PJE_G_Q = 0.523;
    /**
     * Porcentaje de B (azul) para conversion de Y (luminancia).
     */
    double PJE_B_Y = 0.114;
    /**
     * Porcentaje de B (azul) para conversion de I (color 1).
     */
    double PJE_B_I = 0.322;
    /**
     * Porcentaje de B (azul) para conversion de Q (color 2).
     */
    double PJE_B_Q = 0.312;
}
