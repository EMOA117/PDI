/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.awt.image.BufferedImage;
import modelo.Histograma;
import modelo.ImageBufferedImage;
import modelo.LectorDeImagen;

public class FrameImagen extends JFrame {
    private PanelImagen panel;
    private Image img_clase;

    public FrameImagen(Image imagen, String path, String name) {
        this.img_clase = imagen;
        int ancho = imagen.getWidth(null);
        int alto = imagen.getHeight(null);
        setTitle("Visor de imagen " + ancho + " x " + alto + " pixeles.");
        initComponents(imagen, path, name);
    }

    private void initComponents(Image imagen, String path, String name) {
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new BorderLayout());
        panel = new PanelImagen(imagen);
        contenedor.add(panel, BorderLayout.CENTER);
        // Crear barra de menú
        JMenuBar menuBar = new JMenuBar();
        // Crear menús
        JMenu menuImagen = new JMenu("Imagen");
        JMenu menuBrilloContraste = new JMenu("Brillo y Contraste");
        JMenu menuHistograma = new JMenu("Histograma");
        // Crear elementos del menú para cambiar el canal de color
        JMenuItem itemRGB = new JMenuItem("RGB");
        JMenuItem itemGris = new JMenuItem("Ponderacion en Gris");
        JMenuItem itemRojo = new JMenuItem("Canal Rojo");
        JMenuItem itemRojoGris = new JMenuItem("Canal Rojo en Gris");
        JMenuItem itemVerde = new JMenuItem("Canal Verde");
        JMenuItem itemVerdeGris = new JMenuItem("Canal Verde en Gris");
        JMenuItem itemAzul = new JMenuItem("Canal Azul");
        JMenuItem itemAzulGris = new JMenuItem("Canal Azul en Gris");
        JMenuItem histogramaGris = new JMenuItem("generar histograma en gris");
        JMenuItem histogramaColores = new JMenuItem("generar histograma colores");
        
        
        itemRGB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal rojo (debes implementar la lógica de cambio)
                cambiarCanalColor(4, path,name);  // 0 para Rojo
            }
        });
        
        
        itemGris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal rojo (debes implementar la lógica de cambio)
                cambiarCanalColor(5, path,name);  // 0 para Rojo
            }
        });
        
        itemRojo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal rojo (debes implementar la lógica de cambio)
                
                cambiarCanalColor(1, path,name);  // 0 para Rojo
            }
        });
        
        itemRojoGris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal rojo (debes implementar la lógica de cambio)
                
                cambiarCanalColor(6, path,name);  // 0 para Rojo
            }
        });
        
        itemVerde.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal verde
                cambiarCanalColor(2, path,name);  // 1 para Verde
            }
        });
        
        itemVerdeGris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal verde
                cambiarCanalColor(7, path,name);  // 1 para Verde
            }
        });
        
        itemAzul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal azul
                cambiarCanalColor(3, path,name);  // 2 para Azul
            }
        });
        
        itemAzulGris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar al canal azul
                cambiarCanalColor(8, path,name);  // 2 para Azul
            }
        });
        
        histogramaGris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // generarhistograma
                generarhistogramaGris(imagen);
            }
        });
        
        histogramaColores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // generarhistograma
                generarHistogramaRGB(imagen);
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
        //añadir opciones al menú histograma
        menuHistograma.add(histogramaGris);
        menuHistograma.add(histogramaColores);
        
        // Crear elementos del menú para brillo y contraste con sliders
        JMenuItem itemBrillo = new JMenuItem("Ajustar Brillo");
        itemBrillo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajustarBrillo(path,name); // Método para ajustar el brillo
            }
        });
        
        JMenuItem itemContraste = new JMenuItem("Ajustar Contraste");
        itemContraste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajustarContraste(path,name); // Método para ajustar el contraste
            }
        });
        
        // Añadir opciones al menú Brillo y Contraste
        menuBrilloContraste.add(itemBrillo);
        menuBrilloContraste.add(itemContraste);
        
        // Añadir menús a la barra de menú
        menuBar.add(menuImagen);
        menuBar.add(menuBrilloContraste);
        menuBar.add(menuHistograma);
        
        // Añadir barra de menú al Frame
        this.setJMenuBar(menuBar);
        
        // Acción para el botón de cerrar la ventana
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(imagen.getWidth(null), imagen.getHeight(null));
        this.setVisible(true);
    }
    
    
    // Método para ajustar el brillo
    private void ajustarBrillo(String path, String name ) {
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
            cambiarContrasteBrillo(9, valorBrillo, path, name);
            
            // Implementa la lógica para ajustar el brillo con el valor dado
            // Ejemplo: panel.ajustarBrillo(valorBrillo);
        }
    }

    // Método para ajustar el contraste
    private void ajustarContraste(String path, String name ) {
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
    cambiarContrasteBrillo(10, valorSlider, path,name);
    
    // Usa el valor del contraste para procesar la imagen
    System.out.println("Contraste seleccionado: " + valorContraste);
}

    }
    
    // Método para cambiar la imagen dentro del panel
    private void cambiarCanalColor(int canal,String path, String name ) {
         LectorDeImagen lector = new LectorDeImagen(path, name);
        lector.leerBufferedImagen();
        ImageBufferedImage buffered = new ImageBufferedImage();
        // Cerrar el Frame actual
    this.dispose();
        FrameImagen frame = new FrameImagen(
                                buffered.getImage(
                                    lector.getBufferedImagen(), canal, 1),path, name);
    }
    
    private void cambiarContrasteBrillo(int canal, int escalar, String path, String name) {
         LectorDeImagen lector = new LectorDeImagen(path, name);
        lector.leerBufferedImagen();
        ImageBufferedImage buffered = new ImageBufferedImage();
        // Cerrar el Frame actual
    this.dispose();
        FrameImagen frame = new FrameImagen(
                                buffered.getImage(
                                    lector.getBufferedImagen(), canal, escalar), path, name);
    }
    
    private void generarhistogramaGris(Image imagen){
    Histograma histogramaGris = new Histograma(imagen);
    histogramaGris.ejecutarTodo(4);
    FrameHistograma frame = new FrameHistograma(histogramaGris, 4);
    frame.setVisible(true);
    }

    private void generarHistogramaRGB(Image imagen){
    Histograma histogramaRojo = new Histograma(imagen);
    Histograma histogramaVerde = new Histograma(imagen);
    Histograma histogramaAzul = new Histograma(imagen);
    histogramaRojo.ejecutarTodo(1);
    histogramaVerde.ejecutarTodo(2);
    histogramaAzul.ejecutarTodo(3);
    FrameHistograma frameRojo = new FrameHistograma(histogramaRojo, 1);
    FrameHistograma frameVerde = new FrameHistograma(histogramaVerde, 2);
    FrameHistograma frameAzul = new FrameHistograma(histogramaAzul, 3);
    }
}
