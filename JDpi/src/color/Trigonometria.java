package color;

/**
 * Clase que devuelve las funciones basicas trigonometricas
 * @author sdelaot
 */
public class Trigonometria implements TrigonometriaTabla {
    /**
     * Devuelve el seno de los grados enviados
     * @param grados los grados
     * @return devuelve sin(grados)
     */
    public static double sin(int grados) {
        return TABLA[grados][1];
    }
    /**
     * Devuelve el coseno de los grados enviados
     * @param grados los grados
     * @return devuelve cos(grados)
     */
    public static double cos(int grados) {
        return TABLA[grados][2];
    }
    /**
     * Devuelve la tangente de los grados enviados
     * @param grados los grados
     * @return devuelve tg(grados)
     */
    public static double tg(int grados) {
        return TABLA[grados][3];
    }
    /**
     * Devuelve la cotangente de los grados enviados
     * @param grados los grados
     * @return devuelve ctg(grados)
     */
    public static double ctg(int grados) {
        return TABLA[grados][4];
    }
}
