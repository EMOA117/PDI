
package color;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;


public class LabToRgb {
    
    public Image convertirLabtoRgb(Lab[][] img,int ancho,int alto){
        //se crea la imagen a imprimir
        Lms[][] imgLms= LabaLms(img,ancho,alto);
        //despues obtenemos el canal para RGB

        Image construccion = convertirLmsToRgb(imgLms, ancho, alto);

        return construccion;
    }
    public Image convertirLmsToRgb(Lms[][] imgLms, int ancho, int alto) {
        // Se crea la imagen a imprimir
        int[] Img = new int[ancho * alto];

        // Matriz para hacer la conversión de LMS a RGB
        double[][] mtzLmsToRgb = {
            {4.4679, -3.5873, 0.1193},
            {-1.2186, 2.3809, -0.1624},
            {0.0497, -0.2439, 1.2045}
        };

        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                double l = imgLms[i][j].getL();
                double m = imgLms[i][j].getM();
                double s = imgLms[i][j].getS();

                double[][] mtzAuxLms = {
                    {l},
                    {m},
                    {s}
                };

                // Multiplicar la matriz para obtener los valores RGB
                double[][] mtzResRgb = multiplicarMatrices(mtzLmsToRgb, mtzAuxLms);

                //normalizar los valores de 0 a 255
                int r = (int) Math.min(Math.max(mtzResRgb[0][0], 0), 255);
                int g = (int) Math.min(Math.max(mtzResRgb[1][0], 0), 255);
                int b = (int) Math.min(Math.max(mtzResRgb[2][0], 0), 255);
                
                Color rgb;
                rgb = new Color(r,g,b);
                Img[j*ancho+i] = rgb.getRGB();
            }
        }

        // Crear y devolver la imagen final
        JFrame frameTmp = new JFrame();
        Image construccion = frameTmp.createImage(new MemoryImageSource(ancho, alto, Img, 0, ancho));
        return construccion;
    }
    
    
    
    public Lms[][] LabaLms(Lab[][] imgenLab, int ancho, int alto) {
        double[][] mtzLabToLms1 = {
            {1, 1, 1},
            {1, 1, -1},
            {1, -2, 0}
        };

        double[][] mtzLabToLms2 = {
            {Math.sqrt(3)/3, 0, 0},
            {0, Math.sqrt(6)/6, 0},
            {0, 0, Math.sqrt(2)/2}
        };

        // Matriz que se regresa
        Lms[][] imgLms = new Lms[ancho][alto];

        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                // Extraer los valores Lab
                double l = imgenLab[i][j].getL();
                double a = imgenLab[i][j].getA();
                double b = imgenLab[i][j].getB();

                double[][] mtzAuxLab = {
                    {l},
                    {a},
                    {b}
                };

                double[][] mtzIntermedia = multiplicarMatrices(mtzLabToLms1, mtzLabToLms2);

                double[][] mtzRes = multiplicarMatrices(mtzIntermedia,mtzAuxLab);

                double lmsL = mtzRes[0][0];
                double lmsM = mtzRes[1][0];
                double lmsS = mtzRes[2][0];                

                //usando logaritmo:
                /*double lmsL = Math.log10(mtzRes[0][0]);
                double lmsM = Math.log10(mtzRes[1][0]);
                double lmsS = Math.log10(mtzRes[2][0]);*/

                // Asignar los valores LMS a la nueva imagen
                imgLms[i][j] = new Lms();
                imgLms[i][j].setL(lmsL);
                imgLms[i][j].setM(lmsM);
                imgLms[i][j].setS(lmsS);
            }
        }
        return imgLms;
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
