package color;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;
import modelo.ImageBufferedImage;

public class HsiToRgb {

    public Image convertirHsiToRgb(Hsi[] pixelesHSI, int ancho, int alto) {

        // Arreglo para los píxeles RGB resultantes
        int[] pixelsRGB = new int[ancho * alto];

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {

                // Obtener los valores de H, S, I del píxel actual en rangos de 0 a 255
                double H_scaled = pixelesHSI[y * ancho + x].getH(); 
                double S_scaled = pixelesHSI[y * ancho + x].getS(); 
                double I_scaled = pixelesHSI[y * ancho + x].getI();

                // Normalizar los valores
                double H = (H_scaled * 360.0) / 255.0; // Convertir a grados [0, 360]
                double S = S_scaled / 255.0;           // Normalizar a [0, 1]
                double I = I_scaled / 255.0;           // Normalizar a [0, 1]

                //Asegurar que esta en grados
                H = H % 360.0;
                if (H < 0) {
                    H += 360.0;
                }

                // Convertir H a radianes para funciones trigonométricas
                double H_rad = Math.toRadians(H);

                double r = 0, g = 0, b = 0;

                if (H >= 0 && H < 120) {
                   
                    b = I * (1 - S);
                    double cos_H = Math.cos(H_rad);
                    double cos_60_H = Math.cos(Math.toRadians(60) - H_rad);
                    if (cos_60_H == 0) {
                        cos_60_H = 1e-10; // Evitar división por cero
                    }
                    r = I * (1 + (S * cos_H) / cos_60_H);
                    g = 3 * I - (r + b);
                } else if (H >= 120 && H < 240) { //caso 2 de grados
                    
                    double H_new = H - 120.0;
                    double H_new_rad = Math.toRadians(H_new);
                    double cos_H_new = Math.cos(H_new_rad);
                    double cos_60_H_new = Math.cos(Math.toRadians(60) - H_new_rad);
                    if (cos_60_H_new == 0) {
                        cos_60_H_new = 1e-10; // Evitar división por cero
                    }
                    r = I * (1 - S);
                    g = I * (1 + (S * cos_H_new) / cos_60_H_new);
                    b = 3 * I - (r + g);
                } else { // caso 3
                    
                    double H_new = H - 240.0;
                    double H_new_rad = Math.toRadians(H_new);
                    double cos_H_new = Math.cos(H_new_rad);
                    double cos_60_H_new = Math.cos(Math.toRadians(60) - H_new_rad);
                    if (cos_60_H_new == 0) {
                        cos_60_H_new = 1e-10; // Evitar división por cero
                    }
                    g = I * (1 - S);
                    b = I * (1 + (S * cos_H_new) / cos_60_H_new);
                    r = 3 * I - (g + b);
                }

                // Escalar los valores de RGB al rango [0, 255] para la construccion de la imagen
                int R = (int) Math.round(Math.min(255, Math.max(0, r * 255)));
                int G = (int) Math.round(Math.min(255, Math.max(0, g * 255)));
                int B = (int) Math.round(Math.min(255, Math.max(0, b * 255)));

                pixelsRGB[y * ancho + x] = new Color(R, G, B).getRGB();
            }
        }

        JFrame frameTmp = new JFrame();
        Image loImage = frameTmp.createImage(
                new MemoryImageSource(ancho, alto, pixelsRGB, 0, ancho));

        return loImage;
    }
}
