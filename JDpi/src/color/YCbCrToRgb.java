/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package color;

import static color.IBmpConstante.COLORES;
import static color.IBmpConstante.GRANDE;
import static color.IBmpConstante.NGRIS;
import static color.IYiqRgbConstante.CPC;
import static color.IYiqRgbConstante.CPCU;
import static color.IYiqRgbConstante.DCCPC;
import static color.IYiqRgbConstante.QUINCE;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author sdelaot
 */
public class YCbCrToRgb {
    /**
     * Intancia para realizar conversiones con la paleta de colores.
     */
    private Rgb[] rgb;
    /**
     * Intancia para realizar conversiones con la paleta de colores.
     */
    private YCbCr[] ycbcr;
    /**
     * Alto de la imagen a realizar la conversion.
     */
    private int alto;
    /**
     * Ancho de la imagen a realizar la conversion.
     */
    private int ancho;
    /**
     * Buffer de almacen temporal del Rojo del formato RGB.
     */
    private double rr[];
    /**
     * Buffer de almacen temporal del Verde del formato RGB.
     */
    private double gg[];
    /**
     * Buffer de almacen temporal del Azul del formato RGB.
     */
    private double bb[];
    
    /**
     * Inicializa el objeto YCrCbToRgb a null.
     */
    public YCbCrToRgb() {
        rr = null;
        gg = null;
        bb = null;
        alto = 0;
        ancho = 0;
    }
    /**
     * El Constructor recibe los buffers leidos de la paleta de color en 
     * formato RGB, inicializa los Buffers del formato YIQ y manda a llamar 
     * al metodo convierteYIQ().
     *
     * @param y es la lumninacia.
     * @param i es la crominancia 1.
     * @param q es la crominancia 2.
     */
    public YCbCrToRgb(double[] y, double[] i, double[] q) {
        rgb = new Rgb[COLORES];
        ycbcr = new YCbCr[NGRIS];
        for (int n = 0; n < COLORES; n++) {
            rgb[n] = new Rgb();
            ycbcr[n] = new YCbCr(y[n], i[n], q[n]);
            }
        rr = null;
        gg = null;
        bb = null;
        alto = 0;
        ancho = 0;
        convertirYCbCrARgb();
    }
    
    /**
     * El Constructor recibe los buffers leidos de la paleta de color en 
     * formato RGB, inicializa los Buffers del formato YCbCr y manda a llamar 
     * al metodo conviertirYCbCrARgb().
     *
     * @param palYCbCr es la paleta del formato YCbCr.
     */
    public YCbCrToRgb(YCbCr[] palYCbCr) {
        rgb = new Rgb[COLORES];
        ycbcr = new YCbCr[NGRIS];
        for(int n = 0; n < NGRIS; n++) {
            rgb[n] = new Rgb();
            ycbcr[n] = new YCbCr(palYCbCr[n].getY(), 
                             palYCbCr[n].getCb(),
                             palYCbCr[n].getCr());
            }
        rr = null;
        gg = null;
        bb = null;
        alto = 0;
        ancho = 0;
        convertirYCbCrARgb();
    }
    
    /**
     * Convierte la paleta del formato YCbCr a paleta del formato RGB.
     */
    private void convertirYCbCrARgb() {
        rr = new double[NGRIS];
        gg = new double[NGRIS];
        bb = new double[NGRIS];
        Rgb rgb;
        for (int x = 0; x < NGRIS; x++) {
            rgb = ycbcr[x].convertirYCbCrToRgb();
            rr[x] = rgb.getG();
            gg[x] = rgb.getG();
            bb[x] = rgb.getB();
            }
    }
    
