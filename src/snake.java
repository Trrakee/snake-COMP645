import javax.swing.*;

public class snake {

    public static void main(String[] args) {

        UserCreatedWindow gameWindow = new UserCreatedWindow();
        gameWindow.setTitle("Snake COMP645");
        gameWindow.setSize(300, 300);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
