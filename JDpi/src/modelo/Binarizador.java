
package modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.MemoryImageSource;

public class Binarizador {
    
     public Image binarizarUnUmbral(Image image, int umbral, boolean inverse) {
        int alto = image.getHeight(null);
        int ancho = image.getWidth(null);
        ExtractorDePixel op = new ExtractorDePixel();
        int[][] imagenInt = op.obtenerPixelesEn2D(image, 0, 0, ancho, alto);

        for(int y=0; y<alto; y++) {
            for(int x=0; x<ancho; x++) {
                int pixel = imagenInt[y][x];
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >>  8;
                int azul  =  pixel & 0x000000ff;
                int gris = (rojo + verde + azul) / 3;
                if (gris > umbral) {
                    imagenInt[y][x] = (inverse) ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
                } else {
                    imagenInt[y][x] = (inverse) ? Color.WHITE.getRGB() : Color.BLACK.getRGB();
                }
            }
        }
        JFrame padre = new JFrame();
        ImageBufferedImage bufferedImage = new ImageBufferedImage();

        return padre.createImage(new MemoryImageSource(ancho,
                alto, bufferedImage.convertirInt2DAInt1D(imagenInt, ancho, alto),
                0, ancho));
    }

    public Image binarizarDosUmbrales(Image image, int umbral1, int umbral2, boolean inverse) {
        int alto = image.getHeight(null);
        int ancho = image.getWidth(null);
        ExtractorDePixel op = new ExtractorDePixel();
        int[][] imagenInt = op.obtenerPixelesEn2D(image, 0, 0, ancho, alto);

        for(int y=0; y<alto; y++) {
            for(int x=0; x<ancho; x++) {
                int pixel = imagenInt[y][x];
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >>  8;
                int azul  =  pixel & 0x000000ff;
                int gris = (rojo + verde + azul) / 3;
                if (gris < umbral1) {
                    imagenInt[y][x] = (inverse) ? Color.WHITE.getRGB() : Color.BLACK.getRGB();
                } else if (gris > umbral2) {
                    imagenInt[y][x] = (inverse) ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
                } else {
                    imagenInt[y][x] = Color.GRAY.getRGB();
                }
            }
        }
        JFrame padre = new JFrame();
        ImageBufferedImage bufferedImage = new ImageBufferedImage();

        return padre.createImage(new MemoryImageSource(ancho,
                alto, bufferedImage.convertirInt2DAInt1D(imagenInt, ancho, alto),
                0, ancho));
    }

    public Image binarizarTresUmbrales(Image image, int umbral1, int umbral2, int umbral3,boolean inverse) {
        int alto = image.getHeight(null);
        int ancho = image.getWidth(null);
        ExtractorDePixel op = new ExtractorDePixel();
        int[][] imagenInt = op.obtenerPixelesEn2D(image, 0, 0, ancho, alto);

        for(int y=0; y<alto; y++) {
            for(int x=0; x<ancho; x++) {
                int pixel = imagenInt[y][x];
                int rojo  = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >>  8;
                int azul  =  pixel & 0x000000ff;
                int gris = (rojo + verde + azul) / 3;

                if (gris < umbral1) {
                    imagenInt[y][x] = (inverse) ? Color.WHITE.getRGB() : Color.BLACK.getRGB();
                } else if (gris < umbral2) {
                    imagenInt[y][x] = (inverse) ? Color.LIGHT_GRAY.getRGB() : Color.DARK_GRAY.getRGB();
                } else if (gris < umbral3) {
                    imagenInt[y][x] = (inverse) ? Color.DARK_GRAY.getRGB() : Color.LIGHT_GRAY.getRGB();
                } else {
                    imagenInt[y][x] = (inverse) ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
                }
            }
        }
        JFrame padre = new JFrame();
        ImageBufferedImage bufferedImage = new ImageBufferedImage();

        return padre.createImage(new MemoryImageSource(ancho,
                alto, bufferedImage.convertirInt2DAInt1D(imagenInt, ancho, alto),
                0, ancho));
    }   
}
