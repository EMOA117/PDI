package vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import modelo.Histograma;
import modelo.OperacionImg;

public class FrameOperaciones extends JFrame {
    private SmallPanel panel1, panel2, panel3; 
    private JPanel histogramPanel1, histogramPanel2, histogramPanel3; 
    private Image img1;
    private Image img2;
    private Image img3;
    private JComboBox<String> comboBox;
    private String seleccion;
    private FramePrincipal framePrincipal;

    public FrameOperaciones(int operacion, FramePrincipal framePrincipal){
       this.framePrincipal = framePrincipal;
        // Inicializar el JComboBox
        comboBox = new JComboBox<>();
        Panel_principal(operacion);
    }
    
    private void Panel_principal(int operacion) {
    comboBox.setFont(new Font("Arial", Font.BOLD, 14));   // Cambiar fuente
    comboBox.setBackground(Color.LIGHT_GRAY);            // Color de fondo
    comboBox.setForeground(Color.BLACK);                 // Color del texto
    comboBox.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2)); // Borde azul
    comboBox.setPreferredSize(new Dimension(200, 30));   // Tamaño del JComboBox
    comboBox.setToolTipText("Selecciona una opción de operación");   // Tooltip
    setLayout(new BorderLayout());

    setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizamos el JFrame

    // Crear la barra de menú
    JMenuBar menuBar = new JMenuBar();

    // Crear un menú "Opciones"
    JMenu menuOpciones = new JMenu("Opciones");

    // Crear ítem del menú "Devolver Imagen"
    JMenuItem devolverImagenItem = new JMenuItem("Devolver Imagen Procesada");

    // Añadir ActionListener al ítem de menú para devolver la imagen procesada
    devolverImagenItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (img3 != null) {
                // Método que devolverá la imagen procesada al Frame principal
                devolverImagenAlFramePrincipal();
            } else {
                JOptionPane.showMessageDialog(null, "No se ha generado ninguna imagen", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    // Añadir el ítem al menú "Opciones"
    menuOpciones.add(devolverImagenItem);

    // Añadir el menú "Opciones" a la barra de menú
    menuBar.add(menuOpciones);

    // Establecer la barra de menú en el JFrame
    setJMenuBar(menuBar);

    // Crear y configurar el panel principal
    JPanel mainPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    // Añadir el JComboBox en la parte superior del JFrame
    add(comboBox, BorderLayout.NORTH);

    // Inicializar los paneles de imágenes
    panel1 = new SmallPanel(img1);
    panel2 = new SmallPanel(img2);
    panel3 = new SmallPanel(img3);
    Color color = new Color(0, 0, 0);
    Histograma his1 = new Histograma(img1);
    Histograma his2 = new Histograma(img2);
    Histograma his3 = new Histograma(img3);

    // Crear los histogramas
    histogramPanel1 = new PanelHistograma(his1.getPi(), color.GRAY, "Histograma");
    histogramPanel2 = new PanelHistograma(his2.getPi(), color.GRAY, "Histograma");
    histogramPanel3 = new PanelHistograma(his3.getPi(), color.GRAY, "Histograma");

    // Añadir los paneles de imágenes
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1.0;
    gbc.weighty = 0.3; // Peso menor para los paneles de imágenes
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridheight = 1;
    mainPanel.add(panel1, gbc);

    gbc.gridx = 1;
    mainPanel.add(panel2, gbc);

    gbc.gridx = 2;
    mainPanel.add(panel3, gbc);

    // Añadir los histogramas con mayor peso
    gbc.gridy = 1;
    gbc.gridx = 0;
    gbc.weighty = 0.7; // Peso mayor para los histogramas
    mainPanel.add(histogramPanel1, gbc);

    gbc.gridx = 1;
    mainPanel.add(histogramPanel2, gbc);

    gbc.gridx = 2;
    mainPanel.add(histogramPanel3, gbc);

    // Añadir los botones debajo de los histogramas
    gbc.gridy = 2;
    gbc.weighty = 0.1;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;

    gbc.gridx = 0;
    JButton changeImageButton1 = new JButton("Cargar imagen 1");
    mainPanel.add(changeImageButton1, gbc);

    gbc.gridx = 1;
    JButton changeImageButton2 = new JButton("Cargar imagen 2");
    mainPanel.add(changeImageButton2, gbc);

    gbc.gridx = 2;
    JButton EjecutarOperacion = new JButton("Ejecutar");
    mainPanel.add(EjecutarOperacion, gbc);

    EjecutarOperacion.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (img1 == null || img2 == null || seleccion == null) {
                JOptionPane.showMessageDialog(null, "Selecciona ambas imágenes para hacer la operación\n y un operador");
            } else {
                OperacionImg op = new OperacionImg(img1, img2, seleccion);
                switch (operacion) {
                    case 1:
                        PonerRes(mainPanel, op.OperacionesAritmeticas());
                        break;
                    case 2:
                        PonerRes(mainPanel, op.OperacionesLogicas());
                        break;
                    case 3:
                        PonerRes(mainPanel, op.OperacionesRelacionales());
                        break;
                }
            }
        }
    });

    // Añadir el panel principal al frame
    add(mainPanel, BorderLayout.CENTER);

    // Función de los botones
    changeImageButton1.addActionListener(e -> changeImage(1, mainPanel));
    changeImageButton2.addActionListener(e -> changeImage(2, mainPanel));

    // Configurar el JComboBox y llenar según la operación
    Operaciones(operacion);

    // Configurar la ventana
    this.setSize(1250, 800);
    this.setVisible(true);
}


    private void Operaciones(int selector) {
        // Limpiar el JComboBox antes de llenarlo
        comboBox.removeAllItems();
        switch (selector) {
            case 1:
                setTitle("Operaciones aritméticas");
                String[] opcionesAritmeticas = {"Division(/)", "Multiplicacion(*)", "Suma(+)", "Resta(-)"};
                for (String opcion : opcionesAritmeticas) {
                    comboBox.addItem(opcion);
                }
                
                 // Agregar ActionListener al comboBox
                comboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        seleccion = (String) comboBox.getSelectedItem();
                    }
                });
                
                break;
            case 2:
                setTitle("Operaciones lógicas");
                String[] opcionesLogicas = {"AND", "OR", "NOT"};
                for (String opcion : opcionesLogicas) {
                    comboBox.addItem(opcion);
                }
                 // Agregar ActionListener al comboBox
                comboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        seleccion = (String) comboBox.getSelectedItem();
                    }
                });
                break;
            case 3:
                setTitle("Operaciones relacionales");
                String[] opcionesRelacionales = {"Menor que (<)", "Menor o igual que (<=)", "Mayor que (>)", "Mayor o igual que (>=)", "Igual que (==)", "Diferente que (!=)"};
                for (String opcion : opcionesRelacionales) {
                    comboBox.addItem(opcion);
                }
                 // Agregar ActionListener al comboBox
                comboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        seleccion = (String) comboBox.getSelectedItem();
                    }
                });
                break;
        }
    }
    private void PonerRes(JPanel mainPanel,Image newImage){
        Color color = new Color(0, 0, 0);
        SmallPanel panel;
        JPanel histogramPanel;
        GridBagConstraints gbc = new GridBagConstraints();


        img3 = newImage;
        panel = panel3;
                    Histograma his = new Histograma(img3);
                    his.ejecutarTodo(4);
        histogramPanel3 = new PanelHistograma(his.getPi(), color.GRAY, "Histograma");
        histogramPanel = histogramPanel3;
        gbc.gridx = 2;
        
        panel.cambiarImg(newImage);

        // Remover el histograma antiguo
        Component[] components = mainPanel.getComponents();
        for (int i = 0; i < components.length; i++) {
            Component comp = components[i];
            if (comp instanceof JPanel && 
                ((GridBagLayout)mainPanel.getLayout()).getConstraints(comp).gridx == gbc.gridx && 
                ((GridBagLayout)mainPanel.getLayout()).getConstraints(comp).gridy == 1) {
                mainPanel.remove(comp);
                break;
            }
        }

        // Añadir el nuevo histograma
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(histogramPanel, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void changeImage(int imageNumber, JPanel mainPanel) {
        Image newImage = null;
        Color color = new Color(0, 0, 0);
        SmallPanel panel;
        JPanel histogramPanel;
        GridBagConstraints gbc = new GridBagConstraints();
        if (imageNumber == 1) {
            cambiarImagen1();
            Histograma his = new Histograma(img1);
            his.ejecutarTodo(4);
            panel = panel1;
            histogramPanel1 = new PanelHistograma(his.getPi(), color.GRAY, "Histograma");
            histogramPanel = histogramPanel1;
            gbc.gridx = 0;
            panel.cambiarImg(img1);
        }else{
            cambiarImagen2();
            Histograma his = new Histograma(img2);
            his.ejecutarTodo(4);
        panel = panel2;
            histogramPanel2 = new PanelHistograma(his.getPi(), color.GRAY, "Histograma");
            histogramPanel = histogramPanel2;
            gbc.gridx = 1;
            panel.cambiarImg(img2);
        }
        // Remover el histograma antiguo
        Component[] components = mainPanel.getComponents();
        for (int i = 0; i < components.length; i++) {
            Component comp = components[i];
            if (comp instanceof JPanel && 
                ((GridBagLayout)mainPanel.getLayout()).getConstraints(comp).gridx == gbc.gridx && 
                ((GridBagLayout)mainPanel.getLayout()).getConstraints(comp).gridy == 1) {
                mainPanel.remove(comp);
                break;
            }
        }

        // Añadir el nuevo histograma
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(histogramPanel, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    
    // Método para cambiar la imagen y reemplazar la actual
    private void cambiarImagen1() {
         JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");
        
        // Filtro para solo permitir archivos de imagen
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("jpge", "jpg"));
        int resultado = fileChooser.showOpenDialog(null);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            try {
                img1 = ImageIO.read(archivoSeleccionado); // Cargar la imagen

                if (img1 != null) { // Solo si la imagen es válida
                    System.out.println("cargando imagen");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }
    
     // Método para cambiar la imagen y reemplazar la actual
    private void cambiarImagen2() {
         JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");
        
        // Filtro para solo permitir archivos de imagen
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("jpge", "jpg"));
        int resultado = fileChooser.showOpenDialog(null);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            try {
                img2 = ImageIO.read(archivoSeleccionado); // Cargar la imagen

                if (img2 != null) { // Solo si la imagen es válida
                    System.out.println("cargando imagen");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }
    
    // Método para devolver la imagen procesada al Frame principal
    private void devolverImagenAlFramePrincipal() {
       if (img3 != null) {
        // Convertir Image a BufferedImage
        BufferedImage imagenBuffered = framePrincipal.toBufferedImage(img3);
        
        // Pasar la imagen convertida al Frame principal
        framePrincipal.mostrarImagenEnInternalFrameOp(imagenBuffered);
        dispose();  // Cierra el Frame de operaciones
    } else {
        JOptionPane.showMessageDialog(this, "No se ha generado ninguna imagen", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
    
    private BufferedImage convertirImagenAImagenBuffered(Image img) {
    if (img instanceof BufferedImage) {
        return (BufferedImage) img; // Si ya es BufferedImage, simplemente lo retornas
    }

    // Crear un nuevo BufferedImage y dibujar la imagen dentro
    BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    Graphics2D bGr = bufferedImage.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();

    return bufferedImage;
}

    
    
    
}
