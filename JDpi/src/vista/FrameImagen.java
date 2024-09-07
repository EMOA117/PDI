/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.ImageBufferedImage;
import modelo.LectorDeImagen;

public class FrameImagen extends JFrame {
    private PanelImagen panel;
    private String path = "C:\\Users\\EMOA1\\OneDrive\\Imágenes\\";
    private String archivoImagen = "narufondo.jpg";

    public FrameImagen(Image imagen) {
        int ancho = imagen.getWidth(null);
        int alto = imagen.getHeight(null);
        setTitle("Visor de imagen " + ancho + " x " + alto + " pixeles.");
        initComponents(imagen);
    }

    private void initComponents(Image imagen) {
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new BorderLayout());
        panel = new PanelImagen(imagen);
        contenedor.add(panel, BorderLayout.CENTER);
        // Crear barra de menú
        JMenuBar menuBar = new JMenuBar();
        // Crear menús
        JMenu menuImagen = new JMenu("Imagen");
        JMenu menuBrilloContraste = new JMenu("Brillo y Contraste");
        // Crear elementos del menú para cambiar el canal de color
        JMenuItem itemGris = new JMenuItem("Ponderacion en Gris");
        JMenuItem itemRojo = new JMenuItem("Canal Rojo");
        JMenuItem itemRojoGris = new JMenuItem("Canal Rojo en Gris");
        JMenuItem itemVerde = new JMenuItem("Canal Verde");
        JMenuItem itemVerdeGris = new JMenuItem("Canal Verde en Gris");
        JMenuItem itemAzul = new JMenuItem("Canal Azul");
        JMenuItem itemAzulGris = new JMenuItem("Canal Azul en Gris");
        
        
        itemGris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal rojo (debes implementar la lógica de cambio)
                cambiarCanalColor(5);  // 0 para Rojo
            }
        });
        
        itemRojo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal rojo (debes implementar la lógica de cambio)
                
                cambiarCanalColor(1);  // 0 para Rojo
            }
        });
        
        itemRojoGris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal rojo (debes implementar la lógica de cambio)
                
                cambiarCanalColor(6);  // 0 para Rojo
            }
        });
        
        itemVerde.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal verde
                cambiarCanalColor(2);  // 1 para Verde
            }
        });
        
        itemVerdeGris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal verde
                cambiarCanalColor(7);  // 1 para Verde
            }
        });
        
        itemAzul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal azul
                cambiarCanalColor(3);  // 2 para Azul
            }
        });
        
        itemAzulGris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal azul
                cambiarCanalColor(8);  // 2 para Azul
            }
        });

        // Añadir opciones al menú Imagen
        menuImagen.add(itemGris);
        menuImagen.add(itemRojo);
        menuImagen.add(itemRojoGris);
        menuImagen.add(itemVerde);
        menuImagen.add(itemVerdeGris);
        menuImagen.add(itemAzul);
        menuImagen.add(itemAzulGris);
        
        // Crear elementos del menú para brillo y contraste con sliders
        JMenuItem itemBrillo = new JMenuItem("Ajustar Brillo");
        itemBrillo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajustarBrillo(); // Método para ajustar el brillo
            }
        });
        
        JMenuItem itemContraste = new JMenuItem("Ajustar Contraste");
        itemContraste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajustarContraste(); // Método para ajustar el contraste
            }
        });
        
        // Añadir opciones al menú Brillo y Contraste
        menuBrilloContraste.add(itemBrillo);
        menuBrilloContraste.add(itemContraste);
        
        // Añadir menús a la barra de menú
        menuBar.add(menuImagen);
        menuBar.add(menuBrilloContraste);
        
        // Añadir barra de menú al Frame
        this.setJMenuBar(menuBar);
        
        // Acción para el botón de cerrar la ventana
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(imagen.getWidth(null), imagen.getHeight(null));
        this.setVisible(true);
    }
    
    
    // Método para ajustar el brillo
    private void ajustarBrillo() {
        // Crear un slider para ajustar el brillo
        JSlider slider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        int resultado = JOptionPane.showConfirmDialog(this, slider, 
            "Ajustar Brillo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (resultado == JOptionPane.OK_OPTION) {
            int valorBrillo = slider.getValue();
            
            //se suma un escalar
            cambiarContrasteBrillo(9, valorBrillo);
            
            // Implementa la lógica para ajustar el brillo con el valor dado
            // Ejemplo: panel.ajustarBrillo(valorBrillo);
        }
    }

    // Método para ajustar el contraste
    private void ajustarContraste() {
        // Crear un slider para ajustar el contraste
  JSlider sliderContraste = new JSlider(JSlider.HORIZONTAL, 50, 150, 100);
sliderContraste.setMajorTickSpacing(25);
sliderContraste.setMinorTickSpacing(5);
sliderContraste.setPaintTicks(true);
sliderContraste.setPaintLabels(true);

// Mostrar el slider en un diálogo
int resultado = JOptionPane.showConfirmDialog(this, sliderContraste, 
    "Ajustar Contraste", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

if (resultado == JOptionPane.OK_OPTION) {
    int valorSlider = sliderContraste.getValue();  // Valor del slider (10 a 20)
    double valorContraste = valorSlider / 10.0;  // Convertir a rango decimal (1.0 a 2.0)
    cambiarContrasteBrillo(10, valorSlider);
    
    // Usa el valor del contraste para procesar la imagen
    System.out.println("Contraste seleccionado: " + valorContraste);
}

    }
    
    // Método para cambiar la imagen dentro del panel
    private void cambiarCanalColor(int canal) {
         LectorDeImagen lector = new LectorDeImagen(path, archivoImagen);
        lector.leerBufferedImagen();
        ImageBufferedImage buffered = new ImageBufferedImage();
        FrameImagen frame = new FrameImagen(
                                buffered.getImage(
                                    lector.getBufferedImagen(), canal, 1));
    }
    
    private void cambiarContrasteBrillo(int canal, int escalar) {
         LectorDeImagen lector = new LectorDeImagen(path, archivoImagen);
        lector.leerBufferedImagen();
        ImageBufferedImage buffered = new ImageBufferedImage();
        FrameImagen frame = new FrameImagen(
                                buffered.getImage(
                                    lector.getBufferedImagen(), canal, escalar));
    }
}
