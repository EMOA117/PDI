/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author Saul
 */
public class CanvasImagen extends Canvas {
    private Image imagen;
    public CanvasImagen(Image imagen) {
        this.imagen = imagen;
    }
    public void setImagen(Image imagen) {
        this.imagen = imagen;
        repaint();
    }
    @Override
    public void paint(Graphics g) {
        if(imagen!=null)
            g.drawImage(imagen, 0, 0, imagen.getWidth(this), 
                imagen.getHeight(this), this);
    }
    public Image getImagen() {
        return imagen;
    }
}
