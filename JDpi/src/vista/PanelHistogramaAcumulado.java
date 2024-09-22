/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

/**
 *
 * @author EMOA1
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import javax.swing.JPanel;

public class PanelHistogramaAcumulado extends JPanel {
    private DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Formato de 2 decimales
    private double[] hiac;
    private Color color;
    private String titulo; // Agregamos una variable para el título

    public PanelHistogramaAcumulado(double[] hiac, Color color, String titulo) {
        this.hiac = hiac;
        this.color = color;
        this.titulo = titulo; // Inicializamos el título
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth() / hiac.length;
        int height = getHeight();
        int padding = 40; // Espacio para los ejes
        int labelPadding = 20; // Espacio para etiquetas

        // Dibujar el título centrado en la parte superior
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16)); // Definir la fuente para el título
        int titleWidth = g.getFontMetrics().stringWidth(titulo);
        int titleX = (getWidth() - titleWidth) / 2;
        g.drawString(titulo, titleX, padding - 10); // Dibujar el título en la parte superior

        // Dibujar el eje Y
        g.drawLine(padding, height - padding, padding, padding); // Eje Y
        g.drawLine(padding, height - padding, getWidth() - padding, height - padding); // Eje X

        // Graficar histograma acumulado (hiac)
        g.setColor(color);
        for (int i = 0; i < hiac.length; i++) {
            int barHeight = (int) ((hiac[i] / getMax(hiac)) * (height - 2 * padding));
            g.drawRect(padding + i * width, height - padding - barHeight, width, barHeight);
        }

        // Etiquetas del eje Y (Frecuencia con decimales)
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        int numberYDivisions = 10;
        for (int i = 0; i <= numberYDivisions; i++) {
            double yValue = getMax(hiac) / numberYDivisions * i;
            int yPos = height - padding - (i * (height - 2 * padding) / numberYDivisions);
            g.drawString(decimalFormat.format(yValue), padding - labelPadding, yPos + 5); // Formatear con decimales
            g.drawLine(padding, yPos, padding + 5, yPos);
        }

        // Etiquetas del eje X (Escalas de color 0, 50, 100, 150, 200, 250)
        for (int i = 0; i <= 255; i += 16) {
            int xPos = padding + (i * (getWidth() - 2 * padding) / 255);
            g.drawLine(xPos, height - padding, xPos, height - padding + 5); // Marcas en el eje X
            g.drawString(String.valueOf(i), xPos - 10, height - padding + labelPadding); // Etiquetas del eje X
        }
    }

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
