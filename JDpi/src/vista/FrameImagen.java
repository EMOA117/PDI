/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import color.CmyToCmyk;
import color.CmyToRgb;
import color.HsiToRgb;
import color.HsvToRgb;
import color.LabToRgb;
import color.RgbToCmy;
import color.RgbToHsi;
import color.RgbToHsv;
import color.RgbToLab;
import color.RgbToYCbCr;
import color.RgbToYiq;
import color.YiqToRgb;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
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
    private static int gama_Colores;
    

    public FrameImagen(Image imagen, String path, String name) {
        this.img_clase = imagen;
        this.gama_Colores=1;
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
        JMenu menuConversiones= new JMenu("Conversiones (modelos de color)");
        // Crear elementos del menú para cambiar el canal de color
        JMenuItem itemRGB = new JMenuItem("RGB");
        JMenuItem itemGris = new JMenuItem("Ponderacion en Gris");
        JMenuItem itemRojo = new JMenuItem("Canal Rojo");
        JMenuItem itemRojoGris = new JMenuItem("Canal Rojo en Gris");
        JMenuItem itemVerde = new JMenuItem("Canal Verde");
        JMenuItem itemVerdeGris = new JMenuItem("Canal Verde en Gris");
        JMenuItem itemAzul = new JMenuItem("Canal Azul");
        JMenuItem itemAzulGris = new JMenuItem("Canal Azul en Gris");
        //Crear elementos del menú para generar el histograma
        JMenuItem histogramaGris = new JMenuItem("generar histograma en gris");
        JMenuItem histogramaColores = new JMenuItem("generar histograma colores");
        
        // Inicializar las opciones del menú basado en la gama actual
        actualizarMenuConversionColores(menuConversiones, path, name);

        // Agregar los menús a la barra de menú
        menuBar.add(menuConversiones);
        
        
        
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
    
    private void actualizarMenuConversionColores(JMenu menuConversionColores, String path, String name) {
    menuConversionColores.removeAll();
    //Mantener actualizadas las entradas para la utilizacion
    
    LectorDeImagen lector = new LectorDeImagen(path, name);
    lector.leerBufferedImagen();
    ImageBufferedImage buffered = new ImageBufferedImage();
    Image imagen = buffered.getImage(lector.getBufferedImagen(),4, 1);
    int ancho = imagen.getWidth(null);
    int alto = imagen.getHeight(null);
    
    
        if (gama_Colores == 1) { 
        //img = 
        JMenuItem itemOp1 = new JMenuItem("RGB a HSV");
        JMenuItem itemOp2 = new JMenuItem("RGB a Lab");
        JMenuItem itemOp3 = new JMenuItem("RGB a HSI");
        JMenuItem itemOp4 = new JMenuItem("RGB a YIQ");
        JMenuItem itemOp8 = new JMenuItem("RGB a YCbCr");
        JMenuItem itemOp5 = new JMenuItem("RGB a CMY");
        JMenuItem itemOp6 = new JMenuItem("Extracción de canales RGB en color");
        JMenuItem itemOp7 = new JMenuItem("Extracción de canales RGB en grises");

        menuConversionColores.add(itemOp1);
        menuConversionColores.add(itemOp2);
        menuConversionColores.add(itemOp3);
        menuConversionColores.add(itemOp4);
        menuConversionColores.add(itemOp8);
        menuConversionColores.add(itemOp5);
        menuConversionColores.add(itemOp6);
        menuConversionColores.add(itemOp7);

        // Acción para "RGB a HSV"
        itemOp1.addActionListener((ActionEvent e) -> {
            //creacion de objeto para conversion
            RgbToHsv conv = new RgbToHsv();
            JFrame despimgH = new JFrame("Imagen H - Representacion HSV");
            despimgH.setSize(ancho, alto);
            despimgH.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana1 = new PanelImagen(conv.convertirImg(imagen,1));
            ventana1.setPreferredSize(new Dimension(ancho, alto));
            despimgH.add(ventana1);
            despimgH.pack();
            despimgH.setVisible(true);
            
            //Imagen en verde del extractor RGB
            JFrame despimgS = new JFrame("Imagen S - Representacion de HSV");
            despimgS.setSize(ancho, alto);
            despimgS.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana2 = new PanelImagen(conv.convertirImg(imagen,2));
            ventana2.setPreferredSize(new Dimension(ancho, alto));
            despimgS.add(ventana2);
            despimgS.pack();
            despimgS.setVisible(true);
            
            //Imagen en V de HSV
            JFrame despimgV = new JFrame("Imagen V - Representacion de HSV");
            despimgV.setSize(ancho, alto);
            despimgV.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana3 = new PanelImagen(conv.convertirImg(imagen,3));
            ventana3.setPreferredSize(new Dimension(ancho, alto));
            despimgV.add(ventana3);
            despimgV.pack();
            despimgV.setVisible(true);
            
            // Menú de conversión a RGB
            JMenuBar menu = new JMenuBar();
            JMenu menuExtraccion = new JMenu("Conversion RGB");
            JMenuItem itemConversionRGB = new JMenuItem("Convertir a RGB de HSV");
            menuExtraccion.add(itemConversionRGB);
            menu.add(menuExtraccion);
            //agregar a la ventana
            despimgV.setJMenuBar(menu);

            // Acción para "Convertir a RGB"
            itemConversionRGB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    HsvToRgb con = new HsvToRgb();
                    JFrame despimgRGB = new JFrame("Imagen de canales HSV a RGB");
                    despimgRGB.setSize(ancho, alto);
                    despimgRGB.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    
                    PanelImagen ventanaRGB = new PanelImagen(con.convertirHsiToRgb(conv.getImgConv(),ancho, alto)); // Conversión HSI a RGB
                    ventanaRGB.setPreferredSize(new Dimension(ancho, alto));
                    despimgRGB.add(ventanaRGB);
                    despimgRGB.pack();
                    despimgRGB.setVisible(true);
                }
            });
            
        });

        // Acción para "RGB a Lab"
        itemOp2.addActionListener((ActionEvent e) -> {
        RgbToLab con = new RgbToLab(img_clase);
        
        JFrame despimgL = new JFrame("Imagen en L");
            despimgL.setSize(ancho,alto);
            despimgL.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana1 = new PanelImagen(con.ConvertirRGBaLab(1));
            ventana1.setPreferredSize(new Dimension(ancho,alto));
            despimgL.add(ventana1);
            despimgL.pack();
            despimgL.setVisible(true);
            
        JFrame despimgA = new JFrame("Imagen en A");
            despimgA.setSize(ancho,alto);
            despimgA.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana2 = new PanelImagen(con.ConvertirRGBaLab(2));
            ventana2.setPreferredSize(new Dimension(ancho,alto));
            despimgA.add(ventana2);
            despimgA.pack();
            despimgA.setVisible(true);
            
        JFrame despimgB = new JFrame("Imagen en B");
            despimgB.setSize(ancho,alto);
            despimgB.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana3 = new PanelImagen(con.ConvertirRGBaLab(3));
            ventana3.setPreferredSize(new Dimension(ancho,alto));
            despimgB.add(ventana3);
            despimgB.pack();
            despimgB.setVisible(true);
            
            // Menú de conversión a RGB
            JMenuBar menu = new JMenuBar();
            JMenu menuExtraccion = new JMenu("Conversion RGB");
            JMenuItem itemConversionRGB = new JMenuItem("Convertir a RGB de HSI");
            menuExtraccion.add(itemConversionRGB);
            menu.add(menuExtraccion);
            //agregar a la ventana
            despimgB.setJMenuBar(menu);

            // Acción para "Convertir a RGB"
            itemConversionRGB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LabToRgb conv = new LabToRgb();
                    JFrame despimgRGB = new JFrame("Imagen de canales Lab a RGB");
                    despimgRGB.setSize(ancho, alto);
                    despimgRGB.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    
                    PanelImagen ventanaRGB = new PanelImagen(conv.convertirLabtoRgb(con.getImgLab(),ancho,alto)); // Conversión HSI a RGB
                    ventanaRGB.setPreferredSize(new Dimension(ancho, alto));
                    despimgRGB.add(ventanaRGB);
                    despimgRGB.pack();
                    despimgRGB.setVisible(true);
                }
            });
            
        });

        // Acción para "RGB a HSI"
        itemOp3.addActionListener((ActionEvent e) -> {
            //creacion de objeto para la conversion
            //Imagen H
            RgbToHsi conv = new RgbToHsi();
            JFrame despimgY = new JFrame("Imagen H - Representacion HSI");
            despimgY.setSize(ancho, alto);
            despimgY.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana1 = new PanelImagen(conv.convertirImg(imagen,1));
            ventana1.setPreferredSize(new Dimension(ancho, alto));
            despimgY.add(ventana1);
            despimgY.pack();
            despimgY.setVisible(true);
            
            //Imagen S
            JFrame despimgS = new JFrame("Imagen S - Representacion de HSI");
            despimgS.setSize(ancho, alto);
            despimgS.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana2 = new PanelImagen(conv.convertirImg(imagen,2));
            ventana2.setPreferredSize(new Dimension(ancho, alto));
            despimgS.add(ventana2);
            despimgS.pack();
            despimgS.setVisible(true);
            
            //Imagen I
            JFrame despimgI = new JFrame("Imagen I - Representacion de HSI");
            despimgI.setSize(ancho, alto);
            despimgI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana3 = new PanelImagen(conv.convertirImg(imagen,3));
            ventana3.setPreferredSize(new Dimension(ancho, alto));
            despimgI.add(ventana3);
            despimgI.pack();
            despimgI.setVisible(true);
            
            // Menú de conversión a RGB
            JMenuBar menu = new JMenuBar();
            JMenu menuExtraccion = new JMenu("Conversion RGB");
            JMenuItem itemConversionRGB = new JMenuItem("Convertir a RGB de HSI");
            menuExtraccion.add(itemConversionRGB);
            menu.add(menuExtraccion);
            //agregar a la ventana
            despimgI.setJMenuBar(menu);

            // Acción para "Convertir a RGB"
            itemConversionRGB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    HsiToRgb con = new HsiToRgb();
                    JFrame despimgRGB = new JFrame("Imagen de canales HSI a RGB");
                    despimgRGB.setSize(ancho, alto);
                    despimgRGB.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    
                    PanelImagen ventanaRGB = new PanelImagen(con.convertirHsiToRgb(conv.getImgconv(), ancho, alto)); // Conversión HSI a RGB
                    ventanaRGB.setPreferredSize(new Dimension(ancho, alto));
                    despimgRGB.add(ventanaRGB);
                    despimgRGB.pack();
                    despimgRGB.setVisible(true);
                }
            });
        });

        // Acción para "RGB a YIQ"
        itemOp4.addActionListener((ActionEvent e) -> {
            // Creación de objeto para la conversión
            RgbToYiq conv = new RgbToYiq();
            // Imagen Y
            JFrame despimgY = new JFrame("Imagen Y - Representación YIQ");
            despimgY.setSize(ancho, alto);
            despimgY.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana1 = new PanelImagen(conv.convertirRgbAYiq32(imagen, 1)); // Canal Y
            ventana1.setPreferredSize(new Dimension(ancho, alto));
            despimgY.add(ventana1);
            despimgY.pack();
            despimgY.setVisible(true);

            // Imagen I
            JFrame despimgI = new JFrame("Imagen I - Representación YIQ");
            despimgI.setSize(ancho, alto);
            despimgI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana2 = new PanelImagen(conv.convertirRgbAYiq32(imagen, 2)); // Canal I
            ventana2.setPreferredSize(new Dimension(ancho, alto));
            despimgI.add(ventana2);
            despimgI.pack();
            despimgI.setVisible(true);

            // Imagen Q
            JFrame despimgQ = new JFrame("Imagen en union YIQ - Representación YIQ");
            despimgQ.setSize(ancho, alto);
            despimgQ.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana3 = new PanelImagen(conv.convertirRgbAYiq32(imagen, 3)); // Canal Q
            ventana3.setPreferredSize(new Dimension(ancho, alto));
            despimgQ.add(ventana3);
            despimgQ.pack();
            despimgQ.setVisible(true);

            // Menú de conversión a RGB
            JMenuBar menu = new JMenuBar();
            JMenu menuExtraccion = new JMenu("Conversion RGB");
            JMenuItem itemConversionRGB = new JMenuItem("Convertir a RGB");
            menuExtraccion.add(itemConversionRGB);
            menu.add(menuExtraccion);
            //agregar a la ventana
            despimgQ.setJMenuBar(menu);

            // Acción para "Convertir a RGB"
            itemConversionRGB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    YiqToRgb con = new YiqToRgb();
                    JFrame despimgRGB = new JFrame("Imagen de canales YIQ a RGB");
                    despimgRGB.setSize(ancho, alto);
                    despimgRGB.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    PanelImagen ventanaRGB = new PanelImagen(con.YiqToRgb(conv.getConjuntoYIQ(), ancho, alto)); // Conversión YIQ a RGB
                    ventanaRGB.setPreferredSize(new Dimension(ancho, alto));
                    despimgRGB.add(ventanaRGB);
                    despimgRGB.pack();
                    despimgRGB.setVisible(true);
                }
            });
        });

        // Acción para "RGB a CMY"
        itemOp5.addActionListener((ActionEvent e) -> {
            gama_Colores = 2; // Cambiar a gama CMY
            RgbToCmy conv = new RgbToCmy();
            panel.cambiarImg(conv.convertirRgbACmy(img_clase));
            img_clase = conv.convertirRgbACmy(img_clase);
            
            actualizarMenuConversionColores(menuConversionColores, path, name); // Actualizar el menú
        });

        // Acción para "Extracción de canales RGB en color"
        itemOp6.addActionListener((ActionEvent e) -> {
            //Imagen en rojo del extractor RGB
            JFrame despimgR = new JFrame("Imagen Roja - Extracción RGB");
            despimgR.setSize(ancho, alto);
            despimgR.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana1 = new PanelImagen(buffered.getImage(lector.getBufferedImagen(),1,1));
            ventana1.setPreferredSize(new Dimension(ancho, alto));
            despimgR.add(ventana1);
            despimgR.pack();
            despimgR.setVisible(true);
            //Imagen en verde del extractor RGB
            JFrame despimgV = new JFrame("Imagen Verde - Extracción RGB");
            despimgV.setSize(ancho, alto);
            despimgV.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana2 = new PanelImagen(buffered.getImage(lector.getBufferedImagen(),2,1));
            ventana2.setPreferredSize(new Dimension(ancho, alto));
            despimgV.add(ventana2);
            despimgV.pack();
            despimgV.setVisible(true);
            
            //Imagen en azul
            JFrame despimgA = new JFrame("Imagen Azul - Extracción RGB");
            despimgA.setSize(ancho, alto);
            despimgA.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana3 = new PanelImagen(buffered.getImage(lector.getBufferedImagen(),3,1));
            ventana3.setPreferredSize(new Dimension(ancho, alto));
            despimgA.add(ventana3);
            despimgA.pack();
            despimgA.setVisible(true);
        });

        // Acción para "Extracción de canales RGB en grises"
        itemOp7.addActionListener((ActionEvent e) -> {
            //Imagen en rojo del extractor RGB
            JFrame despimgGR = new JFrame("Imagen Roja en grises - Extracción grises");
            despimgGR.setSize(ancho, alto);
            despimgGR.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana1 = new PanelImagen(buffered.getImage(lector.getBufferedImagen(),6,1));
            ventana1.setPreferredSize(new Dimension(ancho, alto));
            despimgGR.add(ventana1);
            despimgGR.pack();
            despimgGR.setVisible(true);
            //Imagen en verde del extractor RGB
            JFrame despimgGV = new JFrame("Imagen Verde en grises - Extracción grises");
            despimgGV.setSize(ancho, alto);
            despimgGV.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana2 = new PanelImagen(buffered.getImage(lector.getBufferedImagen(),7,1));
            ventana2.setPreferredSize(new Dimension(ancho, alto));
            despimgGV.add(ventana2);
            despimgGV.pack();
            despimgGV.setVisible(true);
            
            //Imagen en azul
            JFrame despimgGA = new JFrame("Imagen Azul en grises - Extracción grises");
            despimgGA.setSize(ancho, alto);
            despimgGA.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana3 = new PanelImagen(buffered.getImage(lector.getBufferedImagen(),8,1));
            ventana3.setPreferredSize(new Dimension(ancho, alto));
            despimgGA.add(ventana3);
            despimgGA.pack();
            despimgGA.setVisible(true);
        });
        itemOp8.addActionListener((ActionEvent e) -> {
            //creacion de objeto para conversion
            RgbToYCbCr conv = new RgbToYCbCr();
            JFrame despimgH = new JFrame("Imagen Y - Representacion YCbCr");
            despimgH.setSize(ancho, alto);
            despimgH.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana1 = new PanelImagen(conv.convertirImg(imagen,1));
            ventana1.setPreferredSize(new Dimension(ancho, alto));
            despimgH.add(ventana1);
            despimgH.pack();
            despimgH.setVisible(true);
            
            //Imagen en verde del extractor RGB
            JFrame despimgS = new JFrame("Imagen Cb - Representacion de YCbCr");
            despimgS.setSize(ancho, alto);
            despimgS.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana2 = new PanelImagen(conv.convertirImg(imagen,2));
            ventana2.setPreferredSize(new Dimension(ancho, alto));
            despimgS.add(ventana2);
            despimgS.pack();
            despimgS.setVisible(true);
            
            //Imagen en V de HSV
            JFrame despimgV = new JFrame("Imagen Cr - Representacion de YCbCr");
            despimgV.setSize(ancho, alto);
            despimgV.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana3 = new PanelImagen(conv.convertirImg(imagen,3));
            ventana3.setPreferredSize(new Dimension(ancho, alto));
            despimgV.add(ventana3);
            despimgV.pack();
            despimgV.setVisible(true);
        });

    } else if (gama_Colores == 2) { 
        JMenuItem itemOp1 = new JMenuItem("CMY a RGB");
        JMenuItem itemOp4 = new JMenuItem("CMY a CMYK");
        JMenuItem itemOp2 = new JMenuItem("Extracción de canales CMY en color");
        JMenuItem itemOp3 = new JMenuItem("Extracción de canales CMY en grises");

        menuConversionColores.add(itemOp1);
        menuConversionColores.add(itemOp4);
        menuConversionColores.add(itemOp2);
        menuConversionColores.add(itemOp3);

        // Acción para "CMY a RGB"
        itemOp1.addActionListener((ActionEvent e) -> {
            gama_Colores = 1; // Cambiar de nuevo a gama RGB
            CmyToRgb conv1 = new CmyToRgb();
            panel.cambiarImg(conv1.convertirCmyARgb(img_clase));
            img_clase = conv1.convertirCmyARgb(img_clase);
            
            actualizarMenuConversionColores(menuConversionColores, path, name); // Actualizar el menú
        });
        //Crear CYMToCMYK
        itemOp4.addActionListener((ActionEvent e) -> {
            setLayout(new BorderLayout());
            CmyToCmyk conv1 = new CmyToCmyk();
            JFrame despimgK = new JFrame("Imagen CMYK agregando K");

            // Crear la barra de menú
            JMenuBar menu = new JMenuBar();

            // Crear menú "Extracción"
            JMenu menuExtraccion = new JMenu("Extracción");
            JMenuItem itemExtraccionColores = new JMenuItem("Extraer Canal K");
            menuExtraccion.add(itemExtraccionColores);

            // Agregar los menús a la barra de menú
            menu.add(menuExtraccion);

            // Establecer la barra de menú en el JFrame despimgK
            despimgK.setJMenuBar(menu);

            // Configurar el JFrame y agregar la imagen
            despimgK.setSize(ancho, alto);
            despimgK.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana1 = new PanelImagen(conv1.CMYtoCMYk(img_clase,5));
            ventana1.setPreferredSize(new Dimension(ancho, alto));
            despimgK.add(ventana1);
            despimgK.pack();
            despimgK.setVisible(true);

            // Botón de información
            itemExtraccionColores.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //extraccion del canal C con la imagen CMY
                    CmyToCmyk conv = new CmyToCmyk();
                    //extraccion del canal K con la imagen CMY
                    JFrame despimgK = new JFrame("Imagen en el canal K - Extracción CMYK");
                    despimgK.setSize(ancho, alto);
                    despimgK.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    PanelImagen ventana4 = new PanelImagen(conv.CMYtoCMYk(img_clase,4));
                    ventana4.setPreferredSize(new Dimension(ancho, alto));
                    despimgK.add(ventana4);
                    despimgK.pack();
                    despimgK.setVisible(true);
                }
            });
        });

        
        // Acción para "Extracción de canales CMY en color"
        itemOp2.addActionListener((ActionEvent e) -> {            
            //objeto para conversion 1-C 2-M 3-Y
            RgbToCmy conv1 = new RgbToCmy();
            
            //Imagen en Cyan
            JFrame despimgC = new JFrame("Imagen cyan - Extracción CMY-color");
            despimgC.setSize(ancho, alto);
            despimgC.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana1 = new PanelImagen(conv1.splitRgbACmy(imagen,1));
            ventana1.setPreferredSize(new Dimension(ancho, alto));
            despimgC.add(ventana1);
            despimgC.pack();
            despimgC.setVisible(true);
            //Imagen en Magenta
            JFrame despimgM = new JFrame("Imagen Magenta - Extracción CMY-color");
            despimgM.setSize(ancho, alto);
            despimgM.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana2 = new PanelImagen(conv1.splitRgbACmy(imagen,2));
            ventana2.setPreferredSize(new Dimension(ancho, alto));
            despimgM.add(ventana2);
            despimgM.pack();
            despimgM.setVisible(true);
            
            //Imagen en Amarillo
            JFrame despimgY = new JFrame("Imagen Amarilla - Extracción CMY-color");
            despimgY.setSize(ancho, alto);
            despimgY.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana3 = new PanelImagen(conv1.splitRgbACmy(imagen,3));
            ventana3.setPreferredSize(new Dimension(ancho, alto));
            despimgY.add(ventana3);
            despimgY.pack();
            despimgY.setVisible(true);
        });

        // Acción para "Extracción de canales CMY en grises"
        itemOp3.addActionListener((ActionEvent e) -> {
            //objeto para conversion 1-C 2-M 3-Y
            RgbToCmy conv1 = new RgbToCmy();
            
            //Imagen en Cyan
            JFrame despimgC = new JFrame("Imagen cyan - Extracción CMY-grises");
            despimgC.setSize(ancho, alto);
            despimgC.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana1 = new PanelImagen(conv1.splitRgbACmy(imagen,4));
            ventana1.setPreferredSize(new Dimension(ancho, alto));
            despimgC.add(ventana1);
            despimgC.pack();
            despimgC.setVisible(true);
            //Imagen en Magenta
            JFrame despimgM = new JFrame("Imagen Magenta - Extracción CMY-grises");
            despimgM.setSize(ancho, alto);
            despimgM.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana2 = new PanelImagen(conv1.splitRgbACmy(imagen,5));
            ventana2.setPreferredSize(new Dimension(ancho, alto));
            despimgM.add(ventana2);
            despimgM.pack();
            despimgM.setVisible(true);
            
            //Imagen en amarillo
            JFrame despimgY = new JFrame("Imagen Amarilla - Extracción CMY-grises");
            despimgY.setSize(ancho, alto);
            despimgY.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PanelImagen ventana3 = new PanelImagen(conv1.splitRgbACmy(imagen,6));
            ventana3.setPreferredSize(new Dimension(ancho, alto));
            despimgY.add(ventana3);
            despimgY.pack();
            despimgY.setVisible(true);
        });
    }

}
    
}
