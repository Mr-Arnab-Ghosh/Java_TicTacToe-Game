import java.util.*;
class TicTacToe {
    static char board[][];
    public TicTacToe() {
        board=new char[3][3];
        initBoard();
    }

    void initBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j]=' ';
            }
        }
    }

    static void displayBoard() {
        System.out.println(" -------------------");
        for (int i = 0; i < 3; i++) {
            System.out.print(" |  ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]+"  |  ");
            }
            System.out.println("\n -------------------");
        }
    }

    static void putMark(int row,int column,char mark) {
        board[row][column]=mark;
    }
    
    static boolean checkWinConditions() {
        for(int i=0; i < 3; i++) {
            if(board[i][0]!=' ' && board[i][0]==board[i][1] && board[i][1]==board[i][2]) {
                return true;
            }
        }
        for(int j=0; j < 3; j++) {
            if(board[0][j]!=' ' && board[0][j]==board[1][j] && board[1][j]==board[2][j]) {
                return true;
            }
        }
        if(board[0][0]!=' ' && board[0][0]==board[1][1] && board[1][1]==board[2][2] || board[0][2]!=' ' && board[0][2]==board[1][1] && board[1][1]==board[2][0]) {
            return true;
        }
        else {
            return false;
        }
    }

    static void emptyBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j]=' ';
            }
        }
    }
}

abstract class Player {
    String name;
    char mark;

    abstract void makeMove();

    boolean checkValidMark(int row,int column) {
        if(row>=0 && row<=2 && column>=0 && column<=2) {
            if(TicTacToe.board[row][column]==' ') {
                return true;
            }
        }
        return false;
    }
}

class HumanPlayer extends Player {
    HumanPlayer(String name,char mark) {
        this.name=name;
        this.mark=mark;
    }
    @Override void makeMove() {
        Scanner sc = new Scanner(System.in);
        int row;
        int column;
        while(true) {
            System.out.println("Enter the position (row and column number from 0 to 2): ");
            row = sc.nextInt();
            column = sc.nextInt();
            if(checkValidMark(row, column)) {
                break;
            }
            else {
                System.out.println("Invalid move! Try again.");
            }
        }

        TicTacToe.putMark(row,column,mark);
    }
}

class AIPlayer extends Player {
    AIPlayer(String name,char mark) {
        this.name=name;
        this.mark=mark;
    }
    @Override void makeMove() {
        Random rd = new Random();
        int row;
        int column;
        do {
            row = rd.nextInt(3);
            column = rd.nextInt(3);
        } while(!checkValidMark(row, column));

        TicTacToe.putMark(row,column,mark);
    }
}

public class LaunGa {
    public static void main(String[] args) {
        TicTacToe ttt=new TicTacToe();
        Scanner sc=new Scanner(System.in);
        int ch;
        do {
            System.out.println(" Press 1 to play with Computer or Press 2 to play with Another Player or Press 0 to Exit: ");
            ch=sc.nextInt();
            sc.nextLine();
            switch(ch) {
                default: System.out.println(" Wrong Choice! Try Again.");
                break;
                case 1: System.out.println("Please enter your favourite Mark ('X' or 'O'): ");
                        char pmark=sc.next().charAt(0);
                        pmark=Character.toUpperCase(pmark);
                        sc.nextLine();
                        char cmark;
                        cmark=(pmark=='X')? 'O':'X';

                        HumanPlayer hp=new HumanPlayer("You", pmark);
                        AIPlayer ap=new AIPlayer("Computer",cmark);

                        Player cp;

                        cp=(pmark=='X')? hp:ap;

                        int count=0;
                        while(true) {
                            if(cp==ap) {
                                System.out.println(cp.name+"'s Turn: ");
                            }
                            else {
                                System.out.println(cp.name+"r's Turn: ");
                            }
                            cp.makeMove();
                            count++;
                            TicTacToe.displayBoard();
                            if(TicTacToe.checkWinConditions()) {
                                if(cp==ap) {
                                    System.out.println("You lost! Better luck next time.");
                                    TicTacToe.emptyBoard();
                                    break;
                                }
                                else {
                                    System.out.println(cp.name+" won! Congratulations!");
                                    TicTacToe.emptyBoard();
                                    break;
                                }
                            }
                            if(count==9) {
                                System.out.println("It is a draw");
                                TicTacToe.emptyBoard();
                                break;
                            }
                            else {
                                cp=(cp==hp)? ap:hp;
                            }
                        }

                break;
                case 2: System.out.println(" Enter the Name of First Player: ");
                        String fpName=sc.nextLine();
                        System.out.println(fpName+" please enter your favourite Mark ('X' or 'O'): ");
                        char fpmark=sc.next().charAt(0);
                        fpmark=Character.toUpperCase(fpmark);
                        sc.nextLine();
                        System.out.println(" Enter the Name of Second Player: ");
                        String spName=sc.nextLine();
                        char spmark;
                        if(fpmark=='X') {
                            spmark='O';
                            System.out.println(spName+" your playing Mark is O as "+fpName+" choose X");
                        }
                        else {
                            spmark='X';
                            System.out.println(spName+" your playing Mark is X as "+fpName+" choose O");
                            String tname=fpName;
                            fpName=spName;
                            spName=tname;
                            char tmark=fpmark;
                            fpmark=spmark;
                            spmark=tmark;
                        }

                        HumanPlayer p1=new HumanPlayer(fpName, fpmark);
                        HumanPlayer p2=new HumanPlayer(spName, spmark);

                        int turn=1;
                        while(!TicTacToe.checkWinConditions()) {
                            if(turn==10) {
                                break;
                            }
                            else if(turn%2 != 0) {
                                System.out.println(p1.name+" your's Turn: ");
                                p1.makeMove();
                                turn++;
                                TicTacToe.displayBoard();
                            }
                            else {
                                System.out.println(p2.name+" your's Turn: ");
                                p2.makeMove();
                                turn++;
                                TicTacToe.displayBoard();
                            }
                        }
                        if(turn==10) {
                            System.out.println(" It's a Draw!");
                            TicTacToe.emptyBoard();
                        }
                        else if(turn%2 == 0) {
                            System.out.println(p1.name+ " won! Congratulations!");
                            TicTacToe.emptyBoard();
                        }
                        else {
                            System.out.println(p2.name+ " won! Congratulations!");
                            TicTacToe.emptyBoard();
                        }
                break;
                case 0: break;
            }
        } while(ch!=0);
        sc.close();
    }
}