import java.awt.*;
import java.util.ArrayList;

class SquareAttributes {


    SquarePanel square;
    private ArrayList<Color> colorArray = new ArrayList<>();

    SquareAttributes(int col) {
        colorArray.add(Color.darkGray);//0: Snake Color
        colorArray.add(Color.RED);    // 1: slow food color
        colorArray.add(Color.white); //  2: Background
        colorArray.add(Color.green);//   3: speed up food color
        square = new SquarePanel(colorArray.get(col));
    }

    void setSquareLight(int c) {
        square.ChangeColor(colorArray.get(c));
    }
}
