/*
 * Paquete al que pertenece la clase.
 */
package color;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;
import modelo.ExtractorDePixel;

/**
 * Clase que convierte del formato RGB a YCbCr
 * 
 * @author Saul De La O Torres
 * @version 1.0
 */
public class RgbToYCbCr {
    /**
     * Canal de luminancia del modelo Y
     */
    private double [][] Y;
    /**
     * Canal de color Cb
     */
    private double [][] Cb;
    /**
     * Canal de color Cr
     */
    private double [][] Cr;
    /**
     * Crea el objeto de conversion
     */
    public RgbToYCbCr() {
        
    }
    /**
     * Convierte una imagen RGB en su correspondiente YCbCr
     * 
     * @param imagen la imagen a convertir
     * @param ancho el ancho de la imagen en pixeles
     * @param alto la altura de la imagen en pixeles
     */
    public void convertir( int [][] imagen, int ancho, int alto ) {
        Y  = new double[alto][ancho];
	Cb = new double[alto][ancho];
	Cr = new double[alto][ancho];
        int liRed;
        int liGreen;
	int liBlue;
        double [] dTriada;
        Rgb rgb;
	for( int y=0; y<alto; y++ ) {
            for( int x=0; x<ancho; x++ ) {
		liRed   = (imagen[y][x] >> 16) & 0xff;
		liGreen = (imagen[y][x] >>  8) & 0xff;
		liBlue  = (imagen[y][x]      ) & 0xff;
                rgb = new Rgb(liRed, liGreen, liBlue);
                YCbCr ycbcr = rgb.convertirRgbAYCbCrJpeg();
		Y[y][x]  = ycbcr.getY();
		Cb[y][x] = ycbcr.getCb();
		Cr[y][x] = ycbcr.getCr();
		}
            }
    }
    /**
     * Convierte una imagen RG a YCbCr
     * 
     * @param oImagen la imagen a convertir
     * @param selector 1 = Y, 2 = Cb, 3 = Cr
     * 
     * @return devuelve la imagen convertida a un arreglo int
     */
    public double [] convertir( Image oImagen, int selector ) {
        ExtractorDePixel op = new ExtractorDePixel();
	int alto = oImagen.getHeight(null);
	int ancho = oImagen.getWidth(null);
	int [] pixeles = op.handlepixels( oImagen, 0, 0, ancho, alto );
	double [] ldPixeles = new double[pixeles.length];
        int liRed;
	int liGreen;
	int liBlue;
        double [] dTriada;
        Rgb rgb;
	for( int y=0; y<alto; y++ ) {
            for( int x=0; x<ancho; x++ ) {
                liRed   = (pixeles[y * ancho + x] >> 16) & 0xff;
		liGreen = (pixeles[y * ancho + x] >>  8) & 0xff;
		liBlue  = (pixeles[y * ancho + x]      ) & 0xff;
                rgb = new Rgb(liRed, liGreen, liBlue);
                YCbCr ycbcr = rgb.convertirRgbAYCbCrJpeg();
                if( selector==1 ) {
                    ldPixeles[y * ancho + x] = ycbcr.getY();
                    }
                else
                if( selector==2 ) { 
                    ldPixeles[y * ancho + x] = ycbcr.getCb();
                    }
                else
                if( selector==3 ) { 
                    ldPixeles[y * ancho + x] = ycbcr.getCr();
                    }
		}
            }
        return ldPixeles;
    }
    /**
     * Convierte una imagen RG a YCbCr
     * 
     * @param oImagen la imagen a convertir
     * @param selector 1 = Y, 2 = Cb, 3 = Cr
     * 
     * @return devuelve la imagen convertida a un arreglo int
     */
    public Image convertirImg( Image oImagen, int selector ) {
        Image loImage;
        double [] ldPixeles = convertir( oImagen, selector );
        int alto = oImagen.getHeight(null);
	int ancho = oImagen.getWidth(null);
        ColorFuncion funcion = new ColorFuncion();
        ldPixeles = funcion.normalizarUnCanal(ldPixeles, alto, ancho );
        int [] pixeles = new int[ldPixeles.length];
	Color [] rgbq = new Color[256];
	for( int n=0; n<rgbq.length; n++ ) {
            rgbq[n] = new Color( n, n, n );
            }
	for( int n=0; n<alto; n++ ) {
            for( int m=0; m<ancho; m++ ) {
                pixeles[n * ancho + m] = 
                    rgbq[(int)ldPixeles[n * ancho + m]].getRGB();
                }
            }
        JFrame frameTmp = new JFrame();
		loImage = frameTmp.createImage( 
            new MemoryImageSource( ancho, alto, pixeles, 0, ancho ) );
		return loImage;
    }
    /**
     * Convierte una imagen RGB en una imagen YCbCr
     * 
     * @param oImagen imagen a convertir
     * @param selector 1 = Y, 2 = Cb y 3 = Cr
     * 
     * @return devuelve la imagen convertida segun el selector
     */
    public int [] convertirInt( Image oImagen, int selector ) {
        int alto = oImagen.getHeight(null);
	 	int ancho = oImagen.getWidth(null);
        double [] ldPixeles = convertir( oImagen, selector );
        int [] liPixeles = new int[ldPixeles.length];
        for( int y=0; y<alto; y++ ) {
            for( int x=0; x<ancho; x++ ) {
                liPixeles[y * ancho + x] = (int)ldPixeles[y * ancho + x];
                if( liPixeles[y * ancho + x]<0 ) {
                    liPixeles[y * ancho + x] = 0;
                    }
                if( liPixeles[y * ancho + x]>255 ) {
                    liPixeles[y * ancho + x] = 255;
                    }
                }
            }
        return liPixeles;
    }
    /**
     * Convierte una imagen mayor a 8 bits / pixel de resolucion a ycbcr y 
     * devuelve el calculo
     * 
     * @param oImagen la imagen de origen
     * @param selector el selector, 1 = Y, 2 = Cb y 3 = Cr
     * 
     * @return devuelve la conversion en un arreglo double
     */
    public double [] convertirRgbAYCbCr32d( Image oImagen, int selector ) {
	ExtractorDePixel op = new ExtractorDePixel();
	int alto = oImagen.getHeight(null);
	int ancho = oImagen.getWidth(null);
	int [] pixeles = op.handlepixels( oImagen, 0, 0, ancho, alto );
	double [] ldPixeles = new double[pixeles.length];
	//int [] liCanales;
        int pixel;
	for( int n=0; n<alto; n++ ) {
            for( int m=0; m<ancho; m++ ) {
                pixel = pixeles[n * ancho + m];
                ldPixeles[n * ancho + m] = getCanal(pixel, selector);
		}
            }
	return ldPixeles;
    }
    /**
     * Extrae los canales RGB y los devuelve en un arreglo
     *
     * @param pixel el pixel que es convertido
     * @param selector el selector del canal, 1 = Y, 2 = I, Q = 3
     * 
     * @return devuelve uno de los tres canales y, i o q por separado 
     */
    public double getCanal( int pixel, int selector ) {
        int alpha = (pixel >> 24) & 0xff;
        int red   = (pixel >> 16) & 0xff;
        int green = (pixel >>  8) & 0xff;
        int blue  = (pixel      ) & 0xff;
        Rgb rgb = new Rgb(red, green, blue);
        YCbCr ycbcr = rgb.convertirRgbAYCbCrJpeg();
        double canal = 0;
        switch(selector) {
            case 1: // Y
                canal = ycbcr.getY();
                break;
            case 2:
                canal = ycbcr.getCb();
                break;
            case 3:
                canal = ycbcr.getCr();
                break;
            }
    	return canal;
    }
}
