import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class gameLogic extends Thread {
    static int snakeDirection;
    private ArrayList<ArrayList<SquareAttributes>> Squares;
    private positions snakeHeadPosition;
    private int snakeSize = 1;
    private String chosenFood = "";
    private long snakeSpeed = 100;

    private ArrayList<positions> positions = new ArrayList<>();
    private positions foodLocation;
    private positions foodLocation2;

    gameLogic(positions positionDepart) {
        Squares = UserCreatedWindow.Grid;
        snakeHeadPosition = new positions(positionDepart.x, positionDepart.y);
        snakeDirection = 1;
        positions headPos = new positions(snakeHeadPosition.getX(), snakeHeadPosition.getY());
        positions.add(headPos);
        foodLocation = new positions(UserCreatedWindow.height - 1, UserCreatedWindow.width - 1);
        foodLocation2 = new positions(UserCreatedWindow.height - ThreadLocalRandom.current().nextInt(1, 20), UserCreatedWindow.width - ThreadLocalRandom.current().nextInt(1, 20));
        spawnFood(foodLocation);
        spawnFood(foodLocation2);
    }

    public void run() {
        while (true) {
            moveInside(snakeDirection);
            checkCollision();
            moveOutside();
            deleteTail();
            pause();
        }
    }

    private void pause() {
        try {
            sleep(snakeSpeed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkCollision() {
        positions posCritique = positions.get(positions.size() - 1);
        for (int i = 0; i <= positions.size() - 2; i++) {
            boolean suicideBite = posCritique.getX() == positions.get(i).getX() && posCritique.getY() == positions.get(i).getY();
            if (suicideBite) {
                stopTheGame();
                System.out.println("Game Over");
            }
        }

        boolean eatFood = posCritique.getX() == foodLocation.y && posCritique.getY() == foodLocation.x;
        boolean eatFood2 = posCritique.getX() == foodLocation2.y && posCritique.getY() == foodLocation2.x;
        if (eatFood || eatFood2) {
            if (chosenFood.contains("Slow")) {
                if (snakeSpeed >= 150) {
                    snakeSpeed = 140;
                }
                try {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(new File("./src/powerDown.wav"));
                    Clip test = AudioSystem.getClip();

                    test.open(ais);
                    test.start();
                    while (!test.isRunning())
                        Thread.sleep(1);
                    while (test.isRunning())
                        Thread.sleep(1);
                    test.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                snakeSpeed = snakeSpeed + 25;
                System.out.printf("Snake slowed Down!\nCurrent Snake Speed: %d\nSnake size: %d%n\n------------------------\n", snakeSpeed, snakeSize);
                snakeSize = snakeSize + 1;
                foodLocation2 = positioningHelperSnake();
                spawnFood(foodLocation2);
            } else {
                try {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(new File("./src/powerUP.wav"));
                    Clip test = AudioSystem.getClip();

                    test.open(ais);
                    test.start();
                    while (!test.isRunning())
                        Thread.sleep(1);
                    while (test.isRunning())
                        Thread.sleep(1);
                    test.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                snakeSpeed = snakeSpeed - 35;
                if (snakeSpeed <= 35) {
                    snakeSpeed = 40;
                }
                System.out.printf("Snake sped Up!\nCurrent Snake Speed: %d\nSnake size: %d%n\n------------------------\n", snakeSpeed, snakeSize);
                snakeSize = snakeSize + 1;
                foodLocation = positioningHelperSnake();
                spawnFood(foodLocation);


            }
        }
    }

    private void stopTheGame() {
        while (true) {
            pause();
        }
    }

    private void spawnFood(positions foodPositionIn) {
        int squareChosen = new Random().nextBoolean() ? 1 : 3;
        if (squareChosen == 1) {
            chosenFood = "Slow Food";
        } else {
            chosenFood = "Speed Food";
        }
        Squares.get(foodPositionIn.x).get(foodPositionIn.y).setSquareLight(squareChosen);

    }

    private positions positioningHelperSnake() {
        positions position;
        int randomX = (int) (Math.random() * 19);
        int randomY = (int) (Math.random() * 19);
        position = new positions(randomX, randomY);
        for (int i = 0; i <= positions.size() - 1; i++) {
            if (position.getY() == positions.get(i).getX() && position.getX() == positions.get(i).getY()) {
                randomX = (int) (Math.random() * 19);
                randomY = (int) (Math.random() * 19);
                position = new positions(randomX, randomY);
                i = 0;
            }
        }
        return position;
    }

    //1:right 2:left 3:up 4:down 0:nothing
    private void moveInside(int direction) {
        switch (direction) {
            case 4:
                snakeHeadPosition.DataSwitch(snakeHeadPosition.x, (snakeHeadPosition.y + 1) % 20);
                positions.add(new positions(snakeHeadPosition.x, snakeHeadPosition.y));
                break;
            case 3:
                if (snakeHeadPosition.y - 1 < 0) {
                    snakeHeadPosition.DataSwitch(snakeHeadPosition.x, 19);
                } else {
                    snakeHeadPosition.DataSwitch(snakeHeadPosition.x, Math.abs(snakeHeadPosition.y - 1) % 20);
                }
                positions.add(new positions(snakeHeadPosition.x, snakeHeadPosition.y));
                break;
            case 2:
                if (snakeHeadPosition.x - 1 < 0) {
                    snakeHeadPosition.DataSwitch(19, snakeHeadPosition.y);
                } else {
                    snakeHeadPosition.DataSwitch(Math.abs(snakeHeadPosition.x - 1) % 20, snakeHeadPosition.y);
                }
                positions.add(new positions(snakeHeadPosition.x, snakeHeadPosition.y));
                break;
            case 1:
                snakeHeadPosition.DataSwitch(Math.abs(snakeHeadPosition.x + 1) % 20, snakeHeadPosition.y);
                positions.add(new positions(snakeHeadPosition.x, snakeHeadPosition.y));
                break;
        }
    }

    private void moveOutside() {
        for (positions t : positions) {
            int y = t.getX();
            int x = t.getY();
            Squares.get(x).get(y).setSquareLight(0);

        }
    }

    private void deleteTail() {
        int cmpt = snakeSize;
        for (int i = positions.size() - 1; i >= 0; i--) {
            if (cmpt == 0) {
                positions t = positions.get(i);
                Squares.get(t.y).get(t.x).setSquareLight(2);
            } else {
                cmpt--;
            }
        }
        cmpt = snakeSize;
        for (int i = positions.size() - 1; i >= 0; i--) {
            if (cmpt == 0) {
                positions.remove(i);
            } else {
                cmpt--;
            }
        }
    }
}