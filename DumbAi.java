/**
 *Dumb Ai takes sudoku board and solves them
 *
 *@author Nick Fernandes
 *@version 1.3
 */
public class DumbAi implements Ai{
   private static int[][] b;

  /**
   *Takes a Sudoku board and returns a solved board and prints how long it took;
   *
   *@param board The intial sudoku board to be solved
   *
   *@return A solved sudoku board
   */
   public  int[][] solve(int[][] board){
      b = board;
      long start = System.currentTimeMillis();
      if(solveMe(b)){
         long end = System.currentTimeMillis();
         System.out.println("It took " + (end - start) + " milliseconds to solve with Dumb Ai.");
         return b;
      }
      long end = System.currentTimeMillis();
      System.out.println("Could not be solved. Time spent: " + (end - start) + " milliseconds.");
      return board;
   }

   private static boolean solveMe(int[][] board){
      int row = findUnassignedRow(board);
      int col = findUnassignedCol(board);
      if(row == -1 && col == -1){
         return true;
      }
      for(int val = 1; val <=9; val++){
         if(SudokuGame.checkAll(board, row, col, val)){
            board[row][col] = val;
            if(solveMe(board)){
               return true;
            }
            board[row][col] = 0;
         }
      }
      return false;
   }

   private static int findUnassignedRow(int[][] board){
      for(int r = 0; r < 9; r++){
         for(int c = 0; c < 9; c++){
            if(board[r][c]==0){
               return r;
            }
         }
      }
      return -1;
   }

   private static int findUnassignedCol(int[][] board){
      for(int r = 0; r < 9; r++){
         for(int c = 0; c < 9; c++){
            if(board[r][c] == 0){
               return c;
            }
         }
      }
      return -1;
   }
}
