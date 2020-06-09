public class positions {
    int x;
    int y;
    private int x2;
    private int x3;

    positions(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void DataSwitch(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public int getX2() {
        return x2;
    }

    public int getX3() {
        return x3;
    }


} 