/**
 *Smart Ai to solve sudoku puzzles
 *
 *@author Nick Fernandes
 *@version 2.1
 */
import java.util.Arrays;
public class SmartAi implements Ai{

   private final static int[] allowedBitFields = new int[] {
      0,       //000000000
      1,       //000000001
      1 << 1,  //000000010
      1 << 2,  //000000100
      1 << 3,  //000001000
      1 << 4,  //000010000
      1 << 5,  //000100000
      1 << 6,  //001000000
      1 << 7,  //010000000
      1 << 8,  //100000000
   };

   private final static int allAllowed = 511; //111111111

   private static int[][] b;

  /**
   *Takes in a sudoku game and returns the solved board
   *
   *@param board the intial sudoku board state
   *
   *@return the solved sudoku board
   */
   public final int[][] solve(final int[][] board){
      b = board;
      final long start = System.currentTimeMillis();
      if(solveMe(b) == 81){
         final long end = System.currentTimeMillis();
         System.out.println("It took " + (end - start) + " milliseconds to solve with Smart Ai.");
         return b;
      }
      final long end = System.currentTimeMillis();
      System.out.println("Could not be solved. Time Spent: " + (end - start) + " milliseconds.");
      return board;
   }

   private final static int solveMe(final int[][] board){
      final int[][] allowedValues = new int[9][9];
      int placedNumberCount = 0;
      for(int[] allowedValuesRow : allowedValues){
         Arrays.fill(allowedValuesRow, allAllowed);
      }
      for(int r = 0; r < 9; r++){
         for(int c = 0; c < 9; c++){
            if(board[r][c] > 0){
               allowedValues[r][c] = 0;
               applyAllowedValuesMask(board, allowedValues, r,c);
               placedNumberCount++;
            }
         }
      }
      return solveBoard(board, allowedValues, placedNumberCount);
   }

   private final static int solveBoard(final int[][] board, final int[][]
           allowedValues, int placedNumberCount){
      int lastPlacedNumbersCount = 0;
      while(placedNumberCount - lastPlacedNumbersCount > 3 &&
            placedNumberCount < 68 &&
            placedNumberCount > 10){
         lastPlacedNumbersCount = placedNumberCount;
         placedNumberCount += solveSingle(board, allowedValues);
         if(placedNumberCount < 35){
            applyNakedPairs(allowedValues);
         }
      }
      if(placedNumberCount < 81){
         final int[][] recursiveBoard = attemptRecursion(board, allowedValues, placedNumberCount);
         if(recursiveBoard != null){
            placedNumberCount = 0;
            for(int r =0; r < 9; r++){
               for(int c = 0; c < 9; c++){
                  board[r][c] = recursiveBoard[r][c];
                  if(recursiveBoard[r][c] > 0){
                     placedNumberCount++;
                  }
               }
            }
         }
      }
      return placedNumberCount;
   }

   private final static int[][] attemptRecursion(final int[][] board, final
           int[][] allowedValues, final int placedNumberCount){
      for(int r =0;r < 9; r++){
         final int[] allowedValuesRow = allowedValues[r];
         final int[] boardRow = board[r];
         for(int c = 0; c < 9; c++){
            if(boardRow[c] == 0){
               for(int val = 1; val <= 9;val++){
                  if((allowedValuesRow[c] & allowedBitFields[val]) > 0){
                     final int[][] testBoard = copyBoard(board);
                     final int[][] testAllowedValues = copyBoard(allowedValues);
                     setValue(testBoard, testAllowedValues, val, r, c);
                     final int placedNumbers = solveBoard(testBoard,
                     testAllowedValues, placedNumberCount + 1);
                     if(placedNumbers == 81){
                        return testBoard;
                     }
                  }
               }
            return null;
            }
         }
      }
      return null;
   }

   private final static int solveSingle(final int[][] board, final int[][] allowedValues){
      int moveCount = 0;
      for(int r = 0; r < 9; r++){
         final int[] allowedValuesRow = allowedValues[r];
         for(int c = 0; c < 9; c++){
            final int currentAllowedValues = allowedValuesRow[c];
            if(countSetBits(currentAllowedValues) == 1){
               setValue(board, allowedValues,
               getLastSetBitIndex(currentAllowedValues), r , c);
               moveCount++;
            }
         }
      }
      return moveCount;
   }

   private final static void applyNakedPairs(final int[][] allowedValues){
      for(int r = 0; r<9;r++){
         for(int c = 0; c < 9; c++){
            final int val = allowedValues[r][c];
            if(countSetBits(val)==2){
               for(int scanC = c+1; scanC < 9; scanC++){
                  if(allowedValues[r][scanC] == val){
                     final int removeMask = ~val;
                     for(int applyC =0; applyC < 9; applyC++){
                        if(applyC != c && applyC != scanC){
                           allowedValues[r][applyC] &= removeMask;
                        }
                     }
                  }
               }
            }
         }
      }
      for(int r =0; r < 9; r++){
         for(int c = 0; c < 9; c++){
            final int val = allowedValues[r][c];
            if(countSetBits(val) == 2){
               for(int scanR = r+1 ; scanR < 9 ;scanR++){
                  if(allowedValues[scanR][c] == val){
                     final int removeMask = ~val;
                     for(int applyR = 0;applyR < 9;applyR++){
                        if(applyR != r && applyR != scanR){
                           allowedValues[applyR][c] &= removeMask;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private final static void setValue(final int[][] board, final int[][]
           allowedValues, final int value, final int r, final int c){
      board[r][c] = value;
      allowedValues[r][c] = 0;
      applyAllowedValuesMask(board, allowedValues, r, c);
   }

   private final static int getLastSetBitIndex(int value){
      int bitIndex =0;
      while(value > 0){
         bitIndex++;
         value >>=1;
      }
      return bitIndex;
   }

   private final static void applyAllowedValuesMask(final int[][] board, final
           int[][] allowedValues, final int r, final int c){
      final int mask = ~allowedBitFields[board[r][c]];
      for(int i = 0; i < 9; i++){
         allowedValues[i][c] &= mask;
      }
      final int[] allowedValuesRow = allowedValues[r];
      for(int i = 0; i < 9; i++){
         allowedValuesRow[i] &= mask;
      }
      final int secRow = r - r%3;
      final int secCol = c - c%3;
      for(int i = 0; i < 3; i++){
         for(int j =0; j < 3; j++){
            allowedValues[secRow + i][secCol + j] &= mask;
         }
      }
   }

   private final static int countSetBits(int value){
      int count = 0;
      while(value>0){
         value = value & (value - 1);
         count++;
      }
      return count;
   }

   private final static int[][] copyBoard(final int[][] matrix){
      return new int[][]{
         Arrays.copyOf(matrix[0], 9),
         Arrays.copyOf(matrix[1], 9),
         Arrays.copyOf(matrix[2], 9),
         Arrays.copyOf(matrix[3], 9),
         Arrays.copyOf(matrix[4], 9),
         Arrays.copyOf(matrix[5], 9),
         Arrays.copyOf(matrix[6], 9),
         Arrays.copyOf(matrix[7], 9),
         Arrays.copyOf(matrix[8], 9),
      };
   }
}