    /**
     * Convierte la matriz de Luminancia bidimensional en una paleta en 
     * formato RGB, recibe la matriz de Luminancia y la matriz bidimendsional 
     * de la imagen original para podel crear la paleta de colores nueva.
     *
     * @param imgMatriz es la matriz de lumninacia double.
     * @param imgO es la imagen original.
     */
    public void convertirYiqARgb(double[][] imgMatriz, int[][] imgO) {
        rr = new double[NGRIS];
        gg = new double[NGRIS];
        bb = new double[NGRIS];
        int al = imgMatriz.length;
        int an = imgMatriz[0].length;//al;
        double ro[][] = new double[al][an];
        double go[][] = new double[al][an];
        double bo[][] = new double[al][an];
        int ctr[] = new int[NGRIS];
        int ctg[] = new int[NGRIS];
        int ctb[] = new int[NGRIS];

        for(int n = 0; n < NGRIS; n++) {
            ctr[n] = 0;
            ctg[n] = 0;
            ctb[n] = 0;
            }
        double max1 = -GRANDE;
        double max  = -GRANDE;
        double min  =  GRANDE;
        for(int y = 0; y < NGRIS; y++) {
            if(ycbcr[y].getY() > max1) {
                max1 = ycbcr[y].getY();
                }
            if(ycbcr[y].getY() > max) {
                max = ycbcr[y].getY();
                }
            if(ycbcr[y].getY() < min) {
                min = ycbcr[y].getY();
                }
            }
        Rgb rgb;
        for(int y = 0; y < al; y++) {
            ro[y] = new double[an];
            go[y] = new double[an];
            bo[y] = new double[an];
            for(int x = 0; x < an; x++) {
                double tmpY = ((imgMatriz[y][x] - min)
                        * (((DCCPC + max1) / 2) / (max - min)));
                YCbCr ycbcrT = new YCbCr(tmpY, ycbcr[imgO[y][x]].getCb(), 
                                         ycbcr[imgO[y][x]].getCr());
                rgb = ycbcrT.convertirYCbCrToRgb();
                ro[y][x] = rgb.getR();
                go[y][x] = rgb.getG();
                bo[y][x] = rgb.getB();
                }
            }
        for(int y = 0; y < al; y++) {
            for(int x = 0; x < an; x++) {
                rr[imgO[y][x]] += ro[y][x];
                gg[imgO[y][x]] += go[y][x];
                bb[imgO[y][x]] += bo[y][x];
                ctr[imgO[y][x]]++;
                ctg[imgO[y][x]]++;
                ctb[imgO[y][x]]++;
                }
            }
        for(int x = 0; x < NGRIS; x++) {
            if(ctr[x] == 0) {
                ctr[x] = 1;
                }
            rr[x] = rr[x] / ctr[x];
            if(rr[x] < CPCU) {
                rr[x] = CPC;
                }
            if(rr[x] > QUINCE) {
                rr[x] = QUINCE;
                }

            if(ctg[x] == 0) {
                ctg[x] = 1;
                }
            gg[x] = gg[x] / ctg[x];
            if(gg[x] < CPCU) {
                gg[x] = CPC;
                }
            if(gg[x] > QUINCE) {
                gg[x] = QUINCE;
                }

            if(ctb[x] == 0) {
                ctb[x] = 1;
                }
            bb[x] = bb[x] / ctb[x];
            if(bb[x] < CPCU) {
                bb[x] = CPC;
                }
            if(bb[x] > QUINCE) {
                bb[x] = QUINCE;
                }
            }
    }
    
