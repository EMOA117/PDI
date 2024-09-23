package color;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;
import modelo.ImageBufferedImage;

public class HsvToRgb {

    public Image convertirHsiToRgb(Hsv[] pixelesHSV, int ancho, int alto) {

        // Arreglo para los píxeles RGB resultantes
        int[] pixelsRGB = new int[ancho * alto];

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {

                // Obtener los valores de H, S, V del píxel actual
                double H_scaled = pixelesHSV[y * ancho + x].getH(); 
                double S_scaled = pixelesHSV[y * ancho + x].getS(); 
                double V_scaled = pixelesHSV[y * ancho + x].getV();
                
                // Convertir los valores a rangos adecuados
                double H = (H_scaled * 360.0)/255.0; //se convierte a grados
                double S = S_scaled/255.0;    //se convierte de valores de [0,1]
                double V = V_scaled/255.0;      //intervalo de [0,1]
                
                // Asegurarse de que H está en el rango [0, 360)
                H = H % 360.0;
                if (H < 0) {
                    H += 360.0;
                }
                
                // Calcular M, m y z
                double M = 255 * V;
                double m = M * (1 - S);
                double z = (M - m) * (1 - Math.abs(((H / 60) % 2) - 1));

                // Inicialización de r, g, b
                double r = 0, g = 0, b = 0;

                // Casos según el valor de H
                if (H >= 0 && H < 60) {
                    r = M;
                    g = z + m;
                    b = m;
                } else if (H >= 60 && H < 120) {
                    r = z + m;
                    g = M;
                    b = m;
                } else if (H >= 120 && H < 180) {
                    r = m;
                    g = M;
                    b = z + m;
                } else if (H >= 180 && H < 240) {
                    r = m;
                    g = z + m;
                    b = M;
                } else if (H >= 240 && H < 300) {
                    r = z + m;
                    g = m;
                    b = M;
                } else {
                    r = M;
                    g = m;
                    b = z + m;
                }

                // Escalar los valores de RGB al rango [0, 255]
                int R = (int) Math.round(Math.min(255, Math.max(0, r)));
                int G = (int) Math.round(Math.min(255, Math.max(0, g)));
                int B = (int) Math.round(Math.min(255, Math.max(0, b)));

                // Asignar el valor RGB calculado al píxel actual
                pixelsRGB[y * ancho + x] = new Color(R, G, B).getRGB();
            }
        }

        // Crear la imagen a partir de los píxeles RGB
        JFrame frameTmp = new JFrame();
        ImageBufferedImage buffer = new ImageBufferedImage();
        Image loImage = frameTmp.createImage(
                new MemoryImageSource(ancho, alto, pixelsRGB, 0, ancho));

        return loImage;
    }
}
