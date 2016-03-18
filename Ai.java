/**
 *Interface for the AI classes.
 *
 *@author Nick Fernandes
 *@version Program 07
 */
public interface Ai{
   /**
    *Solves the given game board.
    *
    *@param board The given game board as a 2D array.
    *
    *@return A solved board as a 2D array.
    */
   public int[][] solve(int[][] board);
}
