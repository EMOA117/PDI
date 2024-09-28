package vista;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.IOException;
import modelo.ImageBufferedImage;

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

        // Añadir las opciones a los menús
        menuArchivo.add(menuItemCambiar);
        menuProcesar.add(menuItemExtraerRojo);
        menuProcesar.add(menuItemExtraerAzul);
        menuProcesar.add(menuItemExtraerVerde);
        menuProcesar.add(menuItemEscalaGrises);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuProcesar);
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
        JInternalFrame internalFrame = new JInternalFrame("Imagen", true, true, true, true);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        internalFrame.add(new JScrollPane(imageLabel));

        // Ajustar el tamaño del JInternalFrame al tamaño de la imagen
        internalFrame.setSize(img.getWidth() + 20, img.getHeight() + 40); 
        internalFrame.setVisible(true);
        desktopPane.add(internalFrame); // Añadir el nuevo JInternalFrame al JDesktopPane
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
            imagenProcesada = extraerCanal(imagenActual,5); // Convertir a escala de grises
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

    
    // Método para cambiar la imagen dentro del panel
    private void cambiarCanalColor(int canal,String path, String name ) {
       /*  LectorDeImagen lector = new LectorDeImagen(path, name);
        lector.leerBufferedImagen();
        ImageBufferedImage buffered = new ImageBufferedImage();
        // Cerrar el Frame actual
    this.dispose();
        FrameImagen frame = new FrameImagen(
                                buffered.getImage(
                                    lector.getBufferedImagen(), canal, 1),path, name);
        // Maximizar el frame al tamaño de la pantalla
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

                // Si deseas ocultar la barra de título, puedes usar esta línea:
                // frame.setUndecorated(true);

                // Hacer visible el frame
                frame.setVisible(true);*/
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
    private void ajustarBrillo(BufferedImage img) {
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
            cambiarContrasteBrillo(9, img);
            
            // Implementa la lógica para ajustar el brillo con el valor dado
            // Ejemplo: panel.ajustarBrillo(valorBrillo);
        }
    }

        // Método para ajustar el contraste
        private void ajustarContraste(BufferedImage img) {
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
        cambiarContrasteBrillo( valorSlider, img);

        // Usa el valor del contraste para procesar la imagen
        System.out.println("Contraste seleccionado: " + valorContraste);
    }

    }
    
    
    private BufferedImage cambiarContrasteBrillo(int escalar,BufferedImage img ) {
        
    ImageBufferedImage buffered = new ImageBufferedImage();
    Image imagenN = buffered.getImage(img, 4, escalar);

    // Crear un BufferedImage a partir de la imagen resultante
    BufferedImage imagenBuffered = new BufferedImage(imagenN.getWidth(null), imagenN.getHeight(null), BufferedImage.TYPE_INT_RGB);
    
    // Dibujar la imagen sobre el BufferedImage
    Graphics g = imagenBuffered.getGraphics();
    g.drawImage(imagenN, 0, 0, null);
    g.dispose();

    return imagenBuffered;
         /*LectorDeImagen lector = new LectorDeImagen(path, name);
        lector.leerBufferedImagen();
        ImageBufferedImage buffered = new ImageBufferedImage();
        // Cerrar el Frame actual
    this.dispose();
        FrameImagen frame = new FrameImagen(
                                buffered.getImage(
                                    lector.getBufferedImagen(), canal, escalar), path, name);
        // Maximizar el frame al tamaño de la pantalla
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

                // Si deseas ocultar la barra de título, puedes usar esta línea:
                // frame.setUndecorated(true);

                // Hacer visible el frame
                frame.setVisible(true);*/
    }

}
