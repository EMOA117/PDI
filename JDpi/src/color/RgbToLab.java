
package color;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JFrame;
import modelo.ExtractorDePixel;


public class RgbToLab {
    private int ancho;
    private int alto;
    private Image IoImagen;
    private Rgb[][] imgRgb;
    private Xyz[][] imgXyz;
    private Lms[][] imgLms;
    private Lab[][] imgLab;
    
    public RgbToLab(Image input) {
        alto = input.getHeight(null);
        ancho = input.getWidth(null);
        this.IoImagen = input;
        imgRgb = new Rgb[ancho][alto];
        imgXyz = new Xyz[ancho][alto];
        imgLms = new Lms[ancho][alto];
        imgLab = new Lab[ancho][alto];
    }
    
    public Image ConvertirRGBaLab(int selector) {
        // Realizamos todas las conversiones
        ExtraerRGBdeImg();
        ConvertirRgbAXyz();
        ConvertirXyzALMS();
        ConvertirLmsALab();

        int[] Img = new int[ancho * alto];

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                int value = 0;

                switch (selector) {
                    case 1: // Canal L
                        value = (int)(imgLab[j][i].getL()); 
                        break;
                    case 2: // Canal A
                        value = (int)(imgLab[j][i].getA());
                        break;
                    case 3: // Canal B
                        value = (int)(imgLab[j][i].getB());
                        break;
                }

                // Normalización de valores (ajustar al rango 0-255) para imprimirlos
                value = Math.max(0, Math.min(255, value + 128));
                
                Color rgb;
                rgb = new Color(value,value,value);
                Img[i*ancho+j] = rgb.getRGB();
            }
        }

        JFrame frameTmp = new JFrame();
        Image construccion = frameTmp.createImage(
            new MemoryImageSource(ancho, alto, Img, 0, ancho));

        return construccion;
    }
    
    public void ConvertirLmsALab(){
        // Matriz de conversión LMS a Lab
        double[][] mtzLab = {
            {1 / Math.sqrt(3), 1 / Math.sqrt(3), 1 / Math.sqrt(3)},
            {1 / Math.sqrt(6), 1 / Math.sqrt(6), -2 / Math.sqrt(6)},
            {1 / Math.sqrt(2), -1 / Math.sqrt(2), 0}
        };

        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                // Extraer los valores LMS
                double l = imgLms[i][j].getL();
                double m = imgLms[i][j].getM();
                double s = imgLms[i][j].getS();

                // Crear la matriz LMS como un vector columna
                double[][] mtzAuxLms = {
                    {l},
                    {m},
                    {s}
                };

                // Multiplicamos las matrices para obtener los valores Lab
                double[][] mtzRes = multiplicarMatrices(mtzLab, mtzAuxLms);

                imgLab[i][j] = new Lab();
                imgLab[i][j].setLab(mtzRes[0][0],mtzRes[1][0],mtzRes[2][0]);

            }
        }
    }
    
    public void ConvertirXyzALMS(){
        //matriz a multiplicar XYZ
        double[][] mtzLMS ={
            {0.3897, 0.6890, -0.0787},
            {-0.2298, 1.1834, 0.0464},
            {0.0000, 0.0000, 1.0000}
        };
        
        //hacemos la conversion de RGB a XYZ ya teniendo la matriz RGB
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                //convertimos a matriz
                //cada que se hace el recorrido extraemos los canales y hacemos una matriz
                // Extraer los valores RGB
                double x = imgXyz[i][j].getX();
                double y = imgXyz[i][j].getY();
                double z = imgXyz[i][j].getZ();
                
                double[][] mtzAuxLms = {
                    {x},
                    {y},
                    {z}
                };
                //multiplicamos las matrices y asignamos valores
                double[][] mtzRes = multiplicarMatrices(mtzLMS, mtzAuxLms);
                
                //por ultimo se almacenan en la matriz xyz
                imgLms[i][j] = new Lms();

                imgLms[i][j].setL(mtzRes[0][0]);
                imgLms[i][j].setM(mtzRes[1][0]);
                imgLms[i][j].setS(mtzRes[2][0]);
                
                //aplicando logaritmo
                
                /*imgLms[i][j].setL(Math.log10(mtzRes[0][0]));
                imgLms[i][j].setM(Math.log10(mtzRes[1][0]));
                imgLms[i][j].setS(Math.log10(mtzRes[2][0]));*/
                
            }
        }
        
    }
    
    public void ConvertirRgbAXyz(){
        //matriz a multiplicar XYZ
        double[][] mtzXyz ={
            {0.5141,0.3239, 0.1604},
            {0.2651, 0.6702, 0.0641},
            {0.0241, 0.1228, 0.8444}
        };
        
        //hacemos la conversion de RGB a XYZ ya teniendo la matriz RGB
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                //convertimos a matriz
                //cada que se hace el recorrido extraemos los canales y hacemos una matriz
                // Extraer los valores RGB
                double r = imgRgb[i][j].getR();
                double g = imgRgb[i][j].getG();
                double b = imgRgb[i][j].getB();
                
                // Crear la matriz RGB como un vector columna
                double[][] mtzAuxRgb = {
                    {r},
                    {g},
                    {b}
                };
                //multiplicamos las matrices y asignamos valores
                double[][] mtzRes = multiplicarMatrices(mtzXyz, mtzAuxRgb);
                
                //por ultimo se almacenan en la matriz xyz
                imgXyz[i][j] = new Xyz();
                imgXyz[i][j].setX(mtzRes[0][0]);
                imgXyz[i][j].setY(mtzRes[1][0]);
                imgXyz[i][j].setZ(mtzRes[2][0]);
                
            }
        }
        
    }
    
    
    //funcion para convertir la imagen a canales RGB con la clase RGB
    public void ExtraerRGBdeImg() {
        ExtractorDePixel op = new ExtractorDePixel();

        int[][] pixelesRGB = new int[ancho][alto];
        pixelesRGB = op.obtenerPixelesEn2D(IoImagen, 0, 0, ancho, alto);

        // Aquí es donde se va almacenar la imagen en rgb
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                double rojo  = (pixelesRGB[y][x] & 0x00ff0000) >> 16;
                double verde = (pixelesRGB[y][x] & 0x0000ff00) >> 8;
                double azul  =  pixelesRGB[y][x] & 0x000000ff;

                // Asignar los valores a la matriz RGB
                imgRgb[x][y] = new Rgb();
                imgRgb[x][y].setR(rojo);
                imgRgb[x][y].setG(verde);
                imgRgb[x][y].setB(azul);
            }
        }
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

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public Rgb[][] getImgRgb() {
        return imgRgb;
    }

    public Xyz[][] getImgXyz() {
        return imgXyz;
    }

    public Lms[][] getImgLms() {
        return imgLms;
    }

    public Lab[][] getImgLab() {
        return imgLab;
    }
    
    
}
