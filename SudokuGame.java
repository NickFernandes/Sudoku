/**
 *@author Chris Varanese
 *@version Program 7
 */
public class SudokuGame {
   private int[][] board;
   private static final int[][] BEGINBOARD = {
                                 {1,2,3,4,5,6,7,8,9},
                                 {4,5,6,7,8,9,1,2,3},
                                 {7,8,9,1,2,3,4,5,6},
                                 {2,3,4,5,6,7,8,9,1},
                                 {5,6,7,8,9,1,2,3,4},
                                 {8,9,1,2,3,4,5,6,7},
                                 {3,4,5,6,7,8,9,1,2},
                                 {6,7,8,9,1,2,3,4,5},
                                 {9,1,2,3,4,5,6,7,8}};
   private boolean[][] changeable;

   public SudokuGame(int[][] b){
      board = b;
      changeable = new boolean[9][9];
      for (int i = 0; i < 9; i ++){
         for (int j = 0; j < 9; j ++){
            changeable[i][j] = true;
         }
      }
   }

   public SudokuGame(int diff){
      changeable = new boolean[9][9];
      board = generateBoard(diff);
      for(int i = 0; i < 9; i ++){
         for (int j = 0; j < 9 ; j ++){
            //System.out.print(board[i][j]);
            if (board[i][j] != 0){
               changeable[i][j] = false;
            }
            else {
               changeable[i][j] = true;
            }
         }
      }

   }

   public int get(int i, int j){
      return board[i][j];
   }

   public void set(int i, int j, int value){
      board[i][j] = value;
   }

   public int[][] getBoard(){
      return board;
   }

   public String toString(){
      return board.toString();
   }

   public boolean getChangeable(int row, int col){
      return changeable[row][col];
   }

   public static boolean checkLines(int[][] b, int row, int col, int val){
      for(int i = 0; i < 9;i++){
         if(b[row][i]==val||b[i][col]==val){
            return false;
         }
      }
      return true;
   }

   public static boolean checkSec(int[][] b, int secRow, int secCol, int val){
      for(int r = 0; r < 3; r++){
         for(int c = 0; c < 3; c++){
            if(b[r+secRow][c+secCol] == val){
               return false;
            }
         }
      }
      return true;
   }

   public static boolean checkAll(int[][] b, int row, int col, int val){
      int secRow = row - row%3;
      int secCol = col - col%3;
      return checkLines(b, row, col, val) && checkSec(b, secRow, secCol, val);
   }

   public void printBoard(){
      System.out.println("    0 1 2   3 4 5   6 7 8  ");
      for(int r = 0; r < 9; r++){
         if (r%3 == 0){
            System.out.println("  + - - - + - - - + - - - +");
         }
         System.out.print(r + " ");
         //System.out.print("|");
         for(int c = 0; c < 9; c++){
            if (c%3 == 0){
               System.out.print("| ");
            }
            System.out.print(board[c][r] + " ");
         }
         System.out.print("|");
         //System.out.print("|");
         System.out.println();
      }
      System.out.println("  + - - - + - - - + - - - +");
   }

   public static int[][] generateBoard(int difficulty){
      double chance;
      if (difficulty == 0){
         chance = .4;
      } else if (difficulty == 1){
         chance = .55;
      } else if (difficulty == 2){
         chance = .7;
      } else {
         throw new IllegalArgumentException();
      }
      int[][] newBoard = BEGINBOARD;
      //System.out.println("Starting loop...");
      for(int q = 0; q < 100; q++){
         //System.out.println("Started loop...");
         int r = (int)(Math.random()*5);
         int sw1;
         int sw2;
         if (r == 0){
            sw1 = (int)(Math.random()*9);
            do {
               sw2 = (int)(Math.random()*9);
            } while (sw2 == sw1);
            for (int i = 0; i < 9; i ++){
               for (int j = 0; j < 9; j++){
                  if (newBoard[i][j] == sw1){
                     newBoard[i][j] = sw2;
                  } else if (newBoard[i][j] == sw2){
                     newBoard[i][j] = sw1;
                  }
               }
            }
         }
         if (r == 1){
            int temp = 0;
            sw1 = (int)(Math.random()*3);
            do {
               sw2 =(int)(Math.random()*3);
            } while (sw2 == sw1);
            int swCol = (int)(Math.random()*3);
            switch (swCol){
               case 0: break;
               case 1:
                  sw1 = sw1 + 3;
                  sw2 = sw2 + 3;
                  break;
               case 2:
                  sw1 = sw1 + 6;
                  sw2 = sw2 + 6;
                  break;
            }
            for(int i = 0; i < 9; i++){
               temp = newBoard[i][sw1];
               newBoard[i][sw1] = newBoard[i][sw2];
               newBoard[i][sw2] = temp;
            }
         }
         if (r == 2){
            //System.out.println("r = 2");
            sw1 = 3 * (int)(Math.random()*3);
            do {
               sw2 = 3 * (int)(Math.random()*3);
            } while (sw2 == sw1);
            int temp = 0;
            for(int i = 0; i < 9; i ++){
               for(int j = 0; j < 3; j++){
                  temp = newBoard[i][sw1 + j];
                  newBoard[i][sw1 + j] = newBoard[i][sw2 + j];
                  newBoard[i][sw2 + j] = temp;
               }
            }
            //System.out.println("Finished r = 2");
         }
         if (r == 3){
            int temp = 0;
            int[][] newerBoard = new int[9][9];
            for(int i = 0; i < 9; i++){
               for(int j = 0; j < 9; j++){
                  newerBoard[i][j] = newBoard[8 - j][i];
               }
            }
            newBoard = newerBoard;
         }
      //System.out.println("Finished iteration: " + q);
      }
      //System.out.println("starting dig....");
      for (int i = 0; i < 9; i++){
         for (int j = 0; j < 9; j++){
            if (Math.random() < chance){
               newBoard[i][j] = 0;
            }
         }
      }
      //SudokuGame s = new SudokuGame(newBoard);
      //s.printBoard();
      return newBoard;
   }
}
