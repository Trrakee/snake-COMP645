import javax.swing.*;
import java.awt.*;

class SquarePanel extends JPanel {

    private static final long serialVersionUID = 0b1L;

    SquarePanel(Color d) {
        this.setBackground(d);
    }

    void ChangeColor(Color d) {
        this.setBackground(d);
        this.repaint();
    }

}

