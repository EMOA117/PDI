package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PanelHistograma extends JPanel {
    private double[] hi;
    private Color color;
    private String titulo; // Variable para el título

    public PanelHistograma(double[] hi, Color color, String titulo) {
        this.hi = hi;
        this.color = color;
        this.titulo = titulo; // Inicializamos el título
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int padding = 60; // Espacio más grande para los ejes
        int labelPadding = 40; // Espacio para etiquetas
        int width = getWidth() / hi.length;
        int height = getHeight();

        // Dibujar el título centrado en la parte superior
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16)); // Definir la fuente para el título
        int titleWidth = g.getFontMetrics().stringWidth(titulo);
        int titleX = (getWidth() - titleWidth) / 2;
        g.drawString(titulo, titleX, padding - 20); // Dibujar el título en la parte superior

        // Dibujar los ejes
        g.drawLine(padding, height - padding, padding, padding); // Eje Y
        g.drawLine(padding, height - padding, getWidth() - padding, height - padding); // Eje X

        // Graficar histograma normal (hi)
        g.setColor(color);
        double maxVal = getMax(hi); // Valor máximo para escalar las barras correctamente
        for (int i = 0; i < hi.length; i++) {
            int barHeight = (int) ((hi[i] / maxVal) * (height - 2 * padding));
            g.fillRect(padding + (i * width), height - padding - barHeight, width, barHeight);
        }

        // Etiquetas del eje Y (Frecuencia)
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        int numberYDivisions = 6;
        for (int i = 0; i <= numberYDivisions; i++) {
            int yPos = height - padding - (i * (height - 2 * padding) / numberYDivisions);
            double yValue = maxVal * i / numberYDivisions;
            g.drawLine(padding, yPos, padding + 5, yPos); // Marcas en el eje Y
            g.drawString(String.format("%.3f", yValue), padding - labelPadding, yPos + 5); // Etiquetas del eje Y
        }

        // Etiquetas del eje X (Escalas de color 0, 50, 100, 150, 200, 250)
        for (int i = 0; i <= 255; i += 16) {
            int xPos = padding + (i * (getWidth() - 2 * padding) / 255);
            g.drawLine(xPos, height - padding, xPos, height - padding + 5); // Marcas en el eje X
            g.drawString(String.valueOf(i), xPos - 10, height - padding + labelPadding); // Etiquetas del eje X
        }
    }

    // Método auxiliar para obtener el valor máximo del histograma
    private double getMax(double[] data) {
        double max = 0;
        for (double value : data) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
