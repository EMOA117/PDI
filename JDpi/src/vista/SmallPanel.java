package vista;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Desktop_2.0
 */
public class SmallPanel extends JPanel {

    private JLabel imageLabel; // Etiqueta para mostrar la imagen

    public SmallPanel(Image imagen) {
        initComponents(imagen);
    }

    private void initComponents(Image img) {
        this.setLayout(new BorderLayout());
        
        // Crear un JLabel para mostrar la imagen
        imageLabel = new JLabel();
        
        // Establecer alineación vertical y horizontal en el centro
        imageLabel.setHorizontalAlignment(JLabel.CENTER); // Centrar horizontalmente
        imageLabel.setVerticalAlignment(JLabel.CENTER);   // Centrar verticalmente
        
        this.add(imageLabel, BorderLayout.CENTER);

        // Añadir un borde de 10px

        // Redimensionar y mostrar la imagen si no es nula
        if (img != null) {
            cambiarImg(img);
        }
    }

    public void cambiarImg(Image img) {
        if (img == null) {
            imageLabel.setIcon(null); // Si no hay imagen, limpiar el JLabel
            return;
        }

        // Redimensionar la imagen a 250x200
        Image displayedImg = img.getScaledInstance(400, 350, Image.SCALE_SMOOTH);

        // Crear un ImageIcon con la imagen redimensionada y establecerla en el JLabel
        imageLabel.setIcon(new ImageIcon(displayedImg));

        // Refrescar el panel para asegurar que se actualice la imagen
        this.revalidate();
        this.repaint();
    }
}
