public class Checker {
    private int color;
    public Coordinates position;

    public Checker(int x, int y)
    {
        position = new Coordinates(x, y);
        color = Color.empty;
    }

    public Checker(int color, Coordinates position)
    {
        this.color = color;
        this.position = position;
    }

    public int getColor(){
        return color;
    }
    public void setColor(int color){
        this.color=color;
    }
    public int getX()
    {
        return position.x;
    }

    public int getY()
    {
        return position.y;
    }

    public char printChecker()
    {
        switch (color)
        {
            case Color.empty:
                return  '_';
            case Color.black:
                return 'B';
            case Color.white:
                return 'W';
        }
        return 'E';
    }
}
