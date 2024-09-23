package color;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;

/**
 * Librerias del compilador para que trabaje la clase
 */


/**
 * Clase que convierte una imagen en color en su correspondiente imagen en
 * niveles de gris (256 Niveles)
 *
 * @author Saul De La O Torres
 * @version 1.0
 */
public class RgbToGray {
    /**
     * Pixeles de la imagen en color
     */
    private int[] pixeles;
    /**
     * Pixeles de la imagen en niveles de gris
     */
    private int[] pixGris;
    /**
     * Ancho de la imagen en pixeles
     */
    private int ancho;
    /**
     * Alto de la imagen en pixeles
     */
    private int alto;

    /**
     * Crea la clase que convierte la imagen en color a niveles de gris
     *
     * @param pix los pixeles de la imagen en color
     */
    public RgbToGray(int[] pix) {
        pixeles = pix;
        pixGris = new int[pixeles.length];
    }

    /**
     * Convierte la imagen con un ancho y un alto y un punto de inicio (x, y)
     *
     * @param x coordenada en X donde se inicia la conversion
     * @param y coordenada en Y donde se inicia la conversion
     * @param w ancho de la imagen en pixeles
     * @param h altura de la imagen en pixeles
     */
    public void convertirImagen(int x, int y, int w, int h) {
        ancho = w;
        alto = h;
        //System.out.println("convertir) ancho " + w + " alto " + h);
        for(int j = 0; j < h; j++) {
            for(int i = 0; i < w; i++) {
                pixGris[j * w + i]
                        = procesarPixel(x + i, y + j, pixeles[j * w + i]);
                }
            }
        }

    /**
     * Procesa al pixel en color extrayendo sus componentes RGB (Red, Green y
     * Blue), se promedia y es el nuevo valor del nivel de gris para esa
     * tripleta
     *
     * @param x la coordenada en X del pixel que se procesa
     * @param y la coordenada en Y del pixel que se procesa
     * @param pixel el pixel a ser procesado
     *
     * @return devuelve el nivel de gris calculao del pixel
     */
    public int procesarPixel(int x, int y, int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red   = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue  = (pixel) & 0xff;

        int gris = (red + green + blue) / 3;
        if(gris < 0) {
            gris = 0;
            }
        return gris;
    }

    /**
     * Procesa la imagen en color en para convertirla en niveles de gris desde
     * alguno de sus canales
     *
     * @param x la coordenada en X del pixel que se procesa
     * @param y la coordenada en Y del pixel que se procesa
     * @param w ancho de la imagen en pixeles
     * @param h altura de la imagen en pixeles
     * @param canal el canal que sera procesado<br>
     * 1. canal rojo<br>
     * 2. canal verde<br>
     * 3. canal azul<br>
     */
    public void convertirImagen(int x, int y, int w, int h, int canal) {
        ancho = w;
        alto = h;
        for(int j = 0; j < h; j++) {
            for(int i = 0; i < w; i++) {
                pixGris[j * w + i]
                        = this.procesarPixel(x + i, y + j, 
                                pixeles[j * w + i], canal);
                }
            }
    }

    /**
     * Procesa al pixel en color extrayendo sus componentes RGB (Red, Green y
     * Blue), se devuelve el nuevo valor del nivel de gris para el canal
     *
     * @param x la coordenada en X del pixel que se procesa
     * @param y la coordenada en Y del pixel que se procesa
     * @param pixel el pixel a ser procesado
     * @param canal el canal que sera procesado<br>
     * 1. canal rojo<br>
     * 2. canal verde<br>
     * 3. canal azul<br>
     *
     * @return devuelve el pixels del canal solicitado
     */
    public int procesarPixel(int x, int y, int pixel, int canal) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;

        int gris = 0;
        switch(canal) {
            case 1:
                gris = red;
                break;
            case 2:
                gris = green;
                break;
            case 3:
                gris = blue;
                break;
            }

        return gris;
    }

    /**
     * Devuelve un objeto Image, convertido desde el bufer de la imagen.
     *
     * @return img que es un objeto Image.
     */
    public Image getImagen() {
        int[] buffImg = new int[pixGris.length];
        Color[] color = new Color[256];

        for(int n = 0; n < color.length; n++) {
            color[n] = new Color(n, n, n);
            }

        for(int n = 0; n < buffImg.length; n++) {
            buffImg[n] = color[pixGris[n]].getRGB();
            }

        JFrame frameTmp = new JFrame();
        Image img = frameTmp.createImage(new MemoryImageSource(ancho,
                alto, buffImg,
                0, ancho));
        //System.out.println("getImagen) ancho " + img.getWidth(null) + 
        //       " alto " + img.getHeight(null));
        return img;
    }

    /**
     * Devuelve un objeto Image, convertido desde el bufer de la imagen.
     *
     * @param misPixeles los pixeles que contienen la imagen
     * @return img que es un objeto Image.
     */
    public Image getImagen(int[] misPixeles) {
        int[] buffImg = new int[misPixeles.length];
        Color[] color = new Color[256];

        for(int n = 0; n < color.length; n++) {
            color[n] = new Color(n, n, n);
            }

        for(int n = 0; n < buffImg.length; n++) {
            buffImg[n] = color[misPixeles[n]].getRGB();
            }

        JFrame frameTmp = new JFrame();
        Image img = frameTmp.createImage(new MemoryImageSource(ancho,
                alto, buffImg,
                0, ancho));
        return img;
    }

    /**
     * Devuelve los pixeles en niveles de gris
     *
     * @return la imagen en niveles de gris
     */
    public int[] getPixelesEnGris() {
        return this.pixGris;
    }
}
