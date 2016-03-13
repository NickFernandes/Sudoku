/* I am trying to add a 3d array that will keep track of possible values for the given cell so we can start
   start at cells with the least possible values and get rid of cells with only one value. Using this we can
   apply additional Sudoku tricks to increase the solve speed - 3/10/16 Nick
*/
public class SmartAi{
   private static int[][] b;
   private static boolean[][][] findPosVals;

   public static int[][] solve(int[][] board){
      b = board;
      long start = System.nanoTime();
      findPosVals = trackPosVals(b);
      if(solveMe(b, findPosVals, numOfPosVals(findPosVals))){
         long end = System.nanoTime();
         System.out.println("It took " + (end - start)/1000000 + " milliseconds");
         return b;
      }
      return board;
   }

   private static boolean solveMe(int[][] board, boolean[][][] b2, int[][] b3){
      board = solveSingle(board,b2,b3);
      b2 = trackPosVals(board);
      b3 = numOfPosVals(b2);
      int row = findUnassignedRow(board, b3);
      int col = findUnassignedCol(board, b3);
      if(row == 10 && col == 10){
         return true;
      }
      int[] posVals = findPosVals(row, col, b2);
      for(int val: posVals){
         if(SudokuGame.checkAll(board, row, col, val)){
            board[row][col] = val;
            for(int i = 0; i < 9; i++){
               b2[row][i][val-1]=b2[i][col][val-1]=false;
            }
            int secRow = row - row%3;
            int secCol = col - col%3;
            for(int r = 0; r < 3; r++){
               for(int c = 0; c < 3; c++){
                  b2[r + secRow][c + secCol][val-1]=false;
               }
            }
            b3[row][col] = 0;
            SudokuGame.printBoard(board);
            if(solveMe(board, b2, b3)){
               return true;
            }
            board[row][col] = 0;
            for(int i = 0; i < 9; i++){
               b2[row][i][val-1]=b2[i][col][val-1]=true;
            }
            for(int r = 0; r < 3; r++){
               for(int c = 0; c < 3; c++){
                  b2[r + secRow][c + secCol][val-1] = true;
               }
            }
            b3 = numOfPosVals(b2);
         }
      }
      return false;
   }

   private static int findUnassignedRow(int[][] board, int[][] b2){
      int min =10;
      int minrow =10;
      int temp=0;
      for(int r = 0; r < 9; r++){
         for(int c = 0; c < 9; c++){
            temp = b2[r][c];
            if(temp < min && temp != 0){
               min = temp;
               minrow = r;
            }
         }
      }
      return minrow;
   }

   private static int findUnassignedCol(int[][] board, int[][] b2){
      int min = 10;
      int mincol = 10;
      int temp=0;
      for(int r = 0; r < 9; r++){
         for(int c = 0; c < 9; c++){
            temp = b2[r][c];
            if(temp < min && temp !=0){
               mincol = c;
               min = temp;
            }
         }
      }
      return mincol;
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
      SudokuGame.printBoard(test);
      System.out.println("");
      long start = System.nanoTime();
      findPosVals = trackPosVals(test);
      if(solveMe(test, findPosVals, numOfPosVals(findPosVals))){
         long end = System.nanoTime();
         SudokuGame.printBoard(test);
         System.out.println("It took " + (end - start)/1000000 + " milliseconds");
      }
      System.out.println("Fail");
   }

   public static boolean[][][] trackPosVals(int[][] board){
      findPosVals = new boolean[9][9][9];
      for(int r = 0; r < 9; r++){
         for(int c = 0; c < 9; c++){
            for(int i = 0; i < 9; i++){
               if(board[r][c] == 0 && SudokuGame.checkAll(board, r, c, i+1)){
                  findPosVals[r][c][i] = true;
               }
            }
         }
      }
      return findPosVals;
   }

   public static int[] findPosVals(int row, int col, boolean[][][] b2){
      int count=0;
      for(int i = 0; i < 9; i++){
         if(b2[row][col][i]){
            count++;
         }
      }
      int[] posVals = new int[count];
      count = 0;
      for(int i = 0; i < 9; i++){
         if(b2[row][col][i]){
            posVals[count]=i+1;
            count++;
         }
      }
      return posVals;
   }

   public static int[][] numOfPosVals(boolean[][][] b2){
      int count = 0;
      int[][] numVals = new int[9][9];
      for(int r =0; r < 9; r++){
         for(int c = 0; c < 9; c++){
            for(int i = 0; i < 9; i++){
               if(b2[r][c][i]){
                  count++;
               }
            }
            numVals[r][c] = count;
            count = 0;
         }
      }
      return numVals;
   }

   public static int[][] solveSingle(int[][] board, boolean[][][] b2, int[][] b3){
      int val = 0;
      int[] temp;
      for(int r = 0; r < 9; r++){
         for(int c = 0; c < 9; c++){
            temp = findPosVals(r, c, b2);
            if(temp.length == 1){
               val = temp[0];
               board[r][c] = val;
               for(int i = 0; i < 9; i++){
                  b2[r][i][val-1]=b2[i][c][val-1]=false;
               }
               int secRow = r - r%3;
               int secCol = c - c%3;
               for(int r1 = 0; r1 < 3; r1++){
                  for(int c1 = 0; c1 < 3; c1++){
                     b2[r1 + secRow][c1 + secCol][val-1]=false;
                  }
               }
               b3[r][c] = 0;
               r = 0;
               c = 0;
            }
         }
      }
      return board;
   }
}
