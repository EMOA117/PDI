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
public class RgbToHsv {
    /**
     * Imagen hsi
     */
    private Hsv [][] hsv; 
    private Hsv[] imgConv;
    /**
     * Crea el objeto de conversion
     */
    public RgbToHsv() {
        hsv = null;
    }
    /**
     * Convierte una imagen RGB en su correspondiente HSI
     *
     * @param imagen la imagen a convertir
     * @param ancho el ancho de la imagen en pixeles
     * @param alto la altura de la imagen en pixeles
     */
    public void convertir(int[][] imagen, int ancho, int alto) {
        hsv = new Hsv[alto][ancho];
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
                hsv[y][x] = rgb.convertirRgbToHsv();
                }
            }
    } 
    /**
     * Convierte la imagen RGB en una imagen HSV
     *
     * @param oImagen la imagen a convertir
     *
     * @return devuelve la image convertida en un arreglo double
     */
    public Hsv [] convertir(Image oImagen) {
        ExtractorDePixel op = new ExtractorDePixel();
        int alto = oImagen.getHeight(null);
        int ancho = oImagen.getWidth(null);
        int[] pixeles = op.handlepixels(oImagen, 0, 0, ancho, alto);
        Hsv [] pixelesHsv = new Hsv[pixeles.length];
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
                    pixelesHsv[y * ancho + x] = rgb.convertirRgbToHsv();
                }
            }
        return pixelesHsv;
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
        Hsv [] ldPixeles = convertir(oImagen);
        int alto = oImagen.getHeight(null);
        int ancho = oImagen.getWidth(null);
                //pasar la matriz
        if(selector==1)
            this.imgConv = ldPixeles;
        
        ldPixeles = normalizarCanales(ldPixeles, alto, ancho);
        int[] pixeles = new int[ldPixeles.length];
        Color [] rgbq = new Color[256];
        for(int n = 0; n < rgbq.length; n++) {
            rgbq[n] = new Color(n, n, n);
            }
        for(int n = 0; n < alto; n++) {
            for(int m = 0; m < ancho; m++) {
                switch(selector) {
                    case 1:// estan al reves los S y H
                        int HH = (int)ldPixeles[n*ancho+m].getS();
                        pixeles[n * ancho + m] = rgbq[HH].getRGB();
                        break;
                    case 2:
                        int SS = (int)ldPixeles[n*ancho+m].getH();
                        pixeles[n * ancho + m] = rgbq[SS].getRGB();
                        break;
                    case 3:
                        int II = (int)ldPixeles[n*ancho+m].getV();
                        pixeles[n * ancho + m] = rgbq[II].getRGB();
                        break;
                    case 4: // Unión de los tres canales para la conversión
                        Hsv pixel = ldPixeles[n * ancho + m];
                        Rgb rgb = pixel.convertirHsvToRgb();

                        // Asegúrate de que los valores de RGB estén dentro del rango [0, 255]
                        int r = (int) Math.min(255, Math.max(0, rgb.getR()));
                        int g = (int) Math.min(255, Math.max(0, rgb.getG()));
                        int b = (int) Math.min(255, Math.max(0, rgb.getB()));

                        // Combina los componentes RGB en un solo valor entero
                        pixeles[n * ancho + m] = new Color(r, g, b).getRGB();
                        break;
                    }
                }
            }
        //pasar la matriz
        if(selector==1)
            this.imgConv = ldPixeles;
        
        JFrame frameTmp = new JFrame();
        loImage = frameTmp.createImage(
                new MemoryImageSource(ancho, alto, pixeles, 0, ancho));
        return loImage;
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
                Hsv elHsv = rgb.convertirRgbToHsv();
                switch (selector) {
                    case 1:
                        ldPixeles[y * ancho + x] = elHsv.getH();
                        break;
                    case 2:
                        ldPixeles[y * ancho + x] = elHsv.getS();
                        break;
                    case 3:
                        ldPixeles[y * ancho + x] = elHsv.getV();
                        break;
                    default:
                        break;
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
        Hsv hsv = rgb.convertirRgbToHsv();
        double canal = 0;
        switch(selector) {
            case 1: // H
                canal = hsv.getH();
                break;
            case 2: // S
                canal = hsv.getS();
                break;
            case 3: // I
                canal = hsv.getV();
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
    public Hsv [] normalizarCanales(Hsv [] img, int alto, int ancho) {
        double maxH = -500000.0;
        double minH =  500000.0;
        double maxS = -500000.0;
        double minS =  500000.0;
        double maxV = -500000.0;
        double minV =  500000.0;
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
                if(img[y * ancho + x].getV() > maxV) {
                    maxV = img[y * ancho + x].getV();
                    }
                if(img[y * ancho + x].getV() < minV) {
                    minV = img[y * ancho + x].getV();
                    }
                }
            }
        // DEBUG
        //System.out.println();
        //System.out.println( " maxH " + maxH + " minH " + minH );
        //System.out.println( " maxS " + maxS + " minS " + minS );
        //System.out.println( " maxI " + maxI + " minI " + minI );
        Hsv [] imagen = new Hsv[alto * ancho];
        for(int y = 0; y < alto; y++) {
            for(int x = 0; x < ancho; x++) {
                double H, S, V;
                H = (img[y*ancho+x].getH()-minH) * (255.0/(maxH-minH));
                S = (img[y*ancho+x].getS()-minS) * (255.0/(maxS-minS));
                V = (img[y*ancho+x].getV()-minV) * (255.0/(maxV-minV));
                // DEBUG
                //System.out.println("H: " + H + "\tS: " + S + "\tI: " + I );
                imagen[y * ancho + x] = new Hsv( H, S, V );
                }
            // DEBUG
            //System.out.println();
            }
        return imagen;
    }

    public Hsv[] getImgConv() {
        return imgConv;
    }
    
}
