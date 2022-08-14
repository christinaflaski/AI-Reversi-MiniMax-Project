import java.util.ArrayList;

public class Move {
    private ArrayList<int[]> directions = new ArrayList<>();
    private int x, y;

    public Move(int x, int y)
    {
        this.x = x;
        this.y = y;
        directions.clear();
    }

    void insertDirections (int[] d){
        directions.add(d);
    }

    public ArrayList<int[]> getDirections() {
        return directions;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
