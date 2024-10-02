/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author EMOA1
 */
public class OperacionImg {
    private Image resultado;
    private int ancho1, ancho2, alto1, alto2;
    private Image img1, img2;
    private String op;

    public OperacionImg(Image img1, Image img2, String operacion) {
        this.ancho1 = img1.getWidth(null);
        this.ancho2 = img2.getWidth(null);
        this.alto1 = img1.getHeight(null);
        this.alto2 = img2.getHeight(null);
        this.img1 = img1;
        this.img2 = img2;
        this.op = operacion;
    }

    public Image OperacionesAritmeticas() {
        ExtractorDePixel ex = new ExtractorDePixel();
        int[] mtz1 = ex.handlepixels(img1, 0, 0, ancho1, alto1);
        int[] mtz2 = ex.handlepixels(img2, 0, 0, ancho2, alto2);

        int ancho = Math.max(ancho1, ancho2);
        int alto = Math.max(alto1, alto2);
        int[] pixeles = new int[ancho * alto];

        int min = 255, max = 0;

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                int index1 = (i < alto1 && j < ancho1) ? i * ancho1 + j : -1;
                int index2 = (i < alto2 && j < ancho2) ? i * ancho2 + j : -1;
                
                int gray1 = (index1 >= 0) ? mtz1[index1] & 0xff : 0;
                int gray2 = (index2 >= 0) ? mtz2[index2] & 0xff : 0;

                int result = 0;

                switch (op) {
                    case "Suma(+)":
                        result = (gray1 + gray2) / 2;
                        break;
                    case "Resta(-)":
                        result = Math.abs(gray1 - gray2);
                        break;
                    case "Multiplicacion(*)":
                        result = (gray1 * gray2) / 255; 
                        break;
                    case "Division(/)":
                        double b=gray2;
                        if(b==0)
                            b=0.01;
                        result = (int)((double)gray1/b);
                        break;
                }

                min = Math.min(min, result);
                max = Math.max(max, result);

                pixeles[i * ancho + j] = result;
            }
        }

        //normalizar de 0 a 255 para no usar la clase Color
        for (int i = 0; i < pixeles.length; i++) {
            int normalized = (max > min) ? (pixeles[i] - min) * 255 / (max - min) : pixeles[i];
            int gray = Math.min(255, Math.max(0, normalized));
            pixeles[i] = (255 << 24) | (gray << 16) | (gray << 8) | gray;
        }

        // Crear un BufferedImage para almacenar el resultado
        BufferedImage imgResultado = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
    imgResultado.setRGB(0, 0, ancho, alto, pixeles, 0, ancho);

    // Devolver la imagen resultante
    return imgResultado;
    }
    
    public Image OperacionesLogicas() {
        ExtractorDePixel ex = new ExtractorDePixel();
        int[] mtz1 = ex.handlepixels(img1, 0, 0, ancho1, alto1);
        int[] mtz2 = ex.handlepixels(img2, 0, 0, ancho2, alto2);

        int ancho = Math.max(ancho1, ancho2);
        int alto = Math.max(alto1, alto2);
        int[] pixeles = new int[ancho * alto];

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                int index1 = (i < alto1 && j < ancho1) ? i * ancho1 + j : -1;
                int index2 = (i < alto2 && j < ancho2) ? i * ancho2 + j : -1;
                
                int gray1 = (index1 >= 0) ? mtz1[index1] & 0xff : 0;
                int gray2 = (index2 >= 0) ? mtz2[index2] & 0xff : 0;

                int result = 0;
                switch (op) {
                    case "AND":
                        result = gray1 & gray2;
                        break;
                    case "OR":
                        result = gray1 | gray2;
                        break;
                    case "NOT":
                        result = ~gray1 & 0xff; // Aplicar máscara para mantener en 8 bits
                        break;
                }
                //poner los pixeles en el rango de grises:
                pixeles[i * ancho + j] = (255 << 24) | (result << 16) | (result << 8) | result;
            }
        }

        // Crear un BufferedImage para almacenar el resultado
    BufferedImage imgResultado = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
    imgResultado.setRGB(0, 0, ancho, alto, pixeles, 0, ancho);

    // Devolver la imagen resultante
    return imgResultado;
    }
    public Image OperacionesRelacionales(){
        ExtractorDePixel ex = new ExtractorDePixel();
        int[] mtz1 = ex.handlepixels(img1, 0, 0, ancho1, alto1);
        int[] mtz2 = ex.handlepixels(img2, 0, 0, ancho2, alto2);

        int ancho = Math.max(ancho1, ancho2);
        int alto = Math.max(alto1, alto2);
        int[] pixeles = new int[ancho * alto];

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                int index1 = (i < alto1 && j < ancho1) ? i * ancho1 + j : -1;
                int index2 = (i < alto2 && j < ancho2) ? i * ancho2 + j : -1;
                
                int gray1 = (index1 >= 0) ? mtz1[index1] & 0xff : 0;
                int gray2 = (index2 >= 0) ? mtz2[index2] & 0xff : 0;

                int result = 0;
                switch (op) {
                    case "Menor que (<)":
                            result = (gray1<gray2) ? Color.white.getRGB(): Color.black.getRGB();
                        break;
                    case "Menor o igual que (<=)":
                            result = (gray1<=gray2) ? Color.white.getRGB(): Color.black.getRGB();
                        break;
                    case "Mayor que (>)":
                            result = (gray1>gray2) ? Color.white.getRGB(): Color.black.getRGB();
                        break;
                    case "Mayor o igual que (>=)":
                            result = (gray1>=gray2) ? Color.white.getRGB(): Color.black.getRGB();
                        break;
                    case "Igual que (==)":
                            result = (gray1==gray2) ? Color.white.getRGB(): Color.black.getRGB();
                        break;
                    case "Diferente que (!=)":
                            result = (gray1!=gray2) ? Color.white.getRGB(): Color.black.getRGB();
                        break;
                        
                }
                //poner los pixeles en el rango de grises:
                pixeles[i * ancho + j] = result;
            }
        }

        // Crear un BufferedImage para almacenar el resultado
    BufferedImage imgResultado = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
    imgResultado.setRGB(0, 0, ancho, alto, pixeles, 0, ancho);

    // Devolver la imagen resultante
    return imgResultado;
    }
}
