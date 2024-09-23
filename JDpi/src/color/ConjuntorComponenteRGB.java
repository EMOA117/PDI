package color;

import java.awt.Color;

/**
 *
 * @author sdelaot
 */
public class ConjuntorComponenteRGB {
    /**
     * Componentes RGB
     */
    private int [] pixeles;
    /**
     * Crea el objeto conjuntor de componentes RGB
     */
    public ConjuntorComponenteRGB() {
        pixeles =  null;
    }
    /**
     * Conjunta los canales en una imagen
     * 
     * @param rojo el vector  del canal rojo
     * @param verde el vector del canal verde
     * @param azul el vector del canal zul
     * 
     * @return devuelve la matriz de la imagen reunida de los canales
     */
    public int [] conjuntarComponenteRgb( double [] rojo, double [] verde, 
        double [] azul ) {
        pixeles = new int[rojo.length];
        Color color;
        for( int n=0; n<rojo.length; n++ ) {
            color = new Color( (int)rojo[n], (int)verde[n], (int)azul[n] );
            pixeles[n] = color.getRGB();
            }
        return pixeles;
    }
    /**
     * Conjunta los canales en una imagen a partir de una imagen rgb
     * 
     * @param rgb el vector  rgb
     * 
     * @return devuelve la matriz de la imagen reunida de los canales
     */
    public int [] conjuntarComponenteRgb( Rgb [] rgb ) {
        pixeles = new int[rgb.length];
        Color color;
        for( int n=0; n<rgb.length; n++ ) {
            int r = (int)rgb[n].getR();
            int g = (int)rgb[n].getG();
            int b = (int)rgb[n].getB();
            color = new Color( r, g, b );
            pixeles[n] = color.getRGB();
            }
        return pixeles;
    }
}
