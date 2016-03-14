/**
 *@author Chris Varanese
 *@version Program 7
 */
public class SudokuGame {
   private int[][] board;

   public SudokuGame(int[][] b){
      board = b;
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

   public static void printBoard(int[][] b){
      for(int r = 0; r < 9; r++){
         for(int c = 0; c < 9; c++){
            System.out.print(b[r][c]);
         }
         System.out.println();
      }
      System.out.println();
   }
}
