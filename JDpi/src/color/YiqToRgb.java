
package color;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;
import modelo.ImageBufferedImage;

public class YiqToRgb {
    private int ancho;
    private int alto;
    
    public Image YiqToRgb(double[][] pixelesYIQ, int ancho, int alto) {
        this.alto = alto;
        this.ancho = ancho;

        // Matriz de conversión inversa de YIQ a RGB
        double[][] invConvMatriz = {
            {1,  0.956,  0.621},
            {1, -0.272, -0.647},
            {1, -1.105,  1.702}
        };

        // Multiplicación de matrices para la conversión
        double[][] res = multiplicarMatrices(invConvMatriz, pixelesYIQ);

        // Arreglo para los píxeles RGB resultantes
        int[] pixelsRGB = new int[ancho * alto];

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                // Obtener los valores de r, g, b de la matriz resultante
                double r = res[0][y * ancho + x];
                double g = res[1][y * ancho + x];
                double b = res[2][y * ancho + x];

                // Escalar de nuevo los valores a [0, 255]
                int R = (int) Math.round(Math.min(255, Math.max(0, r)));
                int G = (int) Math.round(Math.min(255, Math.max(0, g)));
                int B = (int) Math.round(Math.min(255, Math.max(0, b)));

                // Crear el píxel RGB
                pixelsRGB[y * ancho + x] = new Color(R, G, B).getRGB();
            }
        }

        // Crear la imagen a partir de los píxeles resultantes
        JFrame frameTmp = new JFrame();
        ImageBufferedImage buffer = new ImageBufferedImage();
        Image loImage = frameTmp.createImage(
            new MemoryImageSource(ancho, alto, pixelsRGB, 0, ancho));

        return loImage;
    }
    
    public double[][] multiplicarMatrices(double[][] A, double[][] B) {
        int filasA = A.length;
        int columnasA = A[0].length;
        int filasB = B.length;
        int columnasB = B[0].length;

        if (columnasA != filasB) {
            throw new IllegalArgumentException("El número de columnas de A debe ser igual al número de filas de B.");
        }

        double[][] resultado = new double[filasA][columnasB];
        for (int i = 0; i < filasA; i++) {
            for (int j = 0; j < columnasB; j++) {
                for (int k = 0; k < columnasA; k++) {
                    resultado[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return resultado;
    }
}

    