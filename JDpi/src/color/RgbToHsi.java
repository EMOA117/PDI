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
 * Clase que convierte del formato RGB a Hsi
 *
 * @author Saul De La O Torres
 * @version 1.0
 */
public class RgbToHsi {
    /**
     * Imagen hsi
     */
    private Hsi [][] hsi; 
    private Hsi[] imgconv;
    /**
     * Crea el objeto de conversion
     */
    public RgbToHsi() {
        hsi = null;
    }
    /**
     * Convierte una imagen RGB en su correspondiente HSI
     *
     * @param imagen la imagen a convertir
     * @param ancho el ancho de la imagen en pixeles
     * @param alto la altura de la imagen en pixeles
     */
    public void convertir(int[][] imagen, int ancho, int alto) {
        hsi = new Hsi[alto][ancho];
        int liRed;
        int liGreen;
        int liBlue;
        Rgb rgb;
        for(int y = 0; y < alto; y++) {
            for(int x = 0; x < ancho; x++) {
                liRed = (imagen[y][x] >> 16) & 0xff;
                liGreen = (imagen[y][x] >> 8) & 0xff;
                liBlue = (imagen[y][x]) & 0xff;
                rgb = new Rgb(liRed, liGreen, liBlue);
                hsi[y][x] = rgb.convertirRGBtoHSI();
                }
            }
    }

    /**
     * Convierte la imagen RGB en una imagen HSI
     *
     * @param oImagen la imagen a convertir
     *
     * @return devuelve la image convertida en un arreglo double
     */
    public Hsi [] convertir(Image oImagen) {
        ExtractorDePixel op = new ExtractorDePixel();
        int alto = oImagen.getHeight(null);
        int ancho = oImagen.getWidth(null);
        int[] pixeles = op.handlepixels(oImagen, 0, 0, ancho, alto);
        Hsi [] pixelesHsi = new Hsi[pixeles.length];
        int liRed;
        int liGreen;
        int liBlue;
        Rgb rgb;
        for(int y = 0; y < alto; y++) {
            for(int x = 0; x < ancho; x++) {
                liRed   = (pixeles[y * ancho + x] >> 16) & 0xff;
                liGreen = (pixeles[y * ancho + x] >> 8)  & 0xff;
                liBlue  = (pixeles[y * ancho + x])       & 0xff;
                rgb = new Rgb(liRed, liGreen, liBlue);
                    pixelesHsi[y * ancho + x] = rgb.convertirRGBtoHSI();
                }
            }
        return pixelesHsi;
    }

    /**
     * Convierte una imagen Rrg a Hsi
     *
     * @param oImagen la imagen a convertir
     * @param selector 1 = H, 2 = S, 3 = I
     *
     * @return devuelve la imagen convertida a un arreglo int
     */
    public Image convertirImg(Image oImagen, int selector) {
        Image loImage;
        Hsi [] ldPixeles = convertir(oImagen);
        int alto = oImagen.getHeight(null);
        int ancho = oImagen.getWidth(null);
        ldPixeles = normalizarCanales(ldPixeles, alto, ancho);
        int[] pixeles = new int[ldPixeles.length];
        Color [] rgbq = new Color[256];
        for(int n = 0; n < rgbq.length; n++) {
            rgbq[n] = new Color(n, n, n);
            }
        
        for(int n = 0; n < alto; n++) {
            for(int m = 0; m < ancho; m++) {
                switch(selector) {
                    case 1:
                        int HH = (int)ldPixeles[n*ancho+m].getS();
                        pixeles[n * ancho + m] = rgbq[HH].getRGB();
                        break;
                    case 2:
                        //estan cambiadas H por S y S por H
                        int SS = (int)ldPixeles[n*ancho+m].getH();
                        pixeles[n * ancho + m] = rgbq[SS].getRGB();
                        break;
                    case 3:
                        int II = (int)ldPixeles[n*ancho+m].getI();
                        pixeles[n * ancho + m] = rgbq[II].getRGB();
                    }
                }
            }
        
        if(selector==1)
            this.imgconv = ldPixeles;
        
        JFrame frameTmp = new JFrame();
        loImage = frameTmp.createImage(
                new MemoryImageSource(ancho, alto, pixeles, 0, ancho));
        return loImage;
        
    }

    public Hsi[] getImgconv() {
        return imgconv;
    }
    
