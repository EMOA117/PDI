package modelo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Saul
 */
public class LectorDeImagen {
    private Image imagen;
    private String path;
    private String nombreArchivo;
    private int tipo;
    private BufferedImage bufferedImagen;
    public LectorDeImagen(String path, String sArchivo) {
        imagen = null;
        this.path = path;
        this.nombreArchivo = sArchivo;
    }
    public void leerImagen() {
        String acceso = this.path + nombreArchivo;
        System.out.println(acceso);
        imagen = new ImageIcon(
        	getClass().getResource( acceso ) ).getImage();
    }
    public void leerBufferedImagen() {
        bufferedImagen = null;
        try {
            String acceso = this.path + nombreArchivo;
            //DEBUG
            System.out.println("Acceso: " + acceso);
            FileInputStream input = 
                        new FileInputStream(
                            new File(acceso));
            bufferedImagen = ImageIO.read(input);
            tipo = bufferedImagen.getType();
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
        int ancho = bufferedImagen.getWidth();
        int alto = bufferedImagen.getHeight();
        imagen = bufferedImagen.getScaledInstance(ancho, alto, 
                BufferedImage.SCALE_DEFAULT);
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public BufferedImage getBufferedImagen() {
        return bufferedImagen;
    }

    public void setBufferedImagen(BufferedImage bufferedImagen) {
        this.bufferedImagen = bufferedImagen;
    }
    public int getAncho() {
        return imagen.getWidth(null);
    }
    public int getAlto() {
        return imagen.getHeight(null);
    }
    public int getAnchoBufferedImage() {
        return bufferedImagen.getWidth();
    }
    public int getAltoBufferedImage() {
        return bufferedImagen.getHeight();
    }
}
