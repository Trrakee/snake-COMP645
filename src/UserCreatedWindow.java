import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


class UserCreatedWindow extends JFrame {
    private static final long serialVersionUID = 0xdcb8ff2d7ffb1f80L;
    static ArrayList<ArrayList<SquareAttributes>> Grid;
    static int width = 20;
    static int height = 20;

    UserCreatedWindow() {


        Grid = new ArrayList<>();
        ArrayList<SquareAttributes> data;
        for (int i = 0; i < width; i++) {
            data = new ArrayList<>();
            for (int j = 0; j < height; j++) {
                SquareAttributes c = new SquareAttributes(2);
                data.add(c);
            }
            Grid.add(data);
        }
        getContentPane().setLayout(new GridLayout(20, 20, 0, 0));

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                getContentPane().add(Grid.get(i).get(j).square);
            }
        }

        positions initialPosition = new positions(10, 10);
        gameLogic controller = new gameLogic(initialPosition);
        controller.start();
        this.addKeyListener(new KeyboardInput());
    }
}
