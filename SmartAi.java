/* I am trying to add a 3d array that will keep track of possible values for the given cell so we can start
   start at cells with the least possible values and get rid of cells with only one value. Using this we can
   apply additional Sudoku tricks to increase the solve speed - 3/10/16 Nick
*/
public class SmartAi{
   private static int[][] b;
   private static boolean[][][] possibleVals;

   public static int[][] solve(int[][] board){
      b = board;
      long start = System.nanoTime();
      findPosVals(board);
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
      if(row == 10 && col == 10){
         return true;
      }
      for(int val = 1; val <=9; val++){
         if(SudokuGame.checkAll(board, row, col, val)){
            board[row][col] = val;
            findPosVals(board);
            if(solveMe(board)){
               return true;
            }
            board[row][col] = 0;
            findPosVals(board);
         }
      }
      return false;
   }

   private static int findUnassignedRow(int[][] board){
      int min =10;
      int temp;
      for(int r = 0; r < 9; r++){
         for(int c = 0; c < 9; c++){
            temp = numOfPosVals(r,c);
            if(temp < min && temp != 0){
               min = r;
            }
         }
      }
      return min;
   }

   private static int findUnassignedCol(int[][] board){
      int min = 10;
      int temp;
      for(int r = 0; r < 9; r++){
         for(int c = 0; c < 9; c++){
            temp = numOfPosVals(r,c);
            if(temp < min && temp !=0){
               min = c;
            }
         }
      }
      return min;
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
      findPosVals(test);
      if(solveMe(test)){
         long end = System.nanoTime();
         SudokuGame.printBoard(test);
         System.out.println("It took " + (end - start)/1000000 + " milliseconds");
      }
   }

   public static void findPosVals(int[][] board){
      possibleVals = new boolean[9][9][9];
      for(int i = 0; i < 9; i++){
         for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
               if(board[r][c] == 0 && SudokuGame.checkAll(board, r, c, i)){
                  possibleVals[r][c][i] = true;
               }
            }
         }
      }
   }

   public static int numOfPosVals(int row, int col){
      int count=0;
      int temp=0;
      for(int i = 0; i < 9; i++){
         if(possibleVals[row][col][i]){
            count++;
            temp = i +1;
         }
      }
      //if(count==1){
        // b[row][col]=temp;
         //findPosVals(b);
         //return 0;
      //}
      return count;
   }
}

