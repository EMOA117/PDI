/**
 * Paquete al que pertenece la clase.
 */
package color;

/**
 * Librerias para el funcionamiento correcto de esta clase.
 */
import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;
import modelo.ExtractorDePixel;

/**
 * Clase para conversion entre formatos RGB == YIQ.
 * 
 * Ultima modificacion:
 * 30/may/2004
 * 29/oct/2004
 * 
 * @author Saul De La O Torres
 * @version 1.0 14 de Julio de 2003
 */
public class RgbToYiq implements IBmpConstante, IRgbYiqConstante {
    /**
     * Intancia para realizar conversiones con la paleta de colores.
     */
    private Rgb [] rgb;
    /**
     * Intancia para realizar conversiones con la paleta de colores.
     */
    private Yiq [] yiq;
    
    private double[][] ConjuntoYIQ;
    /**
     * Variable de comparacion.
     */
    private static final double MEDIO = 0.5;
    /**
     * Inicializa el objeto rgbAYiq a null
     */
    public RgbToYiq( ) {
        //this( null, null, null );
        rgb = null;
        yiq = null;
        ConjuntoYIQ = null;
    }
    /**
     * El Constructor recibe los buffers leidos de la paleta de color en 
     * formato RGB, inicializa los Buffers del formato YIQ y manda a llamar al 
     * metodo convertirRgbAYiq().
     * 
     * @param r el conjunto de canal rojo
     * @param g el conjunto de canal verde
     * @param b el conjunto de canal azul
     */
    public RgbToYiq( int [] r, int [] g, int [] b ) {
	rgb = new Rgb[COLORES];
	yiq = new Yiq[NGRIS];
        int n=0;
	try {
            for( ; n<COLORES; n++ ) {
		rgb[n] = new Rgb(r[n], g[n], b[n]);
		yiq[n] = new Yiq();
		}
        } catch( NullPointerException e ) {
            System.out.println( 
                "RgbToYiq.RgbToYiq( r[], g[], b[] ) : " + 
                        n );
            System.out.println( " Exepcion al crear el objeto rgbAYiq " + e );
	} catch( ArrayIndexOutOfBoundsException e ) {
            System.out.println( 
                "RgbToYiq.RgbToYiq( r[], g[], b[] )" );
            System.out.println( " Exepcion al crear el objeto rgbAYiq " + e );
	}
	convertirRgbAYiq();
    }
    /**
     * El Constructor recibe los buffers leidos de la paleta de color en 
     * formato RGB, inicializa los Buffers del formato YIQ y manda a llamar al 
     * metodo convierteYIQ().
     * 
     * @param pal el conjunto de cuarteta de la paleta de colores
     */
    public RgbToYiq( RgbQuad [] pal ) {
        rgb = new Rgb[COLORES];
	yiq = new Yiq[NGRIS];
	try {
            for( int n=0; n<NGRIS; n++ ) {
		rgb[n] = new Rgb(pal[n].getR(), pal[n].getG(), pal[n].getB());
		yiq[n] = new Yiq(); 
		}
	} catch( NullPointerException e ) {
            System.out.println( "RgbToYiq.RgbToYiq( RGBQ )" );
            System.out.println( " Exepcion al crear el objeto rgbAYiq " + e );
	} catch( ArrayIndexOutOfBoundsException e ) {
            System.out.println( "RgbToYiq.RgbToYiq( RGBQ )" );
            System.out.println( " Exepcion al crear el objeto rgbAYiq " + e );
	}
	convertirRgbAYiq();
    }
    /**
     * Con los datos de la paleta de colores en formato RGB listos, se realiza 
     * la conversion de RGB a YIQ.
     */
    private void convertirRgbAYiq( ) {
	try {
            for( int i=0; i<COLORES; i++ ) {
                yiq[i] = rgb[i].convertirRgbAYiq();
		}
	} catch( NullPointerException e ) {
            System.out.println( "RgbToYiq.convierteRgbAYiq()" );
            System.out.println( 
                    " Exepcion al crear la transformacion RGB a YIQ " + e );
        }
    }
    /**
     * Convierte una imagen mayor a 8 bits / pixel de resolucion de rgb a yiq
     * 
     * @param oImagen la iamgen de origen
     * @param selector el selector 1 imagen Y, 2 imagen I y 3 imagen Q
     * 
     * @return devuelve la imagen RGB convertida en un objeto image Y o I o Q
     */
    public Image convertirRgbAYiq32( Image oImagen, int selector ) {
	Image loImage;
	ExtractorDePixel op = new ExtractorDePixel();
	int alto = oImagen.getHeight(null);
	int ancho = oImagen.getWidth(null);
	int [] pixeles = op.handlepixels( oImagen, 0, 0, ancho, alto );
	double [] ldPixeles = new double[pixeles.length];
	//int [] liCanales;
        int pixel;
        //se crea el conjunto una sola vez para almacenar la matriz YIQ
        if(selector==1)
            this.ConjuntoYIQ = new double[3][alto * ancho];
        
        //para juntar los 3 canales
        double [] pixelesenY = new double[ancho*alto] ;
        double [] pixelesenI = new double[ancho*alto] ;
        double [] pixelesenQ = new double[ancho*alto] ;        
        
	for( int n=0; n<alto; n++ ) {
            for( int m=0; m<ancho; m++ ) {
                pixel = pixeles[n * ancho + m];
                ldPixeles[n * ancho + m] = getCanal(pixel, selector);
                //union de los 3 canales, convertido a vectores
                if (selector==4) {
                    pixelesenY[n*ancho+m] = getCanal(pixel, 1);
                    pixelesenI[n*ancho+m] = getCanal(pixel, 2);
                    pixelesenQ[n*ancho+m] = getCanal(pixel, 3);
                }
                
                
		}
            }
        //antes de normalizar el canal
        for (int x = 0; x < ldPixeles.length; x++) {
            if(selector<4)
                ConjuntoYIQ[selector - 1][x] = ldPixeles[x];
        }
        
        ColorFuncion funcion = new ColorFuncion();
	ldPixeles = funcion.normalizarUnCanal(ldPixeles, alto, ancho );
        
        //normalizar los canales si se unen los 3
        if (selector==4) {
            pixelesenY = funcion.normalizarUnCanal(pixelesenY, alto, ancho);
            pixelesenI = funcion.normalizarUnCanal(pixelesenI, alto, ancho);
            pixelesenQ = funcion.normalizarUnCanal(pixelesenQ, alto, ancho);
        }
        
	Color [] rgb = new Color[256];
	for( int n=0; n<rgb.length; n++ ) {
            rgb[n] = new Color( n, n, n );
            }
        //una vez normalizados los canales se ponen en el arreglo:
	for( int n=0; n<alto; n++ ) {
            for( int m=0; m<ancho; m++ ) {
                if (selector!=4) {
                pixeles[n * ancho + m] = 
                    rgb[(int)ldPixeles[n * ancho + m]].getRGB();
                }else{//la union de los 3 canales
                Color rgbunion;
                rgbunion = new Color((int)pixelesenY[n*ancho+m],(int)pixelesenI[n*ancho+m],(int)pixelesenQ[n*ancho+m]);
                
                pixeles[n * ancho + m] = rgbunion.getRGB();
                    }
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
     * 
     * @return devuelve los canales r g b por separado 
     */
    public int [] getCanales( int pixel ) {
        int alpha = (pixel >> 24) & 0xff;
        int red   = (pixel >> 16) & 0xff;
        int green = (pixel >>  8) & 0xff;
        int blue  = (pixel      ) & 0xff;
        int [] canales = {
        	red,
         	green,
    	 	blue
    		};
    	return canales;
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
        Yiq yiq = rgb.convertirRgbAYiq();
        double canal = 0;
        switch(selector) {
            case 1: // Y
                canal = yiq.getY();
                break;
            case 2:
                canal = yiq.getI();
                break;
            case 3:
                canal = yiq.getQ();
                break;
            }
    	return canal;
    }
    /**
     * Convierte una imagen mayor a 8 bits / pixel de resolucion a yiq y 
     * devuelve el calculo
     * 
     * @param oImagen la imagen de origen
     * @param selector el selector, 1 = Y, 2 = I y 3 = Q
     * 
     * @return devuelve la conversion en un arreglo double
     */
    public double [] convertirRgbAYiq32d( Image oImagen, int selector ) {
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
     * Convierte una imagen mayor a 8 bits / pixel de resolucion a yiq y 
     * devuelve el calculo
     * 
     * @param oImagen la imagen de origen
     * @param selector el selector, 1 = Y, 2 = I y 3 = Q
     * 
     * @return devuelve la conversion en un arreglo int
     */
    public int [] convertirRgbAYiq32i( Image oImagen, int selector ) {
        int alto = oImagen.getHeight(null);
	int ancho = oImagen.getWidth(null);
	double [] ldPixeles = convertirRgbAYiq32d( oImagen, selector );
        /////
        ColorFuncion funcion = new ColorFuncion();
        ldPixeles = funcion.normalizarUnCanal(ldPixeles, alto, ancho);
        /////
        int [] liPixeles = new int[ldPixeles.length];
	for( int n=0; n<alto; n++ ) {
            for( int m=0; m<ancho; m++ ) {
	 	liPixeles[n * ancho + m] = (int)ldPixeles[n * ancho + m];
		}
            }	 
	return liPixeles;
    }
    /**
     * Convierte la matriz de imagen bidimensional en formato RGB a YIQ, recibe 
     * la matriz de imagen y devuelve la matriz de Luminancia Y.
     * 
     * @param matriz la matriz de imagen en 2D que es convertida a Y 2D
     * 
     * @return devuelve la matriz Y en dos dimensiones
     */
    public double [][] convertirYD( int[][] matriz ) {
	int an = matriz.length;
	int al = matriz[0].length;
	double [][] imgY = new double[al][an];
	for( int y=0; y<al; y++ ) {
            imgY[y] = new double[an];
            for( int x=0; x<an; x++ ) {
		imgY[y][x] = yiq[matriz[y][x]].getY();
		}
            }
	return imgY;
    }
    /**
     * Convierte la matriz de imagen bidimensional en formato RGB a YIQ, recibe 
     * la matriz de imagen y devuelve la matriz seleccionada.
     * 
     * @param matriz la matriz a convertir
     * @param alto la altura de la imagen en pixeles
     * @param ancho la anchura de la imagen en pixeles
     * @param sel el selector 1=Y, 2=I, 3=Q
     * 
     * @return devuelve Y o I o Q segun el selector, en una matriz entera de 
     * dos dimensiones
     */
    public int [][] convertirYIQ( int [][] matriz, int alto, int ancho,
		int sel ) {
	int [][] imgYInt = null;
	try {
            double [][] imgY = new double[alto][ancho];
            imgYInt = new int[alto][ancho];
            for( int y=0; y<alto; y++ ) {
		if( imgY[y]==null ) {
                    imgY[y] = new double[ancho];
                    }
		for( int x=0; x<ancho; x++ ) {
                    if( sel==1 ) {
                        imgY[y][x] = yiq[matriz[y][x]].getY();
                        }
                    else
                    if( sel==2 ) {
                        imgY[y][x] = yiq[matriz[y][x]].getI();
                        }
                    else
                    if( sel==3 ) {
                        imgY[y][x] = yiq[matriz[y][x]].getQ();
                        }
                    }
		}
            if( sel>1 ) {
                ColorFuncion funcion = new ColorFuncion();
                imgY = funcion.normalizar( imgY, alto, ancho );
                }
            for( int y=0; y<alto; y++ ) {
		if( imgYInt[y]==null ) {
                    imgYInt[y] = new int[ancho];
                    }
		for( int x=0; x<ancho; x++ ) {
                    int temp =  (int)imgY[y][x];
                    if( imgY[y][x]-(double)temp>MEDIO ) {
                        imgYInt[y][x] = (int) Math.ceil( imgY[y][x] );
                        }
                    else {
                        imgYInt[y][x] = (int) Math.floor( imgY[y][x] );
                        }
                    }
		}
	} catch( Exception e ) {
            System.out.println( "RgbToYiq.convierteYI()" );
            System.out.println( " Error de transformacion de RGB a YIQ " + e );
		}
        return imgYInt;
    }
    /**
     * Devuelve la paleta de Luminancia Y.
     * 
     * @return devuelve la paleta Y en una arreglo double de una dimension
     */
    public double [] getY( ) {
	double [] y = new double[NGRIS];
	try {
            for( int n=0; n<NGRIS; n++ ) {
                y[n] = yiq[n].getY();
                }
	} catch( NullPointerException e ) {
            System.out.println( " Error en RgbToYiq.getY() "  + e );
	}
	return y;
    }
    /**
     * Devuelve la paleta de Crominancia I.
     * 
     * @return devuelve la paleta I en una arreglo double de una dimension
     */
    public double [] getI( ) {
	double [] i = new double[NGRIS];
	try {
            for( int n=0; n<NGRIS; n++ ) {
                i[n] = yiq[n].getI();
                }
	} catch( NullPointerException e ) {
            System.out.println( " Error en RgbToYiq.getI() "  + e );
	}
	return i;
    }
    /**
     * Devuelve la paleta de Crominancia Q.
     * 
     * @return devuelve la paleta Q en una arreglo double de una dimension
     */
    public double [] getQ( ) {
	double [] q = new double[NGRIS];
	try {
            for( int n=0; n<NGRIS; n++ ) {
                q[n] = yiq[n].getQ();
                }
	} catch( NullPointerException e ) {
            System.out.println( " Error en RgbToYiq.getQ() "  + e );
	}
        return q;
    }

    public double[][] getConjuntoYIQ() {
        return ConjuntoYIQ;
    }
    
}