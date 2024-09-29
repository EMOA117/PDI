package vista;

import color.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.IOException;
import modelo.Histograma;
import modelo.ImageBufferedImage;
import modelo.LectorDeImagen;

public class FramePrincipal extends JFrame {

    private JDesktopPane desktopPane; // Para contener los JInternalFrames
    private BufferedImage image;      // Imagen cargada
    private List<JInternalFrame> framesAbiertos; // Lista para gestionar los JInternalFrames abiertos

    public FramePrincipal() {
        // Configurar el JFrame principal en pantalla completa
        setTitle("Procesamiento de Imágenes");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizamos el JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el JDesktopPane
        desktopPane = new JDesktopPane();
        add(desktopPane);

        // Inicializar la lista de JInternalFrames abiertos
        framesAbiertos = new ArrayList<>();

        // Configurar la barra de menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenu menuProcesar = new JMenu("Procesar Imagen");
        JMenu menuBrilloContraste = new JMenu("Brillo y Contraste");
        JMenu menuHistograma = new JMenu("Histograma");
        JMenu menuConversiones= new JMenu("Conversiones Directas (modelos de color)");

        // Opción de menú para cambiar la imagen
        JMenuItem menuItemCambiar = new JMenuItem("Cambiar Imagen");
        menuItemCambiar.addActionListener(e -> cambiarImagen());

        // Opción para procesar imagen (ejemplo: extraer canal rojo)
        JMenuItem menuItemExtraerRojo = new JMenuItem("Extraer Canal Rojo");
        menuItemExtraerRojo.addActionListener(e -> procesarImagenSeleccionada("rojo"));
        JMenuItem menuItemExtraerAzul = new JMenuItem("Extraer Canal Azul");
        menuItemExtraerAzul.addActionListener(e -> procesarImagenSeleccionada("azul"));
        JMenuItem menuItemExtraerVerde = new JMenuItem("Extraer Canal Verde");
        menuItemExtraerVerde.addActionListener(e -> procesarImagenSeleccionada("verde"));

        // Opción para convertir a escala de grises
        JMenuItem menuItemEscalaGrises = new JMenuItem("Convertir a Escala de Grises");
        menuItemEscalaGrises.addActionListener(e -> procesarImagenSeleccionada("grises"));
        
        // Opción para brillo
        JMenuItem menuItemBrillo = new JMenuItem("Subir/Bajar Brillo");
        menuItemBrillo.addActionListener(e ->procesarImagenSeleccionada("brillo"));
        
        // Opción para brillo
        JMenuItem menuItemContraste = new JMenuItem("Subir/Bajar Contraste");
        menuItemContraste.addActionListener(e ->procesarImagenSeleccionada("contraste"));
        
        //Crear elementos del menú para generar el histograma
        JMenuItem histogramaGris = new JMenuItem("generar histograma en gris");
        histogramaGris.addActionListener(e ->procesarImagenSeleccionada("HistG"));
        JMenuItem histogramaColores = new JMenuItem("generar histograma colores");
        histogramaColores.addActionListener(e ->procesarImagenSeleccionada("Hist"));
        
        JMenuItem itemOp1 = new JMenuItem("RGB a HSV");
        itemOp1.addActionListener(e ->procesarImagenSeleccionada("RGBHSV"));
        JMenuItem itemOp2 = new JMenuItem("RGB a LAB");
        itemOp2.addActionListener(e ->procesarImagenSeleccionada("RGBLAB"));
        JMenuItem itemOp3 = new JMenuItem("RGB a HSI");
        itemOp3.addActionListener(e ->procesarImagenSeleccionada("RGBHSI"));
        JMenuItem itemOp4 = new JMenuItem("RGB a YIQ");
        itemOp4.addActionListener(e ->procesarImagenSeleccionada("RGBYIQ"));
        JMenuItem itemOp8 = new JMenuItem("RGB a YCbCr");
        itemOp8.addActionListener(e ->procesarImagenSeleccionada("RGBYCbCr"));
        JMenuItem itemOp5 = new JMenuItem("RGB a CMY");
        itemOp5.addActionListener(e ->procesarImagenSeleccionada("RGBCMY"));
        JMenuItem itemOp6 = new JMenuItem("Extracción de canales RGB en color");
        itemOp6.addActionListener(e ->procesarImagenSeleccionada("RGBCol"));
        JMenuItem itemOp7 = new JMenuItem("Extracción de canales RGB en grises");
        itemOp7.addActionListener(e ->procesarImagenSeleccionada("RGBGris"));

        // Añadir las opciones a los menús
        menuArchivo.add(menuItemCambiar);
        menuProcesar.add(menuItemExtraerRojo);
        menuProcesar.add(menuItemExtraerAzul);
        menuProcesar.add(menuItemExtraerVerde);
        menuProcesar.add(menuItemEscalaGrises);
        menuBrilloContraste.add(menuItemBrillo);
        menuBrilloContraste.add(menuItemContraste);
        menuHistograma.add(histogramaGris);
        menuHistograma.add(histogramaColores);
        menuConversiones.add(itemOp1);
        menuConversiones.add(itemOp2);
        menuConversiones.add(itemOp3);
        menuConversiones.add(itemOp4);
        menuConversiones.add(itemOp8);
        menuConversiones.add(itemOp5);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuProcesar);
        menuBar.add(menuBrilloContraste);
        menuBar.add(menuHistograma);
        menuBar.add(menuConversiones);
        setJMenuBar(menuBar);
    }

    // Método para cambiar la imagen y reemplazar la actual
    private void cambiarImagen() {
         JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");
        
        // Filtro para solo permitir archivos de imagen
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("jpge", "jpg"));
        int resultado = fileChooser.showOpenDialog(null);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            try {
                image = ImageIO.read(archivoSeleccionado); // Cargar la imagen

                if (image != null) { // Solo si la imagen es válida
                    
                    mostrarImagenEnInternalFrame(image); // Mostrar la imagen original
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Mostrar la imagen cargada en un nuevo JInternalFrame
    private void mostrarImagenEnInternalFrame(BufferedImage img) {
    // Crear un JInternalFrame para mostrar la imagen
    JInternalFrame internalFrame = new JInternalFrame("Imagen", true, true, true, true);
    JLabel imageLabel = new JLabel(new ImageIcon(img));
    internalFrame.add(new JScrollPane(imageLabel));

    // Ajustar el tamaño del JInternalFrame al tamaño de la imagen
    internalFrame.setSize(img.getWidth() + 20, img.getHeight() + 40);
    internalFrame.setVisible(true);
    
    // Crear una barra de menú para el JInternalFrame
    JMenuBar menuBar = new JMenuBar();
    
    // Crear un menú "Archivo"
    JMenu menuArchivo = new JMenu("Archivo");
    
    // Crear un ítem para "Guardar"
    JMenuItem itemGuardar = new JMenuItem("Guardar");
    itemGuardar.addActionListener(e -> {
        // Lógica para guardar la imagen
        JOptionPane.showMessageDialog(internalFrame, "Guardar imagen no implementado.");
    });
    
    
    // Agregar los ítems al menú "Archivo"
    menuArchivo.add(itemGuardar);
    
    // Agregar el menú "Archivo" a la barra de menú
    menuBar.add(menuArchivo);
    
    // Establecer la barra de menú en el JInternalFrame
    internalFrame.setJMenuBar(menuBar);
    
    // Agregar el nuevo JInternalFrame al JDesktopPane
    desktopPane.add(internalFrame);
    framesAbiertos.add(internalFrame); // Guardar en la lista de frames abiertos
    
    try {
        internalFrame.setSelected(true); // Seleccionar el nuevo JInternalFrame
    } catch (java.beans.PropertyVetoException e) {
        e.printStackTrace();
    }
}


    // Procesar la imagen seleccionada en el JInternalFrame activo
    private void procesarImagenSeleccionada(String operacion) {
        JInternalFrame frameSeleccionado = desktopPane.getSelectedFrame();
        
        if (frameSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una imagen primero", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener la imagen actual mostrada en el frame seleccionado
        JLabel label = (JLabel)((JScrollPane) frameSeleccionado.getContentPane().getComponent(0)).getViewport().getView();
        ImageIcon icon = (ImageIcon) label.getIcon();
        BufferedImage imagenActual = (BufferedImage) icon.getImage();

        BufferedImage imagenProcesada = null;
        
        // Realizar la operación de procesamiento
        if (operacion.equals("rojo")) {
            imagenProcesada = extraerCanal(imagenActual,1); // Extraer el canal rojo
        } else if (operacion.equals("verde")) {
            imagenProcesada = extraerCanal(imagenActual,2); // Convertir a escala de grises
        } else if (operacion.equals("azul")) {
            imagenProcesada = extraerCanal(imagenActual,3);// Convertir a escala de grises
        } else if (operacion.equals("grises")) {
            imagenProcesada = extraerCanal(imagenActual,5); 
        }
         else if (operacion.equals("brillo")) {
            imagenProcesada = ajustarBrillo(imagenActual); 
        }else if (operacion.equals("contraste")) {
            imagenProcesada = ajustarContraste(imagenActual);
        }else if (operacion.equals("HistG")) {
            generarhistogramaGris(icon.getImage());
        }else if (operacion.equals("Hist")) {
            generarHistogramaRGB(icon.getImage());
        }
        else if (operacion.equals("RGBHSV")) {
    // Creación de objeto para conversión
    RgbToHsv conv = new RgbToHsv();
    
    // Obtener las tres imágenes (H, S, V)
    Image imgH = conv.convertirImg(icon.getImage(), 1);
    Image imgS = conv.convertirImg(icon.getImage(), 2);
    Image imgV = conv.convertirImg(icon.getImage(), 3);

    // Mostrar las imágenes en JInternalFrame
    mostrarImagenEnInternalFrame(imgH, "Componente H", desktopPane, 1, conv.getImgConv());
    mostrarImagenEnInternalFrame(imgS, "Componente S", desktopPane, 1,conv.getImgConv());
    mostrarImagenEnInternalFrame(imgV, "Componente V", desktopPane, 1, conv.getImgConv());
}else if (operacion.equals("RGBLAB")) {
    // Creación de objeto para conversión
    RgbToLab con = new RgbToLab(icon.getImage());
    
    // Obtener las tres imágenes (L, A, B)
    Image imgL = con.ConvertirRGBaLab(1);
    Image imgA = con.ConvertirRGBaLab(2);
    Image imgB = con.ConvertirRGBaLab(3);

    // Mostrar las imágenes en JInternalFrame
    mostrarImagenEnInternalFrame2(imgL, "Componente L", desktopPane, con.getImgLab());
    mostrarImagenEnInternalFrame2(imgA, "Componente A", desktopPane, con.getImgLab());
    mostrarImagenEnInternalFrame2(imgB, "Componente B", desktopPane, con.getImgLab());
}else if (operacion.equals("RGBHSI")) {
    // Creación de objeto para conversión
    RgbToHsi conv = new RgbToHsi();
    
    // Obtener las tres imágenes (H, S, I)
    Image imgH = conv.convertirImg(icon.getImage(),1);
    Image imgS = conv.convertirImg(icon.getImage(),2);
    Image imgI = conv.convertirImg(icon.getImage(),3);

    // Mostrar las imágenes en JInternalFrame
    mostrarImagenEnInternalFrame3(imgH, "Componente H", desktopPane, conv.getImgconv());
    mostrarImagenEnInternalFrame3(imgS, "Componente S", desktopPane, conv.getImgconv());
    mostrarImagenEnInternalFrame3(imgI, "Componente I", desktopPane, conv.getImgconv());
}else if (operacion.equals("RGBYIQ")) {
    // Creación de objeto para conversión
    RgbToYiq conv = new RgbToYiq();
    
    // Obtener las tres imágenes (Y, I, Q)
    Image imgY = conv.convertirRgbAYiq32(icon.getImage(),1);
    Image imgI = conv.convertirRgbAYiq32(icon.getImage(),2);
    Image imgQ = conv.convertirRgbAYiq32(icon.getImage(),3);

    // Mostrar las imágenes en JInternalFrame
    mostrarImagenEnInternalFrame4(imgY, "Componente Y", desktopPane, conv.getConjuntoYIQ());
    mostrarImagenEnInternalFrame4(imgI, "Componente I", desktopPane, conv.getConjuntoYIQ());
    mostrarImagenEnInternalFrame4(imgQ, "Componente Q", desktopPane, conv.getConjuntoYIQ());
}else if (operacion.equals("RGBYCbCr")) {
    // Creación de objeto para conversión
    RgbToYCbCr conv = new RgbToYCbCr();
    
    // Obtener las tres imágenes (Y, Cb, Cr)
    Image imgY = conv.convertirImg(icon.getImage(),1);
    Image imgCb = conv.convertirImg(icon.getImage(),2);
    Image imgCr = conv.convertirImg(icon.getImage(),3);

    // Mostrar las imágenes en JInternalFrame
    mostrarImagenEnInternalFrame(imgY, "Componente Y", desktopPane,0, null);
    mostrarImagenEnInternalFrame(imgCb, "Componente Cb", desktopPane,0, null);
    mostrarImagenEnInternalFrame(imgCr, "Componente Cr", desktopPane,0, null);
}else if (operacion.equals("RGBCMY")) {
    // Creación de objeto para conversión
    RgbToCmy conv = new RgbToCmy();
    
    // Obtener la imagen (C, M, Y)
    Image img = conv.convertirRgbACmy(icon.getImage());

    // Mostrar las imágenes en JInternalFrame
    mostrarImagenEnInternalFrame5(img, "Imagen en CMY", desktopPane);
}

        if (imagenProcesada != null) {
            mostrarImagenEnInternalFrame(imagenProcesada); // Mostrar la imagen procesada en un nuevo frame
        }
    }
    

    // Método de ejemplo para extraer el canal rojo
private BufferedImage extraerCanal(BufferedImage img, int opcion) {
    ImageBufferedImage buffered = new ImageBufferedImage();
    Image imagenN = buffered.getImage(img, opcion, 1);

    // Crear un BufferedImage a partir de la imagen resultante
    BufferedImage imagenBuffered = new BufferedImage(imagenN.getWidth(null), imagenN.getHeight(null), BufferedImage.TYPE_INT_RGB);
    
    // Dibujar la imagen sobre el BufferedImage
    Graphics g = imagenBuffered.getGraphics();
    g.drawImage(imagenN, 0, 0, null);
    g.dispose();

    return imagenBuffered;
}

    

    // Método de ejemplo para convertir la imagen a escala de grises
    private BufferedImage convertirEscalaGrises(BufferedImage img) {
        BufferedImage resultado = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int pixel = img.getRGB(x, y);
                int rojo = (pixel >> 16) & 0xFF;
                int verde = (pixel >> 8) & 0xFF;
                int azul = pixel & 0xFF;
                int gris = (rojo + verde + azul) / 3;
                int nuevoPixel = (gris << 16) | (gris << 8) | gris;
                resultado.setRGB(x, y, nuevoPixel);
            }
        }
        return resultado;
    }
    
    
    // Método para ajustar el brillo
    private BufferedImage ajustarBrillo(BufferedImage img) {
        ImageBufferedImage buffered = new ImageBufferedImage();
        BufferedImage imagenBuffered  = null;

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
            imagenBuffered =  cambiarContrasteBrillo(9, valorBrillo, img);
            // Crear un BufferedImage a partir de la imagen resultante
            
        }
        return imagenBuffered;
    }

        // Método para ajustar el contraste
        private BufferedImage ajustarContraste(BufferedImage img) {
            ImageBufferedImage buffered = new ImageBufferedImage();
        BufferedImage imagenBuffered  = null;
            
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
        imagenBuffered =  cambiarContrasteBrillo(10, valorSlider, img);

        // Usa el valor del contraste para procesar la imagen
        System.out.println("Contraste seleccionado: " + valorContraste);
    }
        return imagenBuffered;

    }
    
    
    private BufferedImage cambiarContrasteBrillo(int canal,int escalar,BufferedImage img ) {
        
    ImageBufferedImage buffered = new ImageBufferedImage();
    Image imagenN = buffered.getImage(img, canal, escalar);

    // Crear un BufferedImage a partir de la imagen resultante
    BufferedImage imagenBuffered = new BufferedImage(imagenN.getWidth(null), imagenN.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
    
    // Dibujar la imagen sobre el BufferedImage
    Graphics g = imagenBuffered.getGraphics();
    g.drawImage(imagenN, 0, 0, null);
    g.dispose();

    return imagenBuffered;
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
    
    private void mostrarImagenEnInternalFrame(Image img, String title, JDesktopPane desktopPane, int opcion, Hsv [] matriz) {
    // Crear un JInternalFrame para mostrar la imagen
    JInternalFrame internalFrame = new JInternalFrame(title, false, true, false, true);
    JLabel imageLabel = new JLabel(new ImageIcon(img));
    internalFrame.add(new JScrollPane(imageLabel));
    
     // Ajustar el tamaño del JInternalFrame al tamaño de la imagen
    internalFrame.setSize(img.getWidth(null) + 20, img.getHeight(null) + 40);
    internalFrame.setVisible(true);
    if(opcion==1){
    // Crear la barra de menú para el JInternalFrame
    JMenuBar menuBar = new JMenuBar();
    JMenu menuExtraccion = new JMenu("Conversion RGB");
            JMenuItem itemConversionRGB = new JMenuItem("Convertir a HSV a RGB");
            menuExtraccion.add(itemConversionRGB);
            menuBar.add(menuExtraccion);

            
            // Establecer la barra de menú en el JInternalFrame
            internalFrame.setJMenuBar(menuBar);

           

            // Acción para "Convertir a RGB"
            itemConversionRGB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Conversión de HSV a RGB
                    HsvToRgb con = new HsvToRgb();
                    // Aquí podrías aplicar la conversión e implementar la lógica para mostrar el resultado.
                    System.out.println("Convertir a RGB de HSV para: " + title);
                    Image imagen= con.convertirHsiToRgb(matriz,img.getWidth(null), img.getHeight(null));
                    mostrarImagenEnInternalFrame(imagen, "Conversion de RGB a HSV", desktopPane, 0, null);
                }
            });
    
    }


            // Añadir el nuevo JInternalFrame al JDesktopPane
            desktopPane.add(internalFrame);
    

    // Seleccionar el nuevo JInternalFrame
    try {
        internalFrame.setSelected(true);
    } catch (java.beans.PropertyVetoException e) {
        e.printStackTrace();
    }
}
    
    private void mostrarImagenEnInternalFrame2(Image img, String title, JDesktopPane desktopPane,  Lab [][] matriz) {
    // Crear un JInternalFrame para mostrar la imagen
    JInternalFrame internalFrame = new JInternalFrame(title, false, true, false, true);
    JLabel imageLabel = new JLabel(new ImageIcon(img));
    internalFrame.add(new JScrollPane(imageLabel));

    // Ajustar el tamaño del JInternalFrame al tamaño de la imagen
    internalFrame.setSize(img.getWidth(null) + 20, img.getHeight(null) + 40);
    internalFrame.setVisible(true);
    
    // Crear la barra de menú para el JInternalFrame
    JMenuBar menuBar = new JMenuBar();
    JMenu menuExtraccion = new JMenu("Conversion RGB");
            JMenuItem itemConversionRGB = new JMenuItem("Convertir a LAB a RGB");
            menuExtraccion.add(itemConversionRGB);
            menuBar.add(menuExtraccion);

            // Establecer la barra de menú en el JInternalFrame
            internalFrame.setJMenuBar(menuBar);

            // Acción para "Convertir a RGB"
            itemConversionRGB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Conversión de LAB a RGB
                    LabToRgb conv = new LabToRgb();
                    // Aquí podrías aplicar la conversión e implementar la lógica para mostrar el resultado.
                    System.out.println("Convertir a RGB de LAB para: " + title);
                    Image imagen= conv.convertirLabtoRgb(matriz,img.getWidth(null), img.getHeight(null));
                    mostrarImagenEnInternalFrame(imagen, "Conversion de LAB a RGB", desktopPane, 0, null);
                }
            });

            // Añadir el nuevo JInternalFrame al JDesktopPane
            desktopPane.add(internalFrame);
    // Seleccionar el nuevo JInternalFrame
    try {
        internalFrame.setSelected(true);
    } catch (java.beans.PropertyVetoException e) {
        e.printStackTrace();
    }
}

    private void mostrarImagenEnInternalFrame3(Image img, String title, JDesktopPane desktopPane,  Hsi [] matriz) {
    // Crear un JInternalFrame para mostrar la imagen
    JInternalFrame internalFrame = new JInternalFrame(title, false, true, false, true);
    JLabel imageLabel = new JLabel(new ImageIcon(img));
    internalFrame.add(new JScrollPane(imageLabel));

    // Ajustar el tamaño del JInternalFrame al tamaño de la imagen
    internalFrame.setSize(img.getWidth(null) + 20, img.getHeight(null) + 40);
    internalFrame.setVisible(true);
    
    // Crear la barra de menú para el JInternalFrame
    JMenuBar menuBar = new JMenuBar();
    JMenu menuExtraccion = new JMenu("Conversion RGB");
            JMenuItem itemConversionRGB = new JMenuItem("Convertir a HSI a RGB");
            menuExtraccion.add(itemConversionRGB);
            menuBar.add(menuExtraccion);

            // Establecer la barra de menú en el JInternalFrame
            internalFrame.setJMenuBar(menuBar);

            // Acción para "Convertir a RGB"
            itemConversionRGB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Conversión de HSI a RGB
                    HsiToRgb con = new HsiToRgb();
                    // Aquí podrías aplicar la conversión e implementar la lógica para mostrar el resultado.
                    System.out.println("Convertir a HSI de LAB para: " + title);
                    Image imagen= con.convertirHsiToRgb(matriz,img.getWidth(null), img.getHeight(null));
                    mostrarImagenEnInternalFrame(imagen, "Conversion de HSI a RGB", desktopPane, 0, null);
                }
            });

            // Añadir el nuevo JInternalFrame al JDesktopPane
            desktopPane.add(internalFrame);
    // Seleccionar el nuevo JInternalFrame
    try {
        internalFrame.setSelected(true);
    } catch (java.beans.PropertyVetoException e) {
        e.printStackTrace();
    }
}
 
     private void mostrarImagenEnInternalFrame4(Image img, String title, JDesktopPane desktopPane,  double [][] matriz) {
    // Crear un JInternalFrame para mostrar la imagen
    JInternalFrame internalFrame = new JInternalFrame(title, false, true, false, true);
    JLabel imageLabel = new JLabel(new ImageIcon(img));
    internalFrame.add(new JScrollPane(imageLabel));

    // Ajustar el tamaño del JInternalFrame al tamaño de la imagen
    internalFrame.setSize(img.getWidth(null) + 20, img.getHeight(null) + 40);
    internalFrame.setVisible(true);
    
    // Crear la barra de menú para el JInternalFrame
    JMenuBar menuBar = new JMenuBar();
    JMenu menuExtraccion = new JMenu("Conversion RGB");
            JMenuItem itemConversionRGB = new JMenuItem("Convertir a YIQ a RGB");
            menuExtraccion.add(itemConversionRGB);
            menuBar.add(menuExtraccion);

            // Establecer la barra de menú en el JInternalFrame
            internalFrame.setJMenuBar(menuBar);

            // Acción para "Convertir a RGB"
            itemConversionRGB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Conversión de HSI a RGB
                    YiqToRgb con = new YiqToRgb();
                    // Aquí podrías aplicar la conversión e implementar la lógica para mostrar el resultado.
                    System.out.println("Convertir a YIQ de RGB para: " + title);
                    Image imagen= con.YiqToRgb(matriz,img.getWidth(null), img.getHeight(null));
                    mostrarImagenEnInternalFrame(imagen, "Conversion de YIQ a RGB", desktopPane, 0, null);
                }
            });

            // Añadir el nuevo JInternalFrame al JDesktopPane
            desktopPane.add(internalFrame);
    // Seleccionar el nuevo JInternalFrame
    try {
        internalFrame.setSelected(true);
    } catch (java.beans.PropertyVetoException e) {
        e.printStackTrace();
    }
}
     
     private void mostrarImagenEnInternalFrame5(Image img, String title, JDesktopPane desktopPane) {
    // Crear un JInternalFrame para mostrar la imagen
    JInternalFrame internalFrame = new JInternalFrame(title, false, true, false, true);
    JLabel imageLabel = new JLabel(new ImageIcon(img));
    internalFrame.add(new JScrollPane(imageLabel));

    // Ajustar el tamaño del JInternalFrame al tamaño de la imagen
    internalFrame.setSize(img.getWidth(null) + 20, img.getHeight(null) + 40);
    internalFrame.setVisible(true);
    
    // Crear la barra de menú para el JInternalFrame
    JMenuBar menuBar = new JMenuBar();
    JMenu menuExtraccion = new JMenu("Conversion");
            JMenuItem itemOp1 = new JMenuItem("CMY a RGB");
        JMenuItem itemOp4 = new JMenuItem("CMY a CMYK");
        JMenuItem itemOp2 = new JMenuItem("Extracción de canales CMY en color");
        JMenuItem itemOp3 = new JMenuItem("Extracción de canales CMY en grises");
            menuExtraccion.add(itemOp1);
            menuExtraccion.add(itemOp4);
            menuExtraccion.add(itemOp2);
            menuExtraccion.add(itemOp3);
            
            menuBar.add(menuExtraccion);

            // Establecer la barra de menú en el JInternalFrame
            internalFrame.setJMenuBar(menuBar);

            // Acción para "Convertir a RGB"
            itemOp1.addActionListener((ActionEvent e) -> {
            CmyToRgb conv1 = new CmyToRgb();
             Image imga = conv1.convertirCmyARgb(img);
                mostrarImagenEnInternalFrame(image);
             });
             //Crear CYMToCMYK
        itemOp4.addActionListener((ActionEvent e) -> {
            CmyToCmyk conv1 = new CmyToCmyk();
            Image imga = conv1.CMYtoCMYk(img, 5);
            mostrarImagenEnInternalFrame6(imga, "Imagen CMYK", desktopPane);
        });
        
        itemOp2.addActionListener((ActionEvent e) -> {
            RgbToCmy conv1 = new RgbToCmy();
            Image img1 = conv1.splitRgbACmy(img,1);
            Image img2 = conv1.splitRgbACmy(img,2);
            Image img3 = conv1.splitRgbACmy(img,3);
            mostrarImagenEnInternalFrame(img1, "Imagen cyan - Extracción CMY-color", desktopPane,0,null);
            mostrarImagenEnInternalFrame(img2, "Imagen magenta - Extracción CMY-color", desktopPane,0,null);
            mostrarImagenEnInternalFrame(img3, "Imagen amarilla - Extracción CMY-color", desktopPane,0,null);
        });
        
        itemOp3.addActionListener((ActionEvent e) -> {
            RgbToCmy conv1 = new RgbToCmy();
            Image img1 = conv1.splitRgbACmy(img,4);
            Image img2 = conv1.splitRgbACmy(img,5);
            Image img3 = conv1.splitRgbACmy(img,6);
            mostrarImagenEnInternalFrame(img1, "Imagen cyan - Extracción CMY-grises", desktopPane,0,null);
            mostrarImagenEnInternalFrame(img2, "Imagen magenta - Extracción CMY-grises", desktopPane,0,null);
            mostrarImagenEnInternalFrame(img3, "Imagen amarilla - Extracción CMY-grises", desktopPane,0,null);
        });
        
        
            // Añadir el nuevo JInternalFrame al JDesktopPane
            desktopPane.add(internalFrame);
    // Seleccionar el nuevo JInternalFrame
    try {
        internalFrame.setSelected(true);
    } catch (java.beans.PropertyVetoException e) {
        e.printStackTrace();
    }
}
     
   private void mostrarImagenEnInternalFrame6(Image img, String title, JDesktopPane desktopPane) {
    // Crear un JInternalFrame para mostrar la imagen
    JInternalFrame internalFrame = new JInternalFrame(title, false, true, false, true);
    JLabel imageLabel = new JLabel(new ImageIcon(img));
    internalFrame.add(new JScrollPane(imageLabel));

    // Ajustar el tamaño del JInternalFrame al tamaño de la imagen
    internalFrame.setSize(img.getWidth(null) + 20, img.getHeight(null) + 40);
    internalFrame.setVisible(true);
    
    // Crear la barra de menú para el JInternalFrame
    JMenuBar menuBar = new JMenuBar();
    JMenu menuExtraccion = new JMenu("Extracción");
            JMenuItem itemExtraccionColores = new JMenuItem("Extraer Canal K");
            menuExtraccion.add(itemExtraccionColores);
            menuBar.add(menuExtraccion);

            // Establecer la barra de menú en el JInternalFrame
            internalFrame.setJMenuBar(menuBar);

            // Acción para extraer canal
            itemExtraccionColores.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setLayout(new BorderLayout());
                CmyToCmyk conv1 = new CmyToCmyk();
                Image imga = conv1.CMYtoCMYk(img, 4);
                mostrarImagenEnInternalFrame(imga, "Canal K", desktopPane, 0, null);
                }
            });

            // Añadir el nuevo JInternalFrame al JDesktopPane
            desktopPane.add(internalFrame);
    // Seleccionar el nuevo JInternalFrame
    try {
        internalFrame.setSelected(true);
    } catch (java.beans.PropertyVetoException e) {
        e.printStackTrace();
    }
}  
}

