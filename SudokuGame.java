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

}
