
package color;

import javax.swing.*;
import java.awt.*;
import java.awt.image.MemoryImageSource;
import modelo.ExtractorDePixel;
import modelo.ImageBufferedImage;

    public class CmyToCmyk {
        
        public Image CMYtoCMYk(Image image, int canal) {
            int alto = image.getHeight(null);
            int ancho = image.getWidth(null);
            ExtractorDePixel op = new ExtractorDePixel();
            int[][] imagenInt = op.obtenerPixelesEn2D(image, 0, 0, ancho, alto);
            for(int y=0; y<alto; y++) {
                for(int x=0; x<ancho; x++) {
                    //colores de cmy normales 
                    int pixel = imagenInt[y][x];
                    int cian = (pixel & 0x00ff0000) >> 16;
                    int magenta = (pixel & 0x0000ff00) >> 8;
                    int amarillo = pixel & 0x000000ff;
                    int negro = Math.min(cian, Math.min(magenta, amarillo));

                    // colores de cmyk
                    float new_cian = (float) (cian - negro) / (255 - negro);
                    float new_magenta = (float) (magenta - negro) / (255 - negro);
                    float new_amarillo = (float) (amarillo - negro) / (255 - negro);
                    float new_negro = negro / 255f;

                    Color color = null;
                    switch(canal) {
                        case 1: // Canal Cian
                                color = new Color(0, (int)(new_magenta * 255), (int)(new_amarillo * 255));
                            
                            break;
                        case 2: // Canal Magenta
                                color = new Color((int)(new_cian * 255), 0, (int)(new_amarillo * 255));
                            
                            break;
                        case 3: // Canal Amarillo

                                color = new Color((int)(new_cian * 255), (int)(new_magenta * 255), 0);
                            
                            break;
                        case 4: // Canal Negro

                                color = new Color((int)(new_negro * 255), (int)(new_negro * 255), (int)(new_negro * 255));
                            
                            break;
                        case 5: // Todos los canales

                                color = new Color(cian, magenta, amarillo);
                            
                            break;
                    }
                    imagenInt[y][x] = color.getRGB();
                }
            }
            JFrame padre = new JFrame();
            ImageBufferedImage bufferedImage = new ImageBufferedImage();

            return padre.createImage(new MemoryImageSource(ancho,
                    alto, bufferedImage.convertirInt2DAInt1D(imagenInt, ancho, alto),
                    0, ancho));
        }
    
    
}
