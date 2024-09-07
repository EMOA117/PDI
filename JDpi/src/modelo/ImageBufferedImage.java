package modelo;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;

/**
 * Clase para convertir entre objetos:<br>
 * BufferedImage a Image<br>
 * y al contrario:<br>
 * Image a bufferedImage<br>
 * 
 * @author sdelaot
 */
public class ImageBufferedImage {
    private Image imagen;
    private BufferedImage bufferedImagen;
    private int [][] matrizImagen;
    /**
     * Convierte un objeto BufferedImage a un objeto Image.
     * 
     * @param input La imagen que se transforma<br>
     * @param queCanal el canal a tomar
     * 
     * @return Devuelve ob objeto de tipo image
     */
    public Image getImage(BufferedImage input, int queCanal, int escalar) {
        int alto = input.getHeight();
        int ancho = input.getWidth();
        int pixel;
        
        int [][] imagenInt = new int[alto][ancho];
        for(int y=0; y<alto; y++) {
            for(int x=0; x<ancho; x++) {
                pixel = input.getRGB(x,y);
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul  =  pixel & 0x000000ff;
                int elPixel;
                Color color = null;
                
                switch(queCanal) {
                    case 1: // red
                        elPixel = rojo;
                        if( rojo==255 && verde==0 && azul==0 ) {       
                            color = new Color(rojo, verde, azul);
                            }
                        else {
                            color = new Color(elPixel, 0, 0);
                            }
                        break;
                    case 2: // green
                        elPixel = verde;
                        if( rojo==0 && verde==255 && azul==0 ) {       
                            color = new Color(rojo, verde, azul);
                            }
                        else {
                            color = new Color(0, elPixel, 0);
                            }
                        break;
                    case 3: // blue
                        elPixel = azul;
                        if( rojo==0 && verde==0 && azul==255 ) {       
                            color = new Color(rojo, verde, azul);
                            }
                        else {
                            color = new Color(0, 0, elPixel);
                            }
                        break;
                    case 4: // todos
                        color = new Color(rojo, verde, azul);
                        //System.out.println(queCanal + " " + color);
                        break;
                    case 5:
                        int gris = (rojo + verde + azul) / 3;
                        double escalarDouble = 0.5;
                        gris *= escalarDouble;
                        if(gris>255) {
                            gris = 255;
                            }
                        if(gris<0) {
                            gris = 0;
                            }
                        color = new Color(gris, gris, gris);
                        break;
                        case 6: // redgray
                        elPixel = rojo;
                        if( rojo==255 && verde==0 && azul==0 ) {       
                            color = new Color(rojo, verde, azul);
                            }
                        else {
                            color = new Color(elPixel, elPixel, elPixel);
                            }
                        break;
                        case 7: // greengray
                        elPixel = verde;
                        if( rojo==0 && verde==255 && azul==0 ) {       
                            color = new Color(rojo, verde, azul);
                            }
                        else {
                            color = new Color(elPixel, elPixel, elPixel);
                            }
                        break;
                    case 8: // bluegray
                        elPixel = azul;
                        if( rojo==0 && verde==0 && azul==255 ) {       
                            color = new Color(rojo, verde, azul);
                            }
                        else {
                            color = new Color(elPixel, elPixel, elPixel);
                            }
                        break;
                        case 9: //brillo
                            //int rojoAjustado = (int) (rojo +escalar);
                            //int verdeAjustado = (int) (verde + escalar);
                            //int azulAjustado = (int) (azul +escalar);

                            // Limitar los valores entre 0 y 255
                            //rojoAjustado = Math.min(255, Math.max(0, rojoAjustado));
                            //verdeAjustado = Math.min(255, Math.max(0, verdeAjustado));
                            //azulAjustado = Math.min(255, Math.max(0, azulAjustado));

                            //color = new Color(rojoAjustado, verdeAjustado, azulAjustado);
                            int grisBrillo = (rojo + verde + azul) / 3;
                            double escalarDoubleBrillo = escalar;
                            grisBrillo += escalarDoubleBrillo;
                            if(grisBrillo>255) {
                            grisBrillo = 255;
                            }
                            if(grisBrillo<0) {
                            grisBrillo = 0;
                            }
                            color = new Color(grisBrillo, grisBrillo, grisBrillo);
                            
                            break;
                           case 10: // todosconcontraste
                           //double factorContraste = escalar/100.0; 
                            //int rojoContraste = (int) (((rojo - 128) * factorContraste) + 128);
                            //int verdeContraste = (int) (((verde - 128) * factorContraste) + 128);
                            //int azulContraste = (int) (int) (((azul - 128) * factorContraste) + 128);

                            // Limitar los valores entre 0 y 255
                            //rojoAjustado = Math.min(255, Math.max(0, rojoContraste));
                            //verdeAjustado = Math.min(255, Math.max(0, verdeContraste));
                            //azulAjustado = Math.min(255, Math.max(0, azulContraste));

                            //color = new Color(rojoAjustado, verdeAjustado, azulAjustado);
                           int grisContraste = (rojo + verde + azul) / 3;
                            double escalarDoubleContraste = escalar/100.0;
                            grisContraste *= escalarDoubleContraste;
                            if(grisContraste>255) {
                            grisContraste = 255;
                            }
                            if(grisContraste<0) {
                            grisContraste = 0;
                            }
                            color = new Color(grisContraste, grisContraste, grisContraste);
                            break;

                    }
                imagenInt[y][x] = color.getRGB();
                }
            }
        JFrame padre = new JFrame();
        imagen = padre.createImage(new MemoryImageSource(ancho,
                alto, convertirInt2DAInt1D(imagenInt, ancho, alto),
                0, ancho));
        
        return imagen;
    }
    /**
     * Convierte un buffer de tipo double y de dos dimensiones de imagen a uno
     * de una dimension de tipo entero.
     * 
     * @param matriz la matriz a convertir
     * @param ancho ancho de la imagen en pixeles
     * @param alto alto de la imagen en pixeles
     * 
     * @return el vector de la image convertida
     */
    public int [] convertirInt2DAInt1D(int[][] matriz, int ancho, int alto) {
        int index = 0;
        int [] bufferInt = null;
        try {
            bufferInt = new int[ancho * alto];
            for(int y = 0; y < alto; y++) {
                for(int x=0; x < ancho; x++) {
                    bufferInt[index++] = matriz[y][x];
                    }
                }
        } catch (NegativeArraySizeException e) {
            System.out.println(" Error alto, ancho o ambos negativos"
                + " en  convierteInt2DAInt1D( double [][] ) "
                + e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(" Error desbordamiento en bufferInt"
                + " en  convierteInt2DAInt1D( double [][] ) "
                + e);
        } catch (NullPointerException e) {
            System.out.println(" Error bufferInt nulo"
                + " en  convierteInt2DAInt1D( double [][] ) "
                + e);
        }
        return bufferInt;
    }
    /**
     * Convierte un objeto Image a un objeto BufferedImage nivel de gris.
     * 
     * @param input La imagen que se transforma<br>
     * 
     * @return Devuelve ob objeto de tipo image
     */
    public BufferedImage getBufferedImage(Image input) {
        int alto = input.getHeight(null);
        int ancho = input.getWidth(null);
        bufferedImagen = 
                new BufferedImage(ancho, alto, BufferedImage.TYPE_BYTE_GRAY);
        ExtractorDePixel op = new ExtractorDePixel();
        matrizImagen = op.obtenerPixelesEn2D(input, 0, 0, ancho, alto);
        for(int y=0; y<alto; y++) {
            for(int x=0; x<ancho; x++) {
                int pixel = matrizImagen[y][x];
                int azul  =  pixel & 0x000000ff;
                Color color = new Color(azul, azul, azul);
                pixel = color.getRGB();
                bufferedImagen.setRGB(x, y, pixel);
                }
            }
        
        return bufferedImagen;
    }
    /**
     * Convierte un objeto Image a un objeto BufferedImage en color.
     * 
     * @param input La imagen que se transforma<br>
     * 
     * @return Devuelve ob objeto de tipo image
     */
    public BufferedImage getBufferedImageColor(Image input) {
        int alto = input.getHeight(null);
        int ancho = input.getWidth(null);
        bufferedImagen = 
                new BufferedImage(ancho, alto, BufferedImage.TYPE_3BYTE_BGR);
        ExtractorDePixel op = new ExtractorDePixel();
        int [][] imagenInt = op.obtenerPixelesEn2D(input, 0, 0, ancho, alto);
        for(int y=0; y<alto; y++) {
            for(int x=0; x<ancho; x++) {
                int pixel = imagenInt[y][x];
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >>  8;
                int azul  =  pixel & 0x000000ff;
                Color color = new Color(rojo, verde, azul);
                pixel = color.getRGB();
                bufferedImagen.setRGB(x, y, pixel);
                }
            }
        return bufferedImagen;
    }

    public Image getImagen() {
        return imagen;
    }

    public BufferedImage getBufferedImagen() {
        return bufferedImagen;
    }

    public int[][] getMatrizImagen() {
        return matrizImagen;
    }
    
}
