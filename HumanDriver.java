/**
 *The driver for a human who wishes to play Sudoku.
 *
 *@author Chris Varanese
 *@version Program 7
 */
import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
public class HumanDriver{
   /**
    *Plays the game.
    */
   public static void main(String[] args){
      Scanner sc = new Scanner(System.in);
      System.out.println("Would you like to read from a file?(y/n)");
      String read = sc.nextLine().toLowerCase();
      while(!(read.equals("y") || read.equals("n"))){
         System.out.println("Please enter \"y\" or \"n\"");
      }
      SudokuGame game = null;
      File file;
      while (read.equals("y")){
         //file
         System.out.println("Enter the name of the file:");
         String fileName = sc.nextLine();
         file = new File(fileName);
         if (file.canRead()){
            int[][] gameNums = new int[9][9];
            boolean[][] gameChange = new boolean[9][9];
            try{
               FileReader reader = new FileReader(file);
               BufferedReader buff = new BufferedReader(reader);
               for(int i = 0; i < 9; i ++){
                  for (int j = 0; j < 9; j++){
                     //if (buff.read() >= 0){
                        gameNums[i][j] = buff.read() - 48;
                        //System.out.println(gameNums[i][j]);
                     //}
                  }
               }
               for(int i = 0; i < 9; i ++){
                  for (int j = 0; j < 9; j ++){
                     int boo = buff.read() - 48;
                     if (boo == 0){
                        gameChange[i][j] = false;
                     } else{
                        gameChange[i][j] = true;
                     }
                  }
               }
            } catch (FileNotFoundException ex){
               System.out.println("Could not open file");
            } catch (IOException ex){
               System.out.println("Error reading file");
            }
            game = new SudokuGame(gameNums);
            for (int i = 0; i < 9; i ++){
               for (int j = 0; j < 9; j ++){
                  game.setChangeable(i, j, gameChange[i][j]);
               }
            }
            break;
            //read file
            //break
         }
         else {
            System.out.println("Cannot read file. Try again? (y/n)");
            read = sc.next();
            if (!(read.equals("y") || read.equals("n"))){
               read = "n";
            }
         }
      }
      if (read.equals("n")){
         System.out.println("What would you like the difficulty to be?\n" +
                     "Enter \"0\", \"1\", or \"2\", with 0 being the easiest.");
         int diff = (int)(sc.next().charAt(0) - 48);
         while (!(diff == 1 || diff == 0 || diff == 2)){
            System.out.println("Between 0 and 2, please:");
            diff = (int)(sc.next().charAt(0) - 48);
         }
         game = new SudokuGame(diff);
      }
      //game.printBoard();
      boolean solved = false;
      while (!solved){
         game.printBoard();
         System.out.println("Enter \"q\" if you would like to quit or \"n\" if not. Changes" +
                     " will be saved to a file called \"gameBoard.txt\"");
         String quit = sc.next();
         System.out.println(quit);
         if(quit.equals("q")){
            File write = new File("gameBoard.txt");
            if (write.exists()){
               write.delete();
            }
            try{
               write.createNewFile();
            } catch(IOException ex){
               System.out.println("Cannot create new file.");
            }
            PrintStream strim;
            try{
               strim = new PrintStream(write);
            } catch (FileNotFoundException ex){
               strim = null;
               System.out.println("Could not create print stream");
            }
            if (write.canWrite()){
               for (int i = 0; i < 9; i ++){
                  for (int j = 0; j < 9; j ++){
                     if (strim != null){
                        strim.print(game.get(i,j));
                     }
                  }
               }
               for (int i = 0; i < 9; i ++){
                  for (int j = 0; j < 9; j ++){
                     if (strim != null){
                        if (game.getChangeable(i,j)){
                           strim.print(1);
                        } else{
                           strim.print(0);
                        }
                     }
                  }
               }
            }
            break;
            //write file
            //break
         }
         System.out.println("Enter the row of the element you wish to change");
         int row = (int)(sc.next().charAt(0) - 48);
         while (row < 0 || row > 8){
            System.out.println("Between 0 and 8, please:");
            row = (int)(sc.next().charAt(0) - 48);
         }
         System.out.println("Enter the column of the element you wish to change");
         int column = (int)(sc.next().charAt(0) - 48);
         while (column < 0 || column > 8){
            System.out.println("Between 0 and 8, please:");
            column = (int)(sc.next().charAt(0) - 48);
         }
         while (!game.getChangeable(column, row)){
            System.out.println("That entry is not changeable. Please Enter new values.");
            System.out.println("Row:");
            row = (int)(sc.next().charAt(0) - 48);
            while (row < 0 || row > 8){
               System.out.println("Between 0 and 8, please:");
               row = (int)(sc.next().charAt(0) - 48);
            }
            System.out.println("Column:");
            column = (int)(sc.next().charAt(0) - 48);
            while (column < 0 || column > 8){
               System.out.println("Between 0 and 8, please:");
               column = (int)(sc.next().charAt(0) - 48);
            }
         }
         System.out.println("Enter the number you wish to enter (1 - 9)");
         int replace = (int)(sc.next().charAt(0) - 48);
         while (column < 0 || column > 8){
            System.out.println("Between 0 and 8, please:");
            column = (int)(sc.next().charAt(0) - 48);
         }
         game.set(column, row, replace);
         boolean finished = true;
         for (int i = 0; i < 9; i ++){
            for (int j = 0; j < 9; j ++){
               if (game.get(i,j) == 0){
                  finished = false;
                  break;
               }
            }
            if (!finished){
               break;
            }
         }
         if (finished){
            System.out.println("Your board is filled!");
            solved = game.checkBoardSolved();//check it
            if (!solved){
               System.out.println("But, it's not solved yet!");
            }
         }
      }
      if (solved){
         System.out.println("Congratulations! You did it!");
      }
   }
}
