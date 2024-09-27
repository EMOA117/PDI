package vista;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modelo.Histograma;
import vista.PanelHistograma;

public class FrameHistograma extends JFrame {

    public FrameHistograma(Histograma histograma, int opcion) {
        Color color = new Color(0, 0, 0);
        String name;
        switch (opcion) {
    case 1:
        name = "en RGB (canal rojo)";
        color = color.RED;
        break;
    case 2:
        name = "en RGB (canal verde)";
        color = color.GREEN;
        break;
    case 3:
        name = "en RGB (canal azul)";
        color = color.BLUE;
        break;
    default:
        name = "en escala de grises";
        color = color.GRAY;
        break;
}
        
        setTitle("Histogramas "+name);
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Configurar el layout para dar más espacio a los histogramas
        setLayout(new GridLayout(3, 1, 10, 10));  // 3 filas, 1 columna con espacio de 10px entre filas

        // Paneles para cada histograma con bordes para agregar padding
        JPanel panelHistogramaGris = new PanelHistograma(histograma.getPi(), color, "Histograma");
        panelHistogramaGris.setBackground(Color.LIGHT_GRAY);
        panelHistogramaGris.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding de 10px

        JPanel panelHistogramaAcumulado = new PanelHistograma(histograma.getPiac(), color, "Histograma acumulado");
        panelHistogramaAcumulado.setBackground(Color.LIGHT_GRAY);
        panelHistogramaAcumulado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding de 10px

        // Añadir ambos paneles al frame
        add(panelHistogramaGris);
        add(panelHistogramaAcumulado);

        // Panel para los valores estadísticos
        JPanel panelEstadisticas = new JPanel();
        panelEstadisticas.setBackground(Color.WHITE);  
        panelEstadisticas.setLayout(new GridLayout(5, 1, 5, 5));  // 5 filas, con espacio de 5px entre etiquetas
        panelEstadisticas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding de 10px

        // Crear etiquetas con formato
        Font labelFont = new Font("Arial", Font.PLAIN, 12);
        Color labelColor = Color.GRAY;

        JLabel mediaLabel = new JLabel(String.format("Media: %.4f", histograma.getMedia()));
        mediaLabel.setFont(labelFont);
        mediaLabel.setForeground(labelColor);

        JLabel varianzaLabel = new JLabel(String.format("Varianza: %.4f", histograma.getVarianza()));
        varianzaLabel.setFont(labelFont);
        varianzaLabel.setForeground(labelColor);

        JLabel asimetriaLabel = new JLabel(String.format("Asimetria: %.4f", histograma.getAsimetria()));
        asimetriaLabel.setFont(labelFont);
        asimetriaLabel.setForeground(labelColor);

        JLabel energiaLabel = new JLabel(String.format("Energia: %.4f", histograma.getEnergia()));
        energiaLabel.setFont(labelFont);
        energiaLabel.setForeground(labelColor);

        JLabel entropiaLabel = new JLabel(String.format("Entropia: %.4f", histograma.getEntropia()));
        entropiaLabel.setFont(labelFont);
        entropiaLabel.setForeground(labelColor);

        // Agregar las etiquetas al panel de texto
        panelEstadisticas.add(mediaLabel);
        panelEstadisticas.add(varianzaLabel);
        panelEstadisticas.add(asimetriaLabel);
        panelEstadisticas.add(energiaLabel);
        panelEstadisticas.add(entropiaLabel);

        // Añadir el panel de estadísticas al frame
        add(panelEstadisticas);

        setVisible(true);
    }
}
