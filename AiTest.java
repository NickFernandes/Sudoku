import java.io.File;
import java.io.PrintStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
public class AiTest{
   public static void main(String[] args){
      File f = new File("gameBoard.txt");
      FileReader fr = new FileReader(f);
      BufferedReader br = new BufferedReader(fr);
      int[][] test = new int[9][9];
      try{
         for(int r = 0; r <9; r++){
            for(int c = 0; c < 9; c++){
               test[r][c] = br.read();
            }
         }
      }
      catch(IOException ie){
         x
      SudokuGame u = new SudokuGame(test);
      u.printBoard();
      test = SmartAi.solve(test);
      SudokuGame s = new SudokuGame(test);
      s.printBoard();
   }
}
