import java.io.File;
import java.io.PrintStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
public class AiTest{
   public static void main(String[] args){
      int[][] test = new int[9][9];
      int ai=0;
      try{
         File f = new File(args[1]);
         FileReader fr = new FileReader(f);
         BufferedReader br = new BufferedReader(fr);
         for(int r = 0; r <9; r++){
            for(int c = 0; c < 9; c++){
               test[r][c] = br.read() - 48;
            }
         }
         if(args[0].toLowerCase().charAt(0)=='s'){
            ai = 1;
         }
         else if(args[0].toLowerCase().charAt(0)=='d'){
            ai = 2;
         }
         else{
            throw new IOException();
         }
      }
      catch(FileNotFoundException ie){
         printError();
      }
      catch(IOException ie){
         printError();
      }
      catch(ArrayIndexOutOfBoundsException ie){
         printError();
      }
      SudokuGame u = new SudokuGame(test);
      u.printBoard();
      SmartAi SA = new SmartAi();
      DumbAi DA = new DumbAi();
      if(ai == 1){
         test = SA.solve(test);
      }
      if(ai == 2){
         test = DA.solve(test);
      }
      SudokuGame s = new SudokuGame(test);
      s.printBoard();
   }

   private static void printError(){
      System.out.println("Error running AiTest");
      System.out.println("Please retry running the test:");
      System.out.println("java AiTest \"AiType\" \"fileName\"");
      System.out.println("Example: java AiTest SmartAi.java gameBoard.txt");
      System.exit(1);
   }
}
