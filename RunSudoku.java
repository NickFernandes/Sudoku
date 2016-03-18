import java.io.File;
import java.io.PrintStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
public class RunSudoku{
   public static void main(String[] args){
      int[][] test = new int[9][9];
      int ai=0;
      try{
         if(args[0].equals("DumbAi")){
            ai = 2;
         }
         else if(args[0].equals("SmartAi")){
            ai = 1;
         }
         else if(args[0].equals("HumanDriver")){
            ai = 3;
         }
         else{
            throw new IOException();
         }
         if (ai != 3){
            File f = new File("gameBoard.txt");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            for(int r = 0; r <9; r++){
               for(int c = 0; c < 9; c++){
                  test[r][c] = br.read() - 48;
               }
            }
         }
      }
      catch(FileNotFoundException ie){
         System.out.println("File \"gameBoard.txt\" not found." 
                           + "Generating random board instead.");
         test = SudokuGame.generateBoard(1);
      }
      catch(IOException ie){
         printError();
      }
      catch(ArrayIndexOutOfBoundsException ie){
         printError();
      }
      if (ai == 3){
         HumanDriver driver = new HumanDriver();
         driver.runHumanGame();
      } else {
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
   }

   private static void printError(){
      System.out.println("Error running RunSudoku");
      System.out.println("Please retry running the test:");
      System.out.println("java RunSudoku \"BoardType\"");
      System.out.println("\"BoardType\" can be \"SmartAi\", \"DumbAi\"" +
                        " or \"HumanDriver\".");
      System.out.println("Example: java RunSudoku SmartAi.java");
      System.exit(1);
   }
}
