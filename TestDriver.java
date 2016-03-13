public class TestDriver{
   public static void main(String[] args){
      int[][] board = new int[9][9];
      SudokuGame gem = new SudokuGame(board);
      gem.printBoard();
      System.out.println("starting...");
      board = gem.generateBoard(0);
      gem = new SudokuGame(board);
      System.out.println("finished board...");
      gem.printBoard();
   }
}