    /**
     * Convierte la matriz de Luminancia bidimensional en una paleta en 
     * formato RGB,recibe la matriz de Luminancia y la matriz bidimendsional 
     * de la imagen original para podel crear la paleta de colores nueva.
     *
     * @param imgMatriz es la matriz de lumninacia int.
     * @param imgO es la imagen original.
     */
    public void convertirYiqARgb(int[][] imgMatriz, int[][] imgO) {
        rr = new double[NGRIS];
        gg = new double[NGRIS];
        bb = new double[NGRIS];
        int al = imgMatriz.length;
        int an = imgMatriz[0].length;//al;
        double ro[][] = new double[al][an];
        double go[][] = new double[al][an];
        double bo[][] = new double[al][an];
        int ctr[] = new int[NGRIS];
        int ctg[] = new int[NGRIS];
        int ctb[] = new int[NGRIS];

        for(int n = 0; n < NGRIS; n++) {
            ctr[n] = 0;
            ctg[n] = 0;
            ctb[n] = 0;
            }

        double max1 = -GRANDE;
        double max  = -GRANDE;
        double min  =  GRANDE;

        for(int y = 0; y < NGRIS; y++) {
            if(ycbcr[y].getY() > max1) {
                max1 = ycbcr[y].getY();
                }
            if(ycbcr[y].getY() > max) {
                max = ycbcr[y].getY();
                }
            if(ycbcr[y].getY() < min) {
                min = ycbcr[y].getY();
                }
            }
        Rgb rgb;
        for(int y = 0; y < al; y++) {
            ro[y] = new double[an];
            go[y] = new double[an];
            bo[y] = new double[an];
            for(int x = 0; x < an; x++) {
                double tmpY = ((imgMatriz[y][x] - min)
                        * (((DCCPC + max1) / 2) / (max - min)));
                YCbCr ycbcrT = new YCbCr(tmpY, ycbcr[imgO[y][x]].getCb(), 
                                         ycbcr[imgO[y][x]].getCr());
                rgb = ycbcrT.convertirYCbCrToRgb();
                ro[y][x] = rgb.getR();
                go[y][x] = rgb.getG();
                bo[y][x] = rgb.getB();
                }
            }
        for(int y = 0; y < al; y++) {
            for(int x = 0; x < an; x++) {
                rr[imgO[y][x]] += ro[y][x];
                gg[imgO[y][x]] += go[y][x];
                bb[imgO[y][x]] += bo[y][x];
                ctr[imgO[y][x]]++;
                ctg[imgO[y][x]]++;
                ctb[imgO[y][x]]++;
                }
            }
        for(int x = 0; x < NGRIS; x++) {
            if(ctr[x] == 0) {
                ctr[x] = 1;
                }
            rr[x] = rr[x] / ctr[x];
            if(rr[x] < CPCU) {
                rr[x] = CPC;
                }
            if(rr[x] > DCCPC) {
                rr[x] = DCCPC;
                }

            if(ctg[x] == 0) {
                ctg[x] = 1;
                }
            gg[x] = gg[x] / ctg[x];
            if(gg[x] < CPCU) {
                gg[x] = CPC;
                }
            if(gg[x] > DCCPC) {
                gg[x] = DCCPC;
                }

            if(ctb[x] == 0) {
                ctb[x] = 1;
                }
            bb[x] = bb[x] / ctb[x];
            if(bb[x] < CPCU) {
                bb[x] = CPC;
                }
            if(bb[x] > DCCPC) {
                bb[x] = DCCPC;
                }
            }
    }
    
    /**
     * Convierte una imagen en formato YCbCr a una Rgb
     *
     * @param oImagen la imagen de entrada
     * @param acceso path mas el nombre del archivo
     *
     * @return devuelve la imagen convertida
     */
    public Image convertirYCbCrARgb32(Image oImagen, String acceso) {
        Image loImagen = null;
        try {
            FileInputStream input = new FileInputStream(new File(acceso));
            loImagen = ImageIO.read(input);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }        
        RgbToYCbCr rgbToYCbCr = new RgbToYCbCr();
        double[] Y = rgbToYCbCr.convertirRgbAYCbCr32d(oImagen, 1);
        double[] I = rgbToYCbCr.convertirRgbAYCbCr32d(loImagen, 2);
        double[] Q = rgbToYCbCr.convertirRgbAYCbCr32d(loImagen, 3);
        int elAlto = oImagen.getHeight(null);
        int elAncho = oImagen.getWidth(null);
        int [] pixeles = new int[elAncho * elAlto];
        YCbCr ycbcr;
        for(int x = 0; x < pixeles.length; x++) {
            ycbcr = new YCbCr(Y[x], I[x], Q[x]);
            Rgb rgb = ycbcr.convertirYCbCrToRgbJpeg();
            Color color = new Color((int)rgb.getR(), 
                                    (int)rgb.getG(), 
                                    (int)rgb.getB());
            pixeles[x] = color.getRGB();
            }
        JFrame frameTmp = new JFrame();
        Image loImage = frameTmp.createImage(new MemoryImageSource(elAncho,
                elAlto, pixeles,
                0, elAncho));
        return loImage;
    }
}
