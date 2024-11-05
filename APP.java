import javax.swing.SwingUtilities;

import Vistas.LoginFrame;

public class APP {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrameInstance = new LoginFrame();
            loginFrameInstance.setVisible(true);
        });
    }
}
