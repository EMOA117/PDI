package modelo;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Saul
 */
public class Histograma {
    private int [][] imagenInt;
    private double [] hi;
    private double [] hiac;
    private double [] pi;
    private double [] piac;
    private final int L = 256;
    private double media;
    private double varianza;
    private double asimetria;
    private double energia;
    private double entropia;
    private Image img;
    private int ancho;
    private int alto;
    private int canal;
    
    public Histograma(Image img) {
        this.img = img;
        hi = new double[L];
        hiac = new double[L];
        pi = new double[L];
        piac = new double[L];
    }

    private void obtenerHistograma() {
        for (int y = 0; y < imagenInt.length; y++) {
            for (int x = 0; x < imagenInt[0].length; x++) {
                int pixel = imagenInt[y][x];
                if (pixel >= 0 && pixel < 256) {
                    hi[pixel]++;
                }
            }
        }
    }

    private void calcularProbabilidadI() {
        for (int y = 0; y < L; y++) {
            pi[y] = (double) hi[y] / (double) (alto * ancho);
        }
    }

    private void calcularMedia() {
        media = 0.0;
        double sumatoria = 0.0;
        for (int y = 0; y < L; y++) {
            sumatoria += y * pi[y];
        }
        media = sumatoria;
    }

    private void calcularVarianza() {
        varianza = 0.0;
        double sumatoria = 0.0;
        for (int y = 0; y < L; y++) {
            sumatoria += Math.pow((y - media), 2.0) * pi[y];
        }
        varianza = sumatoria;
    }

    private void calcularAsimetria() {
        asimetria = 0.0;
        double sumatoria = 0.0;
        for (int y = 0; y < L; y++) {
            sumatoria += Math.pow((y - media), 3.0) * pi[y];
        }
        asimetria = sumatoria;
    }

    private void calcularEnergia() {
        energia = 0.0;
        double sumatoria = 0.0;
        for (int y = 0; y < L; y++) {
            sumatoria += Math.pow(pi[y], 2.0);
        }
        energia = sumatoria;
    }

    private void calcularEntropia() {
        entropia = 0.0;
        double sumatoria = 0.0;
        for (int y = 0; y < L; y++) {
            sumatoria += pi[y] * logDos(pi[y]);
        }
        entropia = -sumatoria;
    }

    public double logDos(double x) {
        if (x == 0.0) {
            return 0;
        }
        return Math.log(x) / Math.log(2);
    }

    private void obtenerHistogramaAcumulativo() {
        for (int i = 0; i < L; i++) {
            hiac[i] = 0;
        }
        hiac[0] = hi[0];
        for (int i = 1; i < L; i++) {
            hiac[i] = hiac[i - 1] + hi[i];
        }
    }

    private void obtenerHistogramaPiAcumulativo() {
        for (int i = 0; i < L; i++) {
            piac[i] = 0;
        }
        piac[0] = pi[0];
        for (int i = 1; i < L; i++) {
            piac[i] = piac[i - 1] + pi[i];
        }
    }

    public void ejecutarTodo(int canal) {
        this.canal = canal;
        // Extraer los píxeles y obtener la matriz  
        ExtractorDePixel op = new ExtractorDePixel();
        alto = img.getHeight(null);
        ancho = img.getWidth(null);
        int pixel;
        int[][] matrizImagen = new int[alto][ancho];
        matrizImagen = op.obtenerPixelesEn2D(img, 0, 0, ancho, alto);
        // Recorrer la imagen y actualizar el histograma basado en el canal
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                pixel = matrizImagen[y][x];
                int rojo = (pixel & 0x00ff0000) >> 16;
                int verde = (pixel & 0x0000ff00) >> 8;
                int azul = pixel & 0x000000ff;
                int gris = (rojo + verde + azul) / 3; // Para escala de grises

                switch (canal) {
                    case 1: // Histograma de Rojo
                        hi[rojo]++;
                        break;
                    case 2: // Histograma de Verde
                        hi[verde]++;
                        break;
                    case 3: // Histograma de Azul
                        hi[azul]++;
                        break;
                    case 4: // Histograma de Grises
                        hi[gris]++;
                        break;
                }
            }
        }

        // Ejecutar cálculos adicionales
        calcularProbabilidadI();
        calcularMedia();
        calcularVarianza();
        calcularAsimetria();
        calcularEnergia();
        calcularEntropia();
        obtenerHistogramaAcumulativo();
        obtenerHistogramaPiAcumulativo();
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("El histograma es: ").append("\n");
        for(int i=0; i<L; i++) {
            builder.append("hi[").append(i).append("]=").append(hi[i]);
            builder.append(" ");
            if(i%8==0 && i!=0) {
                builder.append("\n");
                }
            }
        builder.append("\n\n");
        builder.append("La probabilidad del histograma: ").append("\n");
        for(int i=0; i<L; i++) {
            builder.append("pi[").append(i).append("]=").append(pi[i]);
            builder.append(" ");
            if(i%4==0 && i!=0) {
                builder.append("\n");
                }
            }
        builder.append("\n\n");
        builder.append("Alto imagen: ").append(imagenInt.length).append("\n");
        builder.append("Ancho imagen: ").append(imagenInt[0].length).append("\n");
        builder.append("La media es: ").append(media).append("\n");
        builder.append("La varianza es: ").append(varianza).append("\n");
        double varianzaDos = Math.sqrt(varianza);
        builder.append("La varianza es: ").append(varianzaDos).append("\n");
        builder.append("La asimetria es: ").append(asimetria).append("\n");
        builder.append("La energia es: ").append(energia).append("\n");
        builder.append("La entropia es: ").append(entropia).append("\n");
        builder.append("\n\n");
        builder.append("El histograma acumulado es: ").append("\n");
        for(int i=0; i<L; i++) {
            builder.append("hiac[").append(i).append("]=").append(hiac[i]);
            builder.append(" ");
            if(i%8==0 && i!=0) {
                builder.append("\n");
                }
            }
        builder.append("\n\n");
        builder.append("La probabilidad de histograma acumulado es: \n");
        for(int i=0; i<L; i++) {
            builder.append("piac[").append(i).append("]=").append(piac[i]);
            builder.append(" ");
            if(i%4==0 && i!=0) {
                builder.append("\n");
                }
            }
        return builder.toString();
    }

    public double[] getPi() {
        return pi;
    }

    public int getL() {
        return L;
    }

    public double[] getPiac() {
        return piac;
    }
    
    public double[] getHi() {
    return hi;
    }
    
    public double[] getHiac() {
    return hiac;
    }

    public int[][] getImagenInt() {
        return imagenInt;
    }

    public double getMedia() {
        return media;
    }

    public double getVarianza() {
        return varianza;
    }

    public double getAsimetria() {
        return asimetria;
    }

    public double getEnergia() {
        return energia;
    }

    public double getEntropia() {
        return entropia;
    }

    public Image getImg() {
        return img;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public int getCanal() {
        return canal;
    }
    
}
