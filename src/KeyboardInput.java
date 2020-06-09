import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardInput extends KeyAdapter {

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 39:    // Right Arrow
                if (gameLogic.snakeDirection != 2)
                    gameLogic.snakeDirection = 1;
                break;
            case 38:    // Up Arrow
                if (gameLogic.snakeDirection != 4)
                    gameLogic.snakeDirection = 3;
                break;

            case 37:    // Left Arrow
                if (gameLogic.snakeDirection != 1)
                    gameLogic.snakeDirection = 2;
                break;

            case 40:    // Down Arrow
                if (gameLogic.snakeDirection != 3)
                    gameLogic.snakeDirection = 4;
                break;

            default:
                break;
        }
    }
}
