import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main extends JFrame {

    public Main() {
        setTitle("Editor de Imágenes");
        setSize(1000, 3000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocationRelativeTo(null);

        // Panel con botones
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        // Botones para cada función
        JButton btnDuplicar = new JButton("Duplicar Imagen");
        btnDuplicar.setBounds(500,400,10,10);
        JButton btnCombinar = new JButton("Combinar Imágenes");
        btnCombinar.setBounds(500,300,10,10);
        JButton btnCambiarColor = new JButton("Cambiar Color de Imagen");
        btnCambiarColor.setBounds(500,200,10,10);
        JButton btnRedimensionar = new JButton("Redimensionar Imagen");
        btnRedimensionar.setBounds(500,100,10,10);

        //modificacion botones





        // Agregar botones al panel
        panel.add(btnDuplicar);
        panel.add(btnCombinar);
        panel.add(btnCambiarColor);
        panel.add(btnRedimensionar);

        // Agregar acciones a los botones
        btnDuplicar.addActionListener(e -> duplicarImagen());
        btnCombinar.addActionListener(e -> combinarImagenes());
        btnCambiarColor.addActionListener(e -> cambiarColorImagen());
        btnRedimensionar.addActionListener(e -> redimensionarImagen());

        // Agregar el panel a la ventana
        add(panel);
    }

    private void duplicarImagen() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecciona una imagen para duplicar");
            int resultado = fileChooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivoImagen = fileChooser.getSelectedFile();
                BufferedImage imagenOriginal = ImageIO.read(archivoImagen);

                // Parámetros de duplicado (puedes personalizar estos valores)
                int filas = 3;
                int columnas = 5;

                int nuevoAncho = imagenOriginal.getWidth() * columnas;
                int nuevoAlto = imagenOriginal.getHeight() * filas;
                BufferedImage imagenDuplicada = new BufferedImage(nuevoAncho, nuevoAlto, BufferedImage.TYPE_INT_ARGB);

                Graphics g = imagenDuplicada.getGraphics();
                for (int y = 0; y < filas; y++) {
                    for (int x = 0; x < columnas; x++) {
                        g.drawImage(imagenOriginal, x * imagenOriginal.getWidth(), y * imagenOriginal.getHeight(), null);
                    }
                }
                g.dispose();

                guardarImagen(imagenDuplicada, "Duplicada");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al duplicar la imagen: " + e.getMessage());
        }
    }

    private void combinarImagenes() {
        // Lógica para combinar imágenes en posiciones específicas (se puede extender)
        JOptionPane.showMessageDialog(this, "Función de combinación de imágenes no implementada.");
    }

    private void cambiarColorImagen() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecciona una imagen para cambiar el color");
            int resultado = fileChooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivoImagen = fileChooser.getSelectedFile();
                BufferedImage imagenOriginal = ImageIO.read(archivoImagen);

                Color colorActual = Color.BLACK; // Cambiar color blanco
                Color colorNuevo = Color.RED;    // Cambiar a rojo

                BufferedImage imagenConColorCambiado = cambiarColor(imagenOriginal, colorActual, colorNuevo);

                guardarImagen(imagenConColorCambiado, "ColorCambiado");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cambiar el color de la imagen: " + e.getMessage());
        }
    }

    private BufferedImage cambiarColor(BufferedImage imagen, Color colorActual, Color colorNuevo) {
        int ancho = imagen.getWidth();
        int alto = imagen.getHeight();

        BufferedImage nuevaImagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int colorPixel = imagen.getRGB(x, y);

                if (colorPixel == colorActual.getRGB()) {
                    nuevaImagen.setRGB(x, y, colorNuevo.getRGB());
                } else {
                    nuevaImagen.setRGB(x, y, colorPixel);
                }
            }
        }
        return nuevaImagen;
    }

    private void redimensionarImagen() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecciona una imagen para redimensionar");
            int resultado = fileChooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivoImagen = fileChooser.getSelectedFile();
                BufferedImage imagenOriginal = ImageIO.read(archivoImagen);

                int nuevoAncho = 300; // Nuevo ancho
                int nuevoAlto = 300;  // Nuevo alto

                BufferedImage imagenRedimensionada = redimensionar(imagenOriginal, nuevoAncho, nuevoAlto);

                guardarImagen(imagenRedimensionada, "Redimensionada");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al redimensionar la imagen: " + e.getMessage());
        }
    }

    private BufferedImage redimensionar(BufferedImage imagenOriginal, int ancho, int alto) {
        Image imagenTemporal = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        BufferedImage imagenRedimensionada = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = imagenRedimensionada.createGraphics();
        g2d.drawImage(imagenTemporal, 0, 0, null);
        g2d.dispose();

        return imagenRedimensionada;
    }

    private void guardarImagen(BufferedImage imagen, String nombre) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar imagen " + nombre);
            int resultado = fileChooser.showSaveDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivoGuardar = fileChooser.getSelectedFile();
                ImageIO.write(imagen, "png", archivoGuardar);
                JOptionPane.showMessageDialog(this, "Imagen " + nombre + " guardada exitosamente.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la imagen: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main editor = new Main();
            editor.setVisible(true);
        });
    }
}
