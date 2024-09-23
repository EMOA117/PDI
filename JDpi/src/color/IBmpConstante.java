/**
 * Nombre del paquete al que pertenece la clase.
 */
package color;

/**
 * Interface creada para almacenar las constantes de la clase Bmp.
 * Ultima modificacion:
 * 30/may/2004
 * @author Saul De La O Torres
 * @version 1.0 21 Septiembre de 2003.
 */
public interface IBmpConstante {
	/**
	 * Total de colores en la paleta de la imagen.
	 */
	int COLORES = 256;
	/**
	 * Total de niveles de gris en una imagen.
	 */
	int NGRIS = 256;
	/**
	 * Comienzo de la imagen en el archivo BMP.
	 */
	int COM_IMG = 1078;
	/**
	 * Comienzo de la paleta de colores.
	 */
	int COM_PAL = 54;
	/**
	 * Potencia de dos a la cero igual a cero.
	 */
	int DOS_POT_CERO = 1;
	/**
	 * Potencia de dos a la ocho es igual a doscientos cincuenta y seis.
	 */
	int DOS_POT_OCHO = 256;
	/**
	 * Potencia de dos a la diez y seis es igual a 65535.
	 */
	int DOS_POT_UNOSEIS = 65535;
	/**
	 * Cero es cero.
	 */
	int CERO = 0;
	/**
	 * Comienza ancho.
	 */
	int COM_ANCHO = 18;
	/**
	 * Comienza alto.
	 */
	int COM_ALTO = 22;
	/**
	 * Final alto.
	 */
	int FIN_ALTO = 26;
	/**
	 * Constante de valor muy grande.
	 */
	double GRANDE = 1000000.0;
}