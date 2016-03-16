/**
 *The Sudoku Game class, which contains a representation of the board as a
 *2D array.
 *
 *@author Chris Varanese, Nick Fernandes
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

   /**
    *This will create the game board using a preexisting 2D array.
    *
    *@param b The 2D array representation of the board. Should be 9x9 and
    *contain values 1 - 9.
    */
   public SudokuGame(int[][] b){
      board = b;
      changeable = new boolean[9][9];
      for (int i = 0; i < 9; i ++){
         for (int j = 0; j < 9; j ++){
            changeable[i][j] = true;
         }
      }
   }

   /**
    *This will create a gameboard of a certain difficulty.
    *
    *@param diff The difficulty of the game. Should be 0 - 2.
    */
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

   /**
    *Returns the element at row i, column j in the game board.
    *
    *@param i The row of the desired element.
    *
    *@param j The column of the desired element.
    *
    *@return The element at the row i and the column j in the game board.
    */
   public int get(int i, int j){
      return board[i][j];
   }

   /**
    *This will set the element at the row i and the column j in the game board.
    *
    *@param i The row of the element.
    *
    *@param j The column of the element.
    *
    *@param value The value that is going to be inserted.
    */
   public void set(int i, int j, int value){
      board[i][j] = value;
   }

   /**
    *State whether the element at index i, j is changeable.
    *
    *@param i The row of the element.
    *
    *@param j The column of the element.
    *
    *@param change If it can be changed or not.
    */
   public void setChangeable(int i, int j, boolean change){
      changeable[i][j] = change;
   }
   /**
    *Returns the 2D array of the game board.
    *
    *@return The 2D array of the game board.
    */
   public int[][] getBoard(){
      return board;
   }

   /**
    *Returns whether the element at row row, column col can be changed.
    *
    *@param row The row of the desired element.
    *
    *@param col The column of the desired element.
    *
    *@return Whether the element at row row, column col can be changed.
    */
   public boolean getChangeable(int row, int col){
      return changeable[row][col];
   }

   private static boolean checkLines(int[][] b, int row, int col, int val){
      for(int i = 0; i < 9;i++){
         if(b[row][i]==val||b[i][col]==val){
            return false;
         }
      }
      return true;
   }

   private static boolean checkSec(int[][] b, int secRow, int secCol, int val){
      for(int r = 0; r < 3; r++){
         for(int c = 0; c < 3; c++){
            if(b[r+secRow][c+secCol] == val){
               return false;
            }
         }
      }
      return true;
   }
   /**
    *Checks if the value "val" can be placed in the selected spot.
    *
    *@param b The game board.
    *
    *@param row The selected row.
    *
    *@param col The selected col.
    *
    *@param val The intended value.
    *
    *@return Whether the value can be placed.
    */
   public static boolean checkAll(int[][] b, int row, int col, int val){
      int secRow = row - row%3;
      int secCol = col - col%3;
      return checkLines(b, row, col, val) && checkSec(b, secRow, secCol, val);
   }

   /**
   *Checks if the current board is solved.
   */
   public boolean checkBoardSolved(){
      int[] valuesPassed = new int[9];
      for(int i = 0; i < 9; i ++){
         for(int j = 0; j < 9; j++){
            if(board[i][j] == 0){
               return false;
            }
            for(int v = 0; v < j; v++){
               if(board[i][j] == valuesPassed[v]){
                  //System.out.println("cols");
                  return false;
               }
            }
            valuesPassed[j] = board[i][j];
         }
      }
      for(int i = 0; i < 9; i ++){
         for(int j = 0; j < 9; j++){
            if(board[j][i] == 0){
               return false;
            }
            for(int v = 0; v < j; v++){
               if(board[j][i] == valuesPassed[v]){
                  //System.out.println("rows");
                  return false;
               }
            }
            valuesPassed[j] = board[j][i];
         }
      }
      for(int sec = 0; sec < 9; sec ++){
         for (int i = 0; i < 3; i ++){
            for (int j = 0; j < 3; j ++){
               for (int v = 0; v < 3*i + j; v ++){
                  if (board[i + sec%3*3][j + (sec/3)*3] == valuesPassed[v]){
                     System.out.println("secs");
                     return false;
                  }
               }
               valuesPassed[3*i + j] = board[i + sec%3*3][j+(sec/3)*3];
            }
         }
      }
      return true;
   }

   /**
    *Prints the board in a user-friendly format.
    */
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

   /**
    *Generates a random Sudoku Board and returns it.
    *
    *@param difficulty The difficulty of this game. Should be 0 - 2.
    *
    *@return The generated 2D array.
    */
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
            sw1 = (int)(Math.random()*9) + 1;
            do {
               sw2 = (int)(Math.random()*9) + 1;
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
      SudokuGame s = new SudokuGame(newBoard);
      s.printBoard();
      System.out.println("Difficulty: " + difficulty);
      return newBoard;
   }
}
