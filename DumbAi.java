public class DumbAi{
   private static int[][] b;

   public static int[][] solve(int[][] board){
      b = board;
      long start = System.nanoTime();
      if(solveMe(b)){
         long end = System.nanoTime();
         System.out.println("It took " + (end - start)/1000000 + " milliseconds");
         return b;
      }
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

   public static void main(String[] args){
      int[][] test = {{3, 0, 6, 5, 0, 8, 4, 0, 0},
                      {5, 2, 0, 0, 0, 0, 0, 0, 0},
                      {0, 8, 7, 0, 0, 0, 0, 3, 1},
                      {0, 0, 3, 0, 1, 0, 0, 8, 0},
                      {9, 0, 0, 8, 6, 3, 0, 0, 5},
                      {0, 5, 0, 0, 9, 0, 6, 0, 0},
                      {1, 3, 0, 0, 0, 0, 2, 5, 0},
                      {0, 0, 0, 0, 0, 0, 0, 7, 4},
                      {0, 0, 5, 2, 0, 6, 3, 0, 0}};
      long start = System.nanoTime();
      if(solveMe(test)){
         long end = System.nanoTime();
         System.out.println((end - start)/1000000 + " milliseconds");
         SudokuGame.printBoard(test);
      }
   }
}
