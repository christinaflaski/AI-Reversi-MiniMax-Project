import java.util.ArrayList;
import java.util.Scanner;

public class Reversi {
    public static Board TheBoard;

    public static int current_color = Color.black;
    public static void main(String args[]){
        Reversi_Othello();
    }

    public static void Reversi_Othello(){
        Scanner sc = new Scanner(System.in);
        MiniMax human, computer;

        System.out.print("Press 1 if you want to play first\nelse press 2 to play second\n");
        String turn = sc.nextLine();
        while(!turn.equals("1")&&!turn.equals("2")){
            System.out.println("Error! Please enter 1 or 2 to choose if you play 1st or 2nd: ");
            turn=sc.nextLine();
        }

        int depth = Depth();

        TheBoard = new Board();
        TheBoard.printBoard();

        //analoga poios paizei prwtos anathetoyme to antistoixo xrwma
        if (turn.equals("1"))
        {
            human = new MiniMax(depth,Color.black);
            computer = new MiniMax(depth,Color.white);
        }
        else
        {
            human = new MiniMax(depth,Color.white);
            computer = new MiniMax(depth,Color.black);
        }

        while(TheBoard.isTerminal()==false)
        {
            if (human.isMyTurn(current_color))
            {
                System.out.println("It's your turn to play!");
                //ftiaxnoyme arraylist me tis diathesimes kinhshs poy mporei na kanei
                ArrayList<Move> myMoves = TheBoard.avaliable_moves(current_color);

                if (myMoves.isEmpty())
                {
                    System.out.println("No available moves!");
                }
                else
                {
                    int m=-1;
                    while (m<0) {
                        System.out.print("Give the x for your move:\n");
                        int x=sc.nextInt();
                        System.out.println("Give the y for your move: ");
                        int y=sc.nextInt();
                        for (int i = 0; i < myMoves.size(); i++) {
                            int movesx=myMoves.get(i).getX() + 1;
                            int movesy=myMoves.get(i).getY() + 1;
                            if (x==movesx && y==movesy)
                            {
                                m = i;
                                System.out.println("Your move is correct!");
                            }
                        }
                        if(m==-1) {
                            System.out.println("Your move is incorrect, give another one");
                        }
                    }
                    TheBoard.makeMove(TheBoard, myMoves.get(m), current_color);
                }
            }
            else
            {
                System.out.println("It's computer's turn to play!");
                System.out.println("Loading...");
                TheBoard = computer.MiniMax(TheBoard);
            }
            TheBoard.printBoard();
            System.out.println("SCOREBOARD \n" + TheBoard.ScoreBoard());

            //allazoyme to xrwma, allazei kai to poios paizei
            if(current_color==1){
                current_color=2;
            }else{
                current_color=1;
            }
        }
        TheBoard.Winner();
    }

    //methodos poy zhtaei apo ton xrhsth na dwsei to bathos toy minimax
    public static int Depth()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the depth of MiniMax: \n");
        return scanner.nextInt();
    }

}
