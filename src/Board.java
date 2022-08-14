import java.util.ArrayList;

public class Board {
    public Checker[][] board = new Checker[8][8];
    private int remaining_moves;
    public int value;

    //ftiaxnoyme to arxiko board
    public Board()
    {
        value = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                board[i][j] = new Checker(i, j);
            }
        }
        //menoyn 60 theseis kathws sto paixnidi oi 4 theseis sthn mesh toy pinaka einai sygkekrimenes kai panta idies sthn enarksh toy paixnidioy
        remaining_moves = 60;
        board[3][3].setColor(Color.black);
        board[3][4].setColor(Color.white);
        board[4][3].setColor(Color.white);
        board[4][4].setColor(Color.black);
    }
    //kaloyme ayton ton constructor otan theloyme na enhmerwsoyme to board me nea kinhsh
    public Board(Board board) {
        this.remaining_moves = board.remaining_moves;
        this.value = board.value;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                this.board[i][j] = new Checker(board.board[i][j].getColor(), board.board[i][j].position);
            }
        }

    }

    //ektypwnei ton pinaka
    public void printBoard() {

        System.out.println("  1 2 3 4 5 6 7 8");
        String[] y = {"1", "2", "3", "4", "5", "6", "7", "8"};
        for (int i = 0; i < 8; i++)
        {
            System.out.print(y[i] + " ");
            for (int j = 0; j < 8; j++)
            {
                System.out.print(board[j][i].printChecker() + " ");
            }
            System.out.println();
        }
    }

    public Checker getChecker(int x, int y) {
        return board[x][y];
    }

    //gemizei thn lista me tis diathesimes kinhshs toy xrhsth
    public ArrayList<Move> avaliable_moves(int currentcolor)
    {
        ArrayList<Move> available = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                Checker current = board[i][j];
                if (current.getColor() == Color.empty)
                {
                    //h anazhthsh ginetai gia [-1,1]
                    for (int x = -1; x < 2; x++)
                    {
                        for (int y = -1; y < 2; y++)
                        {
                            if (x != 0 || y != 0)
                            {
                                try
                                {
                                    //psaxnoyme gia geitonikes theseis sto board
                                    Checker neighbor = getChecker(current.getX() + x, current.getY() + y);
                                    //An o geitonas exei to xrwma toy antipaloy psaxnoyme mexri na synantisoyme me color=empty
                                    if (neighbor.getColor() != currentcolor && neighbor.getColor() !=0)
                                    {
                                        int m = 0;
                                        while (neighbor.getColor() != Color.empty)
                                        {
                                            neighbor = getChecker(neighbor.getX() + x, neighbor.getY() + y);
                                            //metrhths geitwnwn
                                            m++;
                                            //An o geitonas exei idio xrwma me ton xrhsth, mporei na apotelei kinhsh
                                            if (neighbor.getColor() == currentcolor)
                                            {
                                                //Elegxoyme an exoyme balei hdh sthn lista mas thn kinhsh poy brhkame
                                                boolean found = false;
                                                for (Move move : available)
                                                {
                                                    //An thn exoyme sthn lista stamatei h for
                                                    if (move.getX() == current.getX() && move.getY() == current.getY())
                                                    {
                                                        move.insertDirections(new int[]{x, y, m});
                                                        found = true;
                                                        break;
                                                    }
                                                }
                                                //An den thn exoyme hdh thn prosthetoyme
                                                if (found==false)
                                                {
                                                    Move temp_move = new Move(current.getX(), current.getY());
                                                    temp_move.insertDirections(new int[]{x, y, m});
                                                    available.add(temp_move);
                                                }
                                            }
                                        }
                                    }
                                }
                                //An psaksoyme ektos twn oriwn na petaksei exception gia thn apofygh error
                                catch (Exception e)
                                {
                                    continue;
                                }
                            }
                        }
                    }
                }

            }
        }
        return available;
    }

    public ArrayList<Board> getChildren(int color)
    {
        ArrayList<Board> children = new ArrayList<>();

        for (Move m : avaliable_moves(color))
        {
            children.add(makeMove(new Board(this), m, color));
        }
        return children;
    }

    public Board makeMove(Board board, Move move, int currentcolor)
    {
        board.getChecker(move.getX(), move.getY()).setColor(currentcolor);
        board.remaining_moves--;

        for (int[] i : move.getDirections())
        {
            int x = move.getX() + i[0];
            int y = move.getY() + i[1];

            for (int j = 0; j < i[2]; j++)
            {
                board.getChecker(x, y).setColor(currentcolor);

                x += i[0];
                y += i[1];
            }
        }
        board.heuristic_evaluation();
        return board;
    }

    //elegxei an exoyn meinei allles eleytheres theseis wste na termatisei to paixnidi
    public boolean isTerminal()
    {
        if (remaining_moves <=0) {
            return true;
        }else {
            return false;
        }
    }
    //eyretikh synarthsh aksiologhshs
    public void heuristic_evaluation()
    {
        value = 0;
        //settarei times(barh) analoga me thn thesh
        int[][] board_value =
                {
                        {100, -10, 11, 6,  6, 11, -10, 100},
                        {-10, -20, 1, 2,  2, 1, -20, -10},
                        {10, 1, 5,  4,  4,  5, 1, 10},
                        { 6,  2, 4, 2, 2,  4,  2,  6},
                        { 6,  2, 4, 2, 2,  4,  2,  6},
                        {10, 1, 5,  4,  4,  5, 1, 10},
                        {-10, -20, 1, 2,  2, 1, -20, -10},
                        {100, -10, 11, 6,  6, 11, -10, 100}
                };

        int b = 0, w = 0;
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(board[i][j].getColor() == Color.black)
                {
                    b += board_value[i][j];
                }
                else if(board[i][j].getColor() == Color.white)
                {
                    w += board_value[i][j];
                }
            }
        }
        try
        {
            value = (b - w) / (b + w);
        }
        catch (Exception e)
        {
            value = 0;
        }

        b += avaliable_moves(Color.black).size();
        w += avaliable_moves(Color.white).size();

        try
        {
            value += (b - w) / (b + w);
        }
        catch (Exception e)
        {
            value = 0;
        }
    }

    //emfanizei to score meta apo kathe kinhsh
    public String ScoreBoard() {
        int whites=0, blacks=0;
        for (int i=0;i<8;i++) for (int j=0;j<8;j++) {
            if (board[i][j].getColor() == Color.white) whites++;
            if (board[i][j].getColor() == Color.black) blacks++;
        }

        return "| B | W | \n"+"| " +blacks +" | "+whites+" |";
    }

    //ektypwnei sygkekrimeno mhnyma gia ton nikhth analoga to apotelesma toy paixnidioy
    public void Winner(){
        int whites=0, blacks=0;
        for (int i=0;i<8;i++) for (int j=0;j<8;j++) {
            if (board[i][j].getColor() == Color.white) whites++;
            if (board[i][j].getColor() == Color.black) blacks++;
        }
        if(blacks>whites){
            System.out.println("Black checkers win!");
        }else if(whites>blacks){
            System.out.println("White checkers win!");
        }else{
            System.out.println("It's a tie!");
        }

    }
}
