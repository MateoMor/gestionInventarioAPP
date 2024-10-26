package Vistas;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VistaLog extends JFrame {

    private JTextArea textArea;

    public VistaLog() {
        setTitle("Registro de Transacciones");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        cargarLog();
    }

    private void cargarLog() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transacciones.log"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar el log.");
        }
    }
}
