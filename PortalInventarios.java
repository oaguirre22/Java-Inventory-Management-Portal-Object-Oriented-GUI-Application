import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PortalInventarios {

    private static String tipoAutomovilSeleccionado;

    public static void main(String[] args) {
        ArrayList<Producto> productos = new ArrayList<>();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        JFrame frame1 = new JFrame();
        frame1.setSize(800, 800);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new EmptyBorder(6, 6, 6, 6));
        panelPrincipal.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextField inventarioField = new JTextField(15);
        JTextField marcaField = new JTextField(15);
        JTextField modeloField = new JTextField(15);
        JTextField anioField = new JTextField(15);
        JTextField precioField = new JTextField(15);
        JSpinner stockSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));

        JPanel panelInventario = new JPanel();
        panelInventario.setLayout(new BoxLayout(panelInventario, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Inventario de Productos");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton mostrarInventariobtn = new JButton("Pestaña donde se muestra el inventario");
        buttonPanel.add(mostrarInventariobtn);

        mostrarInventariobtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame1.setVisible(true);
            }
        });

        JButton agregarProductobtn = new JButton("Agregar Productos");
        buttonPanel.add(agregarProductobtn);

        agregarProductobtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder errorMessage = new StringBuilder();
                boolean hasEmptyFields = false;
                boolean hasInvalidFields = false;

                String nombre = inventarioField.getText();
                String marca = marcaField.getText();
                String modelo = modeloField.getText();
                int anio = 0;
                double precio = 0;
                int stock = (Integer) stockSpinner.getValue();

                if (nombre.isEmpty()) {
                    errorMessage.append("nombre, ");
                    hasEmptyFields = true;
                }
                if (marca.isEmpty()) {
                    errorMessage.append("marca, ");
                    hasEmptyFields = true;
                }
                if (modelo.isEmpty()) {
                    errorMessage.append("modelo, ");
                    hasEmptyFields = true;
                }

                try {
                    anio = Integer.parseInt(anioField.getText());
                } catch (NumberFormatException ex) {
                    errorMessage.append("año, ");
                    hasInvalidFields = true;
                }
                try {
                    precio = Double.parseDouble(precioField.getText());
                } catch (NumberFormatException ex) {
                    errorMessage.append("precio, ");
                    hasInvalidFields = true;
                }
                if (stock <= 0) {
                    errorMessage.append("stock, ");
                    hasInvalidFields = true;
                }

                if (tipoAutomovilSeleccionado == null) {
                    errorMessage.append("tipo de automóvil, ");
                    hasEmptyFields = true;
                }

                if (hasEmptyFields || hasInvalidFields) {
                    if (hasEmptyFields) {
                        errorMessage.insert(0, "Por favor añade datos en los campos vacíos que son: ");
                    }
                    if (hasInvalidFields) {
                        if (hasEmptyFields) {
                            errorMessage.append(". Además, los datos en los campos ");
                        } else {
                            errorMessage.insert(0, "Los datos en los campos ");
                        }
                        errorMessage.append("no son correctos. Por favor corrige estos datos para continuar.");
                    } else {
                        errorMessage.setLength(errorMessage.length() - 2);
                        errorMessage.append(" están vacíos. Por favor ingrese sus datos.");
                    }
                    JOptionPane.showMessageDialog(frame, errorMessage.toString());
                } else {
                    Producto producto = new TipoProducto(tipoAutomovilSeleccionado, nombre, marca, modelo, precio, stock, true, anio);
                    productos.add(producto);

                    JCheckBox cb = new JCheckBox(producto.getDetalles());
                    cb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (cb.isSelected()) {
                                JOptionPane.showMessageDialog(frame1, "Seleccionaste un producto: " + producto.getDetalles());
                            } else {
                                JOptionPane.showMessageDialog(frame1, "Producto no seleccionado: " + producto.getDetalles());
                            }
                        }
                    });

                    inventarioField.setText("");
                    marcaField.setText("");
                    modeloField.setText("");
                    anioField.setText("");
                    precioField.setText("");
                    stockSpinner.setValue(0);

                    panelInventario.add(cb);
                    panelInventario.revalidate();
                    panelInventario.repaint();

                    JOptionPane.showMessageDialog(frame, "Datos agregados exitosamente");
                }
            }
        });

        JButton eliminarProductoButton = new JButton("Eliminar Productos");
        buttonPanel.add(eliminarProductoButton);

        eliminarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < panelInventario.getComponentCount(); i++) {
                    Component comp = panelInventario.getComponent(i);
                    if (comp instanceof JCheckBox) {
                        JCheckBox cb = (JCheckBox) comp;
                        if (cb.isSelected()) {
                            productos.removeIf(p -> p.getDetalles().equals(cb.getText()));
                            panelInventario.remove(i);
                            i--;
                        }
                    }
                }
                panelInventario.revalidate();
                panelInventario.repaint();
                generarReporte(productos);
            }
        });

        JButton generarReporteBoton = new JButton("Generar Reporte");
        buttonPanel.add(generarReporteBoton);

        generarReporteBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte(productos);
            }
        });

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Nombre:"));
        inputPanel.add(inventarioField);
        inputPanel.add(new JLabel("Marca:"));
        inputPanel.add(marcaField);
        inputPanel.add(new JLabel("Modelo:"));
        inputPanel.add(modeloField);
        inputPanel.add(new JLabel("Año:"));
        inputPanel.add(anioField);
        inputPanel.add(new JLabel("Precio:"));
        inputPanel.add(precioField);
        inputPanel.add(new JLabel("Stock:"));
        inputPanel.add(stockSpinner);

        panel.add(inputPanel, BorderLayout.SOUTH);

        panelPrincipal.add(panel, BorderLayout.NORTH);

        frame1.add(new JScrollPane(panelInventario), BorderLayout.CENTER);

        JPanel panelTipos = new JPanel();
        panelTipos.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel seleccionaTipoLabel = new JLabel("Selecciona el tipo de carro:");
        seleccionaTipoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(seleccionaTipoLabel, BorderLayout.CENTER);

        agregarImagenBoton(panelTipos, "Sedan", "src/Sedan.png");
        agregarImagenBoton(panelTipos, "Coupe", "src/Coupe.png");
        agregarImagenBoton(panelTipos, "SUV", "src/SUV.png");
        agregarImagenBoton(panelTipos, "PickUp", "src/PickUp.png");

        panelPrincipal.add(panelTipos, BorderLayout.CENTER);
        frame.add(panelPrincipal);

        frame.setVisible(true);
    }

    private static void agregarImagenBoton(JPanel panel, String tipo, String rutaImagen) {
        ImageIcon icon = new ImageIcon(rutaImagen);
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        JButton button = new JButton(tipo, scaledIcon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tipoAutomovilSeleccionado = tipo;
                JOptionPane.showMessageDialog(null, "Seleccionaste: " + tipoAutomovilSeleccionado);
            }
        });
        panel.add(button);
    }

    private static void generarReporte(ArrayList<Producto> productos) {
        try (FileWriter writer = new FileWriter("inventario.txt")) {
            writer.write("Reporte de Inventario\n");
            writer.write("----------------------------------------------------------------------------------------------------------------\n");
            for (Producto producto : productos) {
                writer.write(producto.getDetalles() + "\n");
            }
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }
}