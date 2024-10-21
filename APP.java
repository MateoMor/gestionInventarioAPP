import javax.swing.SwingUtilities;

import Vistas.GestionInventarioAPP;

public class APP {
    public static void main (String[] args) throws Exception {
            SwingUtilities.invokeLater(() -> {
                GestionInventarioAPP app = new GestionInventarioAPP();
                app.setVisible(true);
            });
    }
}
