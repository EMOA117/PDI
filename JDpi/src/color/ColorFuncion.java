package color;

/**
 * Clase que tiene funciones que emplean las clases del paquete color
 * 
 * @author sdelaot
 */
public class ColorFuncion {
    /**
     * Constructor de la clase
     */
    public ColorFuncion() {
    
    }
    /**
     * Calcula la media de tres numero
     * @param da el primer numero
     * @param db el segundo numero
     * @param dc el tercer numero
     * @return devuelve la media de los tres numeros
     */
    public double calcularMedia(double da, double db, double dc) {
        return (da + db + dc) / 3.0;
    }
    /**
     * Calcula G(x)
     * @param x la x para calcular al funcion
     * @return devuelve el valor calculado
     */
    public double calcularGx(double x) {
        double gx;
        if(x>0.04045) {
            gx = 100.0 * Math.pow((x+0.055)/1.055, 2.4);
            }
        else {
            gx = x / 12.92;
            }
        return gx;
    }
    /**
     * Devuelve el minimo de tres numeros
     * @param a primer numero
     * @param b segundo numero
     * @param c tercer numero
     * @return devuelve el minimo de los tres
     */
    public double min(double a, double b, double c) {
        return menor(a, menor(b, c));
    }
    /**
     * Encuentra el menor de dos numeros 
     * @param a el primer numero
     * @param b el segundo umero
     * @return devuelve el menor
     */
    public double menor(double a, double b) {
        if(a<b) {
            return a;
            }
        return b;
    }
    /**
     * Devuelve el maximo de tres numeros
     * @param a primer numero
     * @param b segundo numero
     * @param c tercer numero
     * @return devuelve el maximo de los tres
     */
    public double max(double a, double b, double c) {
        return mayor(a, mayor(b, c));
    }
    /**
     * Encuentra el mayor de dos numerod 
     * @param a el primer numero
     * @param b el segundo umero
     * @return devuelve el mayor
     */
    public double mayor(double a, double b) {
        if(a>b) {
            return a;
            }
        return b;
    }
    /**
     * Normaliza img para desplegarlos en el rango de 256 niveles de gris.
     * 
     * @param img la imagen a normalizar
     * @param alto la altura de la imagen en pixeles
     * @param ancho el ancho de la imagen en pixeles
     * 
     * @return devuelve el vector normalizado
     */
    public double[] normalizarUnCanal(double[] img, int alto, int ancho) {
        double max = -Double.MAX_VALUE;
        double min = Double.MAX_VALUE;

        // Encontrar los valores máximo y mínimo en la imagen
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                double valor = img[y * ancho + x];
                if (valor > max) {
                    max = valor;
                }
                if (valor < min) {
                    min = valor;
                }
            }
        }

        // Prevenir la división por cero si max == min
        if (max == min) {
            // Si todos los valores son iguales, simplemente devolver la misma imagen
            return img.clone(); // O puedes devolver un arreglo donde todos los valores sean 0 o 255, según lo que necesites
        }

        // Normalizar los valores al rango [0, 255]
        double[] imagen = new double[alto * ancho];
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                imagen[y * ancho + x] = (img[y * ancho + x] - min) * (255.0 / (max - min));
            }
        }

        return imagen;
    }

        /**
     * Normaliza a I y Q para desplegarlos en el rango de 256 niveles
     * de gris.
     * 
     * @param img la imagen que se va a normalizar
     * @param alto la altura de la imagen en pixeles
     * @param ancho el ancho de la imagen en pixeles
     * 
     * @return devuelve la imagen normalizada
     */
    public double [][] normalizar( double [][] img, int alto, int ancho ) {
    	double max = -50000.0;
    	double min =  50000.0;
    	for( int y=0; y<alto; y++ ) {
            for( int x=0; x<ancho; x++ ) {
		if( img[y][x]>max ) {
                    max = img[y][x];
                    }
		if( img[y][x]<min ) {
                    min = img[y][x];
                    }
		}
            }
        //System.out.println( " max " + max + " min " + min );
        double [][] imagen = new double[alto][ancho];
	for( int y=0; y<alto; y++ ) {
            if( imagen[y]==null ) {
                imagen[y] = new double[ancho];
                }
            for( int x=0; x<ancho; x++ ) {
		imagen[y][x] = (img[y][x]-min)*(255.0/(max-min));
		}
            }
        return imagen;
    }
    /**
     * Normaliza a cierto numero de decimales.
     *
     * @param numero el numero que se normaliza
     * @param decimales el total de decimales en la normalizacion 4 a 8
     *
     * @return devuelve le valor normalizado
     */
    public double normalizar(double numero, int decimales) {
        if (decimales == 4) {
            numero *= 10000.0;
        } else if (decimales == 5) {
            numero *= 100000.0;
        } else if (decimales == 6) {
            numero *= 1000000.0;
        } else if (decimales == 7) {
            numero *= 10000000.0;
        } else if (decimales == 8) {
            numero *= 100000000.0;
        }
        numero = Math.floor(numero);
        if (decimales == 4) {
            numero /= 10000.0;
        } else if (decimales == 5) {
            numero /= 100000.0;
        } else if (decimales == 6) {
            numero /= 1000000.0;
        } else if (decimales == 7) {
            numero /= 10000000.0;
        } else if (decimales == 8) {
            numero /= 100000000.0;
        }
        return numero;
    }
    /**
     * Normaliza una lut para crear la imagen al estandar 0 - 255.
     * 
     * @param lut la lut que se normaliza
     *
     * @return devuelve la tabla LUT normalizada
     */
    public double[] normalizarLut(double[] lut) {
        double max = -50000.0;
        double min = 50000.0;
        for(int y = 0; y < lut.length; y++) {
            if(lut[y] < min) {
                min = lut[y];
                }
            if(lut[y] > max) {
                max = lut[y];
                }
            }
        for(int y = 0; y < lut.length; y++) {
            double temp = lut[y];
            lut[y] = (temp - min) * (255.0 / (max - min));
            }
        return lut;
    }
    /**
     * Valida el rango entre 0 y 255
     * @param valor el valor a validar en rango
     * @return devuelve el valor validado o no si esta en rango
     */
    public double validarRango(double valor) {
        if((int)valor<0) {
            valor = 0.0;
            }
        if((int)valor>255.0) {
            valor = 255.0;
            }
        return valor;
    }
}