    /**
     * Convierte la imagen RGB en una imagen HSI
     *
     * @param oImagen la imagen a convertir
     * @param selector 1 = H, 2 = S, 3 = I
     *
     * @return devuelve la image convertida en un arreglo double
     */
    public double [] convertirIntADouble(Image oImagen, int selector) {
        ExtractorDePixel op = new ExtractorDePixel();
        int alto = oImagen.getHeight(null);
        int ancho = oImagen.getWidth(null);
        int[] pixeles = op.handlepixels(oImagen, 0, 0, ancho, alto);
        double [] ldPixeles = new double[pixeles.length];
        int liRed;
        int liGreen;
        int liBlue;
        Rgb rgb = null;
        for(int y = 0; y < alto; y++) {
            for(int x = 0; x < ancho; x++) {
                liRed = (pixeles[y * ancho + x] >> 16) & 0xff;
                liGreen = (pixeles[y * ancho + x] >> 8) & 0xff;
                liBlue = (pixeles[y * ancho + x]) & 0xff;
                //System.out.print( " " + liRed + " " + liGreen + 
                // " " + liBlue );
                rgb = new Rgb(liRed, liGreen, liBlue);
                Hsi elHsi = rgb.convertirRGBtoHSI();
                if(selector == 1) {
                    ldPixeles[y * ancho + x] = elHsi.getH();
                    } 
                else 
                if (selector == 2) {
                    ldPixeles[y * ancho + x] = elHsi.getS();
                    } 
                else 
                if (selector == 3) {
                    ldPixeles[y * ancho + x] = elHsi.getI();
                    }
                }
            }
        return ldPixeles;
    }
    /**
     * Convierte una imagen RGB en una imagen HSI
     *
     * @param oImagen imagen a convertir
     * @param selector 1 = H, 2 = S y 3 = I
     *
     * @return devuelve la imagen convertida segun el selector
     */
    public int[] convertirImageToInt(Image oImagen, int selector) {
        int alto = oImagen.getHeight(null);
        int ancho = oImagen.getWidth(null);
        double [] ldPixeles = convertirIntADouble(oImagen, selector);
        /////
        ColorFuncion funcion = new ColorFuncion();
        ldPixeles = funcion.normalizarUnCanal(ldPixeles, alto, ancho);
        /////
        int[] liPixeles = new int[ldPixeles.length];
        for(int y = 0; y < alto; y++) {
            for(int x = 0; x < ancho; x++) {
                liPixeles[y * ancho + x] = (int) ldPixeles[y * ancho + x];
                }
            }
        return liPixeles;
    }
    
    /**
     * Convierte una imagen mayor a 8 bits / pixel de resolucion de RGB a HSI
     * 
     * @param oImagen la iamgen de origen
     * @param selector el selector 1 imagen Y, 2 imagen I y 3 imagen Q
     * 
     * @return devuelve la imagen RGB convertida en un objeto image Y o I o Q
     */
    public Image convertirRgbAHsi32( Image oImagen, int selector ) {
	Image loImage;
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
        ColorFuncion funcion = new ColorFuncion();
	ldPixeles = funcion.normalizarUnCanal(ldPixeles, alto, ancho );
	Color [] rgb = new Color[256];
	for( int n=0; n<rgb.length; n++ ) {
            rgb[n] = new Color( n, n, n );
            }
	for( int n=0; n<alto; n++ ) {
            for( int m=0; m<ancho; m++ ) {
                pixeles[n * ancho + m] = 
                    rgb[(int)ldPixeles[n * ancho + m]].getRGB();
                }
            }
	JFrame frameTmp = new JFrame();
        loImage = frameTmp.createImage( 
            new MemoryImageSource( ancho, alto, pixeles, 0, ancho ) );
	return loImage;
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
        Hsi hsi = rgb.convertirRGBtoHSI();
        double canal = 0;
        switch(selector) {
            case 1: // H
                canal = hsi.getH();
                break;
            case 2: // S
                canal = hsi.getS();
                break;
            case 3: // I
                canal = hsi.getI();
                break;
            }
    	return canal;
    }
    
    /**
     * Convierte una imagen mayor a 8 bits / pixel de resolucion a hsi y 
     * devuelve el calculo
     * 
     * @param oImagen la imagen de origen
     * @param selector el selector, 1 = Y, 2 = I y 3 = Q
     * 
     * @return devuelve la conversion en un arreglo double
     */
    public double [] convertirRgbAHsi32d( Image oImagen, int selector ) {
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
     * Normaliza a I y Q para desplegarlos en el rango de 256 niveles de gris.
     *
     * @param img la imagen que se normaliza
     * @param alto la altura de la imagen en pixeles
     * @param ancho la anchura de la imagen en pixeles
     *
     * @return devuelve la imagen normalizada
     */
    public Hsi [] normalizarCanales(Hsi [] img, int alto, int ancho) {
        double maxH = -500000.0;
        double minH =  500000.0;
        double maxS = -500000.0;
        double minS =  500000.0;
        double maxI = -500000.0;
        double minI =  500000.0;
        for(int y = 0; y < alto; y++) {
            for(int x = 0; x < ancho; x++) {
                // Huella
                if(img[y * ancho + x].getH() > maxH) {
                    maxH = img[y * ancho + x].getH();
                    }
                if(img[y * ancho + x].getH() < minH) {
                    minH = img[y * ancho + x].getH();
                    }
                // Saturacion
                if(img[y * ancho + x].getS() > maxS) {
                    maxS = img[y * ancho + x].getS();
                    }
                if(img[y * ancho + x].getS() < minS) {
                    minS = img[y * ancho + x].getS();
                    }
                // Luminancia
                if(img[y * ancho + x].getI() > maxI) {
                    maxI = img[y * ancho + x].getI();
                    }
                if(img[y * ancho + x].getI() < minI) {
                    minI = img[y * ancho + x].getI();
                    }
                }
            }
        // DEBUG
        //System.out.println();
        //System.out.println( " maxH " + maxH + " minH " + minH );
        //System.out.println( " maxS " + maxS + " minS " + minS );
        //System.out.println( " maxI " + maxI + " minI " + minI );
        Hsi [] imagen = new Hsi[alto * ancho];
        for(int y = 0; y < alto; y++) {
            for(int x = 0; x < ancho; x++) {
                double H, S, I;
                H = (img[y*ancho+x].getH()-minH) * (255.0/(maxH-minH));
                S = (img[y*ancho+x].getS()-minS) * (255.0/(maxS-minS));
                I = (img[y*ancho+x].getI()-minI) * (255.0/(maxI-minI));
                // DEBUG
                //System.out.println("H: " + H + "\tS: " + S + "\tI: " + I );
                imagen[y * ancho + x] = new Hsi( H, S, I );
                }
            // DEBUG
            //System.out.println();
            }
        return imagen;
    }
}
