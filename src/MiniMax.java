import java.util.ArrayList;

public class MiniMax {
    private int maxDepth;
    int checkers_color;

    public MiniMax(int maxDepth, int checkers_color)
    {
        this.maxDepth = maxDepth;
        this.checkers_color = checkers_color;
    }
    public Board MiniMax(Board board)
    {
        if (checkers_color == Color.black)
        {
            return max(new Board(board), Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        }
        else
        {
            return min(new Board(board), Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        }
    }


    public Board max(Board board, int a, int b, int depth)
    {
        if((board.isTerminal()) || (depth == maxDepth))
        {
            return board;
        }
        ArrayList<Board> children = board.getChildren(Color.black);
        if (children.isEmpty()) return board;
        Board maxBoard = children.get(0);
        for (Board child : children)
        {
            Board oppBoard = min(child, a, b, depth + 1);
            if(oppBoard.value > maxBoard.value)
            {
                maxBoard = child;
            }
            else if(oppBoard.value == maxBoard.value && Math.random() < .5f)
            {
                maxBoard = child;
            }

            //a-b proionisma
            a = Integer.max(a, oppBoard.value);
            if(b <= a)
            {
                break;
            }
        }
        return maxBoard;
    }

    public Board min(Board board, int a, int b, int depth)
    {
        if((board.isTerminal()) || (depth == maxDepth))
        {
            return board;
        }
        ArrayList<Board> children = board.getChildren(Color.white);
        if (children.isEmpty()) return board;

        Board minBoard = children.get(0);
        for (Board child : children)
        {
            Board oppBoard = max(child, a, b, depth + 1);
            if(oppBoard.value < minBoard.value)
            {
                minBoard = child;
            }
            else if(oppBoard.value == minBoard.value && Math.random() < .5f)
            {
                minBoard = child;
            }

            //a-b proionisma
            b = Integer.min(b, oppBoard.value);
            if(b <= a)
            {
                break;
            }
        }
        return minBoard;
    }
    //epistrefei poianoy seira einai analoga me to xrwma
    public boolean isMyTurn(int c)
    {
        return checkers_color==c;
    }
}
