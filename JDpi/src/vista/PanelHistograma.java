package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PanelHistograma extends JPanel {
    private double[] hi;
    private Color color;
    private String titulo; // Variable para el título
    private double max;

    public PanelHistograma(double[] hi, Color color, String titulo) {
        this.hi = hi;
        this.color = color;
        this.titulo = titulo; // Inicializamos el título
        this.max = getMax(hi); // Calculamos el valor máximo del histograma
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
          int padding = 60; // Espacio más grande para los ejes
        int labelPadding = 40; // Espacio para etiquetas
        int width = getWidth();
        int height = getHeight();
        int margin = padding; // Reutilizamos el padding como margen
        // Dibujar el título centrado en la parte superior
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 12)); // Definir la fuente para el título
        int titleWidth = g.getFontMetrics().stringWidth(titulo);
        int titleX = (getWidth() - titleWidth) / 2;
        g.drawString(titulo, titleX, margin - 20); // Dibujar el título en la parte superior
        drawBar(g);
        /*
        super.paintComponent(g);
        int padding = 60; // Espacio más grande para los ejes
        int labelPadding = 40; // Espacio para etiquetas
        int width = getWidth();
        int height = getHeight();
        int margin = padding; // Reutilizamos el padding como margen

        // Dibujar el título centrado en la parte superior
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16)); // Definir la fuente para el título
        int titleWidth = g.getFontMetrics().stringWidth(titulo);
        int titleX = (getWidth() - titleWidth) / 2;
        g.drawString(titulo, titleX, margin - 20); // Dibujar el título en la parte superior

        // Dibujar los ejes
        g.drawLine(margin, height - margin, margin, margin); // Eje Y
        g.drawLine(margin, height - margin, width - margin, height - margin); // Eje X

        // Graficar el histograma
        g.setColor(color);
        double yScale = (height - 2 * margin) / max; // Escala para las alturas de las barras
        int barWidth = Math.max(1, (width - 2 * margin) / hi.length); // Aseguramos al menos 1px de ancho

        for (int i = 0; i < hi.length; i++) {
            int x = margin + i * barWidth;
            int barHeight = (int) (hi[i] * yScale);
            g.fillRect(x, height - margin - barHeight, barWidth, barHeight);
        }

        // Etiquetas del eje Y (Frecuencia)
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        int numberYDivisions = 6;
        for (int i = 0; i <= numberYDivisions; i++) {
            int yPos = height - margin - (i * (height - 2 * margin) / numberYDivisions);
            double yValue = max * i / numberYDivisions;
            g.drawLine(margin, yPos, margin + 5, yPos); // Marcas en el eje Y
            g.drawString(String.format("%.3f", yValue), margin - labelPadding, yPos + 5); // Etiquetas del eje Y
        }

        // Etiquetas del eje X (Escalas de color 0, 50, 100, 150, 200, 250)
        for (int i = 0; i <= 255; i += 51) {
            int xPos = margin + (i * (width - 2 * margin) / 255);
            g.drawLine(xPos, height - margin, xPos, height - margin + 5); // Marcas en el eje X
            g.drawString(String.valueOf(i), xPos - 10, height - margin + labelPadding); // Etiquetas del eje X
        }
        */
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
    
    private void drawBar(Graphics g){
        // Eliminamos la comprobaciÃ³n del tamaÃ±o de datos para hacerlo mÃ¡s flexible
        int width = getWidth();
        int height = getHeight();
        int margin = 50;

        // Ajustamos el ancho de las barras para llenar el espacio disponible
        int barWidth = Math.max(1, (width - 2 * margin) / hi.length); // Asegura al menos 1px de ancho
        int maxHeight = height - 2 * margin;
        double yScale = maxHeight / max;

        // Llamada a dibujar los ejes
        drawAxes(g, width, height, margin, barWidth,maxHeight);
        
		// Dibujar las barras del histograma
        g.setColor(color);
        for (int i = 0; i < hi.length; i++) {
            int x = margin + i * barWidth;
            int barHeight = (int) (hi[i] * yScale);
            g.fillRect(x, height - margin - barHeight, barWidth, barHeight);
        }
        
    }
    
    private void drawAxes(Graphics g, int width, int height, int margin, int barWidth,int maxHeight) {
        g.setColor(Color.BLACK);

        // Eje Y
        g.drawLine(margin, height - margin, margin, margin);
        
        // Eje X
        g.drawLine(margin, height - margin, width - margin, height - margin);

        // Marcas en el eje X cada 50 barras
        int yMark = height - margin;
        for (int i = 0; i < hi.length; i += 51) {
            int x = margin + i * barWidth;
            g.drawLine(x, yMark, x, yMark - 5);
            String markLabel = String.valueOf(i);
            g.drawString(markLabel, x - g.getFontMetrics().stringWidth(markLabel) / 2, yMark + g.getFontMetrics().getHeight());
        }

        // Marca del final (para el Ãºltimo valor, que deberÃ­a ser 255 si el array es de esa longitud)
        int lastX = margin + (hi.length - 1) * barWidth;
        g.drawLine(lastX, yMark, lastX, yMark - 5);
        String lastLabel = String.valueOf(hi.length - 1);
        g.drawString(lastLabel, lastX - g.getFontMetrics().stringWidth(lastLabel) / 2,
                yMark + g.getFontMetrics().getHeight());

        // Marcas en el eje Y
        int numberOfYMarks = 5; // 5 marcas en el eje Y
		for (int i = 1; i <= numberOfYMarks; i++) {
            int y = (height) - (i * (maxHeight / numberOfYMarks)) - margin;
            g.drawLine(margin - 5, y - 5, margin, y - 5); // Marca hacia afuera del eje Y
            String yLabel = String.format("%.3f", (double) (this.max * i) / numberOfYMarks);
			g.drawString(yLabel, margin - g.getFontMetrics().stringWidth(yLabel) - 10, y + (g.getFontMetrics().getHeight() / 4));

        }

	}
}
